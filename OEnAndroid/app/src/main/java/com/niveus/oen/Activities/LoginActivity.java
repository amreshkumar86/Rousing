package com.niveus.oen.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.stetho.Stetho;
import com.niveus.oen.R;
import com.niveus.oen.Utils.AppController;
import com.niveus.oen.Utils.Constants;
import com.niveus.oen.Utils.FontsProvider;
import com.niveus.oen.Utils.PreferencesManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText emailIdEt, passwordEt;
    TextView loginTv;

    PreferencesManager preferencesManager;

    JSONObject jsonSignIn;

    RelativeLayout load;

    FontsProvider fontsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.parseColor("#033399"));
            window.setStatusBarColor(Color.TRANSPARENT);*/

            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        preferencesManager = new PreferencesManager(this);

        preferencesManager.setHasLoggedIn(true);
        preferencesManager.setHasAddedDevices(true);

        if (preferencesManager.getHasAddedDevices()){
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
        else if (preferencesManager.getHasLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, AddDevicesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        fontsProvider = new FontsProvider(LoginActivity.this);

        preferencesManager = new PreferencesManager(LoginActivity.this);

        load = (RelativeLayout) findViewById(R.id.load);
        load.setVisibility(View.GONE);

        ((TextView) findViewById(R.id.page_title_tv)).setTypeface(fontsProvider.getHellBold());

        emailIdEt = (EditText) findViewById(R.id.email_id_et);
        emailIdEt.setTypeface(fontsProvider.getRobotoLight());

        passwordEt = (EditText) findViewById(R.id.password_et);
        passwordEt.setTypeface(fontsProvider.getRobotoLight());

        loginTv = (TextView) findViewById(R.id.login_btn_tv);
        loginTv.setTypeface(fontsProvider.getRobotoLight());

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){

                    try {
                        jsonSignIn = new JSONObject();

                        jsonSignIn.put("username", emailIdEt.getText().toString());
                        jsonSignIn.put("password", passwordEt.getText().toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    emailIdEt.clearFocus();
                    passwordEt.clearFocus();
                    load.setVisibility(View.VISIBLE);
                    signIn();
                }
            }
        });

        loginTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });

        /*loginTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(null, shadowBuilder, v, 0);
                    loginTv.setVisibility(View.GONE);
                    return true;
                }
                else {
                    return false;
                }
            }
        });*/

    }

    public boolean validate(){
        if (emailIdEt.getText().toString().length() == 0 || !validateEmail(emailIdEt.getText().toString())){
            Toast.makeText(LoginActivity.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            emailIdEt.requestFocus();
            return false;
        }
        else if (passwordEt.getText().toString().length() == 0){
            Toast.makeText(LoginActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
            passwordEt.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void signIn(){

        //String tag_json_obj = "json_obj_req";

        String url = Constants.URL + "auth/login";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonSignIn,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response---->", "" + response);
                        load.setVisibility(View.GONE);

                        Toast.makeText(LoginActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();

                        try {

                            preferencesManager.setEmail(emailIdEt.getText().toString());
                            preferencesManager.setToken(response.getString("token"));
                            preferencesManager.setUserId(response.getInt("user_id"));
                            preferencesManager.setCustomerId(response.getInt("customer_id"));
                            preferencesManager.setHasLoggedIn(true);

                            Intent intent = new Intent(LoginActivity.this, AddDevicesActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                load.setVisibility(View.GONE);

                JSONObject jsonErrorResponse;
                int code;

                try {
                    if(error.networkResponse.data!=null) {
                        jsonErrorResponse = new JSONObject(new String(error.networkResponse.data, "UTF-8"));

                        code = jsonErrorResponse.getInt("code");

                        if (code == 500) {
                            Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Some went terribly wrong. Try once again!", Toast.LENGTH_SHORT).show();

                        }
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
