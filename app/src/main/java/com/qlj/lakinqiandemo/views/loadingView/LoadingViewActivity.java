package com.qlj.lakinqiandemo.views.loadingView;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

public class LoadingViewActivity extends BaseActivity {
    BallPulseView mBallPulseView;
    BallGridPulseView mBallGridPulseView;
    BallClipRotateView mBallClipRotateView;
    BallClipRotatePulseView mBallClipRotatePulseView;
    SquareSpinView mSquareSpinView;
    BallClipRotateMultipleView mBallClipRotateMultipleView;
    BallPulseRiseView mBallPulseRiseView;
    BallRotateView mBallRotateView;
    BallZigZagView mBallZigZagView;
    LineScaleView mLineScaleView;
    BallSpinFadeLoaderView mBallSpinFadeLoaderView;
    PacmanView mPacmanView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        initViews();
    }

    private void initViews() {
        mBallPulseView = findViewById(R.id.bpv);
        mBallPulseView.startAnimation();
        mBallGridPulseView = findViewById(R.id.bgpv);
        mBallGridPulseView.startAnimation();
        mBallClipRotateView = findViewById(R.id.bcrv);
        mBallClipRotateView.startAnimation();
        mBallClipRotatePulseView = findViewById(R.id.bcrpv);
        mBallClipRotatePulseView.startAnimation();
        mSquareSpinView = findViewById(R.id.ssv);
        mSquareSpinView.startAnimation();
        mBallClipRotateMultipleView = findViewById(R.id.bcrmv);
        mBallClipRotateMultipleView.startAnimation();
        mBallPulseRiseView = findViewById(R.id.bprv);
        mBallPulseRiseView.startAnimation();
        mBallRotateView = findViewById(R.id.brv);
        mBallRotateView.startAnimation();
        mBallZigZagView = findViewById(R.id.bzzv);
        mBallZigZagView.startAnimation();
        mLineScaleView = findViewById(R.id.lsv);
        mLineScaleView.startAnimation();
        mBallSpinFadeLoaderView = findViewById(R.id.bsflv);
        mBallSpinFadeLoaderView.startAnimation();
        mPacmanView = findViewById(R.id.pv);
        mPacmanView.startAnimation();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
