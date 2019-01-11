package com.qlj.lakinqiandemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by lakinqian on 2019/1/10.
 */

public class CountdownView extends View {
    //  总进度
    private static final int TOTAL_PROGRESS = 100;

    private int mProgressTextColor = 0xff33b5e5;
    private int mProgressTextSize = 14;
    private int mProgressBackgroundColor = 0x33000000;
    private int mProgressStartColor = 0xffFFF024;
    private int mProgressEndColor = 0xffFFA100;

    private Context mContext;

    private Paint mBitmapPaint, mBackgroundPaint, mProgressPaint;
    private Bitmap mClockBitmap;
    private float mTotalWidth, mTotalHeight;
    private float mLeftArcLength;
    // 弧形的半径
    private float mLeftArcRadius, mRightArcRadius;
    // 当前进度
    private int mProgress;
    // 所绘制的进度条部分的宽度
    private float mProgressWidth;
    // 当前所在的绘制的进度条的位置
    private float mCurrentProgressPosition;

    private RectF mArcLeftRectF, mArcRightRectF, mGrayRectF, mProgressRectF;

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
    }

    private void initPaint() {
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
        mRightArcRadius = (float) (mLeftArcRadius * 0.85);
        mProgressWidth = mTotalWidth;

        mLeftArcLength = (float) ((Math.cos(Math.asin(0.85)) + 1) * mLeftArcRadius);

        // 左侧圆形
        mArcLeftRectF = new RectF(0, 0, mLeftArcRadius * 2, mTotalHeight);
        // 进度条矩形区域
        mProgressRectF = new RectF(mLeftArcLength, (float) (mLeftArcRadius * 0.15), mCurrentProgressPosition, (float) (mTotalHeight - (mLeftArcRadius * 0.15)));
        // 背景矩形区域
        mGrayRectF = new RectF(mCurrentProgressPosition, (float) (mLeftArcRadius * 0.15),
                mTotalWidth - mRightArcRadius, (float) (mTotalHeight - (mLeftArcRadius * 0.15)));
        // 右侧圆形
        mArcRightRectF = new RectF(mTotalWidth - 2 * mRightArcRadius, (float) (0.15 * mLeftArcRadius), mTotalWidth, (float) (mTotalHeight - mRightArcRadius * 0.15));

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
        canvas.drawBitmap(mClockBitmap, (float) (mTotalHeight * 0.15), (float) (mTotalHeight * 0.15), mBitmapPaint);
//        postInvalidate();
    }

    private void drawProgress(Canvas canvas) {
        if (mProgress >= TOTAL_PROGRESS) {
            mProgress = 0;
        }
        // 此处没有按标准的面积来算，而是按长度来计算进度
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        Log.i(TAG, "mProgress = " + mProgress + "---mCurrentProgressPosition = "
                + mCurrentProgressPosition);
        if (mCurrentProgressPosition <= mLeftArcLength) {
            // 这个时候需要绘制灰色ARC,在绘制进度ARC,在绘制灰色矩形
            // 左侧白色ARC
            float leftArcAngle = (float) Math.toDegrees(Math.asin(0.85));
            canvas.drawArc(mArcLeftRectF, leftArcAngle, 360 - 2 * leftArcAngle, false, mBackgroundPaint);
            // 绘制白色矩形，这时半圆还没填满，所以白色矩形的X坐标不能用mCurrentProgressPosition表示
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
            float leftArcAngle = (float) Math.toDegrees(Math.asin(0.85));
            canvas.drawArc(mArcLeftRectF, leftArcAngle, 360 - 2 * leftArcAngle, false, mProgressPaint);
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
            float leftArcAngle = (float) Math.toDegrees(Math.asin(0.85));
            canvas.drawArc(mArcLeftRectF, leftArcAngle, 360 - 2 * leftArcAngle, false, mProgressPaint);
            // 绘制白色矩形，这时矩形已被完全填满，所以背景矩形的X坐标不能用mCurrentProgressPosition表示
            mGrayRectF.left = (float) (mLeftArcRadius * 1.6);
            canvas.drawRect(mGrayRectF, mProgressPaint);
            // 右侧ARC进度绘制 -> 现将右侧半圆绘制成进度ARC，再将进度条以后的绘制成背景色
            // 绘制右侧进度ARC
            // 绘制右侧ARC
            canvas.drawArc(mArcRightRectF, -90, 180, false, mProgressPaint);
            // 绘制覆盖右侧背景ARC，有一部分进度ARC被覆盖
            // 单边角度
            float angle = 180 - (float) Math.toDegrees(Math.acos((mRightArcRadius - (mTotalWidth - mCurrentProgressPosition))
                    / mRightArcRadius));
            canvas.drawArc(mArcLeftRectF, -angle, 2 * angle, false, mProgressPaint);
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }
}
