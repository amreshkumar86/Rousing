package com.niveus.oen.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.Fragments.DevicesFragment;
import com.niveus.oen.R;
import com.niveus.oen.Utils.FontsProvider;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

/**
 * Created by Adarsh on 03-Jun-17.
 */

public class DevicesAdapter extends BaseAdapter {

    Context context;
    List<Items> fjobList;
    List<Items> ajobList;
    ViewHolder holder;
    FontsProvider fontsProvider;

    DevicesFragment devicesFragment;

    int mCmdId;

    private static final String CMD_ON = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"on\",\"smooth\",1]}\r\n";
    private static final String CMD_OFF = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"off\",\"smooth\",1]}\r\n";

    public DevicesAdapter(Context context, List<Items> data, DevicesFragment devicesFragment) {
        this.context = context;
        this.fjobList = data;
        this.ajobList = data;

        fontsProvider = new FontsProvider(context);

        this.devicesFragment = devicesFragment;
    }

    private class ViewHolder {
        TextView deviceNameTv;
        TextView subNameTv;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        holder = new ViewHolder();
        final Items item = (Items) getItem(position);
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_item, null);

            holder.deviceNameTv = (TextView) convertView.findViewById(R.id.device_name_tv);
            holder.deviceNameTv.setTypeface(fontsProvider.getRobotoLight());

            holder.subNameTv = (TextView) convertView.findViewById(R.id.sub_name_tv);
            holder.subNameTv.setTypeface(fontsProvider.getRobotoLight());

            final ImageView powerBtnIv = (ImageView) convertView.findViewById(R.id.power_btn_iv);
            final ImageView deviceIconIv = (ImageView) convertView.findViewById(R.id.device_icon);

            final TextView deviceNameTv = (TextView) convertView.findViewById(R.id.device_name_tv);
            final TextView subNameTv = (TextView) convertView.findViewById(R.id.sub_name_tv);

            if (item.getType().equals("device")) {
                if (!item.getDevices().get(0).isAvailable()) {
                    powerBtnIv.setImageResource(R.drawable.refresh);
                } else if (item.getDevices().get(0).isOn()) {
                    powerBtnIv.setImageResource(R.drawable.power_btn_ambient);
                    holder.deviceNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.subNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    deviceIconIv.setImageResource(R.drawable.single_ambient);
                    holder.subNameTv.setText("On");
                } else if (!item.getDevices().get(0).isOn()) {
                    powerBtnIv.setImageResource(R.drawable.power_btn);
                }
            }
            else {
                int on = 0;
                for (int i = 0; i < item.getDevices().size(); i++){
                    if (item.getDevices().get(i).isOn()){
                        powerBtnIv.setImageResource(R.drawable.power_btn_ambient);
                        holder.deviceNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        holder.subNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        deviceIconIv.setImageResource(R.drawable.three_dots_ambient);
                        item.setOn(true);
                        on++;
                    }
                }

                if (on >= 1){
                    holder.subNameTv.setText(String.valueOf(on) + " on, " + String.valueOf(item.getDevices().size() - on) + " Off");
                }
                else {
                    holder.subNameTv.setText("All off");
                    deviceIconIv.setImageResource(R.drawable.three_dots);
                }

            }

            powerBtnIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (item.getType().equals("device")) {
                        if (item.getDevices().get(0).isAvailable()) {
                            if (item.getDevices().get(0).isOn()) {
                                write(item.getDevices().get(0), parseSwitch(false), 0, 1);
                                item.getDevices().get(0).setOn(false);
                                powerBtnIv.setImageResource(R.drawable.power_btn);
                                deviceNameTv.setTextColor(Color.parseColor("#012061"));
                                deviceIconIv.setImageResource(R.drawable.single_dot);

                            } else {
                                write(item.getDevices().get(0), parseSwitch(true), 1, 1);
                                item.getDevices().get(0).setOn(true);
                                powerBtnIv.setImageResource(R.drawable.power_btn_ambient);
                                deviceNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                                deviceIconIv.setImageResource(R.drawable.single_ambient);
                            }

                            for (int i = 0; i < finalDevicesList.size(); i++){
                                if (finalDevicesList.get(i).getId().equals(item.getId())){
                                    finalDevicesList.set(i, item);
                                    break;
                                }
                            }

                        } else {
                            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 50, 25);
                            rotateAnimation.setDuration(1000);
                            rotateAnimation.setRepeatCount(6);
                            rotateAnimation.setRepeatMode(Animation.RESTART);
                            rotateAnimation.setInterpolator(new LinearInterpolator());
                            powerBtnIv.startAnimation(rotateAnimation);
                            //connect(device, position);
                            devicesFragment.getDevices(0);
                        }
                    }
                    else {
                        if (item.isOn()) {
                            for (int i = 0; i < item.getDevices().size(); i++) {
                                if (item.getDevices().get(i).isAvailable()) {
                                    write(item.getDevices().get(i), parseSwitch(false), 0, 1);
                                    item.getDevices().get(i).setOn(false);
                                }
                            }

                            item.setOn(false);
                            powerBtnIv.setImageResource(R.drawable.power_btn);
                            deviceIconIv.setImageResource(R.drawable.three_dots);
                            deviceNameTv.setTextColor(Color.parseColor("#012061"));

                        } else {

                            for (int i = 0; i < item.getDevices().size(); i++) {
                                if (item.getDevices().get(i).isAvailable()) {
                                    write(item.getDevices().get(i), parseSwitch(true), 1, 1);
                                    item.getDevices().get(i).setOn(true);
                                }
                            }

                            item.setOn(true);
                            powerBtnIv.setImageResource(R.drawable.power_btn_ambient);
                            deviceNameTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            deviceIconIv.setImageResource(R.drawable.three_dots_ambient);
                        }

                        for (int j = 0; j < finalDevicesList.size(); j++){
                            if (finalDevicesList.get(j).getId().equals(item.getId())){
                                finalDevicesList.set(j, item);
                                break;
                            }
                        }
                    }

                    devicesFragment.displayList();
                }
            });

            holder.deviceNameTv.setText(item.getName());

        }

        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        if(getCount() < 1){
            return 1;
        }
        else{
            return getCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*public Filter getFilter() {
        return mFilter;
    }*/

    @Override
    public int getCount() {

        if (fjobList != null && fjobList.size() > 0) {
            return fjobList.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return fjobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fjobList.indexOf(getItem(position));
    }

    private void write(final Device device, String cmd, final int attributeId, int attributeValue){
        if (device.getBos() != null && device.getSocket().isConnected()){
            try {
                device.getBos().write(cmd.getBytes());
                device.getBos().flush();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(device.getSocket().getInputStream()));
                            String line = br.readLine();
                            Log.d("Cmd", "Response:" + line);

                            if (attributeId != 0) {
                                //    saveState(attributeId, attributeValue);
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

    private String parseSwitch(boolean on){
        String cmd;
        if (on){
            cmd = CMD_ON.replace("%id", String.valueOf(++mCmdId));
        }else {
            cmd = CMD_OFF.replace("%id", String.valueOf(++mCmdId));
        }
        return cmd;
    }

   /* private void connect(final Device device, final int position){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket(device.getIp(), Integer.valueOf(device.getPort()));
                        socket.setKeepAlive(true);
                        BufferedOutputStream mBos = new BufferedOutputStream(socket.getOutputStream());
                        BufferedReader mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        device.setSocket(socket);
                        device.setBos(mBos);

                        finalDevicesList.add(position, device);

                        devicesFragment.displayList();
                        notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }*/
}
