package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallGridPulseView extends BaseLoading {

    public static final int ALPHA=255;

    public static final float SCALE=1.0f;

    int[] alphas=new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    float[] scaleFloats=new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    public BallGridPulseView(Context context) {
        super(context);
    }

    public BallGridPulseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallGridPulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleSpacing = 4;
        float radius=( getWidth() - circleSpacing*4)/6;
        float x = getWidth()/ 2 - (radius * 2 + circleSpacing);
        float y = getWidth()/ 2 - (radius * 2 + circleSpacing);

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                canvas.save();
                float translateX = x + (radius * 2) * j + circleSpacing * j;
                float translateY = y + (radius * 2) * i + circleSpacing * i;
                canvas.translate(translateX, translateY);
                canvas.scale(scaleFloats[3*i + j], scaleFloats[3*i + j]);
                mPaint.setAlpha(alphas[3*i + j]);
                canvas.drawCircle(0, 0, radius, mPaint);
                canvas.restore();
            }
        }
    }

    @Override
    protected void addAnimations() {
        int[] durations={720, 1020, 1280, 1420, 1450, 1180, 870, 1450, 1060};
        int[] delays= {-60, 250, -170, 480, 310, 30, 460, 780, 450};
        for(int i = 0; i < 9; i++){
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1,0.5f,1);
            scaleAnim.setDuration(durations[i]);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    scaleFloats[index] = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            ValueAnimator alphaAnim=ValueAnimator.ofInt(255,122, 255);
            alphaAnim.setDuration(durations[i]);
            alphaAnim.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    alphas[index] = (int) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimators.add(scaleAnim);
            mAnimators.add(alphaAnim);
        }
    }
}
