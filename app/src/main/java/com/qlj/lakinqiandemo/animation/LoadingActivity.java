package com.qlj.lakinqiandemo.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LoadingActivity extends BaseActivity implements View.OnClickListener {
    private LeafLoadingView mLeafLoadingView;
    private View mFanView;
    private int mProgress = 0;
    private Button mLoadBtn, mClearBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
    }

    private void initView() {
        mFanView = findViewById(R.id.fan_pic);
        RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true,
                Animation.INFINITE);
        mFanView.startAnimation(rotateAnimation);
        mClearBtn = findViewById(R.id.btn_clear);
        mClearBtn.setOnClickListener(this);
        mLoadBtn = findViewById(R.id.btn_clear);
        mLoadBtn.setOnClickListener(this);
        mLeafLoadingView = findViewById(R.id.leaf_loading);

    }

    @Override
    public void onClick(View v) {
        if (v == mClearBtn) {
            mLeafLoadingView.setProgress(0);
            mProgress = 0;
        } else if (v == mLoadBtn) {
            mProgress++;
            mLeafLoadingView.setProgress(0);
        }
    }
}
