package com.qlj.lakinqiandemo.views.loadingView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BallSpinFadeLoaderView extends BaseLoading{

    public static final float SCALE=1.0f;

    public static final int ALPHA=255;

    float[] scaleFloats=new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas=new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    public BallSpinFadeLoaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallSpinFadeLoaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float radius = getWidth()/10;
//        for (int i = 0; i < 8; i++) {
//            canvas.save();
//            Point point = circleAt(getWidth(),getHeight(),getWidth()/2-radius,i*(2*Math.PI/8));
//            canvas.translate(point.x,point.y);
//            canvas.scale(scaleFloats[i],scaleFloats[i]);
//            mPaint.setAlpha(alphas[i]);
//            canvas.drawCircle(0,0,radius,mPaint);
//            canvas.restore();
//        }

        float radius=getWidth()/10;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point=circleAt(getWidth(),getHeight(),getWidth()/2.5f-radius,i*(Math.PI/4));
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.rotate(i*45);
            mPaint.setAlpha(alphas[i]);
            RectF rectF=new RectF(-radius,-radius/1.5f,1.5f*radius,radius/1.5f);
            canvas.drawRoundRect(rectF,5,5,mPaint);
            canvas.restore();
        }
    }

    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     *则点A的坐标为(a+R*cosα,b+R*sinα)
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    Point circleAt(int width,int height,float radius,double angle){
        int x= (int) (width/2 + radius * (Math.cos(angle)));
        int y= (int) (height/2 + radius * (Math.sin(angle)));
        return new Point(x,y);
    }

    @Override
    protected void addAnimations() {
        int[] delays= {0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++) {
            final int index=i;
            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.4f,1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimators.add(scaleAnim);
            mAnimators.add(alphaAnim);
        }
    }
}
