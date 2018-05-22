package com.niveus.oen.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.R;
import com.niveus.oen.Utils.FontTextView;

import java.util.List;

/**
 * Created by Adarsh on 14-Mar-17.
 */

public class DeviceListAdapter extends BaseAdapter {

    Context context;
    List<Device> fjobList;
    List<Device> ajobList;
    ViewHolder holder;

    /*private ItemFilter mFilter = new ItemFilter();*/

    public DeviceListAdapter(Context context, List<Device> data) {
        this.context = context;
        this.fjobList = data;
        this.ajobList = data;
    }

    private class ViewHolder {
        FontTextView deviceNameTv;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        holder = new ViewHolder();
        Device device = (Device) getItem(position);
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_list_item, null);

            holder.deviceNameTv = (FontTextView) convertView.findViewById(R.id.device_name_tv);

            holder.deviceNameTv.setText(device.getNickName());

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
}
