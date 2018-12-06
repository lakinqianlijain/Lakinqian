package com.qlj.lakinqiandemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qlj.lakinqiandemo.banner.BannerActivity;
import com.qlj.lakinqiandemo.contralayout.ContralayoutActivity;
import com.qlj.lakinqiandemo.eventbus.EventbusActivity;
import com.qlj.lakinqiandemo.file.FileActivity;
import com.qlj.lakinqiandemo.h5.H5Activity;
import com.qlj.lakinqiandemo.json.JsonAnalysisActivity;
import com.qlj.lakinqiandemo.optimize.MemoryOptimizeActivity;
import com.qlj.lakinqiandemo.video.VideoActivity;
import com.qlj.lakinqiandemo.views.SlideViewActivity;
import com.qlj.lakinqiandemo.views.animation.LoadingActivity;
import com.qlj.lakinqiandemo.hook.HookActivity;
import com.qlj.lakinqiandemo.mvp.login.view.LoginActivity;
import com.qlj.lakinqiandemo.reflection.ReflectionActivity;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.views.lottie.LottieActivity;
import com.qlj.lakinqiandemo.views.CustomizeViewActivity;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Button mReflection, mHOOK, mMVP, mAnimation, mContralayout,
            mLockPage, mVideoPlayPage, mJson, mLottieAnim, mEventBus, mH5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mReflection = findViewById(R.id.reflection);
        mReflection.setOnClickListener(this);
        mHOOK = findViewById(R.id.hook);
        mHOOK.setOnClickListener(this);
        mMVP = findViewById(R.id.mvp);
        mMVP.setOnClickListener(this);
        mAnimation = findViewById(R.id.animation);
        mAnimation.setOnClickListener(this);
        mContralayout = findViewById(R.id.contralayout);
        mContralayout.setOnClickListener(this);
        mLockPage = findViewById(R.id.lock_page);
        mLockPage.setOnClickListener(this);
        mVideoPlayPage = findViewById(R.id.video_play);
        mVideoPlayPage.setOnClickListener(this);
        mJson = findViewById(R.id.json_analysis);
        mJson.setOnClickListener(this);
        mLottieAnim = findViewById(R.id.bt_lottie);
        mLottieAnim.setOnClickListener(this);
        mEventBus = findViewById(R.id.bt_event_bus);
        mEventBus.setOnClickListener(this);
        mH5 = findViewById(R.id.bt_h5);
        mH5.setOnClickListener(this);
        findViewById(R.id.bt_slide_view).setOnClickListener(this);
        findViewById(R.id.bt_memory_optimization).setOnClickListener(this);
        findViewById(R.id.bt_file_related).setOnClickListener(this);
        findViewById(R.id.bt_banner).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reflection:
                JumpActivityUtil.JumpSelfActivity(this, ReflectionActivity.class);
                break;
            case R.id.hook:
                JumpActivityUtil.JumpSelfActivity(this, HookActivity.class);
                break;
            case R.id.mvp:
                JumpActivityUtil.JumpSelfActivity(this, LoginActivity.class);
                break;
            case R.id.animation:
                JumpActivityUtil.JumpSelfActivity(this, LoadingActivity.class);
                break;
            case R.id.contralayout:
                JumpActivityUtil.JumpSelfActivity(this, ContralayoutActivity.class);
                break;
            case R.id.lock_page:
                JumpActivityUtil.JumpSelfActivity(this, CustomizeViewActivity.class);
                break;
            case R.id.video_play:
                JumpActivityUtil.JumpSelfActivity(this, VideoActivity.class);
                break;
            case R.id.json_analysis:
                JumpActivityUtil.JumpSelfActivity(this, JsonAnalysisActivity.class);
                break;
            case R.id.bt_lottie:
                JumpActivityUtil.JumpSelfActivity(this, LottieActivity.class);
                break;
            case R.id.bt_event_bus:
                JumpActivityUtil.JumpSelfActivity(this, EventbusActivity.class);
                break;
            case R.id.bt_h5:
                JumpActivityUtil.JumpSelfActivity(this, H5Activity.class);
                break;
            case R.id.bt_slide_view:
                JumpActivityUtil.JumpSelfActivity(this, SlideViewActivity.class);
                break;
            case R.id.bt_memory_optimization:
                JumpActivityUtil.JumpSelfActivity(this, MemoryOptimizeActivity.class);
                break;
            case R.id.bt_file_related:
                JumpActivityUtil.JumpSelfActivity(this, FileActivity.class);
                break;
            case R.id.bt_banner:
                JumpActivityUtil.JumpSelfActivity(this, BannerActivity.class);
                break;

        }
    }


}
