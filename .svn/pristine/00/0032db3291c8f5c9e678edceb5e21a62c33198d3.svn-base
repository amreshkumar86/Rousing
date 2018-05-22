package com.niveus.oen.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.niveus.oen.R;

public class SettingsActivity extends AppCompatActivity {

    TextView serverSelectionTv, lanControlTv, checkUpdatesTv, aboutTv;
    ImageView updateIv;

    SeekBar seekBarSwitch;

    int seekBarSwitchProgress = 0;

    TextView switchTextTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#033399"));
        }*/

        seekBarSwitch = (SeekBar) findViewById(R.id.seek_bar_switch);

        switchTextTv = (TextView) findViewById(R.id.switch_text_tv);

        serverSelectionTv = (TextView) findViewById(R.id.server_selection_tv);
        lanControlTv = (TextView) findViewById(R.id.lan_control_tv);
        checkUpdatesTv = (TextView) findViewById(R.id.check_updates_tv);
        aboutTv = (TextView) findViewById(R.id.about_tv);

        updateIv = (ImageView) findViewById(R.id.update_iv);

        lanControlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        updateIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotate = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.rotate);
                rotate.setDuration(4000);
                updateIv.startAnimation(rotate);

                checkUpdatesTv.setText("Checking...");

            }
        });

        checkUpdatesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotate = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.rotate);
                rotate.setDuration(4000);
                updateIv.performClick();

                checkUpdatesTv.setText("Checking...");
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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}