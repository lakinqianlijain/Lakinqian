package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallPulseView extends BaseLoading {

    public static final float SCALE=1.0f;

    //scale x ,y
    private float[] scaleFloats=new float[]{SCALE,
            SCALE,
            SCALE};

    public BallPulseView(Context context) {
        super(context);
    }

    public BallPulseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallPulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleSpacing=4;
        float radius =(Math.min(getWidth(), getHeight()) - circleSpacing*2 )/6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < 3; i++){
            canvas.save();
            canvas.translate(x + (radius * 2) * i + circleSpacing * i, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }

    }

    @Override
    protected void addAnimations() {
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++){
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[i]);

            final int index = i;
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    scaleFloats[index] = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimators.add(scaleAnim);
        }
    }
}
