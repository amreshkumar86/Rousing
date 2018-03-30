package com.rousing.oen.rousing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rousing.mcu.connector.McuConnector;

public class PresetActivity extends AppCompatActivity {

    McuConnector connector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String deviceType = getIntent().getStringExtra("DEVICE_TYPE");
        if(deviceType.equalsIgnoreCase("LIFX")) {
            connector = McuConnector.getSharedConnectorLifx(null);
        }
        else {
            connector = McuConnector.getSharedConnectorStrip(null,null);
        }
    }

    public void handleButtonClick(View v) {
        if (v.getId() == R.id.effect1) {
            connector.setEffect(1);
        }
        else if (v.getId() == R.id.effect2) {
            connector.setEffect(2);
        }
        else if (v.getId() == R.id.effect3) {
            connector.setEffect(3);
        }
        else if (v.getId() == R.id.effect4) {
            connector.setEffect(4);
        }
        else if (v.getId() == R.id.effect5) {
            connector.setEffect(5);
        }
    }

}
