package com.rousing.oen.rousing;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.rousing.mcu.connector.McuConnector;

public class ColorActivity extends AppCompatActivity {

    ColorPickerView colorPicker;
    McuConnector connector;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String deviceIP = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String deviceType = getIntent().getStringExtra("DEVICE_TYPE");
        colorPicker = (ColorPickerView)findViewById(R.id.cpv_color_picker_view);
        deviceIP = getIntent().getStringExtra("DEVICE_IP");
        int savedColor = -1;
        if(deviceType.equalsIgnoreCase("LIFX")) {
            connector = McuConnector.getSharedConnectorLifx(null);
            savedColor = pref.getInt("LIFX_COLOR",-1);
        }
        else {
            connector = McuConnector.getSharedConnectorStrip(ColorActivity.this,deviceIP);
            savedColor = pref.getInt("STRIP_COLOR",-1);
        }
        if(savedColor != -1) {
            int red=   (savedColor >> 16) & 0xFF;
            int green= (savedColor >> 8) & 0xFF;
            int blue=  (savedColor >> 0) & 0xFF;
            colorPicker.setColor(Color.argb(1,red,green,blue));
        }

        colorPicker.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                int red=   (newColor >> 16) & 0xFF;
                int green= (newColor >> 8) & 0xFF;
                int blue=  (newColor >> 0) & 0xFF;
                Log.d("New Color",""+red + ":"+green+":"+blue);
                connector.setColor(red,green,blue,0);
                String key = deviceType.equalsIgnoreCase("LIFX") ? "LIFX_COLOR" : "STRIP_COLOR";
                editor.putInt(key,newColor);
                editor.commit();
            }
        });
    }

}
