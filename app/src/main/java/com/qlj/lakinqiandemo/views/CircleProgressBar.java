package com.qlj.lakinqiandemo.views;

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
import android.view.View;

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

    private ProgressFormatter mProgressFormatter = new DefaultProgressFormatter();

    public CircleProgressBar(Context context) {
        super(context);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        drawRingProgress(canvas);
        canvas.restore();
        drawProgressText(canvas);

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
