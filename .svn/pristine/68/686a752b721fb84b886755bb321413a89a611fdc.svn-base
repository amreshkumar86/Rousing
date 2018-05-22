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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niveus.oen.DataObjects.Scene;
import com.niveus.oen.Fragments.ScenesFragment;
import com.niveus.oen.R;
import com.niveus.oen.Utils.FontsProvider;

import java.util.List;

/**
 * Created by Adarsh on 06-Jun-17.
 */

public class ScenesAdapter extends BaseAdapter {

    Context context;
    List<Scene> fjobList;
    List<Scene> ajobList;
    ViewHolder holder;
    int type;

    FontsProvider fontsProvider;

    ScenesFragment scenesFragment;

    public ScenesAdapter(Context context, List<Scene> data, ScenesFragment scenesFragment) {
        this.context = context;
        this.fjobList = data;
        this.ajobList = data;
        this.scenesFragment = scenesFragment;

        fontsProvider = new FontsProvider(context);
    }

    private class ViewHolder {
        TextView sceneNameTv;
        CheckBox checkBox;
        LinearLayout ll;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        holder = new ViewHolder();
        final Scene scene = (Scene) getItem(position);
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.scene_item, null);

            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            holder.sceneNameTv = (TextView) convertView.findViewById(R.id.scene_name_tv);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.checkBox.setButtonDrawable(R.drawable.blue_line);

            holder.sceneNameTv.setTypeface(fontsProvider.getRobotoLight());

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            final TextView sceneNameTv = (TextView) convertView.findViewById(R.id.scene_name_tv);

            holder.sceneNameTv.setText(scene.getName());

            /*holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()) {
                        checkBox.setChecked(false);
                        checkBox.setButtonDrawable(R.drawable.blue_line);
                        sceneNameTv.setTextColor(Color.parseColor("#012061"));

                    }
                    else {
                        checkBox.setChecked(true);
                        sceneNameTv.setTextColor(Color.parseColor("#FEBCCA"));

                        if (type == 0) {
                            checkBox.setButtonDrawable(R.drawable.blue_tick);
                        }
                        else {
                            checkBox.setButtonDrawable(R.drawable.yellow_tick);

                        }
                    }
                }
            });*/

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scenesFragment.reDisplay(scene.getId(), scene.getType());
                }
            });

            if (scene.isSelected()){
                checkBox.setChecked(true);
                sceneNameTv.setTextColor(Color.parseColor("#FEBCCA"));

                if (scene.getType().equals("Preset")) {
                    checkBox.setButtonDrawable(R.drawable.blue_tick);
                }
                else {
                    checkBox.setButtonDrawable(R.drawable.yellow_tick);
                }
            }

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
