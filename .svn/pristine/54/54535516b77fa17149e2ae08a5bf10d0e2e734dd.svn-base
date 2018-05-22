package com.niveus.oen.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

public class LightMenuActivity extends AppCompatActivity {

    SeekBar seekBarSwitch;

    int seekBarSwitchProgress = 0;

    TextView switchTextTv;

    RelativeLayout nameRL;
    TextView nameTv;

    RelativeLayout defaultStateRL;
    RelativeLayout sleepTimerRL;

    //Device selectedDevice;
    Items item;
    int position;

    String time;
    TextView timeTv;
    ImageView timeIv;

    int mCmdId;
    private static final String CMD_CRON_GET = "{\"id\":%id,\"method\":\"cron_get\",\"params\":[0]}\r\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        item = finalDevicesList.get(position);

        //write(parseCronGet());

        nameRL = (RelativeLayout) findViewById(R.id.name_rl);
        nameTv = (TextView) findViewById(R.id.name_tv);

        nameTv.setText(item.getName());

        defaultStateRL = (RelativeLayout) findViewById(R.id.default_state_rl);
        sleepTimerRL = (RelativeLayout) findViewById(R.id.sleep_timer_rl);

        timeTv = (TextView) findViewById(R.id.time_tv);
        timeIv = (ImageView) findViewById(R.id.time_iv);

        seekBarSwitch = (SeekBar) findViewById(R.id.seek_bar_switch);

        switchTextTv = (TextView) findViewById(R.id.switch_text_tv);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });

        seekBarSwitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 90){
                    if (seekBarSwitchProgress == 0) {
                        seekBarSwitch.setProgress(100);
                        seekBarSwitchProgress = 1;
                        switchTextTv.setText("On");
                    }

                }
                else {
                    seekBarSwitch.setProgress(0);
                    switchTextTv.setText("Off");
                }

                if(seekBar.getProgress() < 10) {
                    if (seekBarSwitchProgress == 1) {
                        seekBarSwitch.setProgress(0);
                        seekBarSwitchProgress = 0;
                        switchTextTv.setText("Off");
                    }
                }
                else {
                    seekBarSwitch.setProgress(100);
                    switchTextTv.setText("On");
                }
            }
        });

        nameRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNamePopUp();
            }
        });

        defaultStateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LightMenuActivity.this, DefaultStateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        sleepTimerRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LightMenuActivity.this, SleepTimerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                time = (data.getStringExtra("time"));

                timeIv.setVisibility(View.GONE);
                timeTv.setVisibility(View.VISIBLE);
                timeTv.setText(time);
            }
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(0, R.anim.right_out);
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
        nameEt.setHint("Group name");
        nameEt.setText(nameTv.getText().toString());
        nameEt.setSelection(nameEt.getText().length());

        TextView saveTv = (TextView) layout.findViewById(R.id.save_btn_tv);

        TextView cancelTv = (TextView) layout.findViewById(R.id.cancel_btn_tv);

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEt.getText().toString().length() == 0){
                    Toast.makeText(LightMenuActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    nameTv.setText(nameEt.getText().toString());
                    alert.dismiss();

                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write("{\"id\":112,\"method\":\"set_name\",\"params\":[\""+nameEt.getText().toString()+"\"]}\r\n", item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
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

    private void write(String cmd, final Socket socket, final BufferedOutputStream bos){
        if (bos != null && socket.isConnected()){
            try {
                bos.write(cmd.getBytes());
                bos.flush();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String line = br.readLine();
                            Log.d("Cmd", "Response getttt:" + line);

                            //{"id":1, "result":[{"type": 0, "delay": 1, "mix": 0}]}

                            JSONObject jsonObject = new JSONObject(line);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");

                            if (jsonArray.length() != 0) {
                                JSONObject json2 = (JSONObject) jsonArray.get(0);
                                int delay = json2.getInt("delay");

                                showTime(delay);

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
    }

    public void showTime(int delay){
        //timeIv.setVisibility(View.GONE);
        //timeTv.setVisibility(View.VISIBLE);

        long millis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(delay);

        Date date = new Date();
        date.setTime(millis);
        int h = date.getHours();
        int m = date.getMinutes();

        timeTv.setText(String.valueOf(h) + ":" + String.valueOf(m));
        Log.d("Time", "Sleep at:" + String.valueOf(h) + ":" + String.valueOf(m));
    }

    private String parseCronGet(){
        return CMD_CRON_GET.replace("%id",String.valueOf(++mCmdId));
    }
}