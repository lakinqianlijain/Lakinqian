package com.qlj.lakinqiandemo.ui;

import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qlj.lakinqiandemo.R;

import com.qlj.lakinqiandemo.reflection.util.Util;

/**
 * Created by lakinqian on 2018/5/29.
 */

public class ReflectionActivity extends AppCompatActivity implements View.OnClickListener  {
    Button mBasicReflection, mFrameWork, mBetweenApk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        initView();
    }

    private void initView() {
        mBasicReflection = findViewById(R.id.basic_reflection);
        mBasicReflection.setOnClickListener(this);
        mFrameWork = findViewById(R.id.framework);
        mFrameWork.setOnClickListener(this);
        mBetweenApk = findViewById(R.id.between_apk);
        mBetweenApk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basic_reflection:
                reflectPeople();
                break;
            case R.id.framework:
                reflectFramework();
                break;
            case R.id.between_apk:
                reflectOtherApk();
                break;

        }
    }

    private void reflectOtherApk() {
        Util.reflectOtherApk(this);
    }

    private void reflectFramework() {
        Util.reflectFramework(this);
    }

    private void reflectPeople() {
        Util.reflectPeopleConstructor();
        Util.reflectPeopleFields();
        Util.reflectPeopleMethods();
    }
}
