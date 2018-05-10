package com.niveus.oen.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.niveus.oen.Activities.LightControlActivity;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.Fragments.DevicesFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

/**
 * Created by Adarsh on 21-Aug-17.
 */

public class ConnectionManager {

    Context context;

    List<Device> serverDevicesList = new ArrayList<Device>();
    List<HashMap<String, String>> localDevicesList = new ArrayList<HashMap<String, String>>();
    //ArrayList<Device> finalDevicesList = new ArrayList<Device>();

    PreferencesManager preferencesManager;

    private static final int MSG_SHOWLOG = 0;
    private static final int MSG_FOUND_DEVICE = 1;
    private static final int MSG_DISCOVER_FINISH = 2;
    private static final int MSG_STOP_SEARCH = 3;
    private static final int MSG_CONNECT_SUCCESS = 0;
    private static final int MSG_CONNECT_FAILURE = 1;

    private static final String UDP_HOST = "239.255.255.250";
    private static final int UDP_PORT = 1982;
    private static final String message = "M-SEARCH * HTTP/1.1\r\n" +
            "HOST:239.255.255.250:1982\r\n" +
            "MAN:\"ssdp:discover\"\r\n" +
            "ST:wifi_bulb\r\n";//用于发送的字符串
    private DatagramSocket mDSocket;
    private boolean mSeraching = true;

    int listCreated = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FOUND_DEVICE:
                    //createDisplayList();
                    break;
                case MSG_SHOWLOG:
                    //Toast.makeText(getActivity(), "" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case MSG_STOP_SEARCH:
                    //searchThread.interrupt();
                    //adapter.notifyDataSetChanged();
                    if (listCreated == 0) {
                        listCreated = 1;
                        createDisplayList();
                    }
                    mSeraching = false;

                    break;
                case MSG_DISCOVER_FINISH:
                    //createDisplayList();
                    break;
            }
        }
    };

    private Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CONNECT_FAILURE:
                    break;
                case MSG_CONNECT_SUCCESS:
                    //Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("android.intent.action.DEVICE_LIST");
                    //i.putExtra("fdl", finalDevicesList);
                    context.sendBroadcast(i);
            }
        }
    };

    public ConnectionManager(Context context){
        this.context = context;
        this.preferencesManager = new PreferencesManager(context);
    }

    public void getDevices(){
        try {
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
                            serverDevicesList.remove(device);
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
                            localDevicesList.remove(bulbInfo);
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
            //Log.d(TAG, "location params = " + bulbinfo.get("Location"));
            if (info.get("Location").equals(bulbinfo.get("Location"))){
                return true;
            }
        }
        return false;
    }

    public void createDisplayList(){

        Items group = new Items();
        group.setId("22145");
        group.setName("My group");
        group.setType("group");
        List<Device> list2 = new ArrayList<Device>();

        for (int i = 0; i < serverDevicesList.size(); i++) {
            for (int j = 0; j < localDevicesList.size(); j++) {

                if (serverDevicesList.get(i).getItemUniqueNumber().equals(localDevicesList.get(j).get("id"))) {
                    Device device = new Device();
                    device.setNickName(serverDevicesList.get(i).getNickName());
                    device.setCustomerItemId(serverDevicesList.get(i).getCustomerItemId());
                    device.setItemUniqueNumber(serverDevicesList.get(i).getItemUniqueNumber());
                    device.setAvailable(true);

                    String ipinfo = localDevicesList.get(j).get("Location").split("//")[1];
                    String ip = ipinfo.split(":")[0];
                    String port = ipinfo.split(":")[1];
                    String on = localDevicesList.get(j).get("power").trim();
                    int rgb = Integer.valueOf(localDevicesList.get(j).get("rgb").trim());
                    int brightness = Integer.valueOf(localDevicesList.get(j).get("bright").trim());
                    int saturation = Integer.valueOf(localDevicesList.get(j).get("sat").trim());
                    int hue = Integer.valueOf(localDevicesList.get(j).get("hue").trim());
                    int color_mode = Integer.valueOf(localDevicesList.get(j).get("color_mode").trim());
                    if (on.equals("on")){
                        device.setOn(true);
                    }
                    else {
                        device.setOn(false);
                    }
                    device.setIp(ip);
                    device.setPort(port);
                    device.setRgb(rgb);
                    device.setBrightness(brightness);
                    device.setSaturation(saturation);
                    device.setHue(hue);
                    device.setColor_mode(color_mode);

                    //sat = 0 -> White
                    //sat = 100 -> Moody

                    Items item = new Items();
                    item.setId(device.getItemUniqueNumber());
                    item.setName(device.getNickName());
                    item.setType("device");
                    List<Device> list = new ArrayList<Device>();
                    list.add(device);
                    item.setDevices(list);
                    finalDevicesList.add(item);

                    //

                    break;
                }
                else {
                    if (j == localDevicesList.size()) {
                        Device device = new Device();
                        device.setNickName(serverDevicesList.get(i).getNickName());
                        device.setCustomerItemId(serverDevicesList.get(i).getCustomerItemId());
                        device.setItemUniqueNumber(serverDevicesList.get(i).getItemUniqueNumber());
                        device.setAvailable(false);
                        device.setOn(false);

                        Items item = new Items();
                        item.setId(device.getItemUniqueNumber());
                        item.setName(device.getNickName());
                        item.setType("device");
                        List<Device> list = new ArrayList<Device>();
                        list.add(device);
                        item.setDevices(list);
                        finalDevicesList.add(item);
                    }
                }
            }
        }

        list2.add(finalDevicesList.get(0).getDevices().get(0));
        list2.add(finalDevicesList.get(1).getDevices().get(0));

        group.setDevices(list2);
        finalDevicesList.add(group);

        if (localDevicesList.isEmpty()){
            for (int i = 0; i < serverDevicesList.size(); i++) {

                Device device = new Device();
                device.setNickName(serverDevicesList.get(i).getNickName());
                device.setAvailable(false);

                Items item = new Items();
                item.setName(device.getNickName());
                item.setType("device");
                List<Device> list = new ArrayList<Device>();
                list.add(device);
                item.setDevices(list);
                finalDevicesList.add(item);
            }
            mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
        }
        else {
            new Connect().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }

    /*private void connect(final List<Device> finalDevicesList){

        for (int i = 0; i < finalDevicesList.size(); i++) {
            ii = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sockets[ii] = new Socket(finalDevicesList.get(ii).getIp(), Integer.valueOf(finalDevicesList.get(ii).getPort()));
                        sockets[ii].setKeepAlive(true);
                        BufferedOutputStream mBos = new BufferedOutputStream(sockets[ii].getOutputStream());
                        mHandler2.sendEmptyMessage(ii);
                        BufferedReader mReader = new BufferedReader(new InputStreamReader(sockets[ii].getInputStream()));

                        try {
                            String value = mReader.readLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        //mHandler.sendEmptyMessage(MSG_CONNECT_FAILURE);
                    }
                }
            }).start();
        }
    }*/

    private class Connect extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void...params) {

            for (int i = 0; i < finalDevicesList.size(); i++) {
                if (finalDevicesList.get(i).getType().equals("device")) {
                    if (finalDevicesList.get(i).getDevices().get(0).isAvailable()) {
                        try {
                            Socket socket = new Socket(finalDevicesList.get(i).getDevices().get(0).getIp(), Integer.valueOf(finalDevicesList.get(i).getDevices().get(0).getPort()));
                            socket.setKeepAlive(true);
                            BufferedOutputStream mBos = new BufferedOutputStream(socket.getOutputStream());
                            BufferedReader mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            finalDevicesList.get(i).getDevices().get(0).setSocket(socket);
                            finalDevicesList.get(i).getDevices().get(0).setBos(mBos);
                            if (i == finalDevicesList.size() - 1) {
                                //return (null);
                                mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (i == finalDevicesList.size() - 1) {
                                //return (null);
                                mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                            }
                        }
                    } else if (i == finalDevicesList.size() - 1) {
                        mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                    }
                }
                else {
                    for (int j = 0; j < finalDevicesList.get(i).getDevices().size(); j++) {
                        if (finalDevicesList.get(i).getDevices().get(j).isAvailable()) {
                            try {
                                Socket socket = new Socket(finalDevicesList.get(i).getDevices().get(j).getIp(), Integer.valueOf(finalDevicesList.get(i).getDevices().get(j).getPort()));
                                socket.setKeepAlive(true);
                                BufferedOutputStream mBos = new BufferedOutputStream(socket.getOutputStream());
                                BufferedReader mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                finalDevicesList.get(i).getDevices().get(j).setSocket(socket);
                                finalDevicesList.get(i).getDevices().get(j).setBos(mBos);
                                if (i == finalDevicesList.size() - 1) {
                                    //return (null);
                                    mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                if (i == finalDevicesList.size() - 1) {
                                    //return (null);
                                    mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                                }
                            }
                        } else if (i == finalDevicesList.size() - 1) {
                            mHandler2.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                        }
                    }
                }
            }
            return (null);
        }

        @Override
        protected void onPostExecute(Void unused) {

            /*Intent i = new Intent("android.intent.action.DEVICE_LIST");
            //i.putExtra("fdl", finalDevicesList);
            context.sendBroadcast(i);*/

        }
    }

}
