package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.qlj.lakinqiandemo.views.animation.UiUtils;

public class PacmanView extends BaseLoading {

    private float translateX;

    private int alpha;

    private float degrees1,degrees2;

    public PacmanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PacmanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPacman(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        float radius=getWidth()/11;
        mPaint.setAlpha(alpha);
        canvas.drawCircle(translateX, getHeight() / 2, radius, mPaint);
    }

    private void drawPacman(Canvas canvas) {
        float x=getWidth()/2;
        float y=getHeight()/2;

        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(degrees1);
        mPaint.setAlpha(255);
        RectF rectF1=new RectF(-x/1.7f,-y/1.7f,x/1.7f,y/1.7f);
        canvas.drawArc(rectF1, 0, 180, true, mPaint);
        canvas.restore();

        canvas.save();
        canvas.save();
        canvas.translate(x, y);
        mPaint.setAlpha(255);
        canvas.rotate(degrees2);
        RectF rectF2=new RectF(-x/1.7f,-y/1.7f,x/1.7f,y/1.7f);
        canvas.drawArc(rectF2,90,270,true,mPaint);
        canvas.restore();
    }

    @Override
    protected void addAnimations() {
        float width = UiUtils.dipToPx(getContext(), 60);
        float startT = width/11;
        ValueAnimator translationAnim=ValueAnimator.ofFloat(width - startT,width/2);
        translationAnim.setDuration(650);
        translationAnim.setInterpolator(new LinearInterpolator());
        translationAnim.setRepeatCount(-1);
        translationAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator alphaAnim=ValueAnimator.ofInt(255,122);
        alphaAnim.setDuration(650);
        alphaAnim.setRepeatCount(-1);
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator rotateAnim1=ValueAnimator.ofFloat(0, 45, 0);
        rotateAnim1.setDuration(650);
        rotateAnim1.setRepeatCount(-1);
        rotateAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees1 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator rotateAnim2=ValueAnimator.ofFloat(0,-45,0);
        rotateAnim2.setDuration(650);
        rotateAnim2.setRepeatCount(-1);
        rotateAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees2 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mAnimators.add(translationAnim);
        mAnimators.add(alphaAnim);
        mAnimators.add(rotateAnim1);
        mAnimators.add(rotateAnim2);
    }
}
