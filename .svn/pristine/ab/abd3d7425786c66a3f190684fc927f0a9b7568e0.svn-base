package com.niveus.oen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Items;
import com.niveus.oen.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

public class SleepTimerActivity extends AppCompatActivity {

    //Device selectedDevice;
    Items item;
    int position;

    TextView doneBtnTv;

    private int mCmdId;
    private static final String CMD_CRON_ADD = "{\"id\":%id,\"method\":\"cron_add\",\"params\":[0, %value]}\r\n";
    private static final String CMD_CRON_DEL = "{\"id\":%id,\"method\":\"cron_del\",\"params\":[0]}\r\n";

    Calendar calSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        item = finalDevicesList.get(position);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        //timePicker.setIs24HourView(true);
        doneBtnTv = (TextView) findViewById(R.id.done_btn_tv);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });

        doneBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calNow = Calendar.getInstance();
                calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calSet.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);


                if(calNow.compareTo(calSet) <= 0){

                    for (int i = 0; i < item.getDevices().size(); i++) {
                        write(parseCronDel(), 0, 1, 0, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                    }

                    long millis = calSet.getTimeInMillis() - calNow.getTimeInMillis();
                    long time = TimeUnit.MILLISECONDS.toMinutes(millis) + 1;

                    if (time <= 0){
                        Toast.makeText(SleepTimerActivity.this, "Too short", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String cmd = parseCronAdd(time);
                        for (int i = 0; i < item.getDevices().size(); i++) {
                            write(cmd, 0, 1, 1, item.getDevices().get(i).getSocket(), item.getDevices().get(i).getBos());
                        }
                    }
                }
                else {
                    Toast.makeText(SleepTimerActivity.this, "Choose future time", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Calendar now = Calendar.getInstance();
        /*DatePickerDialog dpd = DatePickerDialog.newInstance(
                SleepTimerActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                SleepTimerActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
        );*/

        /*DatePickerDialog d = new DatePickerDialog();
        d.show(getFragmentManager(), "a");*/

    }

    private void write(String cmd, final int attributeId, int attributeValue, final int mode, final Socket socket, BufferedOutputStream bos){
        if (bos != null && socket.isConnected()){
            try {
                bos.write(cmd.getBytes());
                bos.flush();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String line = br.readLine();
                            Log.d("Cmd", "Response cron add:" + line);

                            if (mode == 1) {
                                Date date = calSet.getTime();
                                int h = date.getHours();
                                int m = date.getMinutes();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("time", String.valueOf(h) + ":" + String.valueOf(m));
                                setResult(Activity.RESULT_OK, returnIntent);
                                //Toast.makeText(SleepTimerActivity.this, "Sleep timer running", Toast.LENGTH_SHORT).show();
                                finish();
                            }

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

    private String parseCronAdd(long minutes){
        return CMD_CRON_ADD.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(minutes));
    }

    private String parseCronDel(){
        return CMD_CRON_DEL.replace("%id",String.valueOf(++mCmdId));
    }
}
