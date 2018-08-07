package com.qlj.lakinqiandemo.contralayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.R;

public class SwipeHeader extends LinearLayout {

    public static final int LAYOUT_ID = R.layout.header_refresh_blue_mud;

    private AnimationDrawable mAnimationDrawable;
    private ImageView mLoadingImage;

    public SwipeHeader(Context context) {
        this(context, null);
    }

    public SwipeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setVisibility(GONE);
        LayoutInflater.from(context).inflate(LAYOUT_ID, this);
        mLoadingImage = findViewById(R.id.iv_loading);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
    }

    public void onBegin() {
        this.setScaleX(0.001f);
        this.setScaleY(0.001f);
    }

    public void onPull(float fraction) {
        float a = limitValue(1, fraction);
        this.setScaleX(a);
        this.setScaleY(a);
        this.setAlpha(a);
    }

    public void onRefreshing() {
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    public void onCompleted() {
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        this.setScaleX(0);
        this.setScaleY(0);
    }

    private float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }

    public void onAuto() {
        this.setScaleX(1);
        this.setScaleY(1);
        this.setAlpha(1);
    }
}
