package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

public class SquareSpinView extends BaseLoading {

    private float rotateX;
    private float rotateY;

    private Camera mCamera;
    private Matrix mMatrix;

    public SquareSpinView(Context context) {
        this(context, null);
    }

    public SquareSpinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareSpinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(rotateX);
        mCamera.rotateY(rotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-getWidth()/2, -getHeight()/2);
        mMatrix.postTranslate(getWidth()/2, getHeight()/2);
        canvas.concat(mMatrix);

        canvas.drawRect(new RectF(getWidth()/5, getHeight()/5, getWidth()*4/5, getHeight()*4/5), mPaint);
    }

    @Override
    protected void addAnimations() {
        ValueAnimator animator=ValueAnimator.ofFloat(0,180,180, 0, 0);
        animator.setDuration(2500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateX =(float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimators.add(animator);

        ValueAnimator animator2=ValueAnimator.ofFloat(0,0,180,180, 0);
        animator2.setDuration(2500);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateY =(float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimators.add(animator2);

    }
}
