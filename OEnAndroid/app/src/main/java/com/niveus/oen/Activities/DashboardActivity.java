package com.niveus.oen.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.stetho.Stetho;
import com.niveus.oen.DataObjects.Group;
import com.niveus.oen.Fragments.DevicesFragment;
import com.niveus.oen.Fragments.PowerFragment;
import com.niveus.oen.Fragments.ScenesFragment;
import com.niveus.oen.R;
import com.niveus.oen.Utils.FontTextView;
import com.niveus.oen.Utils.FontsProvider;
import com.niveus.oen.Utils.ViewPagerParallax;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    ViewPagerParallax viewPager;

    ImageView devicesIv, powerIv, scenesIv;
    ImageView arrowLeft, arrowRight;
    View tabIndicator;

    RelativeLayout.LayoutParams tabIndicatorParams;

    FontTextView pageTitleTv;

    FontsProvider fontsProvider;

    int tempTabPos;

    TranslateAnimation tabMoveLeft, tabMoveRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //View circleV = (View) findViewById(R.id.circle_v);

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle_v);

        circleV.setImageBitmap(bitmap);*/

        Stetho.initializeWithDefaults(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#033399"));
        }

        fontsProvider = new FontsProvider(DashboardActivity.this);

        initMenu();

        pageTitleTv = (FontTextView) findViewById(R.id.page_title_tv);
        pageTitleTv.setTypeface(fontsProvider.getHellBold());

        devicesIv = (ImageView) findViewById(R.id.devices_iv);
        powerIv = (ImageView) findViewById(R.id.power_iv);
        scenesIv = (ImageView) findViewById(R.id.scenes_iv);

        arrowLeft = (ImageView) findViewById(R.id.arrow_left_iv);
        arrowLeft.setVisibility(View.GONE);
        arrowRight = (ImageView) findViewById(R.id.arrow_right_iv);

        tabIndicator = (View) findViewById(R.id.tab_indicator_v);
        tabIndicatorParams = (RelativeLayout.LayoutParams) tabIndicator.getLayoutParams();

        viewPager = (ViewPagerParallax) findViewById(R.id.viewpager);
        viewPager.set_max_pages(3);
        viewPager.setBackgroundAsset(R.raw.circle_original_big);
        setupViewPager(viewPager);

        tabMoveLeft = new TranslateAnimation(0, -60, 0, 0);
        tabMoveLeft.setDuration(100);

        tabMoveRight = new TranslateAnimation(0, 60, 0, 0);
        tabMoveRight.setDuration(100);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position){

                if (position == 0) {
                    pageTitleTv.setText("MY SMART\nDEVICES");

                    devicesIv.setImageResource(R.drawable.devices_blue);
                    powerIv.setImageResource(R.drawable.power);
                    scenesIv.setImageResource(R.drawable.scenes);

                    tabIndicator.startAnimation(tabMoveLeft);
                    tabMoveLeft.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tabIndicatorParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                            tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                            tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                            tabIndicator.setLayoutParams(tabIndicatorParams);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    arrowLeft.setVisibility(View.GONE);
                    arrowRight.setVisibility(View.VISIBLE);

                    /*YoYo.with(Techniques.Landing)
                            .duration(400)
                            .playOn(tabIndicator);*/

                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(devicesIv);

                    YoYo.with(Techniques.FadeIn)
                            .duration(800)
                            .playOn(pageTitleTv);

                    tempTabPos = 0;
                }
                else if (position == 1){
                    pageTitleTv.setText("MY ENERGY\nPROFILE");

                    ((ScrollView) findViewById(R.id.details_scroll)).fullScroll(View.FOCUS_UP);

                    devicesIv.setImageResource(R.drawable.devices);
                    powerIv.setImageResource(R.drawable.power_blue);
                    scenesIv.setImageResource(R.drawable.scenes);

                    if (tempTabPos == 2) {
                        tabIndicator.startAnimation(tabMoveLeft);

                        tabMoveLeft.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                                tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                                tabIndicatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                tabIndicator.setLayoutParams(tabIndicatorParams);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                    else if (tempTabPos == 0){
                        tabIndicator.startAnimation(tabMoveRight);

                        tabMoveRight.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                tabIndicatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                                tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                                tabIndicator.setLayoutParams(tabIndicatorParams);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    arrowLeft.setVisibility(View.VISIBLE);
                    arrowRight.setVisibility(View.VISIBLE);

                    /*YoYo.with(Techniques.Landing)
                            .duration(400)
                            .playOn(tabIndicator);*/

                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(powerIv);

                    YoYo.with(Techniques.FadeIn)
                            .duration(800)
                            .playOn(pageTitleTv);

                    tempTabPos = 1;
                }
                else{
                    pageTitleTv.setText("SCENES");

                    devicesIv.setImageResource(R.drawable.devices);
                    powerIv.setImageResource(R.drawable.power);
                    scenesIv.setImageResource(R.drawable.scenes_blue);

                    tabIndicator.startAnimation(tabMoveRight);
                    tabMoveRight.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tabIndicatorParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                            tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

                            tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            tabIndicator.setLayoutParams(tabIndicatorParams);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    arrowLeft.setVisibility(View.VISIBLE);
                    arrowRight.setVisibility(View.GONE);

                    /*YoYo.with(Techniques.Landing)
                            .duration(400)
                            .playOn(tabIndicator);*/

                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(scenesIv);

                    YoYo.with(Techniques.FadeIn)
                            .duration(800)
                            .playOn(pageTitleTv);

                    tempTabPos = 2;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset , int positionOffsetPixels ) {
                //findViewById(R.id.circle_rl).setX(positionOffset*30);
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        devicesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewPager.getCurrentItem() != 0) {

                    viewPager.setCurrentItem(0);

                    /*devicesIv.setImageResource(R.drawable.devices_blue);
                    powerIv.setImageResource(R.drawable.power);
                    scenesIv.setImageResource(R.drawable.scenes);

                    tabIndicatorParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    tabIndicator.setLayoutParams(tabIndicatorParams);*/
                }
            }
        });

        powerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewPager.getCurrentItem() != 1) {

                    viewPager.setCurrentItem(1);

                    /*devicesIv.setImageResource(R.drawable.devices);
                    powerIv.setImageResource(R.drawable.power_blue);
                    scenesIv.setImageResource(R.drawable.scenes);

                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

                    tabIndicatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    tabIndicator.setLayoutParams(tabIndicatorParams);*/
                }
            }
        });

        scenesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewPager.getCurrentItem() != 2) {

                    viewPager.setCurrentItem(2);

                    /*devicesIv.setImageResource(R.drawable.devices);
                    powerIv.setImageResource(R.drawable.power);
                    scenesIv.setImageResource(R.drawable.scenes_blue);

                    tabIndicatorParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

                    tabIndicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    tabIndicator.setLayoutParams(tabIndicatorParams);*/
                }
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() != 0){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);

                    YoYo.with(Techniques.BounceIn)
                            .duration(300)
                            .playOn(arrowLeft);
                }
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);

                    YoYo.with(Techniques.BounceIn)
                            .duration(300)
                            .playOn(arrowRight);
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DevicesFragment(), "Devices");
        adapter.addFrag(new PowerFragment(), "Power");
        adapter.addFrag(new ScenesFragment(), "Scenes");
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);

        //viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void initMenu(){

        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        FrameLayout f = (FrameLayout) findViewById(R.id.frame);

        int width = Math.round(getResources().getDisplayMetrics().widthPixels*0.9f);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) f.getLayoutParams();
        params.width = width;

        f.setLayoutParams(params);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        TextView addNewDeviceTv = (TextView) findViewById(R.id.add_new_devices_tv);
        TextView helpTv = (TextView) findViewById(R.id.help_tv);
        TextView settingsTv = (TextView) findViewById(R.id.settings_tv);
        TextView accountTv = (TextView) findViewById(R.id.account_tv);
        TextView createGroupTv = (TextView) findViewById(R.id.create_group_tv);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LightMenuActivity.class);
                startActivity(intent);
                return true;
            }
        });

        addNewDeviceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(DashboardActivity.this, AddDevicesActivity.class);
                startActivity(intent);
            }
        });

        helpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(DashboardActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        settingsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        accountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(DashboardActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        createGroupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                showNamePopUp();
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                findViewById(R.id.img).setTranslationX((1-slideOffset)*150);
                findViewById(R.id.name_tv).setAlpha(slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                findViewById(R.id.name_tv).setVisibility(View.VISIBLE);
                findViewById(R.id.email_tv).setVisibility(View.VISIBLE);

                YoYo.with(Techniques.SlideInLeft)
                        .duration(300)
                        .playOn(findViewById(R.id.name_tv));

                YoYo.with(Techniques.SlideInLeft)
                        .duration(400)
                        .playOn(findViewById(R.id.email_tv));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.pencil_iv).setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.BounceIn)
                                .duration(500)
                                .playOn(findViewById(R.id.pencil_iv));
                    }
                }, 400);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.name_tv).setVisibility(View.GONE);
                findViewById(R.id.email_tv).setVisibility(View.GONE);
                findViewById(R.id.pencil_iv).setVisibility(View.INVISIBLE);

                ((ScrollView) findViewById(R.id.menu_scroll)).fullScroll(View.FOCUS_UP);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

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
        nameEt.setSelection(nameEt.getText().length());

        TextView saveTv = (TextView) layout.findViewById(R.id.save_btn_tv);

        TextView cancelTv = (TextView) layout.findViewById(R.id.cancel_btn_tv);

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEt.getText().toString().length() == 0){
                    Toast.makeText(DashboardActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Group group = new Group();
                    group.setName(nameEt.getText().toString());

                    Intent intent = new Intent(DashboardActivity.this, GroupSettingsActivity.class);
                    intent.putExtra("group", group);
                    startActivity(intent);
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

        /*nameEt.requestFocus();
        InputMethodManager imm = (InputMethodManager) DashboardActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);*/
    }
}
