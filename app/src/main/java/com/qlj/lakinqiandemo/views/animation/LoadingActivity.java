package com.qlj.lakinqiandemo.views.animation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.views.countdown.CountdownView;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LoadingActivity extends BaseActivity implements View.OnClickListener {
    private static final int REFRESH_PROGRESS = 0x10;

    private LeafLoadingView mLeafLoadingView;
    private CountdownView mCountdownView;
    private View mFanView;
    private int mProgress = 0;
    private Button mLoadBtn, mClearBtn;
    private long mStartTime = 0;
    private long mTotalTime = 20;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    mProgress++;
                    mLeafLoadingView.setProgress(mProgress);
                    mStartTime++;
                    mCountdownView.setProgress(mStartTime, mTotalTime);
                    // 随机800ms以内刷新一次
                    mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
                    break;

                default:
                    break;
            }
        }
    };

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
        mLoadBtn = findViewById(R.id.btn_load);
        mLoadBtn.setOnClickListener(this);
        mLeafLoadingView = findViewById(R.id.leaf_loading);
        mCountdownView = findViewById(R.id.cv_countdown_view);

    }

    @Override
    public void onClick(View v) {
        if (v == mClearBtn) {
            mProgress = 0;
            mLeafLoadingView.setProgress(0);
            mStartTime = 0;
            mCountdownView.setProgress(mStartTime, mTotalTime);
            mHandler.removeMessages(REFRESH_PROGRESS);
        } else if (v == mLoadBtn) {
            mProgress = 0;
            mLeafLoadingView.setProgress(0);
            mCountdownView.setProgress(mStartTime, mTotalTime);
            mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
        }
    }
}
