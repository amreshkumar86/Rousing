package com.niveus.oen.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.niveus.oen.Adapters.ScenesAdapter;
import com.niveus.oen.DataObjects.Device;
import com.niveus.oen.DataObjects.Scene;
import com.niveus.oen.R;
import com.niveus.oen.Utils.DBHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.niveus.oen.Fragments.DevicesFragment.finalDevicesList;

/**
 * Created by Adarsh on 01-Jun-17.
 */

public class ScenesFragment extends Fragment {

    RelativeLayout switchRL;
    View switchV;
    RelativeLayout.LayoutParams switchParams;
    TextView presetsTv, favTv;

    int switchToggle = 0;

    ///List<Scene> scenesList = new ArrayList<Scene>();
    List<Scene> presetList = new ArrayList<Scene>();
    public static List<Scene> favList = new ArrayList<Scene>();
    ListView sceneLV;
    ScenesAdapter adapter;

    SeekBar seekBarSwitch;
    int seekBarSwitchProgress = 0;;

    DBHelper dbh;

    private static final String CMD_RGB = "{\"id\":%id,\"method\":\"set_rgb\",\"params\":[%value, \"smooth\", 200]}\r\n";
    private static final String CMD_BRIGHTNESS = "{\"id\":%id,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 100]}\r\n";

    int mCmdId;

    public ScenesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scenes, container, false);

        dbh = new DBHelper(getActivity());

        sceneLV = (ListView) view.findViewById(R.id.scenes_lv);

        seekBarSwitch = (SeekBar) view.findViewById(R.id.scene_seek_bar_switch);

        switchRL = (RelativeLayout) view.findViewById(R.id.switch_rl);

        switchV = (View) view.findViewById(R.id.switch_v);
        switchParams = (RelativeLayout.LayoutParams) switchV.getLayoutParams();

        presetsTv = (TextView) view.findViewById(R.id.presets_tv);
        favTv = (TextView) view.findViewById(R.id.fav_tv);

        final TranslateAnimation moveLeft = new TranslateAnimation(0, -82, 0 ,0);
        moveLeft.setDuration(50);

        final TranslateAnimation moveRight = new TranslateAnimation(0, 82, 0 ,0);
        moveRight.setDuration(50);

        /*Scene preset1 = new Scene();
        Scene preset2 = new Scene();
        Scene preset3 = new Scene();
        Scene preset4 = new Scene();
        Scene preset5 = new Scene();
        Scene preset6 = new Scene();

        preset1.setId(1);
        preset2.setId(2);
        preset3.setId(3);
        preset4.setId(4);
        preset5.setId(5);
        preset6.setId(6);

        preset1.setName("GOOD MORNING");
        preset2.setName("GOOD MORNING");
        preset3.setName("GOOD MORNING");
        preset4.setName("GOOD MORNING");
        preset5.setName("GOOD MORNING");
        preset6.setName("GOOD MORNING");

        preset1.setType("Preset");
        preset2.setType("Preset");
        preset3.setType("Preset");
        preset4.setType("Preset");
        preset5.setType("Preset");
        preset6.setType("Preset");

        presetList.add(preset1);
        presetList.add(preset2);
        presetList.add(preset3);
        presetList.add(preset4);
        presetList.add(preset5);
        presetList.add(preset6);*/

        /*Scene fav1 = new Scene();
        Scene fav2 = new Scene();
        Scene fav3 = new Scene();
        Scene fav4 = new Scene();
        Scene fav5 = new Scene();
        Scene fav6= new Scene();

        fav1.setId(7);
        fav2.setId(8);
        fav3.setId(9);
        fav4.setId(10);
        fav5.setId(11);
        fav6.setId(12);

        fav1.setName("GOOD NIGHT");
        fav2.setName("GOOD NIGHT");
        fav3.setName("GOOD NIGHT");
        fav4.setName("GOOD NIGHT");
        fav5.setName("GOOD NIGHT");
        fav6.setName("GOOD NIGHT");

        fav1.setType("Fav");
        fav2.setType("Fav");
        fav3.setType("Fav");
        fav4.setType("Fav");
        fav5.setType("Fav");
        fav6.setType("Fav");

        favList.add(fav1);
        favList.add(fav2);
        favList.add(fav3);
        favList.add(fav4);
        favList.add(fav5);
        favList.add(fav6);*/

        initLists();

        setPresets();

        switchRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchToggle == 0){

                    favTv.setTextColor(Color.parseColor("#FEBCCA"));
                    presetsTv.setTextColor(Color.parseColor("#033399"));

                    switchToggle = 1;
                    setPresets();
                }
                else {

                    presetsTv.setTextColor(Color.parseColor("#FEBCCA"));
                    favTv.setTextColor(Color.parseColor("#033399"));

                    switchToggle = 0;
                    setFavourites();
                }
            }
        });

        seekBarSwitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 90){
                    if (seekBarSwitchProgress == 0) {
                        seekBarSwitch.setProgress(100);
                        seekBarSwitchProgress = 1;

                        presetsTv.setTextColor(Color.parseColor("#FEBCCA"));
                        favTv.setTextColor(Color.parseColor("#033399"));

                        switchToggle = 0;
                        setFavourites();
                    }

                }
                else {
                    seekBarSwitch.setProgress(0);
                }

                if(seekBar.getProgress() < 10) {
                    if (seekBarSwitchProgress == 1) {
                        seekBarSwitch.setProgress(0);
                        switchRL.performClick();
                        seekBarSwitchProgress = 0;

                        favTv.setTextColor(Color.parseColor("#FEBCCA"));
                        presetsTv.setTextColor(Color.parseColor("#033399"));

                        switchToggle = 1;
                        setPresets();
                    }
                }
                else {
                    seekBarSwitch.setProgress(100);
                }
            }
        });

        return view;
    }

    public void setPresets(){

        YoYo.with(Techniques.FadeOutLeft)
                .duration(300)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        YoYo.with(Techniques.FadeInRight)
                                .duration(300)
                                .playOn(sceneLV);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(sceneLV);


        adapter = new ScenesAdapter(getContext(), presetList, ScenesFragment.this);
        sceneLV.setAdapter(adapter);

        /*sceneLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < presetList.size(); i++){
                    presetList.get(i).setSelected(false);
                }

                for (int i = 0; i < favList.size(); i++){
                    favList.get(i).setSelected(false);
                }
                presetList.get(position).setSelected(true);

                adapter = new ScenesAdapter(getContext(), presetList, switchToggle);
                sceneLV.setAdapter(adapter);
            }
        });*/
    }

    public void setFavourites(){

        YoYo.with(Techniques.FadeOutRight)
                .duration(300)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        YoYo.with(Techniques.FadeInLeft)
                                .duration(300)
                                .playOn(sceneLV);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(sceneLV);

        adapter = new ScenesAdapter(getContext(), favList, ScenesFragment.this);
        sceneLV.setAdapter(adapter);

        /*sceneLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < presetList.size(); i++){
                    presetList.get(i).setSelected(false);
                }

                for (int i = 0; i < favList.size(); i++){
                    favList.get(i).setSelected(false);
                }
                favList.get(position).setSelected(true);

                adapter = new ScenesAdapter(getContext(), favList, switchToggle);
                sceneLV.setAdapter(adapter);
            }
        });*/
    }

    public void reDisplay(int id, String type){

        int index = sceneLV.getFirstVisiblePosition();
        View v = sceneLV.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - sceneLV.getPaddingTop());

        for (int i = 0; i < presetList.size(); i++){
            if (presetList.get(i).getId() != id) {
                presetList.get(i).setSelected(false);
            }
            else {
                presetList.get(i).setSelected(true);
                applyScene(presetList.get(i));
            }
        }

        for (int i = 0; i < favList.size(); i++){
            if (favList.get(i).getId() != id) {
                favList.get(i).setSelected(false);
            }
            else {
                favList.get(i).setSelected(true);
                applyScene(favList.get(i));
            }
        }

        if (type.equals("preset")) {
            adapter = new ScenesAdapter(getContext(), presetList, ScenesFragment.this);
            sceneLV.setAdapter(adapter);
        }
        else {
            adapter = new ScenesAdapter(getContext(), favList, ScenesFragment.this);
            sceneLV.setAdapter(adapter);
        }

        sceneLV.setSelectionFromTop(index, top);

    }

    public void applyScene(Scene scene){
        int color = Color.parseColor(scene.getColor());

        for (int i = 0; i < finalDevicesList.size(); i++) {
            /*if (finalDevicesList.get(i).getType().equals("device")) {
                write(parseRGBCmd(msAccessColor(color)), finalDevicesList.get(i).getDevices().get(0));
                write(parseBrightnessCmd(scene.getBrightness()), finalDevicesList.get(i).getDevices().get(0));
            }
            else {*/
                for (int j = 0; j < finalDevicesList.get(i).getDevices().size(); j++){
                    write(parseRGBCmd(msAccessColor(color)), finalDevicesList.get(i).getDevices().get(j));
                    write(parseBrightnessCmd(scene.getBrightness()), finalDevicesList.get(i).getDevices().get(j));
                }
            //}
        }
    }

    public int msAccessColor(int color){
        return (65536 * Color.red(color) + 256 * Color.green(color) + Color.blue(color));
    }

    private String parseRGBCmd(int color){
        return CMD_RGB.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(color));
    }

    private void write(String cmd, final Device device){
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

    private String parseBrightnessCmd(int brightness){
        return CMD_BRIGHTNESS.replace("%id",String.valueOf(++mCmdId)).replace("%value",String.valueOf(brightness));
    }

    public void initLists(){
        presetList.clear();
        favList.clear();
        List<Scene> sceneList = new ArrayList<Scene>();

        sceneList = dbh.getAllScenes();

        for (int i = 0; i < sceneList.size(); i++){
            if (sceneList.get(i).getType().equals("preset")){
                presetList.add(sceneList.get(i));
            }
            else {
                favList.add(sceneList.get(i));
            }
        }
    }
}
