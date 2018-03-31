package com.rousing.oen.rousing;

import android.app.Application;

/**
 * Created by amreshkumar on 3/30/18.
 */

public class RousingApplication extends Application {
    private static RousingApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static RousingApplication getContext() {
        return mContext;
    }
}
