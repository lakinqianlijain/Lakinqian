package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallClipRotatePulseView extends BaseLoading {

    float scaleFloat1 = 1, scaleFloat2 = 1, degrees;

    public BallClipRotatePulseView(Context context) {
        super(context);
    }

    public BallClipRotatePulseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallClipRotatePulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleSpacing = 12;
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        //draw fill circle
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scaleFloat1, scaleFloat1);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, x / 3, mPaint);
        canvas.restore();

        canvas.translate(x, y);
        canvas.scale(scaleFloat2, scaleFloat2);
        canvas.rotate(degrees);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        //draw two arc
        float[] startAngles=new float[]{45,225};
        for (int i = 0; i < 2; i++){
            RectF rectF = new RectF(-x + circleSpacing, -y + circleSpacing,
                    x - circleSpacing, y - circleSpacing);
            canvas.drawArc(rectF, startAngles[i], 90, false, mPaint);
        }

    }

    @Override
    protected void addAnimations() {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(1,0.3f,1);
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scaleFloat1 = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimators.add(scaleAnim);

        ValueAnimator scaleAnim2 = ValueAnimator.ofFloat(1,0.6f,1);
        scaleAnim2.setDuration(1000);
        scaleAnim2.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scaleFloat2 = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimators.add(scaleAnim2);

        ValueAnimator rotateAnim = ValueAnimator.ofFloat(0,180,360);
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                degrees = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimators.add(rotateAnim);
    }
}
