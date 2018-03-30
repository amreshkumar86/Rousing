package com.rousing.oen.rousing;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rousing.mcu.connector.McuConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class LandingActivity extends AppCompatActivity {

    boolean isStripConnected, isLifxConnected;
    Button stripButton, lifxButton;
    EditText textView;
    private int bulbConnectRetryCount = 0;
    private String bulbPowerState;
    private String nodeMCUIP;
    double bulbBrightnessValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        isStripConnected = isLifxConnected = false;
        addListenerOnButton();
        setButtonState();
//        connectToLifx();
//        connectToStrip();
        String[] devices = discoverDevices(this);
        System.out.println(Arrays.toString(devices));

    }

    private void addListenerOnButton() {

        final Context context = this;

        stripButton = (Button) findViewById(R.id.stripButton);

        stripButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("DEVICE_TYPE", "STRIP");
                startActivity(intent);
            }

        });

        lifxButton = (Button) findViewById(R.id.lifxButton);

        lifxButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("DEVICE_TYPE", "LIFX");
                intent.putExtra("bulbPowerState", bulbPowerState);
                intent.putExtra("bulbBrightnessValue", bulbBrightnessValue);
                startActivity(intent);
            }

        });
        textView = (EditText) findViewById(R.id.editText);
    }

    private void setButtonState() {

        if(isStripConnected){
            stripButton.setText("Connected to strip");
            stripButton.setEnabled(true);
            McuConnector.getSharedConnectorStrip(this,nodeMCUIP);
        }
        else {
            stripButton.setText("Connecting to strip");
            stripButton.setEnabled(false);
        }

        if(isLifxConnected){
            lifxButton.setText("Connected to lifx");
            lifxButton.setEnabled(true);
            McuConnector.getSharedConnectorLifx(this);
        }
        else {
            lifxButton.setText(bulbConnectRetryCount >= 5 ? "Unable to connect to lifx" : "Connecting to lifx");
            lifxButton.setEnabled(false);
        }
    }

    private String[] discoverDevices(Context ctx) {
        UPnPDiscovery discover = new UPnPDiscovery(ctx);
        discover.execute();
        try {
            Thread.sleep(1500);
            return discover.addresses.toArray(new String[discover.addresses.size()]);
        } catch (InterruptedException e) {
            return null;
        }
    }

    private void connectToStrip() {
        StripConnector connector = new StripConnector();
        connector.execute();
    }

    public String sendBroadcast(String messageStr) {
        // Hack Prevent crash (sending should be done using an async task)
        StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String nodeMCUIP = null;

        try {
            //Open a random port to send the package
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = messageStr.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, getBroadcastAddress(), 2390);
            socket.send(sendPacket);

            System.out.println(getClass().getName() + "Broadcast Packet sent to: " + getBroadcastAddress().getHostAddress());
            textView.setText(textView.getText()+ "\n"+getClass().getName() + "Broadcast Packet sent to: " + getBroadcastAddress().getHostAddress());
            boolean receivedPacket = false;
            while (!receivedPacket) {
                Log.i("","Ready to receive broadcast Packets!");
                textView.setText(textView.getText()+ "\n"+ "Ready to receive broadcast Packets!");

                //Receive a packet
                byte[] recvBuf = new byte[255];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                //Packet received
                Log.i("", "Packet received from: " + packet.getAddress().getHostAddress());
                textView.setText(textView.getText()+ "\n"+"Packet received from: " + packet.getAddress().getHostAddress());
                String data = new String(packet.getData()).trim();
                Log.i("", "Packet received; data: " + data);
                textView.setText(textView.getText()+ "\n"+"Packet received; data: " + data);
                nodeMCUIP = data;

//                receivedPacket = true;
            }
        } catch (IOException e) {
            Log.e("", "IOException: " + e.getMessage());
        }
        return nodeMCUIP;
    }

    InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }


    private void connectToLifx() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.lifx.com/v1/lights/"+getResources().getString(R.string.LIFXID);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LIFX",response);
                        try
                        {
                            JSONArray arrObj = new JSONArray(response);
                            if(arrObj.length()>0){
                                JSONObject bulbObj = arrObj.getJSONObject(0);
                                if(bulbObj != null){
                                    isLifxConnected = bulbObj.getBoolean("connected");
//                                    isLifxConnected = true;
                                    bulbConnectRetryCount++;
                                    setButtonState();
                                    if(!isLifxConnected && bulbConnectRetryCount < 5){
                                        //Retry
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        connectToLifx();
                                                    }
                                                }, 2000);
                                    }
                                    else {
                                        bulbPowerState = bulbObj.getString("power");
                                        bulbBrightnessValue = bulbObj.getDouble("brightness");
//                                        bulbPowerState = "on";
                                    }
                                }
                            }
                        }
                        catch (Exception ex){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+getResources().getString(R.string.LIFXtoken));
                return params;
            }
        };

        queue.add(stringRequest);

    }

    private class StripConnector extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... objects) {
            return sendBroadcast("Hello");
        }

        @Override
        protected void onPostExecute(String resultIP) {
            if(resultIP != null) {
                nodeMCUIP = resultIP;
                isStripConnected = true;
                setButtonState();
            }
        }
    }


    /**
     * @author Bernd Verst(@berndverst)
     */
    private class UPnPDiscovery extends AsyncTask
    {
        HashSet<String> addresses = new HashSet<>();
        Context ctx;

        public UPnPDiscovery(Context context) {
            ctx = context;
        }


        @Override
        protected Object doInBackground(Object[] params) {

            WifiManager wifi = (WifiManager)ctx.getSystemService( ctx.getApplicationContext().WIFI_SERVICE );

            if(wifi != null) {

                WifiManager.MulticastLock lock = wifi.createMulticastLock("The Lock");
                lock.acquire();

                DatagramSocket socket = null;

                try {

                    InetAddress group = InetAddress.getByName("239.255.255.250");
                    int port = 1900;
                    String query =
                            "M-SEARCH * HTTP/1.1\r\n" +
                                    "HOST: 239.255.255.250:1883\r\n"+
                                    "MAN: \"ssdp:discover\"\r\n"+
                                    "MX: 1\r\n"+
                                    "ST: urn:schemas-upnp-org:service:RousingLight:1\r\n"+  // Use for Sonos
//                                    "ST: ssdp:all\r\n"+  // Use this for all UPnP Devices
                                    "\r\n";
//                    HTTP/1.1 200 OK
//                    EXT:
//                    CACHE-CONTROL: max-age=1200
//                    SERVER: Arduino/1.0 UPNP/1.1 Philips hue bridge 2012/929000226503
//                    USN: uuid:38323636-4558-4dda-9188-cda0e6056a52
//                    ST: urn:schemas-upnp-org:device:Basic:1
//                    LOCATION: http://192.168.0.101:80/description.xml
//

                    socket = new DatagramSocket(port);
                    socket.setReuseAddress(true);

                    DatagramPacket dgram = new DatagramPacket(query.getBytes(), query.length(),
                            group, port);
                    socket.send(dgram);

                    long time = System.currentTimeMillis();
                    long curTime = System.currentTimeMillis();

                    // Let's consider all the responses we can get in 1 second
                    while (curTime - time < 1000) {
//                        DatagramPacket p = new DatagramPacket(new byte[12], 12);
                        DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
                        socket.receive(p);

                        String s1 = new String(p.getData(), 0, p.getLength());
                        String s = new String(p.getData(), 0, 12);
                        System.out.println(s);
                        System.out.println(s1);
                        if (s.toUpperCase().equals("HTTP/1.1 200")) {
                            addresses.add(p.getAddress().getHostAddress());
                        }

                        curTime = System.currentTimeMillis();
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    socket.close();
                }
                lock.release();
            }
            return null;
        }
    }
}
