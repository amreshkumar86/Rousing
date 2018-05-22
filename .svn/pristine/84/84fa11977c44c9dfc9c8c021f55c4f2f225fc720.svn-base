package com.niveus.oen.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.niveus.oen.R;
import com.niveus.oen.Utils.AppController;
import com.niveus.oen.Utils.Constants;
import com.niveus.oen.Utils.CustomRequest;
import com.niveus.oen.Utils.FontsProvider;
import com.niveus.oen.Utils.PreferencesManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.wifi.WifiManager.MulticastLock;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class AddDevicesActivity extends AppCompatActivity {

    PreferencesManager preferencesManager;
    RelativeLayout load;

    TextView pageTitleTv;
    ImageView addButton;
    ListView deviceLV;

    RelativeLayout devicesLvRL;

    List<HashMap<String, String>> deviceList = new ArrayList<HashMap<String, String>>();

    RelativeLayout radarRL;
    ImageView radarIv;
    TextView findingTv;
    int count = 0;

    private static final String TAG = "Add devices";
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
    private boolean mSeraching = true;
    private MyAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FOUND_DEVICE:
                    //adapter.notifyDataSetChanged();

                    if (deviceList.isEmpty()) {
                        //show search button again
                        addButton.setVisibility(View.VISIBLE);
                        devicesLvRL.setVisibility(View.GONE);
                        radarRL.setVisibility(View.GONE);
                    }
                    else {
                        getDevices();
                    }

                    break;
                case MSG_SHOWLOG:
                    Toast.makeText(AddDevicesActivity.this, "" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case MSG_STOP_SEARCH:
                    searchThread.interrupt();
                    //adapter.notifyDataSetChanged();
                    mSeraching = false;

                    if (deviceList.isEmpty()) {
                        //show search button again
                        addButton.setVisibility(View.VISIBLE);
                        devicesLvRL.setVisibility(View.GONE);
                        radarRL.setVisibility(View.GONE);
                    }
                    else {
                        getDevices();
                    }

                    break;
                case MSG_DISCOVER_FINISH:
                    //adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private MulticastLock multicastLock;

    FontsProvider fontsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#033399"));
        }

        fontsProvider = new FontsProvider(AddDevicesActivity.this);

        preferencesManager = new PreferencesManager(this);

        load = (RelativeLayout) findViewById(R.id.load);
        load.setVisibility(View.GONE);

        pageTitleTv = (TextView) findViewById(R.id.page_title_tv);
        pageTitleTv.setTypeface(fontsProvider.getHellBold());

        ((TextView) findViewById(R.id.lights_tv)).setTypeface(fontsProvider.getRobotoLight());

        addButton = (ImageView) findViewById(R.id.add_button);
        deviceLV = (ListView) findViewById(R.id.device_lv);

        devicesLvRL = (RelativeLayout) findViewById(R.id.device_lv_rl);

        //
        radarRL = (RelativeLayout) findViewById(R.id.radar_rl);
        radarIv = (ImageView) findViewById(R.id.radar_iv);
        findingTv = (TextView) findViewById(R.id.finding_tv);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addButton.setVisibility(View.GONE);

                radarRL.setVisibility(View.VISIBLE);
                devicesLvRL.setVisibility(View.GONE);

                YoYo.with(Techniques.FadeIn)
                        .duration(400)
                        .playOn(radarRL);

                Animation rotateAnimation = AnimationUtils.loadAnimation(AddDevicesActivity.this, R.anim.rotate);
                rotateAnimation.setDuration(5000);

                radarIv.startAnimation(rotateAnimation);
                findingAnimation();

                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        devicesLvRL.setVisibility(View.VISIBLE);

                        HashMap<String, String> bulbInfo = new HashMap<String, String>();
                        bulbInfo.put("Wifi", "12345");
                        deviceList.add(bulbInfo);

                        HashMap<String, String> bulbInfo1 = new HashMap<String, String>();
                        bulbInfo.put("Wifi", "12345");
                        deviceList.add(bulbInfo1);

                        HashMap<String, String> bulbInfo2 = new HashMap<String, String>();
                        bulbInfo.put("Wifi", "12345");
                        deviceList.add(bulbInfo2);

                        pageTitleTv.setText("1 DEVICE\nFOUND");

                        adapter = new MyAdapter(AddDevicesActivity.this);
                        deviceLV.setAdapter(adapter);

                        radarRL.setVisibility(View.GONE);
                        radarIv.clearAnimation();

                    }
                }, 8000);*/

                searchDevice();
            }
        });

        deviceLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> bulbInfo = deviceList.get(position);
                Intent intent = new Intent(AddDevicesActivity.this, AddDeviceToServerActivity.class);
                String ipinfo = bulbInfo.get("Location").split("//")[1];
                String ip = ipinfo.split(":")[0];
                String port = ipinfo.split(":")[1];
                intent.putExtra("bulbinfo", bulbInfo);
                intent.putExtra("ip", ip);
                intent.putExtra("port", port);
                intent.putExtra("uniqueNumber", bulbInfo.get("id"));
                startActivityForResult(intent, 0);

                //preferencesManager.setHasAddedDevices(true);

                /*Intent intent = new Intent(AddDevicesActivity.this, DashboardActivity.class);
                startActivity(intent);*/

            }
        });

        WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        multicastLock = wm.createMulticastLock("test");
        multicastLock.acquire();

    }

    private Thread searchThread = null;
    private void searchDevice() {

        deviceList.clear();
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
                            deviceList.add(bulbInfo);
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

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mLayoutInflater;
        private  FontsProvider fontsProvider;

        public MyAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            fontsProvider = new FontsProvider(context);
        }

        @Override
        public int getCount() {
            return deviceList.size();
        }

        @Override
        public Object getItem(int position) {
            return deviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            HashMap<String, String> data = (HashMap<String, String>) getItem(position);
            if (convertView == null) {
                view = mLayoutInflater.inflate(R.layout.device_list_item, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(R.id.device_name_tv);

            textView.setTypeface(fontsProvider.getRegular());

            if (data.get("name").trim().length() == 0) {
                textView.setText("Yeelight " + position + 1);
            }
            else {
                textView.setText(data.get("name"));
            }

            return view;
        }
    }

    private boolean hasAdd(HashMap<String,String> bulbinfo){
        for (HashMap<String,String> info : deviceList){
            Log.d(TAG, "location params = " + bulbinfo.get("Location"));
            if (info.get("Location").equals(bulbinfo.get("Location"))){
                return true;
            }
        }
        return false;
    }

    private boolean mNotify = true;
    /*@Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //DatagramSocket socket = new DatagramSocket(UDP_PORT);
                    InetAddress group = InetAddress.getByName(UDP_HOST);
                    MulticastSocket socket = new MulticastSocket(UDP_PORT);
                    socket.setLoopbackMode(true);
                    socket.joinGroup(group);
                    Log.d(TAG, "join success");
                    mNotify = true;
                    while (mNotify){
                        byte[] buf = new byte[1024];
                        DatagramPacket receiveDp = new DatagramPacket(buf,buf.length);
                        Log.d(TAG, "waiting device....");
                        socket.receive(receiveDp);
                        byte[] bytes = receiveDp.getData();
                        StringBuffer buffer = new StringBuffer();
                        for (int i = 0; i < receiveDp.getLength(); i++) {
                            // parse /r
                            if (bytes[i] == 13) {
                                continue;
                            }
                            buffer.append((char) bytes[i]);
                        }
                        if (!buffer.toString().contains("yeelight")){
                            Log.d(TAG,"Listener receive msg:" + buffer.toString()+" but not a response");
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
                            Log.d(TAG, "title = " + title + " value = " + value);
                            bulbInfo.put(title, value);
                        }
                        if (!hasAdd(bulbInfo)){
                            deviceList.add(bulbInfo);
                        }
                        mHandler.sendEmptyMessage(MSG_FOUND_DEVICE);
                        Log.d(TAG, "get message:" + buffer.toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        deviceList.clear();
        addButton.performClick();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                deviceList.clear();
                addButton.performClick();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNotify = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        multicastLock.release();
    }

    public void getDevices(){
        try {

            JSONObject sendObj = null;
            String url = Constants.URL + "device/item/list/1";

            CustomRequest jsonObj = new CustomRequest(Request.Method.POST,url,sendObj,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {
                            JSONObject device = (JSONObject) response.get(i);

                            for (int j = 0; j < deviceList.size(); j++){
                                if (deviceList.get(j).get("id").equals(device.getString("itemUniqueNumber"))){
                                    deviceList.remove(j);
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    adapter = new MyAdapter(AddDevicesActivity.this);
                    deviceLV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if (deviceList.isEmpty()) {
                        //show search button again
                        addButton.setVisibility(View.VISIBLE);
                        devicesLvRL.setVisibility(View.GONE);
                        radarRL.setVisibility(View.GONE);
                    }
                    else {
                        addButton.setVisibility(View.GONE);
                        devicesLvRL.setVisibility(View.VISIBLE);
                        radarRL.setVisibility(View.GONE);
                    }

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

            // queue.add(jsonObj);
            AppController.getInstance().addToRequestQueue(jsonObj);
            AppController.getInstance().setRetry(jsonObj);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void findingAnimation(){

        if (count == 0){
            findingTv.setText("Finding");
            count++;
        }
        else if (count == 1){
            findingTv.setText("Finding.");
            count++;
        }
        else if (count == 2){
            findingTv.setText("Finding..");
            count++;
        }
        else if (count == 3){
            findingTv.setText("Finding...");
            count = 0;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findingAnimation();
            }
        }, 500);
    }
}
