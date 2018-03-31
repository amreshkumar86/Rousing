package com.rousing.oen.rousing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.rousing.database.DBManager;
import com.rousing.models.Group;
import com.rousing.models.RousingLight;

import java.util.ArrayList;

public class Page1 extends AppCompatActivity {

    ListView listView;
    RousingDeviceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        //Check if any existing lights are added
        //If yes then display them and try to connect to them
        //Else display option to add lights
        ArrayList<RousingLight> existingLights = DBManager.GetSharedDBManager().GetAllLights();
        if(existingLights != null && !existingLights.isEmpty()){
            //List all the existing lights
            listView = (ListView)findViewById(R.id.existingDeviceListView);
            listView.setVisibility(View.VISIBLE);
            adapter = new RousingDeviceAdapter(this, new ArrayList<RousingLight>());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> _adapter, View v, int position, long tag){

                }
            });
            adapter.addAll(existingLights);
        }
        else {
            //Display option to connect to lights
            Button searchLightButton = (Button) findViewById(R.id.searchLightButton);
            searchLightButton.setVisibility(View.VISIBLE);
        }
    }

    public void handleSearchLightClick(View v) {
        Intent intent = new Intent(this, LightSearchActivity.class);
        startActivity(intent);
    }
}
