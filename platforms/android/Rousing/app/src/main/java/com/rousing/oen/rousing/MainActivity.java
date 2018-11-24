package com.rousing.oen.rousing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import com.rousing.mcu.connector.*;

public class MainActivity extends AppCompatActivity {

    Button colorButton;
    Button smartLightButton;
    Button settingButton;
    ToggleButton powerButton;
    SeekBar brightnessBar;
    McuConnector connector;
    String deviceType = "STRIP";
    String deviceIP = null;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        deviceType = getIntent().getStringExtra("DEVICE_TYPE");
        deviceIP = getIntent().getStringExtra("DEVICE_IP");
        addListenerOnButton();
        if(deviceType.equalsIgnoreCase("LIFX")) {
            connector = McuConnector.getSharedConnectorLifx(null);
           String powerState = getIntent().getStringExtra("bulbPowerState");
           powerButton.setChecked(powerState.equalsIgnoreCase("on"));
           double brightVal = getIntent().getDoubleExtra("bulbBrightnessValue",0.0);
           brightnessBar.setProgress((int)(brightVal*100));
           smartLightButton.setVisibility(View.INVISIBLE);
        }
        else {
            connector = McuConnector.getSharedConnectorStrip(MainActivity.this,deviceIP);
            boolean wasChecked = pref.getBoolean("STRIP_POWER",false);
            powerButton.setChecked(wasChecked);
            double brightVal = pref.getFloat("STRIP_BRIGHT",100);
            brightnessBar.setProgress((int)(brightVal));
            brightnessBar.setEnabled(wasChecked);
            colorButton.setEnabled(wasChecked);
            smartLightButton.setEnabled(wasChecked);
        }
    }

    public void addListenerOnButton() {

        final Context context = this;

        colorButton = (Button) findViewById(R.id.moodyButton);

        colorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ColorActivity.class);
                intent.putExtra("DEVICE_TYPE", deviceType);
                intent.putExtra("DEVICE_IP",deviceIP);
                startActivity(intent);

            }

        });

        smartLightButton = (Button) findViewById(R.id.smartLightButton);

        smartLightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, PresetActivity.class);
                intent.putExtra("DEVICE_TYPE", deviceType);
                intent.putExtra("DEVICE_IP",deviceIP);
                startActivity(intent);

            }

        });

        settingButton = (Button) findViewById(R.id.settingButton);

        settingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);

            }

        });

        powerButton = (ToggleButton) findViewById(R.id.powerButton);
        powerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.d("power value:",""+checked);
                brightnessBar.setEnabled(checked);
                colorButton.setEnabled(checked);
                smartLightButton.setEnabled(checked);
                if(checked == true ) {
                    connector.switchOn();
                }
                else {
                    connector.switchOff();
                }
                if(deviceType.equalsIgnoreCase("STRIP")){
                    editor.putBoolean("STRIP_POWER", checked);
                    editor.commit();
                }
            }
        });

        brightnessBar = (SeekBar) findViewById(R.id.brightnessBar);
        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("brightness value:",""+i);
                if(!deviceType.equalsIgnoreCase("LIFX")){
                    connector.setBrightness(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(deviceType.equalsIgnoreCase("LIFX")){
                    connector.setBrightness(seekBar.getProgress());
                }

                if(deviceType.equalsIgnoreCase("STRIP")){
                    editor.putFloat("STRIP_BRIGHT", seekBar.getProgress());
                    editor.commit();
                }
            }
        });
    }
}
