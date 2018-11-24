package com.rousing.oen.rousing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.http.RequestQueue;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rousing.database.DBManager;
import com.rousing.mcu.connector.McuConnector;
import com.rousing.models.RousingLight;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class LandingActivity extends AppCompatActivity {

    ListView deviceListView;
    RousingDeviceAdapter adapter;
    ProgressDialog progress;
    ArrayList<RousingLight> savedDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        deviceListView = (ListView)findViewById(R.id.landingDeviceList);
        adapter = new RousingDeviceAdapter(this, new ArrayList<RousingLight>());
        deviceListView.setAdapter(adapter);
        Button addNewDeviceButton = (Button) findViewById(R.id.addNewDeviceButton);
        addNewDeviceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, LightSearchActivity.class);
                startActivity(intent);
            }
        });
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> _adapter, View v, int position, long tag){
                RousingLight item = (RousingLight) _adapter.getItemAtPosition(position);
                if(item.isActive){
                    Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                    intent.putExtra("DEVICE_TYPE", "STRIP");
                    intent.putExtra("DEVICE_IP",item.deviceIP);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LandingActivity.this);
                    builder.setTitle("Light not responding.");
                    builder.setMessage("Please wait while we try to connect...");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadLightsFromDB();
    }

    void loadLightsFromDB() {
        progress = new ProgressDialog(this);
        progress.setTitle("Searching");
        progress.setMessage("Wait while we refresh saved lights...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        savedDevices = DBManager.GetSharedDBManager().GetAllLights();
        adapter.clear();
        adapter.addAll(savedDevices);
        adapter.notifyDataSetChanged();
        progress.dismiss();
        if(savedDevices.isEmpty()){
            //Alert that no devices found, show option to rescan
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Context ctx = this;
            builder.setTitle("No saved devices found");
            builder.setMessage("Search for new devices?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(LandingActivity.this, LightSearchActivity.class);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else {
            discoverActiveRousingDevices();
        }
    }

    private void discoverActiveRousingDevices() {
        LandingActivity.UPnPDiscovery discover = new LandingActivity.UPnPDiscovery(LandingActivity.this);
        discover.execute();
    }

    int pendingRequests = 0;
    private void deviceDiscoveryComplete(HashSet<String> addresses) {
        if(addresses != null && addresses.isEmpty()) {
            showDevicesInList(new ArrayList<RousingLight>());
        }
        else {
            final ArrayList<RousingLight> foundDevices = new ArrayList<>();
            //Get XML description of all found devices
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
            pendingRequests = addresses.toArray().length;
            for (final String xmlDescUrl:addresses
                    ) {
                StringRequest req = new StringRequest(Request.Method.GET, xmlDescUrl,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try
                                {
                                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                    Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
                                    Element element=doc.getDocumentElement();
                                    element.normalize();
                                    NodeList nList = doc.getElementsByTagName("device");
                                    for (int i=0; i<nList.getLength(); i++) {
                                        Node node = nList.item(i);
                                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                                            Element element2 = (Element) node;
                                            RousingLight device = new RousingLight();
                                            device.deviceType = getValue("deviceType",element2);
                                            device.friendlyName = getValue("friendlyName",element2);
                                            device.deviceID = getValue("UDN",element2).replace("uuid:","");
                                            device.modelName= getValue("modelName",element2);
                                            device.modelNumber = getValue("modelNumber",element2);
                                            device.serialNumber = getValue("serialNumber",element2);
                                            device.deviceIP= getValue("modelURL",element2);
                                            foundDevices.add(device);
                                            if(--pendingRequests == 0) {
                                                showDevicesInList(foundDevices);
                                            }
                                        }
                                    }
                                }
                                catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }

                            private String getValue(String tag, Element element) {
                                NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
                                Node node = (Node) nodeList.item(0);
                                return node.getNodeValue();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // handle error response
                                if(--pendingRequests == 0) {
                                    showDevicesInList(foundDevices);
                                }
                            }
                        }
                );
                queue.add(req);
            }
        }
    }
    static int retryCount = 0;
    private void showDevicesInList(ArrayList<RousingLight> foundDevices) {
        progress.dismiss();
        if(foundDevices.toArray().length == 0) {
            if(retryCount < 5) {
                retryCount++;
                discoverActiveRousingDevices();
            }
        }
        else {
            retryCount = 0;
            ArrayList<RousingLight> nonSavedDevices = new ArrayList<RousingLight>();
            for (RousingLight eachLight : foundDevices) {
                for (RousingLight eachSavedLight : savedDevices ) {
                    if(eachLight.deviceID.equalsIgnoreCase(eachSavedLight.deviceID)) {
                        eachSavedLight.isActive = true;
                    }
                }
            }
        }
    }

    private class UPnPDiscovery extends AsyncTask {
        HashSet<String> addresses = new HashSet<>();
        Context ctx;

        public UPnPDiscovery(Context context) {
            ctx = context;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            WifiManager wifi = (WifiManager) ctx.getSystemService(ctx.getApplicationContext().WIFI_SERVICE);

            if (wifi != null) {

                WifiManager.MulticastLock lock = wifi.createMulticastLock("The Lock");
                lock.acquire();

                DatagramSocket socket = null;

                try {
                    InetAddress group = InetAddress.getByName("239.255.255.250");
                    int port = 1900;
                    String query =
                            "M-SEARCH * HTTP/1.1\r\n" +
                                    "HOST: 239.255.255.250:1883\r\n" +
                                    "MAN: \"ssdp:discover\"\r\n" +
                                    "MX: 1\r\n" +
                                    "ST: urn:schemas-upnp-org:service:RousingLight:1\r\n" +  // Use for Sonos
//                                    "ST: ssdp:all\r\n"+  // Use this for all UPnP Devices
                                    "\r\n";

                    socket = new DatagramSocket(port);
                    socket.setReuseAddress(true);

                    DatagramPacket dgram = new DatagramPacket(query.getBytes(), query.length(),
                            group, port);
                    socket.send(dgram);
                    socket.setSoTimeout(1000);
                    long time = System.currentTimeMillis();
                    long curTime = System.currentTimeMillis();
                    boolean shouldContinue = true;
                    while (shouldContinue) {
                        try{
                            DatagramPacket p = new DatagramPacket(new byte[2048], 2048);
                            socket.receive(p);

                            String packetContent = new String(p.getData(), 0, p.getLength());
                            String s = new String(p.getData(), 0, 12);
                            if (s.toUpperCase().equals("HTTP/1.1 200")) {
                                String xmlDescURL = packetContent.split("LOCATION: ")[1];
                                addresses.add(xmlDescURL);
                            }
                        } catch (SocketTimeoutException ex1){
                            System.out.println("Timeout reached!!! " + ex1);
                            shouldContinue = false;
                        }
                    }

                }
                catch (SocketException e1) {
                    System.out.println("Socket closed " + e1);
                }
                catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    socket.close();
                }
                lock.release();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            deviceDiscoveryComplete(addresses);
        }
    }
}
