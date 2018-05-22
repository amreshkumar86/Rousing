package com.niveus.oen.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niveus.oen.Activities.GroupManagementActivity;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adarsh on 02-Sep-17.
 */

public class GroupDeviceAdapter extends BaseAdapter {

    Context context;
    List<Device> fjobList;
    List<Device> ajobList;
    List<Device> selectedDevList;
    ViewHolder holder = null;

    public GroupDeviceAdapter(Context context, List<Device> data, List<Device> selectedDevList) {
        this.context = context;
        this.fjobList = data;
        this.ajobList = data;
        this.selectedDevList = selectedDevList;
    }

    private class ViewHolder {
        TextView nameTv;
        CheckBox cb;
        LinearLayout ll;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        notifyDataSetChanged();
        final Device device = (Device) getItem(position);
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_device_item, null);

            holder = new ViewHolder();
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.cb.setChecked(false);
            holder.cb.setButtonDrawable(R.drawable.blue_line);

            holder.nameTv.setText(device.getNickName());

            final CheckBox cc = (CheckBox) convertView.findViewById(R.id.checkbox);
            cc.setChecked(false);
            cc.setButtonDrawable(R.drawable.blue_line);

            if (selectedDevList.contains(device)) {
                holder.cb.setChecked(true);
                holder.cb.setButtonDrawable(R.drawable.yellow_tick);
            }

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cc.isChecked()) {
                        cc.setChecked(false);
                        cc.setButtonDrawable(R.drawable.blue_line);
                    }
                    else {
                        cc.setChecked(true);
                        cc.setButtonDrawable(R.drawable.yellow_tick);

                    }
                    ((GroupManagementActivity) context).checkHandler(cc, device);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
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

    @Override
    public int getCount() {

        if (fjobList != null) {
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
