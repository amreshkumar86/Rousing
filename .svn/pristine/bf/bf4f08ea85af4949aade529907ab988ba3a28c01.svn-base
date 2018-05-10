package com.niveus.oen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.R;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

public class DefaultStateActivity extends AppCompatActivity {

    SeekBar seekBarSwitch;

    int seekBarSwitchProgress = 0;

    TextView switchTextTv;

    View colorPickerView;

    int color;
    int colorAngle = 0;

    RelativeLayout currentDefaultRL;

    //Device selectedDevice;
    Items item;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_state);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        item = finalDevicesList.get(position);

        color = Color.argb(255, 255, 0, 0);

        seekBarSwitch = (SeekBar) findViewById(R.id.seek_bar_switch);
        switchTextTv = (TextView) findViewById(R.id.switch_text_tv);

        currentDefaultRL = (RelativeLayout) findViewById(R.id.current_default_rl);
        colorPickerView = (View) findViewById(R.id.color_picker_v);

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

                        currentDefaultRL.setAlpha(0.2f);
                        colorPickerView.setClickable(false);
                    }

                }
                else {
                    seekBarSwitch.setProgress(0);
                    switchTextTv.setText("Off");

                    currentDefaultRL.setAlpha(1f);
                    colorPickerView.setClickable(true);
                }

                if(seekBar.getProgress() < 10) {
                    if (seekBarSwitchProgress == 1) {
                        seekBarSwitch.setProgress(0);
                        seekBarSwitchProgress = 0;
                        switchTextTv.setText("Off");

                        currentDefaultRL.setAlpha(1f);
                        colorPickerView.setClickable(true);
                    }
                }
                else {
                    seekBarSwitch.setProgress(100);
                    switchTextTv.setText("On");

                    currentDefaultRL.setAlpha(0.2f);
                    colorPickerView.setClickable(false);

                }
            }
        });

        colorPickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefaultStateActivity.this, ColorPickerActivity.class);
                intent.putExtra("color", color);
                intent.putExtra("angle", colorAngle);
                startActivityForResult(intent, 0);
            }
        });

        setPickerColor();
    }

    public void setPickerColor(){
        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.seekbar_color_thumb);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.color);
        gradientDrawable.setColor(color);
        colorPickerView.setBackground(layerDrawable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                color = (data.getIntExtra("color", 0));
                colorAngle = (data.getIntExtra("angle", 0));
                setPickerColor();
            }
        }
    }
}
