package com.niveus.oen.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.niveus.oen.Activities.LightControlActivity;
import com.niveus.oen.Adapters.DevicesAdapter;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.R;
import com.niveus.oen.Utils.AppController;
import com.niveus.oen.Utils.ConnectionManager;
import com.niveus.oen.Utils.Constants;
import com.niveus.oen.Utils.CustomRequest;
import com.niveus.oen.Utils.DeviceListReceiver;
import com.niveus.oen.Utils.FontsProvider;
import com.niveus.oen.Utils.PreferencesManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adarsh on 01-Jun-17.
 */

public class DevicesFragment extends Fragment implements DeviceListReceiver.DeviceListListener {

    public static List<Items> finalDevicesList = new ArrayList<Items>();
    ListView devicesLV;

    DevicesAdapter adapter;

    SpinKitView loader;

    PreferencesManager preferencesManager;

    private static final String TAG = "APITEST";
    private static final int MSG_SHOWLOG = 0;
    private static final int MSG_FOUND_DEVICE = 1;
    private static final int MSG_DISCOVER_FINISH = 2;
    private static final int MSG_STOP_SEARCH = 3;

    private static final String UDP_HOST = "239.255.255.250";
    private static final int UDP_PORT = 1982;
    private static final String message = "M-SEARCH * HTTP/1.1\r\n" +
            "HOST:239.255.255.250:1982\r\n" +
            "MAN:\"ssdp:discover\"\r\n" +
            "ST:wifi_bulb\r\n";//用于发送的字符串
    private DatagramSocket mDSocket;
    private boolean mSearching = true;

    /*private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FOUND_DEVICE:
                    //adapter.notifyDataSetChanged();
                    createDisplayList();
                    break;
                case MSG_SHOWLOG:
                    Toast.makeText(getActivity(), "" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case MSG_STOP_SEARCH:
                    searchThread.interrupt();
                    //adapter.notifyDataSetChanged();
                    createDisplayList();
                    mSeraching = false;

                    break;
                case MSG_DISCOVER_FINISH:
                    createDisplayList();
                    break;
            }
        }
    };*/

    private WifiManager.MulticastLock multicastLock;

    FontsProvider fontsProvider;

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        AppController.getInstance().setDeviceListListener(this);

        /*int color = Color.argb(255, 255, 0, 0);
        float[] hsv = new float[3];

        String ColorFlow;*//* = "200,1,16711680,100,   200,1,255,100"*//*
        StringBuilder str = new StringBuilder();

        for (int i = 360; i >= 0; i--){
            if (i % 30 == 0) {
                Color.colorToHSV(color, hsv);
                hsv[0] = i;
                //hsv[1] = 100;
                hsv[2] = 1f;
                color = Color.HSVToColor(hsv);
                str.append("200,1," + (65536 * Color.red(color) + 256 * Color.green(color) + Color.blue(color)) + ",100,");
            }
        }

        ColorFlow = str.toString();*/

        fontsProvider = new FontsProvider(getActivity());
        preferencesManager = new PreferencesManager(getContext());

        loader = (SpinKitView) view.findViewById(R.id.loader);
        //loader.setVisibility(View.GONE);

        ((TextView) view.findViewById(R.id.lights_tv)).setTypeface(fontsProvider.getRobotoLight());

        devicesLV = (ListView) view.findViewById(R.id.devices_lv);

        devicesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Device selectedDevice = (Device) adapter.getItem(position);
                Items item = (Items) adapter.getItem(position);

                if (item.getType().equals("device")) {
                    if (item.getDevices().get(0).isAvailable()) {
                        Intent intent = new Intent(getActivity(), LightControlActivity.class);
                        Bundle bundle = new Bundle();
                        //bundle.putSerializable("selectedDevice", selectedDevice);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent intent = new Intent(getActivity(), LightControlActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("selectedDevice", selectedDevice);
                    bundle.putInt("position", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        getDevices(0);
        //getFakeDevices();

        return view;
    }

    /*public void getDevices(){
        try {
            loader.setVisibility(View.VISIBLE);
            JSONObject sendObj = null;
            String url = Constants.URL + "device/item/list_with_complete_data/1";

            CustomRequest jsonObj = new CustomRequest(Request.Method.POST,url,sendObj,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {
                            JSONObject deviceObj = (JSONObject) response.get(i);
                            Device device = new Device();
                            device.setCustomerItemId(deviceObj.getInt("id"));
                            device.setNickName(deviceObj.getString("nickName"));
                            device.setItemUniqueNumber(deviceObj.getString("itemUniqueNumber"));
                            serverDevicesList.add(device);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    searchDevice();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.setVisibility(View.GONE);
                }
            })
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("X-Auth-Token", preferencesManager.getToken());
                    return headers;
                }
            };

            AppController.getInstance().addToRequestQueue(jsonObj);
            AppController.getInstance().setRetry(jsonObj);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private Thread searchThread = null;
    private void searchDevice() {

        //adapter.notifyDataSetChanged();
        mSeraching = true;
        searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mDSocket = new DatagramSocket();
                    DatagramPacket dpSend = new DatagramPacket(message.getBytes(),
                            message.getBytes().length, InetAddress.getByName(UDP_HOST),
                            UDP_PORT);
                    mDSocket.send(dpSend);
                    mHandler.sendEmptyMessageDelayed(MSG_STOP_SEARCH, 2000);
                    while (mSeraching) {
                        byte[] buf = new byte[1024];
                        DatagramPacket dpRecv = new DatagramPacket(buf, buf.length);
                        mDSocket.receive(dpRecv);
                        byte[] bytes = dpRecv.getData();
                        StringBuffer buffer = new StringBuffer();
                        for (int i = 0; i < dpRecv.getLength(); i++) {
                            // parse /r
                            if (bytes[i] == 13) {
                                continue;
                            }
                            buffer.append((char) bytes[i]);
                        }
                        Log.d("socket", "got message:" + buffer.toString());
                        if (!buffer.toString().contains("yeelight")) {
                            mHandler.obtainMessage(MSG_SHOWLOG, "++").sendToTarget();
                            return;
                        }
                        String[] infos = buffer.toString().split("\n");
                        HashMap<String, String> bulbInfo = new HashMap<String, String>();
                        for (String str : infos) {
                            int index = str.indexOf(":");
                            if (index == -1) {
                                continue;
                            }
                            String title = str.substring(0, index);
                            String value = str.substring(index + 1);
                            bulbInfo.put(title, value);
                        }
                        if (!hasAdd(bulbInfo)){
                            localDevicesList.add(bulbInfo);
                        }

                    }
                    mHandler.sendEmptyMessage(MSG_DISCOVER_FINISH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        searchThread.start();

    }

    private boolean hasAdd(HashMap<String,String> bulbinfo){
        for (HashMap<String,String> info : localDevicesList){
            Log.d(TAG, "location params = " + bulbinfo.get("Location"));
            if (info.get("Location").equals(bulbinfo.get("Location"))){
                return true;
            }
        }
        return false;
    }

    public void createDisplayList(){

        for (int i = 0; i < serverDevicesList.size(); i++) {
            for (int j = 0; j < localDevicesList.size(); j++) {

                if (serverDevicesList.get(i).getItemUniqueNumber().equals(localDevicesList.get(j).get("id"))) {
                    Device device = new Device();
                    device.setNickName(serverDevicesList.get(i).getNickName());
                    device.setCustomerItemId(serverDevicesList.get(i).getCustomerItemId());
                    device.setAvailable(true);

                    String ipinfo = localDevicesList.get(j).get("Location").split("//")[1];
                    String ip = ipinfo.split(":")[0];
                    String port = ipinfo.split(":")[1];
                    device.setIp(ip);
                    device.setPort(port);

                    finalDevicesList.add(device);
                }
                else {
                    Device device = new Device();
                    device.setNickName(serverDevicesList.get(i).getNickName());
                    device.setCustomerItemId(serverDevicesList.get(i).getCustomerItemId());
                    device.setAvailable(false);

                    finalDevicesList.add(device);
                }
            }
        }

        if (localDevicesList.isEmpty()){
            for (int i = 0; i < serverDevicesList.size(); i++) {

                Device device = new Device();
                device.setNickName(serverDevicesList.get(i).getNickName());
                device.setAvailable(false);

                finalDevicesList.add(device);
            }
        }

        adapter = new DevicesAdapter(getContext(), finalDevicesList);
        devicesLV.setAdapter(adapter);

        loader.setVisibility(View.GONE);
    }
*/
    /*public void getFakeDevices(){
        Device device = new Device();
        device.setItemUniqueNumber("1");
        device.setNickName("Bob the bulb");
        device.setAvailable(true);

        Device device2 = new Device();
        device2.setItemUniqueNumber("2");
        device2.setNickName("Larry's Lamp");
        device2.setAvailable(true);

        Device device3 = new Device();
        device3.setItemUniqueNumber("3");
        device3.setNickName("Torture torch");
        device3.setAvailable(true);

        finalDevicesList.add(device);
        finalDevicesList.add(device2);
        finalDevicesList.add(device3);

        adapter = new DevicesAdapter(getContext(), finalDevicesList);
        devicesLV.setAdapter(adapter);

        loader.setVisibility(View.GONE);

    }*/

    @Override
    public void getDeviceList(Intent intent) {
        displayList();
    }

    public void displayList(){
        int index = devicesLV.getFirstVisiblePosition();
        View v = devicesLV.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - devicesLV.getPaddingTop());

        loader.setVisibility(View.GONE);
        adapter = new DevicesAdapter(getContext(), finalDevicesList, DevicesFragment.this);
        devicesLV.setAdapter(adapter);
        devicesLV.setEnabled(true);

        devicesLV.setSelectionFromTop(index, top);

    }

    public void getDevices(int mode){

        if (mode == 0) {
            loader.setVisibility(View.VISIBLE);
        }
        finalDevicesList.clear();
        devicesLV.setEnabled(false);
        ConnectionManager connectionManager = new ConnectionManager(getActivity());
        connectionManager.getDevices();
    }
}
