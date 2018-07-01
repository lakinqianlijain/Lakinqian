package com.qlj.lakinqiandemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qlj.lakinqiandemo.animation.LoadingActivity;
import com.qlj.lakinqiandemo.hook.HookActivity;
import com.qlj.lakinqiandemo.mvp.login.view.LoginActivity;
import com.qlj.lakinqiandemo.reflection.ReflectionActivity;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Button mReflection, mHOOK, mMVP, mAnimation;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        }
    }
}
