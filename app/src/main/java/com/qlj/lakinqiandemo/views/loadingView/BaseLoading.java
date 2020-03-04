package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public abstract class BaseLoading extends View {
    protected Paint mPaint=new Paint();
    protected ArrayList<ValueAnimator> mAnimators = new ArrayList<>();

    public BaseLoading(Context context) {
        this(context, null);
    }

    public BaseLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    protected abstract void addAnimations();

    public void startAnimation(){
        addAnimations();
        Log.e("6666", "startAnimation: "+mAnimators.size() );
        for (int i = 0; i < mAnimators.size(); i++) {
            ValueAnimator animator = mAnimators.get(i);
            animator.start();
        }
    }

    public void stopAnimation(){

    }
}
