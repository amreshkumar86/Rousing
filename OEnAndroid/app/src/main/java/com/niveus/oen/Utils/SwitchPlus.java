package com.niveus.oen.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Switch;

/**
 * Created by Adarsh on 03-Jun-17.
 */

public class SwitchPlus extends Switch {

    public SwitchPlus(Context context) {
        super(context);
    }

    public SwitchPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        changeColor(checked);
    }

    private void changeColor(boolean isChecked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int thumbColor = Color.argb(255, 32, 255, 126);
            int trackColor = Color.argb(255, 42, 171, 228);

            /*if(isChecked) {
                thumbColor = Color.argb(255, 253, 153, 0);
                trackColor = thumbColor;
            } else {
                thumbColor = Color.argb(255, 236, 236, 236);
                trackColor = Color.argb(255, 0, 0, 0);
            }*/

            try {
                getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
                getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
