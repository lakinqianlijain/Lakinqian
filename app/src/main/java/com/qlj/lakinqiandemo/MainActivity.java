package com.qlj.lakinqiandemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qlj.lakinqiandemo.ui.ReflectionActivity;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mReflection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mReflection = findViewById(R.id.reflection);
        mReflection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reflection:
                JumpActivityUtil.JumpSelfActivity(this, ReflectionActivity.class);
        }
    }
}
