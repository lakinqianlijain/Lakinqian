package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.qlj.lakinqiandemo.views.animation.UiUtils;

public class BallZigZagView extends BaseLoading {

    float[] translateX=new float[2],translateY=new float[2];

    float mWidth, mHeight;

    public BallZigZagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallZigZagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWidth = UiUtils.dipToPx(getContext(), 60);
        mHeight = UiUtils.dipToPx(getContext(), 60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            canvas.save();
            canvas.translate(translateX[i], translateY[i]);
            canvas.drawCircle(0, 0, getWidth() / 10, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void addAnimations() {
        float startX = mWidth / 6;
        float startY = mHeight / 6;
        for (int i = 0; i < 2; i++) {
            final int index=i;
            ValueAnimator translateXAnim=ValueAnimator.ofFloat(startX,mWidth-startX,mWidth/2,startX);
            if (i==1){
                translateXAnim=ValueAnimator.ofFloat(mWidth-startX,startX,mWidth/2,mWidth-startX);
            }
            ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,startY,mHeight/2,startY);
            if (i==1){
                translateYAnim=ValueAnimator.ofFloat(mHeight-startY,mHeight-startY,mHeight/2,mHeight-startY);
            }

            translateXAnim.setDuration(1000);
            translateXAnim.setInterpolator(new LinearInterpolator());
            translateXAnim.setRepeatCount(-1);
            translateXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateX[index] = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            translateYAnim.setDuration(1000);
            translateYAnim.setInterpolator(new LinearInterpolator());
            translateYAnim.setRepeatCount(-1);
            translateYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateY[index] = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimators.add(translateXAnim);
            mAnimators.add(translateYAnim);
        }
    }
}
