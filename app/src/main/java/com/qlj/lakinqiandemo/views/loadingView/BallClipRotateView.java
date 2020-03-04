package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallClipRotateView extends BaseLoading {
    float scaleFloat = 1, degrees;

    public BallClipRotateView(Context context) {
        super(context);
    }

    public BallClipRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallClipRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        float circleSpacing = 12;
        float translateX = getWidth() / 2;
        float translateY = getHeight() / 2;
        canvas.translate(translateX, translateY);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.rotate(degrees);
        RectF rectF = new RectF(-translateX + circleSpacing, -translateY + circleSpacing, translateX - circleSpacing, translateY - circleSpacing);
        canvas.drawArc(rectF, 0, 315, false, mPaint);
    }

    @Override
    protected void addAnimations() {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(1,0.7f, 0.4f, 1);
        scaleAnim.setDuration(750);
        scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scaleFloat = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180, 360);
        rotateAnim.setDuration(750);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimators.add(scaleAnim);
        mAnimators.add(rotateAnim);

    }
}
