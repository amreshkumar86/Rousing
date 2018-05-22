package com.niveus.oen.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.niveus.oen.Adapters.GroupDeviceAdapter;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Group;
import com.niveus.oen.R;

import java.util.ArrayList;
import java.util.List;

public class GroupManagementActivity extends AppCompatActivity {

    Group selectedGroup;

    ListView lv;

    List<Device> deviceList = new ArrayList<Device>();

    List<Device> selectedDeviceList = new ArrayList<Device>();
    List<Device> selectedTempDeviceList = new ArrayList<Device>();

    GroupDeviceAdapter adapter;

    TextView saveBtnTv, cancelBtnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        selectedGroup = (Group) intent.getSerializableExtra("group");

        Device device1 = new Device();
        device1.setNickName("Light 1");
        device1.setItemUniqueNumber("1");

        Device device2 = new Device();
        device2.setNickName("Light 2");
        device2.setItemUniqueNumber("2");

        Device device3 = new Device();
        device3.setNickName("Light 3");
        device3.setItemUniqueNumber("3");

        deviceList.add(device1);
        deviceList.add(device2);
        deviceList.add(device3);

        lv = (ListView) findViewById(R.id.list);
        adapter = new GroupDeviceAdapter(GroupManagementActivity.this, deviceList, selectedDeviceList);
        lv.setAdapter(adapter);

        saveBtnTv = (TextView) findViewById(R.id.save_btn_tv);
        cancelBtnTv = (TextView) findViewById(R.id.cancel_btn_tv);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });

        saveBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDeviceList.clear();
                selectedDeviceList.addAll(selectedTempDeviceList);
                selectedTempDeviceList.clear();

                Toast.makeText(GroupManagementActivity.this, String.valueOf(selectedDeviceList.size() + " devices added to group"), Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        cancelBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTempDeviceList.clear();

                finish();
            }
        });
    }

    public void checkHandler(final CheckBox checkbox, final Device device){

        if(checkbox.isChecked()){
            selectedTempDeviceList.remove(device);
            selectedTempDeviceList.add(device);
        }
        else {
            //checkbox.setChecked(false);
            selectedTempDeviceList.remove(device);
        }

    }
}
