package com.niveus.oen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lukedeighton.wheelview.WheelView;
import com.niveus.oen.R;
import com.niveus.oen.Utils.VerticalSeekBar;

public class ColorPickerActivity extends AppCompatActivity {

    int colorAngle;
    double r = 255, g = 0, b = 0;
    float colorValue = 1f;
    int color;

    VerticalSeekBar seekBarColor;
    WheelView wheelView;

    int colorSat = 100;
    int colorVal = 50;
    int colorAlpha = 255;

    View colorPickerView;

    TextView saveBtnTv, cancelBtnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        colorPickerView = (View) findViewById(R.id.color_picker_v);

        seekBarColor = (VerticalSeekBar) findViewById(R.id.seek_bar_color);

        saveBtnTv = (TextView) findViewById(R.id.save_btn_tv);
        cancelBtnTv = (TextView) findViewById(R.id.cancel_btn_tv);

        wheelView = (WheelView) findViewById(R.id.wheelview);

        //rgb color
        wheelView.setOnWheelAngleChangeListener(new WheelView.OnWheelAngleChangeListener() {
            @Override
            public void onWheelAngleChange(float angle) {

                colorAngle = Math.round(angle*3);
                colorAngle = -colorAngle;

                if (colorAngle > 360){
                    createColors(colorAngle % 360);
                    findViewById(R.id.rot).setRotation(-colorAngle % 360);

                }
                else if (colorAngle < -360){
                    createColors(colorAngle % 360);
                    findViewById(R.id.rot).setRotation(-colorAngle % 360);
                }
                else {
                    createColors(colorAngle);
                    findViewById(R.id.rot).setRotation(-colorAngle);
                }

            }
        });

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

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("color", color);
                returnIntent.putExtra("angle", (int) wheelView.getAngle());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        colorAngle = getIntent().getIntExtra("angle", 0);

        if (colorAngle == 0){
            color = Color.argb(255, 255, 0, 0);
        }
        else {
            color = getIntent().getIntExtra("color", 0);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            retainColor(colorAngle);
        }

    }

    public void createColors(int angle){

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

        changeSeekBarColor();

    }

    public void changeSeekBarColor(){

        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.seekbar_color_thumb);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.color);
        gradientDrawable.setColor(color);

        seekBarColor.setThumb(layerDrawable);

        seekBarColor.setProgress(colorSat);

        LayerDrawable layerDrawable2 = (LayerDrawable) getResources().getDrawable(R.drawable.seekbar_color_thumb);
        GradientDrawable gradientDrawable2 = (GradientDrawable) layerDrawable2.findDrawableByLayerId(R.id.color);
        gradientDrawable2.setColor(color);
        colorPickerView.setBackground(layerDrawable2);
    }

    public float scale(final float valueIn, final float baseMin, final float baseMax, final float limitMin, final float limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }

    public void changeColorBrightness(){

        color = Color.argb(colorAlpha, Color.red(color), Color.green(color), Color.blue(color));

        r = Color.red(color);
        g = Color.green(color);
        b = Color.blue(color);

        changeSeekBarColor();

    }

    public void retainColor(int angle){

        //initialize previous colors and set saved angle
        //createColors(-angle);
        //findViewById(R.id.rot).setRotation(-angle);
        wheelView.setAngle(angle);
    }
}
