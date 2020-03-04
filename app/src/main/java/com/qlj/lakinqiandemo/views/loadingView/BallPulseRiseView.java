package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

public class BallPulseRiseView extends BaseLoading {
    private Camera mCamera;
    private Matrix mMatrix;

    private float dregess;

    public BallPulseRiseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallPulseRiseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCamera=new Camera();
        mMatrix=new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(dregess);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-getWidth()/2, -getHeight()/2);
        mMatrix.postTranslate(getWidth()/2, getHeight()/2);
        canvas.concat(mMatrix);

        float radius=getWidth()/10;
        canvas.drawCircle(getWidth()/4,radius*2,radius,mPaint);
        canvas.drawCircle(getWidth()*3/4,radius*2,radius,mPaint);

        canvas.drawCircle(radius,getHeight()-2*radius,radius,mPaint);
        canvas.drawCircle(getWidth()/2,getHeight()-2*radius,radius,mPaint);
        canvas.drawCircle(getWidth()-radius,getHeight()-2*radius,radius,mPaint);
    }

    @Override
    protected void addAnimations() {
        ValueAnimator animator=ValueAnimator.ofFloat(0,360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dregess = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setDuration(1500);
        mAnimators.add(animator);
    }
}
