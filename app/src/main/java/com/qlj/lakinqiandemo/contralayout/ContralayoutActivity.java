package com.qlj.lakinqiandemo.contralayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

/**
 * Created by Administrator on 2018/8/12.
 */

public class ContralayoutActivity extends BaseActivity implements View.OnClickListener {
    private TextView mScrollSnap, mParallaxEffect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contralayout);
        initView();
    }

    private void initView() {
        mScrollSnap = findViewById(R.id.first);
        mScrollSnap.setOnClickListener(this);
        mParallaxEffect = findViewById(R.id.second);
        mParallaxEffect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                JumpActivityUtil.JumpSelfActivity(this, FirstActivity.class);
                break;
            case R.id.second:
                JumpActivityUtil.JumpSelfActivity(this, SecondActivity.class);
                break;
        }
    }
}
