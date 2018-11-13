package com.qlj.lakinqiandemo.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

/**
 * Created by lakinqian on 2018/10/23.
 */

public class CircleProgressBar extends View {
    private int mProgressColor = 0xffaa66cc;
    private int mProgressTextColor = 0xff33b5e5;
    private int mProgressTextSize = 14;
    private int mProgressStrokeWidth = 4;
    private int mProgressBackgroundColor = 0xffe3e3e5;

    private static final float MAX_DEGREE = 360.0f;
    private static final int DEFAULT_MAX = 100;

    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    private final Paint mProgressTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mProgressBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF mProgressRectF = new RectF();
    private final Rect mProgressTextRect = new Rect();

    private int mProgress;
    private int mMax = DEFAULT_MAX;
    private boolean isDrag;
    private float downX;
    private float downY;
    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;

    int l, r, t, b;

    private ProgressFormatter mProgressFormatter = new DefaultProgressFormatter();

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        initPaint();
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        mProgressColor = a.getColor(R.styleable.CircleProgressBar_cpb_progress_color, mProgressColor);
        mProgressTextColor = a.getColor(R.styleable.CircleProgressBar_cpb_progress_text_color, mProgressTextColor);
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.CircleProgressBar_cpb_progress_text_size, DensityUtil.dip2px(getContext(), mProgressTextSize));
        mProgressStrokeWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_cpb_progress_stroke_width, DensityUtil.dip2px(getContext(), mProgressStrokeWidth));
        mProgressBackgroundColor = a.getColor(R.styleable.CircleProgressBar_cpb_progress_background_color, mProgressBackgroundColor);
        a.recycle();
    }

    private void initPaint() {
        mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mProgressBackgroundPaint.setStyle(Paint.Style.STROKE);
        mProgressBackgroundPaint.setStrokeWidth(mProgressStrokeWidth);
        mProgressBackgroundPaint.setColor(mProgressBackgroundColor);
        mProgressBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressStrokeWidth);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        mRadius = Math.min(mCenterX, mCenterY);
        mProgressRectF.top = mCenterY - mRadius;
        mProgressRectF.bottom = mCenterY + mRadius;
        mProgressRectF.left = mCenterX - mRadius;
        mProgressRectF.right = mCenterX + mRadius;

        mProgressRectF.inset(mProgressStrokeWidth / 2, mProgressStrokeWidth / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        screenWidth = DensityUtil.getScreenWidth(JianApplication.get());
        screenHeight = DensityUtil.getScreenHeight(JianApplication.get()) - getStatusBarHeight();
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        drawRingProgress(canvas);
        canvas.restore();
        drawProgressText(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDrag = false;
                    downX = event.getX();
                    downY = event.getY();
                    Log.e("6666", "getLeft: " + getLeft() + "getX: " + getX());
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
//                    Log.e("6666", "onTouchEvent: " + xDistance + "---" + yDistance);

                    //当水平或者垂直滑动距离大于10,才算拖动事件
                    if (Math.abs(xDistance) > 10 || Math.abs(yDistance) > 10) {
                        isDrag = true;
                        Log.e("6666", "getLeft1111: " + getLeft());
                        l = (int) (getLeft() + xDistance);
                        r = l + width;
                        t = (int) (getTop() + yDistance);
                        b = t + height;
                        //不划出边界判断,此处应按照项目实际情况,因为本项目需求移动的位置是手机全屏,
                        // 所以才能这么写,如果是固定区域,要得到父控件的宽高位置后再做处理
                        if (l < 0) {
                            l = 0;
                            r = l + width;
                        } else if (r > screenWidth) {
                            r = screenWidth;
                            l = r - width;
                        }
                        if (t < 0) {
                            t = 0;
                            b = t + height;
                        } else if (b > screenHeight) {
                            b = screenHeight;
                            t = b - height;
                        }

                        this.layout(l, t, r, b);
                    }
                    break;
                case MotionEvent.ACTION_UP:
//                    setPressed(false);
//                    int left = (int) (2 * getLeft() + width) / 2;
//                    Log.e("6666", "onTouchEvent: " + "getLeft:" + getLeft() + "getRight:" + getRight() + "screenWidth:" + screenWidth);
                    moveToSides(l, t, r, b);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

    private void moveToSides(final int left, final int top, final int right, final int bottom) {
//        int fromXDelta = 0;
        final int toXDelta;
//        if (left < screenWidth / 2) {
//            toXDelta = -(left - width / 2);
////            marginRight = screenWidth - width;
//        } else {
//            toXDelta = screenWidth - left - width / 2;
////            mMarginRight = 0;
//        }
//        TranslateAnimation translate = new TranslateAnimation(fromXDelta, toXDelta, height, height);
//        translate.setDuration(1000);
//        translate.setFillAfter(true);
//        startAnimation(translate);
//        mMarginBottom = mScreenHeight - bottom;
//        ObjectAnimator.ofFloat(this, "translationX", fromXDelta, toXDelta).setDuration(1000).start();
        Log.e("6666", "moveToSides: " + left + "----" + screenWidth / 2);
        int left1 = left + width / 2;
        if (left1 < screenWidth / 2) {
            toXDelta = -(left1 - width / 2);
        } else {
            toXDelta = screenWidth - left1 - width / 2;
        }

        ValueAnimator mAnimator = ValueAnimator.ofInt(0, 100);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                CircleProgressBar.this.layout((int) (left + toXDelta * progress / 100), top, (int) (left + toXDelta * progress / 100 + width), bottom);
            }
        });
//        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(500);
        mAnimator.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("6666", "onLayout: " + changed + "  left:" + left + "  top:" + top + "  right:" + right + "  bottom:" + bottom);
    }

    private void drawProgressText(Canvas canvas) {
        if (mProgressFormatter == null) {
            return;
        }
        CharSequence progressText = mProgressFormatter.format(mProgress, mMax);
        if (TextUtils.isEmpty(progressText)) {
            return;
        }
        mProgressTextPaint.setTextSize(mProgressTextSize);
        mProgressTextPaint.setColor(mProgressTextColor);

        mProgressTextPaint.getTextBounds(String.valueOf(progressText), 0, progressText.length(), mProgressTextRect);
        canvas.drawText(progressText, 0, progressText.length(), mCenterX, mCenterY + mProgressTextRect.height() / 2, mProgressTextPaint);
    }

    private void drawRingProgress(Canvas canvas) {
        canvas.drawArc(mProgressRectF, 0.0f, MAX_DEGREE, false, mProgressBackgroundPaint);
        canvas.drawArc(mProgressRectF, 0.0f, MAX_DEGREE * mProgress / mMax, false, mProgressPaint);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }


    public interface ProgressFormatter {
        CharSequence format(int progress, int max);
    }

    private static final class DefaultProgressFormatter implements ProgressFormatter {
        private static final String DEFAULT_PATTERN = "%d%%";

        @Override
        public CharSequence format(int progress, int max) {
            return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
        }
    }
}
