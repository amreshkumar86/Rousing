package com.niveus.oen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lukedeighton.wheelview.WheelView;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Group;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.DataObjects.Scene;
import com.niveus.oen.R;
import com.niveus.oen.Utils.AppController;
import com.niveus.oen.Utils.Constants;
import com.niveus.oen.Utils.DBHelper;
import com.niveus.oen.Utils.PreferencesManager;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;
import static com.niveus.oen.Fragments.ScenesFragment.favList;
import static java.lang.Math.floor;

public class LightControlActivity extends AppCompatActivity {

    int colorAngle;
    double r = 255, g = 0, b = 0;
    float colorValue = 1f;
    float brightnessValue = 1f;
    int color;

    ImageView menu;
    TextView emoTv;

    TextView smartLightBtnTv, moodyBtnTv;
    CardView moodyCard;

    ImageView powerIv, favouriteIv, cycleIv;

    TextView favTv, cycleTv;

    SeekBar seekBarBrightness;
    SeekBar seekBarColor;

    int controlTypeToggle = 0;

    RelativeLayout rootRL, brightnessSeekRL, wheelRL, colorWheelRL;

    GradientDrawable gradient;

    int powerToggle = 1;
    int cycleToggle = 0;

    Handler handler;

    WheelView wheelView;

    private int mCmdId;
    //private Socket mSocket;
    private String mBulbIP;
    private int mBulbPort;
    //private BufferedOutputStream mBos;
    private BufferedReader mReader;
    private ProgressDialog mProgressDialog;

    private static final int MSG_CONNECT_SUCCESS = 0;
    private static final int MSG_CONNECT_FAILURE = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CONNECT_FAILURE:
                    mProgressDialog.dismiss();
                    //Toast.makeText(LightControlActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                    //connect();
                    break;
                case MSG_CONNECT_SUCCESS:
                    mProgressDialog.dismiss();
                    switchSmart();
                    //Toast.makeText(LightControlActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private static final String CMD_ON = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"on\",\"smooth\",1]}\r\n";
    private static final String CMD_OFF = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"off\",\"smooth\",1]}\r\n";
    private static final String CMD_BRIGHTNESS = "{\"id\":%id,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 100]}\r\n";
    private static final String CMD_HSV = "{\"id\":%id,\"method\":\"set_hsv\",\"params\":[%value, 100, \"smooth\", 200]}\r\n";
    private static final String CMD_START_CF = "{\"id\":%id,\"method\":\"start_cf\",\"params\":[0,1,\"%value\"]}\r\n";
    private static final String ColorFlow = "900,1,16711808,100,900,1,16711935,100,900,1,8388863,100,900,1,255,100,900,1,33023,100,900,1,65535,100,900,1,65408,100,900,1,65280,100,900,1,8453888,100,900,1,16776960,100,900,1,16744448,100,900,1,16711680,100";
    //private static final String ColorFlow = "200,1,16711680,100,200,1,16712960,100,200,1,16713984,100,200,1,16715008,100,200,1,16716032,100,200,1,16717312,100,200,1,16718336,100,200,1,16719360,100,200,1,16720384,100,200,1,16721664,100,200,1,16722688,100";
    // ,200,1,16717312,100,200,1,16718336,100,200,1,16719360,100,200,1,16720384,100,200,1,16721664,100,200,1,16722688,100,200,1,16723712,100,200,1,16724736,100,200,1,16726016,100,200,1,16727040,100,200,1,16728064,100,200,1,16729088,100,200,1,16730368,100,200,1,16731392,100,200,1,16732416,100,200,1,16733440,100,200,1,16734720,100,200,1,16735744,100,200,1,16736768,100,200,1,16737792,100,200,1,16739072,100,200,1,16740096,100,200,1,16741120,100,200,1,16742144,100,200,1,16743424,100,200,1,16744448,100,200,1,16745472,100,200,1,16746496,100,200,1,16747776,100,200,1,16748800,100,200,1,16749824,100,200,1,16750848,100,200,1,16752128,100,200,1,16753152,100,200,1,16754176,100,200,1,16755200,100,200,1,16756480,100,200,1,16757504,100,200,1,16758528,100,200,1,16759552,100,200,1,16760832,100,200,1,16761856,100,200,1,16762880,100,200,1,16763904,100,200,1,16765184,100,200,1,16766208,100,200,1,16767232,100,200,1,16768256,100,200,1,16769536,100,200,1,16770560,100,200,1,16771584,100,200,1,16772608,100,200,1,16773888,100,200,1,16774912,100,200,1,16775936,100,200,1,16776960,100,200,1,16514816,100,200,1,16252672,100,200,1,15990528,100,200,1,15728384,100,200,1,15400704,100,200,1,15138560,100,200,1,14876416,100,200,1,14614272,100,200,1,14286592,100,200,1,14024448,100,200,1,13762304,100,200,1,13500160,100,200,1,13172480,100,200,1,12910336,100,200,1,12648192,100,200,1,12386048,100,200,1,12058368,100,200,1,11796224,100,200,1,11534080,100,200,1,11271936,100,200,1,10944256,100,200,1,10682112,100,200,1,10419968,100,200,1,10157824,100,200,1,9830144,100,200,1,9568000,100,200,1,9305856,100,200,1,9043712,100,200,1,8716032,100,200,1,8453888,100,200,1,8191744,100,200,1,7929600,100,200,1,7601920,100,200,1,7339776,100,200,1,7077632,100,200,1,6815488,100,200,1,6487808,100,200,1,6225664,100,200,1,5963520,100,200,1,5701376,100,200,1,5373696,100,200,1,5111552,100,200,1,4849408,100,200,1,4587264,100,200,1,4259584,100,200,1,3997440,100,200,1,3735296,100,200,1,3473152,100,200,1,3145472,100,200,1,2883328,100,200,1,2621184,100,200,1,2359040,100,200,1,2031360,100,200,1,1769216,100,200,1,1507072,100,200,1,1244928,100,200,1,917248,100,200,1,655104,100,200,1,392960,100,200,1,65280,100,200,1,65285,100,200,1,65289,100,200,1,65293,100,200,1,65297,100,200,1,65302,100,200,1,65306,100,200,1,65310,100,200,1,65314,100,200,1,65319,100,200,1,65323,100,200,1,65327,100,200,1,65331,100,200,1,65336,100,200,1,65340,100,200,1,65344,100,200,1,65348,100,200,1,65353,100,200,1,65357,100,200,1,65361,100,200,1,65365,100,200,1,65370,100,200,1,65374,100,200,1,65378,100,200,1,65382,100,200,1,65387,100,200,1,65391,100,200,1,65395,100,200,1,65399,100,200,1,65404,100,200,1,65408,100,200,1,65412,100,200,1,65416,100,200,1,65421,100,200,1,65425,100,200,1,65429,100,200,1,65433,100,200,1,65438,100,200,1,65442,100,200,1,65446,100,200,1,65450,100,200,1,65455,100,200,1,65459,100,200,1,65463,100,200,1,65467,100,200,1,65472,100,200,1,65476,100,200,1,65480,100,200,1,65484,100,200,1,65489,100,200,1,65493,100,200,1,65497,100,200,1,65501,100,200,1,65506,100,200,1,65510,100,200,1,65514,100,200,1,65518,100,200,1,65523,100,200,1,65527,100,200,1,65531,100,200,1,65535,100,200,1,64511,100,200,1,63487,100,200,1,62463,100,200,1,61439,100,200,1,60159,100,200,1,59135,100,200,1,58111,100,200,1,57087,100,200,1,55807,100,200,1,54783,100,200,1,53759,100,200,1,52735,100,200,1,51455,100,200,1,50431,100,200,1,49407,100,200,1,48383,100,200,1,47103,100,200,1,46079,100,200,1,45055,100,200,1,44031,100,200,1,42751,100,200,1,41727,100,200,1,40703,100,200,1,39679,100,200,1,38399,100,200,1,37375,100,200,1,36351,100,200,1,35327,100,200,1,34047,100,200,1,33023,100,200,1,31999,100,200,1,30975,100,200,1,29695,100,200,1,28671,100,200,1,27647,100,200,1,26623,100,200,1,25343,100,200,1,24319,100,200,1,23295,100,200,1,22271,100,200,1,20991,100,200,1,19967,100,200,1,18943,100,200,1,17919,100,200,1,16639,100,200,1,15615,100,200,1,14591,100,200,1,13567,100,200,1,12287,100,200,1,11263,100,200,1,10239,100,200,1,9215,100,200,1,7935,100,200,1,6911,100,200,1,5887,100,200,1,4863,100,200,1,3583,100,200,1,2559,100,200,1,1535,100,200,1,255,100,200,1,327935,100,200,1,590079,100,200,1,852223,100,200,1,1114367,100,200,1,1442047,100,200,1,1704191,100,200,1,1966335,100,200,1,2228479,100,200,1,2556159,100,200,1,2818303,100,200,1,3080447,100,200,1,3342591,100,200,1,3670271,100,200,1,3932415,100,200,1,4194559,100,200,1,4456703,100,200,1,4784383,100,200,1,5046527,100,200,1,5308671,100,200,1,5570815,100,200,1,5898495,100,200,1,6160639,100,200,1,6422783,100,200,1,6684927,100,200,1,7012607,100,200,1,7274751,100,200,1,7536895,100,200,1,7799039,100,200,1,8126719,100,200,1,8388863,100,200,1,8651007,100,200,1,8913151,100,200,1,9240831,100,200,1,9502975,100,200,1,9765119,100,200,1,10027263,100,200,1,10354943,100,200,1,10617087,100,200,1,10879231,100,200,1,11141375,100,200,1,11469055,100,200,1,11731199,100,200,1,11993343,100,200,1,12255487,100,200,1,12583167,100,200,1,12845311,100,200,1,13107455,100,200,1,13369599,100,200,1,13697279,100,200,1,13959423,100,200,1,14221567,100,200,1,14483711,100,200,1,14811391,100,200,1,15073535,100,200,1,15335679,100,200,1,15597823,100,200,1,15925503,100,200,1,16187647,100,200,1,16449791,100,200,1,16711935,100,200,1,16711931,100,200,1,16711927,100,200,1,16711923,100,200,1,16711919,100,200,1,16711914,100,200,1,16711910,100,200,1,16711906,100,200,1,16711902,100,200,1,16711897,100,200,1,16711893,100,200,1,16711889,100,200,1,16711885,100,200,1,16711880,100,200,1,16711876,100,200,1,16711872,100,200,1,16711868,100,200,1,16711863,100,200,1,16711859,100,200,1,16711855,100,200,1,16711851,100,200,1,16711846,100,200,1,16711842,100,200,1,16711838,100,200,1,16711834,100,200,1,16711829,100,200,1,16711825,100,200,1,16711821,100,200,1,16711817,100,200,1,16711812,100,200,1,16711808,100,200,1,16711804,100,200,1,16711800,100,200,1,16711795,100,200,1,16711791,100,200,1,16711787,100,200,1,16711783,100,200,1,16711778,100,200,1,16711774,100,200,1,16711770,100,200,1,16711766,100,200,1,16711761,100,200,1,16711757,100,200,1,16711753,100,200,1,16711749,100,200,1,16711744,100,200,1,16711740,100,200,1,16711736,100,200,1,16711732,100,200,1,16711727,100,200,1,16711723,100,200,1,16711719,100,200,1,16711715,100,200,1,16711710,100,200,1,16711706,100,200,1,16711702,100,200,1,16711698,100,200,1,16711693,100,200,1,16711689,100,200,1,16711685,100,200,1,16711680,100";
    private static final String CMD_RGB = "{\"id\":%id,\"method\":\"set_rgb\",\"params\":[%value, \"smooth\", 200]}\r\n";
    private static final String CMD_STOP_CF = "{\"id\":%id,\"method\":\"stop_cf\",\"params\":[]}\r\n";

    //Device selectedDevice;
    int position;

    boolean runCommand = true;

    int colorSat = 100;
    int colorVal = 50;
    int colorAlpha = 255;

    JSONObject jsonState;

    PreferencesManager preferencesManager;

    //int customerItemId;

    DBHelper dbh;

    Items item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#033399"));
        }*/

        preferencesManager = new PreferencesManager(this);

        dbh = new DBHelper(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        item = finalDevicesList.get(position);
        //selectedDevice = finalDevicesList.get(position);
        //selectedDevice = (Device) bundle.get("selectedDevice");
        //customerItemId = selectedDevice.getCustomerItemId();
        //mBulbIP = selectedDevice.getIp();
        //mBulbPort = Integer.valueOf(selectedDevice.getPort());

        //mSocket = selectedDevice.getSocket();
        //mBos = selectedDevice.getBos();

        mProgressDialog = new ProgressDialog(this);

        //connect();

        color = Color.argb(255, (int) r, (int) g, (int) b);

        handler = new Handler();

        menu = (ImageView) findViewById(R.id.menu);
        emoTv = (TextView) findViewById(R.id.emo_tv);

        smartLightBtnTv = (TextView) findViewById(R.id.smart_light_btn_tv);
        moodyBtnTv = (TextView) findViewById(R.id.moody_btn_tv);
        moodyCard = (CardView) findViewById(R.id.moody_card);

        seekBarBrightness = (SeekBar) findViewById(R.id.seek_bar_brightness);
        seekBarColor = (SeekBar) findViewById(R.id.seek_bar_color);

        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.seekbar_color_thumb);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.color);
        gradientDrawable.setColor(Color.RED);
        seekBarColor.setThumb(layerDrawable);
        seekBarColor.setProgress(100);

        powerIv = (ImageView) findViewById(R.id.power_btn);
        favouriteIv = (ImageView) findViewById(R.id.favourite_btn);
        cycleIv = (ImageView) findViewById(R.id.cycle_btn);

        favTv = (TextView) findViewById(R.id.fav_tv);
        cycleTv = (TextView) findViewById(R.id.cycle_tv);

        rootRL = (RelativeLayout) findViewById(R.id.activity_light_control);
        brightnessSeekRL = (RelativeLayout) findViewById(R.id.brightness_seek_rl);
        wheelRL = (RelativeLayout) findViewById(R.id.wheel_rl);
        colorWheelRL = (RelativeLayout) findViewById(R.id.color_wheel_rl);

        smartLightBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controlTypeToggle == 1){

                    cycleToggle = 0;

                    switchSmart();

                    //moodyBtnTv.setPadding(moodyBtnTv.getPaddingLeft(), moodyBtnTv.getPaddingTop()+200, moodyBtnTv.getPaddingRight(), moodyBtnTv.getPaddingBottom()+200);

                    smartLightBtnTv.setBackground(getResources().getDrawable(R.drawable.smart_light_btn_bg));
                    moodyBtnTv.setBackground(getResources().getDrawable(R.drawable.disabled_btn_bg));

                    if (Build.VERSION.SDK_INT >= 21) {
                        smartLightBtnTv.setElevation(20f);
                        moodyCard.setCardElevation(0);
                    }

                    /*YoYo.with(Techniques.BounceIn)
                            .duration(300)
                            .playOn(smartLightBtnTv);*/

                    favouriteIv.setVisibility(View.GONE);
                    cycleIv.setVisibility(View.GONE);

                    favTv.setVisibility(View.GONE);
                    cycleTv.setVisibility(View.GONE);

                    brightnessSeekRL.setVisibility(View.VISIBLE);
                    wheelRL.setVisibility(View.GONE);
                    colorWheelRL.setVisibility(View.GONE);
                    seekBarColor.setVisibility(View.GONE);

                    controlTypeToggle = 0;

                    handler.removeCallbacksAndMessages(true);
                    cycleIv.setRotation(0);

                    rootRL.setBackground(getResources().getDrawable(R.drawable.smart_light_bg));
                }
            }
        });

        moodyBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controlTypeToggle == 0){

                    switchMoody();

                    //moodyBtnTv.setPadding(moodyBtnTv.getPaddingLeft(), moodyBtnTv.getPaddingTop()-200, moodyBtnTv.getPaddingRight(), moodyBtnTv.getPaddingBottom()-200);

                    moodyBtnTv.setBackground(getResources().getDrawable(R.drawable.moody_btn_gb));
                    smartLightBtnTv.setBackground(getResources().getDrawable(R.drawable.disabled_btn_bg));

                    if (Build.VERSION.SDK_INT >= 21) {
                        moodyCard.setCardElevation(20f);
                        smartLightBtnTv.setElevation(0);
                    }

                    /*YoYo.with(Techniques.BounceIn)
                            .duration(300)
                            .playOn(moodyBtnTv);*/

                    favouriteIv.setVisibility(View.VISIBLE);
                    cycleIv.setVisibility(View.VISIBLE);

                    favTv.setVisibility(View.VISIBLE);
                    cycleTv.setVisibility(View.VISIBLE);

                    brightnessSeekRL.setVisibility(View.GONE);
                    wheelRL.setVisibility(View.VISIBLE);
                    colorWheelRL.setVisibility(View.VISIBLE);
                    seekBarColor.setVisibility(View.VISIBLE);

                    gradient = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, new int[] { Color.argb(255, (int)r, (int)g, (int)b), getResources().getColor(R.color.white)
                    });

                    rootRL.setBackground(gradient);

                    controlTypeToggle = 1;
                }
            }
        });

        powerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*YoYo.with(Techniques.BounceIn)
                        .duration(300)
                        .playOn(powerIv);*/

                if (powerToggle == 0) {

                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseSwitch(true), 1, 1, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }

                    findViewById(R.id.buttons_ll).setVisibility(View.VISIBLE);

                    if (controlTypeToggle == 0){
                        brightnessSeekRL.setVisibility(View.VISIBLE);
                        wheelRL.setVisibility(View.GONE);
                        colorWheelRL.setVisibility(View.GONE);

                        rootRL.setBackground(getResources().getDrawable(R.drawable.smart_light_bg));

                    }
                    else {
                        brightnessSeekRL.setVisibility(View.GONE);
                        wheelRL.setVisibility(View.VISIBLE);
                        colorWheelRL.setVisibility(View.VISIBLE);
                        seekBarColor.setVisibility(View.VISIBLE);

                        favouriteIv.setVisibility(View.VISIBLE);
                        cycleIv.setVisibility(View.VISIBLE);

                        favTv.setVisibility(View.VISIBLE);
                        cycleTv.setVisibility(View.VISIBLE);

                        gradient = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM, new int[]{color, getResources().getColor(R.color.white)
                        });
                        rootRL.setBackground(gradient);
                    }

                    powerToggle = 1;

                }
                else {
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseSwitch(false), 1, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }

                    brightnessSeekRL.setVisibility(View.GONE);
                    wheelRL.setVisibility(View.GONE);
                    colorWheelRL.setVisibility(View.GONE);
                    seekBarColor.setVisibility(View.GONE);

                    favouriteIv.setVisibility(View.GONE);
                    cycleIv.setVisibility(View.GONE);

                    favTv.setVisibility(View.GONE);
                    cycleTv.setVisibility(View.GONE);

                    gradient = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.BLACK, getResources().getColor(R.color.white)
                    });
                    rootRL.setBackground(gradient);
                    powerToggle = 0;

                    findViewById(R.id.buttons_ll).setVisibility(View.GONE);

                    cycleToggle = 0;
                    handler.removeCallbacksAndMessages(true);
                    cycleIv.setRotation(0);
                }
            }
        });

        favouriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNamePopUp();
            }
        });

        cycleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cycleToggle == 0){
                    cycleToggle = 1;
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseCFCmd(), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                    cycle();
                }
                else {
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(CMD_STOP_CF.replace("%id", String.valueOf(++mCmdId)), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                    cycleToggle = 0;
                    handler.removeCallbacksAndMessages(true);
                    cycleIv.setRotation(0);
                    findViewById(R.id.rot).setRotation(0);

                    colorAngle = 0;
                    color = Color.argb(255, 255, 0, 0);
                    r = 255;
                    g = 0;
                    b = 0;

                    final float[] hsv2 = new float[3];
                    Color.colorToHSV(color, hsv2);
                    //write(parseColorCmd((int) hsv2[0]), 0, 0);
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseRGBCmd(msAccessColor(color)), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }

                    changeSeekBarColor();
                }
            }
        });

        wheelView = (WheelView) findViewById(R.id.wheelview);

        //rgb color
        wheelView.setOnWheelAngleChangeListener(new WheelView.OnWheelAngleChangeListener() {
            @Override
            public void onWheelAngleChange(float angle) {

                colorAngle = Math.round(angle*3);
                colorAngle = -colorAngle;

                if (colorAngle > 360){
                    createColors(colorAngle % 360, 0);
                    findViewById(R.id.rot).setRotation(-colorAngle % 360);
                }
                else if (colorAngle < -360){
                    createColors(colorAngle % 360, 0);
                    findViewById(R.id.rot).setRotation(-colorAngle % 360);
                }
                else {
                    createColors(colorAngle, 0);
                    findViewById(R.id.rot).setRotation(-colorAngle);
                }

            }
        });

        //easy color
        /*wheelView.setOnWheelAngleChangeListener(new WheelView.OnWheelAngleChangeListener() {
            @Override
            public void onWheelAngleChange(float angle) {

                colorAngle = Math.round(angle*5);
                colorAngle = -colorAngle;

                if (colorAngle > 360){
                    easyColors(colorAngle % 360);
                    findViewById(R.id.rot).setRotation(-colorAngle % 360);

                }
                else if(colorAngle < 0){
                    if (colorAngle < -360){
                        colorAngle = colorAngle % 360;
                    }
                    colorAngle = 360-colorAngle;
                    easyColors(colorAngle);
                }
                else {
                    easyColors(colorAngle);
                    findViewById(R.id.rot).setRotation(-colorAngle);
                }
            }
        });*/

        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                /*colorValue = scale(progress, 0, 100, 10, 100);
                colorValue = colorValue / 100f;
                changeColorBrightness();*/

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                colorSat = seekBar.getProgress();

                colorVal = ((100 - colorSat) + (colorSat/2));

                colorAlpha = (int) scale(seekBar.getProgress(), 0, 100, 120, 255);

                changeColorBrightness();

                if (seekBar.getProgress() <= 0){
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseBrightnessCmd(1), 3, 1, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                }
                else {
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseBrightnessCmd(seekBar.getProgress()), 3, seekBar.getProgress(), item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                }

            }
        });

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() <= 0){
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseBrightnessCmd(1), 3, 1, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                }
                else {
                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseBrightnessCmd(1), 3, 1, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }
                }
                /*if (seekBar.getProgress() < 20) {
                    seekBarBrightness.setThumbOffset(25);
                }
                else {
                    seekBarBrightness.setThumbOffset(10);
                }*/
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType().equals("device")) {
                    Intent intent = new Intent(LightControlActivity.this, LightMenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, 0);
                }
                else {
                    /*Intent intent = new Intent(LightControlActivity.this, GroupSettingsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    intent.putExtras(bundle);
                    startActivity(intent);*/

                    Group group = new Group();
                    group.setName(item.getName());

                    Intent intent = new Intent(LightControlActivity.this, GroupSettingsActivity.class);
                    intent.putExtra("group", group);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, 0);
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //retainColor();

    }

    public void createColors(int angle, int cycle){

        if (angle >= 0) {
            if (angle <= 60) {
                r = 255;
                g = angle * 4.25;
                b = 0;
            } else if (angle > 60 && angle <= 120) {
                r = 255 - ((angle % 60) * 4.25);

                if (angle == 120) {
                    r = 0;
                }
            } else if (angle > 120 && angle <= 180) {
                b = (angle % 120) * 4.25;
            } else if (angle > 180 && angle <= 240) {
                g = 255 - ((angle % 180) * 4.25);

                if (angle == 240) {
                    g = 0;
                }
            } else if (angle > 240 && angle <= 300) {
                r = (angle % 240) * 4.25;
            } else if (angle > 300 && angle <= 360) {
                b = 255 - ((angle % 300) * 4.25);

                if (angle == 360) {
                    b = 0;
                }
            }
        }
        else {
            if (angle >= -60) {
                b = (angle/-1) * 4.25;
                r = 255;
                g = 0;

            } else if (angle < -60 && angle >= -120) {
                r = 255 + ((angle % 60) * 4.25);

                if (angle == -120) {
                    r = 0;
                }
            } else if (angle < -120 && angle >= -180) {
                g = -(angle % 120) * 4.25;
            } else if (angle < -180 && angle >= -240) {
                b = 255 + ((angle % 180) * 4.25);

                if (angle == -240) {
                    b = 0;
                }
            } else if (angle < -240 && angle >= -300) {
                r = -(angle % 240) * 4.25;
            } else if (angle < -300 && angle >= -360) {
                g = 255 + ((angle % 300) * 4.25);

                if (angle == -360) {
                    g = 0;
                }
            }
        }

        color = Color.argb(colorAlpha, (int) r, (int) g, (int) b);
        emoTv.setText(String.valueOf(wheelView.getAngle()));

        gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[] { color, getResources().getColor(R.color.white)
        });

        changeSeekBarColor();

        rootRL.setBackground(gradient);

        final float[] hsv2 = new float[3];
        Color.colorToHSV(color, hsv2);

        if (cycle == 0) {
            if (runCommand) {
                runCommand = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //write(parseColorCmd((int) hsv2[0]), 2, color);
                        for (int i = 0; i < item.getDevices().size(); i++) {
                            write(parseRGBCmd(msAccessColor(color)), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                        }
                        runCommand = true;
                    }
                }, 500);
            }
        }

    }

    /*public void easyColors(int angle){
        color = Color.argb(255, (int) r, (int) g, (int) b);

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = angle;
        hsv[2] = colorValue; //value component
        color = Color.HSVToColor(hsv);

        gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[] { color, getResources().getColor(R.color.white)
        });

        rootRL.setBackground(gradient);

        write(parseColorCmd(angle), 2, color);
    }*/

    public void changeColorBrightness(){

        color = Color.argb(colorAlpha, Color.red(color), Color.green(color), Color.blue(color));

        r = Color.red(color);
        g = Color.green(color);
        b = Color.blue(color);

        gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[] { color, getResources().getColor(R.color.white)
        });

        changeSeekBarColor();

        rootRL.setBackground(gradient);

        final float[] hsv2 = new float[3];
        Color.colorToHSV(color, hsv2);

        /*if (runCommand) {
            runCommand = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    write(parseColorCmd((int) hsv2[0]), 2, 167);
                    runCommand = true;
                }
            }, 500);
        }*/
    }

    public void cycle(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (cycleToggle == 1) {

                    colorAngle += 1;

                    if (colorAngle > 360 || colorAngle < -360){
                        colorAngle = colorAngle % 360;
                    }

                    createColors(-colorAngle, 1);
                    findViewById(R.id.rot).setRotation(colorAngle);
                    cycleIv.setRotation(colorAngle);

                    cycle();
                }
            }
        }, 30);
    }

    public float scale(final float valueIn, final float baseMin, final float baseMax, final float limitMin, final float limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }

    /*private void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(mBulbIP, mBulbPort);
                    mSocket.setKeepAlive(true);
                    mBos = new BufferedOutputStream(mSocket.getOutputStream());
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
    }*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if (mSocket!=null)
                mSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/

    private String parseSwitch(boolean on){
        String cmd;
        if (on){
            cmd = CMD_ON.replace("%id", String.valueOf(++mCmdId));
        }else {
            cmd = CMD_OFF.replace("%id", String.valueOf(++mCmdId));
        }
        return cmd;
    }

    /*private String parseColorCmd(int color){
        return CMD_HSV.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(color));
    }*/

    private String parseRGBCmd(int color){
        return CMD_RGB.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(color));
    }

    private String parseBrightnessCmd(int brightness){
        return CMD_BRIGHTNESS.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(brightness));
    }

    private String parseCFCmd(){
        return CMD_START_CF.replace("%id",String.valueOf(++mCmdId)).replace("%value",ColorFlow);
    }

    private void write(String cmd, final int attributeId, int attributeValue, final Socket mSocket, final BufferedOutputStream mBos ){
        if (mBos != null && mSocket.isConnected()){
            try {
                mBos.write(cmd.getBytes());
                mBos.flush();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                            String line = br.readLine();
                            Log.d("Cmd", "Response:" + line);

                            if (attributeId != 0) {
                                //    saveState(attributeId, attributeValue);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //new Save().execute(cmd, String.valueOf(attributeId), String.valueOf(attributeValue));
    }

    public void switchSmart(){
        //write(CMD_STOP_CF.replace("%id", String.valueOf(++mCmdId)), 0, 0);

        for (int i = 0; i < item.getDevices().size(); i++) {
            write(parseBrightnessCmd(seekBarBrightness.getProgress()), 3, seekBarBrightness.getProgress(), item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
        }

        //String CMD_HSV = "{\"id\":1,\"method\":\"set_hsv\",\"params\":[0, 0, \"smooth\", 30]}\r\n";
        //write(CMD_HSV, 0, 0);

        for (int i = 0; i < item.getDevices().size(); i++) {
            write(parseRGBCmd(msAccessColor(Color.WHITE)), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
        }

    }

    public void switchMoody(){
        for (int i = 0; i < item.getDevices().size(); i++) {
            write(parseBrightnessCmd(seekBarColor.getProgress()), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
        }


        /*final float[] hsv2 = new float[3];
        Color.colorToHSV(color, hsv2);
        write(parseColorCmd((int) hsv2[0]), 0, 0);*/
        for (int i = 0; i < item.getDevices().size(); i++) {
            write(parseRGBCmd(msAccessColor(color)), 0, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
        }


    }

    public void changeSeekBarColor(){

        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.seekbar_color_thumb);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.color);
        gradientDrawable.setColor(color);

        seekBarColor.setThumb(layerDrawable);

        seekBarColor.setProgress(colorSat);
    }

    /*public void saveState(int attributeId, int attributeValue){

        jsonState = new JSONObject();

        try {
            jsonState.put("customerItemId", customerItemId);
            jsonState.put("itemAttributeId", attributeId);
            jsonState.put("attributeValue", attributeValue);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String url = Constants.URL + "device/save/attribute/state";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonState,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response---->", "" + response);
                        mProgressDialog.dismiss();

                        Toast.makeText(LightControlActivity.this, "Updated", Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(LightControlActivity.this, "Failed", Toast.LENGTH_SHORT).show();

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
    }*/

    /*private class Save extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String...params) {

            if (mBos != null && mSocket.isConnected()){
                try {
                    mBos.write(params[0].getBytes());
                    mBos.flush();

                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

                    char[] buffer = new char[1024];
                    int read = 0;
                    StringBuilder sb = new StringBuilder();

                    while ((read = mReader.read(buffer, 0, buffer.length)) > 0) {
                        sb.append(buffer, 0, read);
                        // conduct some test that when passes marks the end of message, then break;
                    }
                    mReader.close();

                    String str = sb.toString().trim();


                    if (Integer.valueOf(params[1]) != 0) {
                        saveState(Integer.valueOf(params[1]), Integer.valueOf(params[2]));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return "a";

        }

        @Override
        protected void onPostExecute(String unused) {
        }
    }*/

    /*public void retainColor(){

        if (selectedDevice.getRgb() != 16777215) {
            r = floor(selectedDevice.getRgb() / (256 * 256));
            g = floor((selectedDevice.getRgb() - r * 256 * 256) / 256);
            b = selectedDevice.getRgb() - r * 256 * 256 - g * 256;

            color = Color.argb(255, (int) r, (int) g, (int) b);
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);

            int angle = (int) (360 - (int) hsv[0]) / 3;
            wheelView.setAngle(angle);

            //
            moodyBtnTv.setBackground(getResources().getDrawable(R.drawable.moody_btn_gb));
            smartLightBtnTv.setBackground(getResources().getDrawable(R.drawable.disabled_btn_bg));

            if (Build.VERSION.SDK_INT >= 21) {
                moodyCard.setCardElevation(20f);
                smartLightBtnTv.setElevation(0);
            }

            favouriteIv.setVisibility(View.VISIBLE);
            cycleIv.setVisibility(View.VISIBLE);

            favTv.setVisibility(View.VISIBLE);
            cycleTv.setVisibility(View.VISIBLE);

            brightnessSeekRL.setVisibility(View.GONE);
            wheelRL.setVisibility(View.VISIBLE);
            colorWheelRL.setVisibility(View.VISIBLE);
            seekBarColor.setVisibility(View.VISIBLE);

            controlTypeToggle = 1;

            seekBarColor.setProgress(selectedDevice.getBrightness());
        }
        else {
            seekBarBrightness.setProgress(selectedDevice.getBrightness());
        }
    }*/

    public int msAccessColor(int color){
        return (65536 * Color.red(color) + 256 * Color.green(color) + Color.blue(color));
    }

    public void showNamePopUp(){

        final LayoutInflater inflater = this.getLayoutInflater();

        View layout = inflater.inflate(R.layout.rename_device_dialog, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(false);

        final AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.white_btn_bg);

        final EditText nameEt = (EditText) layout.findViewById(R.id.name_et);
        nameEt.setHint("Favourite name");
        nameEt.setSelection(nameEt.getText().length());

        TextView saveTv = (TextView) layout.findViewById(R.id.save_btn_tv);

        TextView cancelTv = (TextView) layout.findViewById(R.id.cancel_btn_tv);

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEt.getText().toString().length() == 0){
                    Toast.makeText(LightControlActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (favList.size() <= 4) {
                        Scene fav = new Scene();
                        fav.setName(nameEt.getText().toString());
                        fav.setType("fav");
                        fav.setColor(String.format("#%06X", (0xFFFFFF & color)));
                        fav.setBrightness(seekBarColor.getProgress());
                        dbh.addFav(fav);
                        Toast.makeText(LightControlActivity.this, "Save success", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                    else {
                        Toast.makeText(LightControlActivity.this, "Already 5 favourites added", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                }

            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });

        alert.show();

    }
}
