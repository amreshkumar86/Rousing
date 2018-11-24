package com.rousing.oen.rousing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rousing.database.DBManager;
import com.rousing.models.RousingLight;

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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LightSearchActivity extends AppCompatActivity {

    ListView listView;
    RousingDeviceAdapter adapter;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_search);
        setTitle("Rousing");
        discoverRousingDevices(this);
        listView = (ListView)findViewById(R.id.deviceListView);
        adapter = new RousingDeviceAdapter(this, new ArrayList<RousingLight>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> _adapter, View v, int position, long tag){
                RousingLight item = (RousingLight) _adapter.getItemAtPosition(position);
                if(DBManager.GetSharedDBManager().AddLight(item)){
                    adapter.remove(item);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void rescan(View v) {
        discoverRousingDevices(this);
    }

    private void discoverRousingDevices(Context ctx) {
        progress = new ProgressDialog(this);
        progress.setTitle("Searching");
        progress.setMessage("Wait while we search for connected lights...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        UPnPDiscovery discover = new UPnPDiscovery(ctx);
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

    private void showDevicesInList(ArrayList<RousingLight> foundDevices) {
        progress.dismiss();
        if(foundDevices.toArray().length == 0) {
            //Alert that no devices found, show option to rescan
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Context ctx = this;
            builder.setTitle("No Devices found");
            builder.setMessage("Rescan?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    discoverRousingDevices(ctx);
                    dialog.dismiss();
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
            ArrayList<RousingLight> savedDevices = DBManager.GetSharedDBManager().GetAllLights();
            ArrayList<RousingLight> nonSavedDevices = new ArrayList<RousingLight>();
            for (RousingLight eachLight : foundDevices) {
                boolean isFound = false;
                for (RousingLight eachSavedLight : savedDevices ) {
                    if(eachLight.deviceID.equalsIgnoreCase(eachSavedLight.deviceID)) {
                        isFound = true;
                        break;
                    }
                }
                if(!isFound) {
                    nonSavedDevices.add(eachLight);
                }
            }
            adapter.clear();
            adapter.addAll(nonSavedDevices);
            adapter.notifyDataSetChanged();
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

class RousingDeviceAdapter extends ArrayAdapter<RousingLight> {
    // View lookup cache
    private static class ViewHolder {
        TextView modelName;
        TextView modelNumber;
        TextView serialNumber;
    }

    public RousingDeviceAdapter(Context context, ArrayList<RousingLight> devices) {
        super(context, R.layout.device_search_list_layout, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RousingLight device= getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.device_search_list_layout, parent, false);
            viewHolder.modelName = (TextView) convertView.findViewById(R.id.modelName);
            viewHolder.modelNumber = (TextView) convertView.findViewById(R.id.modelNumber);
            viewHolder.serialNumber = (TextView) convertView.findViewById(R.id.serialNumber);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.modelName.setText("Model Name: " + device.modelName);
        viewHolder.modelNumber.setText("Model #" + device.modelNumber);
        viewHolder.serialNumber.setText("Serial #" + device.serialNumber);
        // Return the completed view to render on screen
        return convertView;
    }
}