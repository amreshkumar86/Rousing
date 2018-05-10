package com.niveus.oen.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.niveus.oen.R;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity {

    ImageView resultBtn;
    LinearLayout credentialsLL;
    TextView connectBtnTv;

    int connectionStatus = 0;

    List<String> networkList = new ArrayList<String>();

    Spinner networkSP;
    EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        networkSP = (Spinner) findViewById(R.id.network_sp);
        passwordEt = (EditText) findViewById(R.id.password_et);

        resultBtn = (ImageView) findViewById(R.id.result_button);
        credentialsLL = (LinearLayout) findViewById(R.id.credential_ll);
        connectBtnTv = (TextView) findViewById(R.id.connect_btn_tv);

        resultBtn.setVisibility(View.GONE);
        credentialsLL.setVisibility(View.VISIBLE);

        connectBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionStatus == 0) {
                    if (validate()) {
                        credentialsLL.setVisibility(View.GONE);
                        resultBtn.setVisibility(View.VISIBLE);
                        resultBtn.setImageResource(R.drawable.success);
                        connectBtnTv.setText("DONE");

                        connectionStatus = 1;
                    }
                }
                else {
                    Intent intent = new Intent(ConfigurationActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        networkList.add("Select Network");
        networkList.add("4D4R5H N3TW0RK");
        networkList.add("Niveus Solutions");
        networkList.add("Niveus Solutions 2");
        networkList.add("Niveus Solutions 2.5");
        networkList.add("Niveus Solutions 2.7");

        MySpinnerAdapter adapterQualification = new MySpinnerAdapter(ConfigurationActivity.this, R.layout.spinner_item_layout, networkList);
        adapterQualification.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        networkSP.setAdapter(adapterQualification);

    }

    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        //Typeface Fonts = Typeface.createFromAsset(getContext().getAssets(), "Fonts/fonts.ttf");

        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            if (position == 0){
                view.setTextColor(getContext().getResources().getColor(R.color.gray));
                //view.setTypeface(Fonts);
            }
            else {
                view.setTextColor(getContext().getResources().getColor(R.color.disabled_color));
                //view.setTypeface(Fonts);
            }
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            //view.setTypeface(Fonts);
            view.setTextColor(getContext().getResources().getColor(R.color.disabled_color));

            /*if (position == 0){
                view.setTextColor(getContext().getResources().getColor(R.color.disabled_color));
                view.setTypeface(fonts);
            }*/

            return view;
        }
    }

    public boolean validate(){
        if (networkSP.getSelectedItemPosition() == 0){
            Toast.makeText(ConfigurationActivity.this, "Select a wifi network", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if (passwordEt.getText().toString().length() == 0){
            Toast.makeText(ConfigurationActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else {
            return true;
        }
    }
}
