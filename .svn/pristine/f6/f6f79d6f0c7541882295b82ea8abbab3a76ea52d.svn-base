package com.niveus.oen.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.niveus.oen.R;
import com.niveus.oen.Utils.FontsProvider;
import com.niveus.oen.Utils.PreferencesManager;

public class AccountActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    TextView signOutTv;

    FontsProvider fontsProvider;

    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fontsProvider = new FontsProvider(AccountActivity.this);

        preferencesManager = new PreferencesManager(this);

        emailEt = (EditText) findViewById(R.id.email_id_et);
        emailEt.setTypeface(fontsProvider.getRobotoLight());

        signOutTv = (TextView) findViewById(R.id.sign_out_btn_tv);
        signOutTv.setTypeface(fontsProvider.getRobotoLight());

        passwordEt = (EditText) findViewById(R.id.password_et);
        passwordEt.setTypeface(fontsProvider.getRobotoLight());

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferencesManager.setHasLoggedIn(false);
                preferencesManager.setHasAddedDevices(false);

                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
