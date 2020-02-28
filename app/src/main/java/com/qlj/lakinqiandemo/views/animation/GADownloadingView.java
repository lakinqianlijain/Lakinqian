package com.qlj.lakinqiandemo.views.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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

import static com.qlj.lakinqiandemo.views.animation.UiUtils.dipToPx;
import static com.qlj.lakinqiandemo.views.animation.UiUtils.getScreenDensity;

/**
 * Created by Administrator on 2018/9/4.
 */

public class GADownloadingView extends View {
    private static final int ZERO_PROGRESS = 0;
    private static final int FULL_PROGRESS = 100;
    private static final int HALF_PROGRESS = 50;
    private static final int INVALID_PROGRESS = -1;
    private static final float FULL_NORMALIZED_TIME = 1F;

    private static final float FULL_NORMALIZED_PROGRESS = 1F;
    private static final float HALF_NORMALIZED_PROGRESS = 0.5F;
    private static final String FULL_PROGRESS_STR = "100%";
    private static final String FULL_PROGRESS_DONE_STR = "done";
    private static final String FAILED_PROGRESS_STR = "failed";

    private static final int FULL_ALPHA = 255;
    private static final int FULL_ANGLE = 360;
    private static final int HALF_FULL_ANGLE = FULL_ANGLE / 2;

    private static final int DEFAULT_ARROW_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_LOADING_CIRCLE_BG_COLOR = 0xFF491C14;
    private static final int DEFAULT_LOADING_LINE_COLOR = 0xFF491c14;
    private static final int DEFAULT_PROGRESS_LINE_LEFT_COLOR = 0XFFFFFFFF;
    private static final int DEFAULT_PROGRESS_TEXT_COLOR = 0xFF000000;
    private static final int DEFAULT_DONE_PROGRESS_TEXT_COLOR = 0xFF5F9C62;

    private static final int BEFORE_PROGRESS_CIRCLE_SCALE_DURATION = 3000;
    private static final int BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION
            = BEFORE_PROGRESS_CIRCLE_SCALE_DURATION / 2;
    private static final int BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DELAY
            = BEFORE_PROGRESS_CIRCLE_SCALE_DURATION - BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION;
    private static final int BEFORE_PROGRESS_CIRCLE_TO_LINE_DURATION = 150;
    private static final int BEFORE_PROGRESS_ARROW_MOVE_AND_LINE_OSCILL = 800;

    private static final float[] CIRCLE_TO_LINE_SEASONS = new float[]{0, 0.4f, 0.8f, 1f};
    private static final float[] CIRCLE_TO_LINE_WIDTH_FACTOR = new float[]{1f, 1.2f, 2.4f, 3.45f};
    private static final float[] CIRCLE_TO_LINE_HEIGHT_FACTOR = new float[]{1f, 0.73f, 0.36f, 0f};
    private static final float[] CIRCLE_TO_LINE_FST_CON_X_FACTOR = new float[]{-0.65f, 0.18f, 0.72f, 1.04f};
    private static final float[] CIRCLE_TO_LINE_FST_CON_Y_FACTOR = new float[]{0f, 0.036f, 0f, 0f};
    private static final float[] CIRCLE_TO_LINE_SEC_CON_X_FACTOR = new float[]{-0.65f, 0.06f, 0.72f, 1.04f};
    private static final float[] CIRCLE_TO_LINE_SEC_CON_Y_FACTOR = new float[]{1f, 0.73f, 0.36f, 0f};
    // value of MAX_LINE_WIDTH_FACTOR is max value in CIRCLE_TO_LINE_WIDTH_FACTOR array
    public static final float MAX_LINE_WIDTH_FACTOR = 3.45f;

    private static final int FULL_PROGRESS_ANIMATION_DURATION = 3000;
    private static final int MIN_PROGRESS_ANIMATION_DURATION = 100;

    private static final int DONE_ANIMATION_DURATION = 500;
    private static final int DONE_LINE_PACK_UP_ANIMATION_DURATION = 500;
    private static final int DONE_LINE_PACK_UP_ARROW_SHAKE_ANIMATION_DURATION = 500;
    private static final int DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION = 1000;
    private static final int DONE_DIALOG_TO_ARROW_DURATION
            = DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION / 4;
    private static final int DONE_DIALOG_UP_DOWN_DURATION
            = DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION - DONE_DIALOG_TO_ARROW_DURATION;


    private static final int DONE_LINE_PACK_UP_ARROW_SHAKE_BASE_POINT_DIAMETER = 2;
    private static final int FAILED_ANIMATION_DURATION = 1000;
    private static final int FAILED_BOMB_ANIMATION_DURATION =
            FAILED_ANIMATION_DURATION / 3;
    private static final int FAILED_ROPE_OSCILLATION_ANIMATION_DURATION = 250;
    private static final int FAILED_ARROW_MOVE_ANIMATION_DURATION = 800;
    private static final int FAILED_ROPE_PACK_UP_ANIMATION_DURATION =
            (FAILED_ARROW_MOVE_ANIMATION_DURATION - FAILED_ROPE_OSCILLATION_ANIMATION_DURATION) / 2;
    private static final int FAILED_CIRCLE_SCALE_ANIMATION_DURATION =
            (FAILED_ARROW_MOVE_ANIMATION_DURATION - FAILED_ROPE_OSCILLATION_ANIMATION_DURATION) / 2;

    // state of arrow's rectangle and triangle
    private static final float DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO = 1F;
    private static final float DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_MIDDLE_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.7F;
    private static final float DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO = 1F;
    private static final float DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO = 0.34F;
    private static final float DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.17F;
    private static final float DEFAULT_ARROW_TOP_CONNER_RADIUS_TO_CIRCLE_RADIUS_RATIO = 0.05f;

    private static final float DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO = MAX_LINE_WIDTH_FACTOR / 2
            + (DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO
            + DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO) / 4;

    private static final float SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO = 0.75F;
    private static final int SUGGEST_LOADING_VIEW_WIDTH = 300;
    private static final int SUGGEST_LOADING_VIEW_HEIGHT = (int) (SUGGEST_LOADING_VIEW_WIDTH
            / (SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO + MAX_LINE_WIDTH_FACTOR)
            * DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO);

    // progress text size and line stroke width
    private static final int DEFAULT_PROGRESS_TEXT_SIZE = 12;
    private static final int MIN_PROGRESS_TEXT_SIZE = 8;
    private static final float PROGRESS_TEXT_SIZE_TO_VIEW_WIDTH_RATIO
            = (float) DEFAULT_PROGRESS_TEXT_SIZE / SUGGEST_LOADING_VIEW_WIDTH;
    private static final int DEFAULT_LOADING_LINE_STROKE_WIDTH = 3;
    private static final int MIN_LOADING_LINE_STROKE_WIDTH = 1;
    private static final float LOADING_LINE_STROKE_WIDTH_TO_VIEW_WIDTH_RATIO
            = (float) DEFAULT_LOADING_LINE_STROKE_WIDTH / SUGGEST_LOADING_VIEW_WIDTH;

    private static final float CHANGE_ARROW_TO_DIALOG_RECT_CHANGE = 0.563F;
    private static final float CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME = 0.625F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1 = 0.313F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2 = 0.626F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3 = 0.813F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_4 = 1F;

    private static final int ROTATE_ARROW_TO_DIALOG_INIT_ANGLE = 0;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_1 = -30;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_2 = 20;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_3 = -10;

    private static final float DEFAULT_PROGRESS_MAX_HEIGHT_TO_BASELINE_RATIO = 0.1F;
    private static final float DEFAULT_LINE_OSCILLATION_MAX_HEIGHT_TO_BASELINE_RATIO = 0.15F;
    private static final float ARROW_BOUNCE_LENGTH_RATIO = 0.2F;
    private static final float ARROW_BOUNCE_LENGTH_RATIO_2 = 0.1F;

    // Animation State
    private static final int STATE_BEFORE_PROGRESS_CIRCLE_SCALE = 1;
    private static final int STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE = 2;
    private static final int STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE = 3;
    private static final int STATE_BEFORE_PROGRESS_ARROW_MOVE_LINE_OSCILL = 4;

    private static final int STATE_IN_PROGRESS = 5;
    private static final int STATE_DONE_ROTATE = 6;
    private static final int STATE_DONE_LINE_PACK_UP = 7;
    private static final int STATE_DONE_ARROW_SHAKE = 8;
    private static final int STATE_DONE_REST_TO_CIRCLE = 9;

    private static final int STATE_FAILED_ARROW_SHAKE = 10;
    private static final int STATE_FAILED_LINE_OSCILL = 11;
    private static final int STATE_FAILED_LINE_PACK_UP = 12;
    private static final int STATE_FAILED_CIRCLE_SCALE = 13;

    private static final int DONE_LINE_PACK_UP_ARROW_ANGLE = 10;

    // View color
    private int mArrowColor;
    private int mLoadingCircleBackColor = DEFAULT_LOADING_CIRCLE_BG_COLOR;
    private int mLoadingLineColor;
    private int mProgressLineColor;
    private int mProgressTextColor;
    private int mDoneTextColor;

    private boolean mIsFailed;
    private int mNextProgress, mLastValidProgress, mLastProgress;
    private String mLastValidProgressTextStr;
    private List<Animator> mAnimatorList;

    // loadingView
    private int mLoadingViewCenterX;
    private int mLoadingViewCenterY;
    private int mLoadingViewWidth;
    private int mLoadingViewHeight;

    // paint
    private Paint mDefaultPaint;
    private Xfermode mXfermode;

    // progress text
    private Paint mProgressTextPaint;
    private Rect mProgressTextRect;
    private int mProgressTextSize;

    // before progress
    private ValueAnimator mBefProgCircleScaleAnimator;
    private float mBefProgCircleScalingFactor;
    private ValueAnimator mBefProgInnerCircleScaleAnimator;
    private float mBefProgInnerCircleScalingFactor;
    private ValueAnimator mBefProgCircleToLineAnimator;
    private float mBefProgCircleToLineNormalizedTime;


    // circle
    private RectF mCircleRectF;
    private int mCircleRadius;
    private int mCircleDiameter;

    // line
    private Path mOscillationLinePath;
    private Paint mBaseLinePaint;
    private Path mBaseLinePath;
    private RectF mBaseLineRectF;
    private int mBaseLineLen;
    private int mHalfBaseLineLen;
    private int mBaseLineStrokeWidth;
    private int mBaseLineX;
    private int mBaseLineY;
    private int mBaseLineCenterX;
    private int mProgressLineMaxHeight;
    private int mLineOscillationMaxHeight;


    // arrow
    private Path mArrowPath;
    private RectF mArrowRectF;
    private int mInitArrowRectWidth;
    private int mInitArrowRectHeight;
    private int mInitArrowTriWidth;
    private int mInitArrowTriHeight;
    private int mInitArrowJointConnerRadius;
    private int mEndArrowRectHeight;
    private int mLastArrowRectWidth;
    private int mLastArrowRectHeight;
    private int mLastArrowTriWidth;
    private int mLastArrowTriHeight;



    private int mCurrentState;


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
        mCircleDiameter = (int) Math.min(w / (MAX_LINE_WIDTH_FACTOR
                        + SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO),
                h / (DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO));
        int actualViewWidth = (int) (mCircleDiameter * MAX_LINE_WIDTH_FACTOR
                + SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO);
        mCircleRadius = mCircleDiameter / 2;
        mCircleRectF.set(0,0, mCircleDiameter, mCircleDiameter);
        // 将圆背景移到View的中间
        mCircleRectF.offsetTo((w - mCircleRectF.width()) / 2,
                (h - mCircleRectF.height()) / 2);
        mInitArrowRectWidth = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowRectHeight = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowTriWidth = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowTriHeight = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowJointConnerRadius = (int) (mCircleRadius
                * DEFAULT_ARROW_TOP_CONNER_RADIUS_TO_CIRCLE_RADIUS_RATIO);
        mEndArrowRectHeight = (int) (mCircleRadius
                * DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO);
        mArrowRectF.set(mCircleRectF);
        mArrowRectF.inset(
                (mCircleRectF.width() - Math.max(mInitArrowRectWidth, mInitArrowTriWidth)) / 2,
                (mCircleRectF.height() - mInitArrowRectHeight - mInitArrowTriHeight) / 2);

        mBaseLineStrokeWidth = dipToPx(getContext(), (int) Math.max(MIN_LOADING_LINE_STROKE_WIDTH,
                LOADING_LINE_STROKE_WIDTH_TO_VIEW_WIDTH_RATIO * actualViewWidth / getScreenDensity(getContext())));
        mBaseLinePaint.setStrokeWidth(mBaseLineStrokeWidth);
        mBaseLineLen = (int) (mCircleDiameter * MAX_LINE_WIDTH_FACTOR);
        mHalfBaseLineLen = mBaseLineLen / 2;
        mBaseLineX = mLoadingViewCenterX - mHalfBaseLineLen;
        mBaseLineY = mLoadingViewCenterY - mBaseLineStrokeWidth / 2;
        mBaseLineCenterX = mLoadingViewCenterX;
        mProgressLineMaxHeight = (int) (mBaseLineLen * DEFAULT_PROGRESS_MAX_HEIGHT_TO_BASELINE_RATIO);
        mLineOscillationMaxHeight =
                (int) (mBaseLineLen * DEFAULT_LINE_OSCILLATION_MAX_HEIGHT_TO_BASELINE_RATIO);
        mProgressTextSize = dipToPx(getContext(), (int) Math.max(MIN_PROGRESS_TEXT_SIZE,
                PROGRESS_TEXT_SIZE_TO_VIEW_WIDTH_RATIO * actualViewWidth / getScreenDensity(getContext())));

        mProgressTextPaint.setTextSize(mProgressTextSize);
        mDefaultPaint.setPathEffect(new CornerPathEffect(mInitArrowJointConnerRadius));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentState){
            // in this state, scale circle and arrow
            case STATE_BEFORE_PROGRESS_CIRCLE_SCALE:
                drawCircleAndArrowScale(canvas, mBefProgCircleScalingFactor);
                break;
            // in this state, scale circle, arrow and inner circle
            case STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE:
                drawCircleAndArrowScaleAndInnerScale(canvas, mBefProgCircleScalingFactor,
                        mBefProgInnerCircleScalingFactor);
                break;
            // in this state, circle change to a line, while the arrow moves up along the y-axis
            case STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE:
                drawBeforeProgressCircleToLine(canvas, mBefProgCircleToLineNormalizedTime);
                break;
        }
    }

    private void drawBeforeProgressCircleToLine(Canvas canvas, float normalizeTime) {
        mBaseLinePaint.setColor(mLoadingLineColor);
        // update path in center of bounds[0, 0, mCircleDiameter, mCircleDiameter]
        updateCircleToLinePath(mBaseLinePath, mCircleDiameter, normalizeTime);
        // offset the path to the place of circle
        mBaseLinePath.offset(mCircleRectF.left, mCircleRectF.top);
        canvas.drawPath(mBaseLinePath, mBaseLinePaint);
        // compute bounds to get the lowest (center) point of the path
        mBaseLinePath.computeBounds(mBaseLineRectF, false);
    }

    /**
     * Assumed the circle height is h, width is w, we can obtain the following content:
     * this animation have four State:
     *
     * State 1: Circle to special line 1
     * width: w -> 1.2w
     * height: w -> 0.73w
     * firstConXFactor: -0.65w to 0.18w
     * firstConYFactor: 0 to 0.036w
     * SecondConXFactor: -0.65w to 0.06w
     * SecondConYFactor: 1w to 0.73w
     *
     * State 2: line 1 to line 2
     * width: 1.2w -> 2.4w
     * height: w -> 0.36w
     * firstConXFactor: 0.18w to 0.72w
     * firstConYFactor: 0.036w to 0
     * SecondConXFactor: 0.06w to 0.72w
     * SecondConYFactor: 0.73w to 0.36w
     *
     * State 3: line 2 to line 3
     * width: 2.4w -> 2.4w
     * height: 0.36w -> 0
     * firstConXFactor: 0.72w to 1.04w
     * firstConYFactor: 0 to  0
     * SecondConXFactor: 0.72w to 1.04w
     * SecondConYFactor: 0.36w to 0
     */
    private void updateCircleToLinePath(Path linePath, int circleDiameter, float normalizedTime) {
        if (linePath == null) {
            return;
        }
        int index = 0;
        float adjustNormalizedTime = 0;
        if (normalizedTime <= CIRCLE_TO_LINE_SEASONS[1]) {
            adjustNormalizedTime = normalizedTime / CIRCLE_TO_LINE_SEASONS[1];
        } else if (normalizedTime < CIRCLE_TO_LINE_SEASONS[2]) {
            index = 1;
            adjustNormalizedTime = (normalizedTime - CIRCLE_TO_LINE_SEASONS[1])
                    / (CIRCLE_TO_LINE_SEASONS[2] - CIRCLE_TO_LINE_SEASONS[1]);
        } else {
            index = 2;
            adjustNormalizedTime = (normalizedTime - CIRCLE_TO_LINE_SEASONS[2])
                    / (CIRCLE_TO_LINE_SEASONS[3] - CIRCLE_TO_LINE_SEASONS[2]);
        }

        // the path bounds width
        int boundWidth = (int) (((CIRCLE_TO_LINE_WIDTH_FACTOR[index + 1]
                - CIRCLE_TO_LINE_WIDTH_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_WIDTH_FACTOR[index]) * circleDiameter);

        // the distance of cubic line1' x1 to cubic line2's x2
        int adjustBoundWidth = boundWidth;
        if (normalizedTime <= CIRCLE_TO_LINE_SEASONS[1]) {
            adjustBoundWidth = (int) (boundWidth * adjustNormalizedTime);
        }

        // the path bounds height
        int boundHeight = (int) (((CIRCLE_TO_LINE_HEIGHT_FACTOR[index + 1]
                - CIRCLE_TO_LINE_HEIGHT_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_HEIGHT_FACTOR[index]) * circleDiameter);

        // calculate the four points
        float firstControlXFactor = (CIRCLE_TO_LINE_FST_CON_X_FACTOR[index + 1]
                - CIRCLE_TO_LINE_FST_CON_X_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_FST_CON_X_FACTOR[index];
        float firstControlYFactor = (CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index + 1]
                - CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index];
        float secondControlXFactor = (CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index + 1]
                - CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index];
        float secondControlYFactor = (CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index + 1]
                - CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index];
        int firstControlX = (int) (circleDiameter * firstControlXFactor);
        int firstControlY = (int) (circleDiameter * firstControlYFactor);
        int secondControlX = (int) (circleDiameter * secondControlXFactor);
        int secondControlY = (int) (circleDiameter * secondControlYFactor);

        linePath.reset();
        // left line
        linePath.cubicTo(firstControlX, firstControlY,
                secondControlX, secondControlY, adjustBoundWidth / 2, boundHeight);
        // left right line
        linePath.cubicTo(adjustBoundWidth - secondControlX,
                secondControlY, adjustBoundWidth - firstControlX, firstControlY, adjustBoundWidth, 0);

        // translate path to move the origin to the center
        int offsetX = (circleDiameter - adjustBoundWidth) / 2;
        int offsetY = (circleDiameter - boundHeight) / 2;
        linePath.offset(offsetX, offsetY);

    }

    private void drawCircleAndArrowScaleAndInnerScale(Canvas canvas, float scalingFactor, float innerCircleScalingFactor) {
        // draw bg circle
        canvas.save();
        canvas.scale(scalingFactor, scalingFactor, mCircleRectF.centerX(), mCircleRectF.centerY());
        int layoutCount = canvas.saveLayer(mCircleRectF, mDefaultPaint, Canvas.ALL_SAVE_FLAG);
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), mCircleRadius, mDefaultPaint);

        mDefaultPaint.setXfermode(mXfermode);
        // draw bg circle 2
        int innerCircleRadius = (int) (mCircleRadius * innerCircleScalingFactor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), innerCircleRadius, mDefaultPaint);
        mDefaultPaint.setXfermode(null);
        canvas.restoreToCount(layoutCount);

        // draw arrow
        updateArrowPath(mInitArrowRectWidth,
                mInitArrowRectHeight, mInitArrowTriWidth, mInitArrowTriHeight);
        // translate the canvas  causes the center point of the arrow to coincide with the center point of the circle
        canvas.translate(mCircleRectF.centerX() - mArrowRectF.width() / 2 * scalingFactor,
                mCircleRectF.centerY() - mArrowRectF.height() / 2 * scalingFactor);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();

    }

    private void drawCircleAndArrowScale(Canvas canvas, float scaleFactor) {
        // draw bg circle
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor, mCircleRectF.centerX(), mCircleRectF.centerY());
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), mCircleRadius, mDefaultPaint);

        // draw arrow
        updateArrowPath(mInitArrowRectWidth,
                mInitArrowRectHeight, mInitArrowTriWidth, mInitArrowTriHeight);
        // translate the canvas  causes the center point of the arrow to coincide with the center point of the circle
        canvas.translate(mCircleRectF.centerX() - mArrowRectF.width()/2 * scaleFactor,
                mCircleRectF.centerY() - mArrowRectF.height() / 2 * scaleFactor);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void updateArrowPath(int rectWith, int rectHeight, int triWidth, int triHeight) {
        if (mArrowPath == null){
            mArrowPath = new Path();
        } else if (mLastArrowRectWidth == rectWith && mLastArrowRectHeight == rectHeight
                && mLastArrowTriWidth == triWidth && mLastArrowTriHeight == triHeight){
            return;
        } else {
            mArrowPath.reset();
        }
        mLastArrowRectWidth = rectWith;
        mLastArrowRectHeight = rectHeight;
        mLastArrowTriWidth = triWidth;
        mLastArrowTriHeight = triHeight;

        int arrowWidth = Math.max(rectWith, triWidth);
        int halfArrowWidth = arrowWidth / 2;
        int arrowHeight = rectHeight + triHeight;
        int rectPaddingLeft = (arrowWidth - rectWith) / 2;
        int triPaddingLeft = (arrowWidth - triWidth) / 2;

        // move to bottom center
        mArrowPath.moveTo(halfArrowWidth, 0);
        // rect bottom left edge
        mArrowPath.lineTo(rectPaddingLeft, 0);
        // rect left edge
        mArrowPath.lineTo(rectPaddingLeft, rectHeight);
        // tri bottom left edge
        mArrowPath.lineTo(triPaddingLeft, rectHeight);
        // tri left edge
        mArrowPath.lineTo(halfArrowWidth, arrowHeight);
        // tri right edge
        mArrowPath.lineTo(arrowWidth - triPaddingLeft, rectHeight);
        // tri bottom right edge
        mArrowPath.lineTo(arrowHeight - rectPaddingLeft, rectHeight);
        // rect right edge
        mArrowPath.lineTo(arrowHeight - rectPaddingLeft, 0);
        // rect right bottom edge
        mArrowPath.lineTo(halfArrowWidth, 0);

        // update path RectF
        if (mArrowRectF == null) {
            mArrowRectF = new RectF();
        }
        mArrowPath.computeBounds(mArrowRectF, true);
    }

    public void performAnimation(){
        mIsFailed = false;
        mLastProgress = ZERO_PROGRESS;
        mNextProgress = ZERO_PROGRESS;
        releaseAnimation();

        // circle and arrow scale
        mBefProgCircleScaleAnimator = ValueAnimator.ofFloat(1f, 0.91f, 1f);
        mBefProgCircleScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBefProgCircleScalingFactor = (float ) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgCircleScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                mCurrentState = STATE_BEFORE_PROGRESS_CIRCLE_SCALE;
            }
        });
        mBefProgCircleScaleAnimator.setDuration(BEFORE_PROGRESS_CIRCLE_SCALE_DURATION);
        mAnimatorList.add(mBefProgCircleScaleAnimator);

        // inner circle arrow scale
        mBefProgInnerCircleScaleAnimator = ValueAnimator.ofFloat(0, 0.9f);
        mBefProgInnerCircleScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBefProgInnerCircleScalingFactor = (float ) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgInnerCircleScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE;
            }
        });
        mBefProgInnerCircleScaleAnimator.setDuration(BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION);
        mBefProgInnerCircleScaleAnimator.setStartDelay(BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DELAY);
        mAnimatorList.add(mBefProgInnerCircleScaleAnimator);


        // circle to line animation
        mBefProgCircleToLineAnimator = ValueAnimator.ofFloat(0, 1f);
        mBefProgCircleToLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBefProgCircleToLineNormalizedTime = (float ) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgCircleToLineAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE;
            }
        });
        mBefProgCircleToLineAnimator.setDuration(BEFORE_PROGRESS_CIRCLE_TO_LINE_DURATION);
        mAnimatorList.add(mBefProgCircleToLineAnimator);




    }

    public void releaseAnimation() {
        if (mAnimatorList == null || mAnimatorList.size() == 0) {
            return;
        }

        for (Animator animator : mAnimatorList) {
            if (animator == null) {
                continue;
            }

            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).removeAllUpdateListeners();
            }
            animator.removeAllListeners();
            animator.cancel();
            animator = null;
        }
        mAnimatorList.clear();
    }
}
