package com.qlj.lakinqiandemo.views.animation;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class GADownloadingView extends View {
    private static final int ZERO_PROGRESS = 0;
    private static final int FULL_PROGRESS = 100;
    private static final int HALF_PROGRESS = 50;
    private static final int INVALID_PROGRESS = -1;
    private static final float FULL_NORMALIZED_TIME = 1F;

    private int mArrowColor;
    private int mLoadingCircleBackColor;
    private int mLoadingLineColor;
    private int mProgressLineColor;
    private int mProgressTextColor;
    private int mDoneTextColor;

    private boolean mIsFailed;
    private int mNextProgress, mLastValidProgress;
    private String mLastValidProgressTextStr;
    private List<Animator> mAnimatorList;

    // loadingView
    private int mLoadingViewCenterX;
    private int mLoadingViewCenterY;
    private int mLoadingViewWidth;
    private int mLoadingViewHeight;

    // paint
    private Paint mDefaultPaint, mBaseLinePaint, mProgressTextPaint;
    private Path mBaseLinePath, mArrowPath;
    private RectF mBaseLineRectF, mArrowRectF, mCircleRectF;
    private Rect mProgressTextRect;
    private Xfermode mXfermode;

    public GADownloadingView(Context context) {
        super(context);
    }

    public GADownloadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.downloading_def_style);
    }

    public GADownloadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.GADownloadingView, defStyleAttr, 0);
        mArrowColor = typedArray.getColor(R.styleable.GADownloadingView_arrow_color, 0);
        mLoadingCircleBackColor = typedArray.getColor(R.styleable.GADownloadingView_loading_circle_back_color, 0);
        mLoadingLineColor = typedArray.getColor(R.styleable.GADownloadingView_loading_line_color, 0);
        mProgressLineColor = typedArray.getColor(R.styleable.GADownloadingView_progress_line_color, 0);
        mProgressTextColor = typedArray.getColor(R.styleable.GADownloadingView_progress_text_color, 0);
        mDoneTextColor = typedArray.getColor(R.styleable.GADownloadingView_done_text_color, 0);
        typedArray.recycle();

        init();
    }

    private void init() {
        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT); //图像混合处理模式

        mBaseLinePaint = new Paint();
        mBaseLinePaint.setAntiAlias(true);// 抗锯齿
        mBaseLinePaint.setStyle(Paint.Style.STROKE); //Paint.Style.STROKE 只绘制图形轮廓,Paint.Style.FILL 只绘制图形内容, Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
        mBaseLinePaint.setStrokeCap(Paint.Cap.ROUND); // Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        mBaseLinePaint.setPathEffect(new CornerPathEffect(10f)); // CornerPathEffect——圆形拐角效果,DashPathEffect——虚线效果,DiscretePathEffect——离散路径效果

        mBaseLinePath = new Path();
        mBaseLineRectF = new RectF();
        mArrowPath = new Path();
        mCircleRectF = new RectF();
        mArrowRectF = new RectF();

        mIsFailed = false;
        mNextProgress = ZERO_PROGRESS;
        mLastValidProgressTextStr = "";
        mLastValidProgress = INVALID_PROGRESS;

        mProgressTextPaint = new Paint();
        mProgressTextPaint.setColor(mProgressTextColor);
        mProgressTextPaint.setAntiAlias(true);
        mProgressTextRect = new Rect();

        mLoadingViewWidth = DensityUtil.dip2px(getContext(), 300);
        mLoadingViewHeight = DensityUtil.dip2px(getContext(), 4);
        mAnimatorList = new ArrayList<Animator>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode != MeasureSpec.EXACTLY) {
            widthMeasureSpec =
                    MeasureSpec.makeMeasureSpec(mLoadingViewWidth, MeasureSpec.EXACTLY);
        }
        if (heightSpecMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(mLoadingViewHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLoadingViewCenterX = w/2;
        mLoadingViewCenterY = h/2;
    }
}
