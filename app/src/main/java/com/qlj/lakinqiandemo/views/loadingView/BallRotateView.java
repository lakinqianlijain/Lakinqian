package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallRotateView extends BaseLoading {
    float scaleFloat=0.5f;

    float degress;

    public BallRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius=getWidth()/10;
        float x = getWidth()/ 2;
        float y = getHeight()/2;

        canvas.rotate(degress,getWidth()/2, getHeight()/2);

        canvas.save();
        canvas.translate(x - radius * 2 - radius, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.drawCircle(0, 0, radius, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.drawCircle(0, 0, radius, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(x + radius * 2 + radius, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.drawCircle(0,0,radius, mPaint);
        canvas.restore();
    }

    @Override
    protected void addAnimations() {
        ValueAnimator scaleAnim=ValueAnimator.ofFloat(0.5f,1,0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleFloat = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimators.add(scaleAnim);

        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0,180,360);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        mAnimators.add(rotateAnim);
    }
}
