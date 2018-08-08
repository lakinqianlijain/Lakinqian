package com.qlj.lakinqiandemo.contralayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

public class SwipeRefreshRecycler extends FrameLayout {

    private RecyclerView mRecycler;
    private OnRefreshListener mOnRefreshListener;
    private SwipeHeader mHeader;
    private DecelerateInterpolator mDecelerateInterpolator;


    private boolean mIsRefreshing = false;
    private boolean mRefreshable = true;
    private float mTouchY;
    private float mCurrentY;

    public static final int DEFAULT_WAVE_HEIGHT = 150;
    public static final int DEFAULT_HEAD_HEIGHT = 150;
    int mWaveHeight = DEFAULT_WAVE_HEIGHT;
    int mHeadHeight = DEFAULT_HEAD_HEIGHT;

    public SwipeRefreshRecycler(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshRecycler);
            mWaveHeight = t.getDimensionPixelSize(R.styleable.SwipeRefreshRecycler_waveHeight, mWaveHeight);
            mHeadHeight = t.getDimensionPixelSize(R.styleable.SwipeRefreshRecycler_headHeight, mHeadHeight);
            t.recycle();
        }
        init(context);
    }

    private void init(Context ctx) {
        mRecycler = new RecyclerView(ctx);
        addView(mRecycler);
        mDecelerateInterpolator = new DecelerateInterpolator(10);
        mHeader = new SwipeHeader(ctx);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(ctx, 50));
        layoutParams.gravity = Gravity.TOP;
        addView(mHeader);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mRefreshable) return super.onInterceptTouchEvent(ev);
        if (mIsRefreshing) return true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0 && !canChildScrollUp()) {
                    if (mHeader != null) {
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.onBegin();
                    }
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mIsRefreshing || !mRefreshable) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = e.getY();
                float dy = mCurrentY - mTouchY;
                dy = Math.min(mWaveHeight * 2, dy);
                dy = Math.max(0, dy);
                if (mRecycler != null) {
                    float offsetY = mDecelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
                    float fraction = offsetY / mHeadHeight;
                    if (mHeader != null) {
                        mHeader.getLayoutParams().height = (int) offsetY;
                        mHeader.requestLayout();
                        mHeader.onPull(fraction);
                    }
                    mRecycler.setTranslationY(offsetY);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mRecycler != null) {
                    if (mHeader != null) {
                        if (mRecycler.getTranslationY() >= mHeadHeight) {
                            createAnimatorTranslationY(mRecycler, mHeadHeight, mHeader);
                            startRefresh();
                            if (mOnRefreshListener != null) mOnRefreshListener.onRefresh();
                        } else {
                            createAnimatorTranslationY(mRecycler, 0, mHeader);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(e);
    }

    public void setRefreshing(boolean refresh) {
        if (mHeader == null) return;
        if (refresh) {
            if (mIsRefreshing) return;
            mHeader.setVisibility(View.VISIBLE);
            mHeader.onAuto();
            createAnimatorTranslationY(mRecycler, mHeadHeight, mHeader);
            startRefresh();
        } else {
            stopRefresh();
        }
    }

    private void startRefresh() {
        mIsRefreshing = true;
        mHeader.onRefreshing();
    }

    private void stopRefresh() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(mRecycler);
        viewPropertyAnimatorCompat.setUpdateListener(null);
        viewPropertyAnimatorCompat.setDuration(200);
        viewPropertyAnimatorCompat.y(mRecycler.getTranslationY());
        viewPropertyAnimatorCompat.translationY(0);
        viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
        viewPropertyAnimatorCompat.start();
        mHeader.onCompleted();
        mIsRefreshing = false;
    }

    private void createAnimatorTranslationY(final View v, final float h, final View target) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(v);
        viewPropertyAnimatorCompat.setDuration(250);
        viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
        viewPropertyAnimatorCompat.translationY(h);
        viewPropertyAnimatorCompat.start();
        viewPropertyAnimatorCompat.setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(View view) {
                float height = v.getTranslationY();
                target.getLayoutParams().height = (int) height;
                target.requestLayout();
            }
        });
    }

    private boolean canChildScrollUp() {
        return mRecycler.canScrollVertically(-1);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecycler.setItemAnimator(animator);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecycler.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecycler.setLayoutManager(layout);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        mRecycler.addOnScrollListener(onScrollListener);
    }

    public void setRefreshable(boolean refreshable){
        mRefreshable = refreshable;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}

