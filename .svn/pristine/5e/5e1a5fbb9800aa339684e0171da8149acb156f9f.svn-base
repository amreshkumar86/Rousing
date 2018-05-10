package com.niveus.oen.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.niveus.oen.DataObjects.Group;
import com.niveus.oen.R;

public class GroupSettingsActivity extends AppCompatActivity {

    Group selectedGroup;

    TextView groupNameTv;
    TextView groupManTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        selectedGroup = (Group) intent.getSerializableExtra("group");

        groupNameTv = (TextView) findViewById(R.id.group_name_tv);
        groupNameTv.setText(selectedGroup.getName());

        groupManTv = (TextView) findViewById(R.id.group_man_tv);

        groupNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNamePopUp();
            }
        });

        groupManTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupSettingsActivity.this, GroupManagementActivity.class);
                intent.putExtra("group", selectedGroup);
                startActivity(intent);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(0, R.anim.right_out);
    }

    public void showNamePopUp(){

        final LayoutInflater inflater = this.getLayoutInflater();

        View layout = inflater.inflate(R.layout.rename_device_dialog, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(false);

        final AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.white_btn_bg);

        final EditText nameEt = (EditText) layout.findViewById(R.id.name_et);
        nameEt.setHint("Group name");
        nameEt.setText(groupNameTv.getText().toString());
        nameEt.setSelection(nameEt.getText().length());

        TextView saveTv = (TextView) layout.findViewById(R.id.save_btn_tv);

        TextView cancelTv = (TextView) layout.findViewById(R.id.cancel_btn_tv);

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEt.getText().toString().length() == 0){
                    Toast.makeText(GroupSettingsActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    selectedGroup.setName(nameEt.getText().toString());
                    groupNameTv.setText(nameEt.getText().toString());

                    alert.dismiss();
                }

            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });

        alert.show();

    }
}
