package com.niveus.oen.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.niveus.oen.R;
import com.niveus.oen.Utils.AppController;
import com.niveus.oen.Utils.Constants;
import com.niveus.oen.Utils.PreferencesManager;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AddDeviceToServerActivity extends AppCompatActivity {

    EditText nickNameEt;
    TextView doneBtnTv;

    private Socket mSocket;
    private String mBulbIP;
    private String mBulbUniqueNumber;
    private int mBulbPort;
    private BufferedOutputStream mBos;
    private BufferedReader mReader;
    private ProgressDialog mProgressDialog;

    private static final int MSG_CONNECT_SUCCESS = 0;
    private static final int MSG_CONNECT_FAILURE = 1;

    JSONObject jsonDevice;

    PreferencesManager preferencesManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CONNECT_FAILURE:
                    mProgressDialog.dismiss();
                    break;
                case MSG_CONNECT_SUCCESS:
                    mProgressDialog.dismiss();
                    Toast.makeText(AddDeviceToServerActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_to_server);

        preferencesManager = new PreferencesManager(this);

        nickNameEt = (EditText) findViewById(R.id.nick_name_et);
        doneBtnTv = (TextView) findViewById(R.id.done_btn_tv);

        mBulbIP = getIntent().getStringExtra("ip");
        mBulbPort = Integer.parseInt(getIntent().getStringExtra("port"));
        mBulbUniqueNumber = getIntent().getStringExtra("uniqueNumber");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Connecting...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        connect();

        doneBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nickNameEt.getText().toString().length() == 0){
                    Toast.makeText(AddDeviceToServerActivity.this, "Give a nick name for this bulb", Toast.LENGTH_SHORT).show();
                }
                else {
                    //String cmd = "{\"id\":1,\"method\":\"set_name\",\"params\":[\"" +nickNameEt.getText().toString()+ "\"]}\r\n";
                    //String cmd = "{\"method\":\"props\",\"params\":{\"bright\":100}}\r\n";
                    //write(cmd);

                    try {
                        mProgressDialog.show();

                        jsonDevice = new JSONObject();
                        jsonDevice.put("customerId", preferencesManager.getCustomerId());
                        jsonDevice.put("userId", preferencesManager.getUserId());
                        jsonDevice.put("itemId", 1);
                        jsonDevice.put("manufactureId", 1);
                        jsonDevice.put("itemUniqueNumber", mBulbUniqueNumber);
                        jsonDevice.put("itemBatchNumber", 1);
                        jsonDevice.put("deviceNickName", nickNameEt.getText().toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    write("{\"id\":2,\"method\":\"set_name\",\"params\":[\""+nickNameEt.getText().toString()+"\"]}\r\n");
                }
            }
        });
    }

    private void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(mBulbIP, mBulbPort);
                    mSocket.setKeepAlive(true);
                    mBos= new BufferedOutputStream(mSocket.getOutputStream());
                    mHandler.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

                    try {
                        String value = mReader.readLine();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(MSG_CONNECT_FAILURE);
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if (mSocket!=null)
                mSocket.close();
        }
        catch (Exception e){

        }

    }

    private void write(String cmd){
        if (mBos != null && mSocket.isConnected()){
            try {
                mBos.write(cmd.getBytes());
                mBos.flush();

                addDevice();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addDevice(){

        //String tag_json_obj = "json_obj_req";

        String url = Constants.URL + "device/add/new";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonDevice,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response---->", "" + response);
                        mProgressDialog.dismiss();

                        Toast.makeText(AddDeviceToServerActivity.this, "Device added successfully", Toast.LENGTH_SHORT).show();

                        preferencesManager.setHasAddedDevices(true);

                        //Intent returnIntent = new Intent();
                        //setResult(Activity.RESULT_OK, returnIntent);
                        Intent intent = new Intent(AddDeviceToServerActivity.this, DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();

                JSONObject jsonErrorResponse;
                int code;

                try {
                    if(error.networkResponse.data!=null) {
                        jsonErrorResponse = new JSONObject(new String(error.networkResponse.data, "UTF-8"));

                        code = jsonErrorResponse.getInt("code");

                        Toast.makeText(AddDeviceToServerActivity.this, "Failed to add device", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


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

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        //AppController.getInstance().setRetry(jsonObjReq);
    }
}
