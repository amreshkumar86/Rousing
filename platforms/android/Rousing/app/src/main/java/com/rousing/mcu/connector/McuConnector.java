package com.rousing.mcu.connector;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rousing.oen.rousing.R;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Created by amreshkumar on 2/17/18.
 */

public class McuConnector {
    public enum DEVICE_TYPE {
        STRIP,
        LIFX
    };

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.d("","Connection established : ");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.d("","Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.d("","Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.d("","Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.d("","Error : " + t.getMessage());
        }
    }
    final String serverAddress = "http://192.168.4.1:80";
    private static McuConnector instanceStrip = null;
    private static McuConnector instanceLifx = null;
    private static HashMap<String,McuConnector> stripInstances = new HashMap<String,McuConnector>();
    private OkHttpClient client;
    private WebSocket webSocketClient;
    private DEVICE_TYPE currentDevice;
    private com.android.volley.RequestQueue queue;
    private String LIFXToken = "c7d77fe4b94a81d39eb21623a63b1fc82388c072b7aa5796013bee73cf1a12d1";
    private String BulbId = "d073d531f221";
    public static McuConnector getSharedConnectorStrip(Context context, String nodeMCUIP) {
        if(!nodeMCUIP.contains("http://")){
            nodeMCUIP = "http://" + nodeMCUIP;
        }
        McuConnector strip = stripInstances.get(nodeMCUIP);
        if(strip == null) {
            strip= new McuConnector(DEVICE_TYPE.STRIP,context);
            strip.createWebSocketClient(nodeMCUIP);
            stripInstances.put(nodeMCUIP,strip);
        }
        return strip;
    }

    public static McuConnector getSharedConnectorLifx(Context context) {
        if(instanceLifx  == null) {
            instanceLifx = new McuConnector(DEVICE_TYPE.LIFX,context);
        }
        return instanceLifx;
    }

    public McuConnector(DEVICE_TYPE type, Context context){
        currentDevice = type;
        if(currentDevice == DEVICE_TYPE.LIFX){
            queue = Volley.newRequestQueue(context);
        }
    }
    public void createWebSocketClient(String url) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        webSocketClient = client.newWebSocket(request, listener);

    }

    public void switchOn() {
        if(currentDevice == DEVICE_TYPE.LIFX) {
            queue.add(getLifxPUTRequest("power=on",new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("LifxBrightness",response);
                }
            },null));
        }
        else {
            webSocketClient.send("p1");
        }
    }

    public void switchOff() {
        if(currentDevice == DEVICE_TYPE.LIFX) {
            queue.add(getLifxPUTRequest("power=off",new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("LifxBrightness",response);
                }
            },null));
        }
        else {
            webSocketClient.send("p0");
        }
    }

    public void setColor(int r, int g, int b, int w) {
        if(currentDevice == DEVICE_TYPE.LIFX) {
            queue.add(getLifxPUTRequest("color=rgb:"+r+","+g+","+b,new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("LifxBrightness",response);
                }
            },null));
        }
        else {
            webSocketClient.send("c"+r+","+g+","+b+","+w);
        }
    }

    public void setBrightness(int brightness) {
        if(currentDevice == DEVICE_TYPE.LIFX) {
            queue.add(getLifxPUTRequest("brightness=" + (double) brightness / 100, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("LifxBrightness",response);
                }
            }, null));
        }
        else {
            webSocketClient.send("b"+brightness);
        }
    }

    public void setEffect(int effectNum) {
        webSocketClient.send("e"+effectNum);
    }



    private StringRequest getLifxGETRequest(com.android.volley.Response.Listener<String> listener, com.android.volley.Response.ErrorListener errorListener){
        String url ="https://api.lifx.com/v1/lights/"+ BulbId;
        return new StringRequest(com.android.volley.Request.Method.GET,url,listener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+LIFXToken);
                return params;
            }
        };

    }

    private StringRequest getLifxPUTRequest(String params,com.android.volley.Response.Listener<String> listener, com.android.volley.Response.ErrorListener errorListener){
        String url ="https://api.lifx.com/v1/lights/"+ BulbId+"/state?" + params;
        return new StringRequest(com.android.volley.Request.Method.PUT,url,listener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+LIFXToken);
                return params;
            }
        };

    }
}
