package com.niveus.oen.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Adarsh on 21-Aug-17.
 */

public class DeviceListReceiver extends BroadcastReceiver {

    public static DeviceListListener deviceListListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(deviceListListener!=null){
            deviceListListener.getDeviceList(intent);
        }
    }

    public interface DeviceListListener{
        void getDeviceList(Intent intent);
    }
}
