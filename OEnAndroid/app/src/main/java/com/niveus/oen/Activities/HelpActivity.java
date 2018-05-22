package com.niveus.oen.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.niveus.oen.Fragments.ContactUsFragment;
import com.niveus.oen.Fragments.FAQsFragment;
import com.niveus.oen.R;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    RelativeLayout switchRL;
    View switchV;
    RelativeLayout.LayoutParams switchParams;
    TextView faqsTv, contactUsTv;

    int switchToggle = 0;

    ViewPager viewPager;

    SeekBar seekBarSwitch;

    int seekBarSwitchProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        switchRL = (RelativeLayout) findViewById(R.id.switch_rl);

        seekBarSwitch = (SeekBar) findViewById(R.id.seek_bar_switch);

        switchV = (View) findViewById(R.id.switch_v);
        switchParams = (RelativeLayout.LayoutParams) switchV.getLayoutParams();

        faqsTv = (TextView) findViewById(R.id.faqs_tv);
        contactUsTv = (TextView) findViewById(R.id.contact_us_tv);

        final TranslateAnimation moveLeft = new TranslateAnimation(0, -82, 0 ,0);
        moveLeft.setDuration(50);

        final TranslateAnimation moveRight = new TranslateAnimation(0, 82, 0 ,0);
        moveRight.setDuration(50);

        switchRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchToggle == 0){

                    /*switchV.startAnimation(moveRight);

                    moveRight.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            switchV.setLayoutParams(switchParams);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });*/

                    switchToggle = 1;
                    viewPager.setCurrentItem(1);
                }
                else {

                    /*switchV.startAnimation(moveLeft);

                    moveLeft.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                            switchV.setLayoutParams(switchParams);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });*/

                    switchToggle = 0;
                    viewPager.setCurrentItem(0);
                }
            }
        });

        /*seekBarSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    return true;
                }
                return false;
            }
        });*/

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
                        switchRL.performClick();
                        seekBarSwitchProgress = 1;
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
                    }
                }
                else {
                    seekBarSwitch.setProgress(100);
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position){

                if (position == 0) {
                    /*switchV.startAnimation(moveLeft);

                    moveLeft.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                            switchV.setLayoutParams(switchParams);

                            faqsTv.setTextColor(Color.parseColor("#FEBCCA"));
                            contactUsTv.setTextColor(getResources().getColor(R.color.button_color));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });*/
                    faqsTv.setTextColor(Color.parseColor("#FEBCCA"));
                    contactUsTv.setTextColor(getResources().getColor(R.color.button_color));
                    switchToggle = 0;
                }
                else if (position == 1){
                    /*switchV.startAnimation(moveRight);

                    moveRight.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                            switchParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            switchV.setLayoutParams(switchParams);

                            contactUsTv.setTextColor(Color.parseColor("#FEBCCA"));
                            faqsTv.setTextColor(getResources().getColor(R.color.button_color));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });*/

                    switchToggle = 1;
                    contactUsTv.setTextColor(Color.parseColor("#FEBCCA"));
                    faqsTv.setTextColor(getResources().getColor(R.color.button_color));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FAQsFragment(), "FAQs");
        adapter.addFrag(new ContactUsFragment(), "Contact Us");
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);

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
}
