package com.qlj.lakinqiandemo.views.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.Locale;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by lakinqian on 2019/1/10.
 */

public class CountdownView extends View {
    public final static int SECONDS_PER_HOUR = 3600;
    public final static int SECONDS_PER_MINUTE = 60;
    //  总进度
    private static final float TOTAL_PROGRESS = 100;
    private long mRemainTime;

    private static final float HEIGHT_FACTOR = 0.85f;
    private static final float MARGIN_FACTOR = 1 - HEIGHT_FACTOR;

    private int mProgressTextColor = 0xff33b5e5;
    private int mProgressTextSize = 12;
    private int mProgressBackgroundColor = 0x33000000;
    private int mProgressStartColor = 0xffFFF024;
    private int mProgressEndColor = 0xffFFA100;

    private Context mContext;

    private Paint mBitmapPaint, mBackgroundPaint, mProgressPaint;
    private final Paint mProgressTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mClockBitmap;
    private float mTotalWidth, mTotalHeight;
    // 左边弧的长度
    private float mLeftArcLength;
    // 左边劣弧角度
    private float mLeftArcAngle;
    // 圆的半径
    private float mLeftArcRadius, mRightArcRadius;
    // 当前进度
    private float mProgress;
    // 所绘制的进度条部分的宽度
    private float mProgressWidth;
    // 当前所在的绘制的进度条的位置
    private float mCurrentProgressPosition;
    //
    private int mBitmapWidth, mBitmapHeight;
    private Matrix mMatrix;

    private RectF mArcLeftRectF, mArcRightRectF, mGrayRectF, mProgressRectF;
    private CountDownFormatter mCountDownFormatter = new DefaultCountDownFormatter();
    private final Rect mCountdownTextRect = new Rect();

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttributes(context, attrs);
        initBitmap();
        initPaint();
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountdownView);

        mProgressStartColor = a.getColor(R.styleable.CountdownView_cv_progress_start_color, mProgressStartColor);
        mProgressEndColor = a.getColor(R.styleable.CountdownView_cv_progress_end_color, mProgressEndColor);
        mProgressTextColor = a.getColor(R.styleable.CountdownView_cv_progress_text_color, mProgressTextColor);
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.CountdownView_cv_progress_text_size, DensityUtil.dip2px(getContext(), mProgressTextSize));
        mProgressBackgroundColor = a.getColor(R.styleable.CountdownView_cv_progress_background_color, mProgressBackgroundColor);
        a.recycle();
    }

    private void initBitmap() {
        mClockBitmap = ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ic_clock)).getBitmap();
        mBitmapWidth = mClockBitmap.getWidth();
        mBitmapHeight = mClockBitmap.getHeight();
        mMatrix = new Matrix();
    }

    private void initPaint() {
        mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mProgressBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(mProgressStartColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;
        mLeftArcRadius = mTotalHeight / 2;
        mRightArcRadius = mLeftArcRadius * HEIGHT_FACTOR;
        mProgressWidth = mTotalWidth;

        mLeftArcLength = (float) (Math.cos(Math.asin(HEIGHT_FACTOR)) + 1) * mLeftArcRadius;
        mLeftArcAngle = (float) Math.toDegrees(Math.asin(HEIGHT_FACTOR));
        Log.e("6666", "onSizeChanged: " + mLeftArcLength + "----" + mTotalHeight);

        // 左侧圆形
        mArcLeftRectF = new RectF(0, 0, mLeftArcRadius * 2, mTotalHeight);
        // 进度条矩形区域
        mProgressRectF = new RectF(mLeftArcLength, mLeftArcRadius * MARGIN_FACTOR, mCurrentProgressPosition, mTotalHeight - mLeftArcRadius * MARGIN_FACTOR);
        // 背景矩形区域
        mGrayRectF = new RectF(mCurrentProgressPosition, mLeftArcRadius * MARGIN_FACTOR,
                mTotalWidth - mRightArcRadius, mTotalHeight - (mLeftArcRadius * MARGIN_FACTOR));
        // 右侧圆形
        mArcRightRectF = new RectF(mTotalWidth - 2 * mRightArcRadius, mLeftArcRadius * MARGIN_FACTOR, mTotalWidth, mLeftArcRadius * MARGIN_FACTOR + mRightArcRadius * 2);

        updateProgressShader();

    }

    private void updateProgressShader() {
        if (mProgressStartColor != mProgressEndColor) {
            Shader shader = null;
            shader = new LinearGradient(0, 0,
                    mTotalWidth, mTotalHeight,
                    mProgressStartColor, mProgressEndColor, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.setRotate(0, mTotalWidth / 2, mTotalHeight / 2);
            shader.setLocalMatrix(matrix);
            mProgressPaint.setShader(shader);
        } else {
            mProgressPaint.setShader(null);
            mProgressPaint.setColor(mProgressStartColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawableClickBitmap(canvas);
        drawCountdownText(canvas);
    }

    private void drawableClickBitmap(Canvas canvas) {
//        Rect src = new Rect(0, 0, (int)mTotalHeight, (int)mTotalHeight);
//        Rect dst = new Rect((int)(mTotalHeight/2),(int)(mTotalHeight/2),(int)(mTotalHeight),(int)(mTotalHeight));
//
//        canvas.drawBitmap(mClockBitmap, src, dst, mBitmapPaint);

        mMatrix.reset();
        mMatrix.postScale(3, 3);
        mMatrix.postTranslate(mTotalHeight/2 - 1.5f * mBitmapWidth, mTotalHeight/2 - 1.5f * mBitmapHeight);
//        canvas.scale(3,3);
//        canvas.translate(mTotalHeight/6 - 0.5f * mBitmapWidth, mTotalHeight/6 - 0.5f * mBitmapHeight);
        canvas.drawBitmap(mClockBitmap, mMatrix, mBitmapPaint);

    }

    private void drawProgress(Canvas canvas) {
//        if (mProgress >= TOTAL_PROGRESS) {
//            mProgress = 0;
//        }
        // 此处没有按标准的面积来算，而是按长度来计算进度
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        Log.i(TAG, "mProgress = " + mProgress + "---mCurrentProgressPosition = "
                + mCurrentProgressPosition);
        if (mCurrentProgressPosition <= mLeftArcLength) {
            // 这个时候需要先绘制背景ARC,再绘制背景矩形，最后绘制进度ARC,
            // 背景ARC
            canvas.drawArc(mArcLeftRectF, mLeftArcAngle, 360 - 2 * mLeftArcAngle, false, mBackgroundPaint);
            // 绘制背景矩形，这时半圆还没填满，所以背景矩形的X坐标不能用mCurrentProgressPosition表示
            mGrayRectF.left = mLeftArcLength;
            canvas.drawRect(mGrayRectF, mBackgroundPaint);
            // 绘制右侧ARC
            canvas.drawArc(mArcRightRectF, -90, 180, false, mBackgroundPaint);
            // 绘制进度ARC，背景ARC有一部分会被盖住
            float angle; // 单边角度
            if (mCurrentProgressPosition <= mLeftArcRadius) {
                angle = 180 - (float) Math.toDegrees(Math.acos((mLeftArcRadius - mCurrentProgressPosition)
                        / mLeftArcRadius));
            } else {
                angle = (float) Math.toDegrees(Math.acos((mCurrentProgressPosition - mLeftArcRadius)
                        / mLeftArcRadius));
            }

            canvas.drawArc(mArcLeftRectF, angle, 360 - 2 * angle, false, mProgressPaint);
        } else if (mCurrentProgressPosition <= (mTotalWidth - mRightArcRadius)) {
            // 这个时候，左边圆已经填满，先绘制进度条圆和进度矩形，再绘制灰色背景矩形，
            // 左侧ARC
            canvas.drawArc(mArcLeftRectF, mLeftArcAngle, 360 - 2 * mLeftArcAngle, false, mProgressPaint);
            // 绘制进度矩形
            mProgressRectF.left = mLeftArcLength;
            mProgressRectF.right = mCurrentProgressPosition;
            canvas.drawRect(mProgressRectF, mProgressPaint);
            // 绘制灰色背景矩形
            mGrayRectF.left = mCurrentProgressPosition;
            canvas.drawRect(mGrayRectF, mBackgroundPaint);
            // 绘制右侧ARC
            canvas.drawArc(mArcRightRectF, -90, 180, false, mBackgroundPaint);
        } else {
            // 这个时候，左边圆已经填满，矩形区域也填满，只剩右边半圆，先绘制左边进度条圆和进度矩形，再绘制右边进度圆，最后绘制剩下的一点点灰色半圆
            // 左侧ARC
            canvas.drawArc(mArcLeftRectF, mLeftArcAngle, 360 - 2 * mLeftArcAngle, false, mProgressPaint);
            // 绘制白色矩形，这时矩形已被完全填满，所以背景矩形的X坐标不能用mCurrentProgressPosition表示
            mGrayRectF.left = (mLeftArcLength);
            canvas.drawRect(mGrayRectF, mProgressPaint);
            // 右侧ARC进度绘制 -> 现将右侧半圆绘制成进度ARC，再将进度条以后的绘制成背景色
            // 单边角度
            float angle = (float) Math.toDegrees(Math.acos((mRightArcRadius - (mTotalWidth - mCurrentProgressPosition))
                    / mRightArcRadius));
            // 绘制右侧进度ARC
            canvas.drawArc(mArcRightRectF, angle, 360 - 2 * angle, false, mProgressPaint);
            canvas.drawArc(mArcRightRectF, -angle, 2 * angle, false, mBackgroundPaint);
        }
    }

    private void drawCountdownText(Canvas canvas) {
        if (mCountDownFormatter == null) {
            return;
        }
        if (mRemainTime < 0) {
            mRemainTime = 0;
        }
        CharSequence countDownText = mCountDownFormatter.format(mRemainTime);
        if (TextUtils.isEmpty(countDownText)) {
            return;
        }
        mProgressTextPaint.setTextSize(mProgressTextSize);
        mProgressTextPaint.setColor(mProgressTextColor);

        mProgressTextPaint.getTextBounds(String.valueOf(countDownText), 0, countDownText.length(), mCountdownTextRect);
        canvas.drawText(countDownText, 0, countDownText.length(), (mTotalWidth + mLeftArcLength) / 2, (mTotalHeight + mCountdownTextRect.height()) / 2, mProgressTextPaint);
    }

    public interface CountDownFormatter {
        CharSequence format(long remain);
    }

    private static final class DefaultCountDownFormatter implements CountDownFormatter {
        @Override
        public CharSequence format(long secs) {
            int hour = (int) (secs / SECONDS_PER_HOUR);
            secs = secs % SECONDS_PER_HOUR;
            int minute = (int) (secs / SECONDS_PER_MINUTE);
            secs = secs % SECONDS_PER_MINUTE;

            if (hour > 0) {
                return String.format(Locale.US, "%02d:%02d:%02d", hour, minute, secs);
            } else {
                return String.format(Locale.US, "%02d:%02d", minute, secs);
            }
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        mProgress = progress < TOTAL_PROGRESS ? progress : TOTAL_PROGRESS;
        postInvalidate();
    }

    public void setProgress(long startTime, long total) {
        if (startTime > total) return;
        mRemainTime = total - startTime;
        setProgress(startTime * TOTAL_PROGRESS / total);
    }
}
