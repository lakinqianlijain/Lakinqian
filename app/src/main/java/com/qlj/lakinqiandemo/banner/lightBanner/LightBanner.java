package com.qlj.lakinqiandemo.banner.lightBanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class LightBanner extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;

    private Drawable mPointContainerBackgroundDrawable;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private LinearLayout mPointRealContainerLl;
    private int mContentBottomMargin;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
    private int mPointDrawableResId = R.drawable.bga_banner_selector_point_solid;

    private List<View> mViews;
    private LightViewPager mViewPager;
    private int mPageChangeDuration = 800;
    private boolean mAutoPlayAble = false;
    private AutoPlayTask mAutoPlayTask;
    private int mAutoPlayInterval = 3000;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };


    public LightBanner(Context context) {
        super(context);
    }

    public LightBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initViews(context);
    }

    private void initDefaultAttrs(Context context) {
        mAutoPlayTask = new AutoPlayTask(this);
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        mPointLeftRightMargin = DensityUtil.dip2px(context, 3);
        mPointTopBottomMargin = DensityUtil.dip2px(context, 6);
        mPointContainerLeftRightPadding = DensityUtil.dip2px(context, 10);
        mContentBottomMargin = 0;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LightBanner);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.LightBanner_banner_pointDrawable) {
            mPointDrawableResId = typedArray.getResourceId(attr, R.drawable.bga_banner_selector_point_solid);
        } else if (attr == R.styleable.LightBanner_banner_pointContainerBackground) {
            mPointContainerBackgroundDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.LightBanner_banner_indicatorGravity) {
            mPointGravity = typedArray.getInt(attr, mPointGravity);
        } else if (attr == R.styleable.LightBanner_banner_pointAutoPlayAble) {
            mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble);
        } else if (attr == R.styleable.LightBanner_banner_pointAutoPlayInterval) {
            mAutoPlayInterval = typedArray.getInteger(attr, mAutoPlayInterval);
        } else if (attr == R.styleable.LightBanner_android_scaleType) {
            final int index = typedArray.getInt(attr, -1);
            if (index >= 0 && index < sScaleTypeArray.length) {
                mScaleType = sScaleTypeArray[index];
            }
        }
    }

    private void initViews(Context context) {
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, mPointTopBottomMargin, mPointContainerLeftRightPadding, mPointTopBottomMargin);
        RelativeLayout.LayoutParams pointContainerLp = new RelativeLayout.LayoutParams(RMP, RWC);
        // 处理圆点在顶部还是底部
        if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        addView(pointContainerRl, pointContainerLp);


        RelativeLayout.LayoutParams indicatorLp = new RelativeLayout.LayoutParams(RWC, RWC);
        indicatorLp.addRule(CENTER_VERTICAL);
        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        mPointRealContainerLl.setGravity(Gravity.CENTER_VERTICAL);
        pointContainerRl.addView(mPointRealContainerLl, indicatorLp);

        int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 处理圆点在左边、右边还是水平居中
        if (horizontalGravity == Gravity.LEFT) {
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            indicatorLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position % mViews.size(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        position = position % mViews.size();
        switchToPoint(position);

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    /**
     * 添加ViewPager滚动监听器
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }


    private static class AutoPlayTask implements Runnable {
        private final WeakReference<LightBanner> mBanner;

        private AutoPlayTask(LightBanner banner) {
            mBanner = new WeakReference<>(banner);
        }

        @Override
        public void run() {
            LightBanner banner = mBanner.get();
            if (banner != null) {
                banner.switchToNextPage();
                banner.startAutoPlay();
            }
        }
    }

    /**
     * 切换到下一页
     */
    private void switchToNextPage() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    /**
     * 设置每一页的控件、数据模型和文案
     *
     * @param views 每一页的控件集合
     */
    public void setData(List<View> views) {
        if (LightBannerUtil.isCollectionEmpty(views)) {
            mAutoPlayAble = false;
            views = new ArrayList<>();
        }
        if (mAutoPlayAble && views.size() < 3) {
            mAutoPlayAble = false;
        }
        mViews = views;

        initIndicator();
        initViewPager();
    }

    /**
     * 设置每一页图片的资源 id，主要针对引导页的情况
     *
     * @param localImageSize 内存优化，Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间，传 null 的话默认为 720, 1280, 320, 640
     * @param scaleType      图片缩放模式，传 null 的话默认为 CENTER_CROP
     * @param resIds         每一页图片资源 id
     */
    public void setData(@Nullable LocalImageSize localImageSize, @Nullable ImageView.ScaleType scaleType, @DrawableRes int... resIds) {
        if (localImageSize != null) {
            localImageSize = new LocalImageSize(720, 1280, 320, 640);
        }
        if (scaleType != null) {
            mScaleType = scaleType;
        }
        List<View> views = new ArrayList<>();
        for (int resId : resIds) {
            views.add(LightBannerUtil.getItemImageView(getContext(), resId, localImageSize, mScaleType));
        }
        setData(views);
    }


    private void initIndicator() {
        if (mPointRealContainerLl != null) {
            mPointRealContainerLl.removeAllViews();

            if (mViews.size() > 1) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
                lp.setMargins(mPointLeftRightMargin, 0, mPointLeftRightMargin, 0);
                ImageView imageView;
                for (int i = 0; i < mViews.size(); i++) {
                    imageView = new ImageView(getContext());
                    imageView.setLayoutParams(lp);
                    imageView.setImageResource(mPointDrawableResId);
                    mPointRealContainerLl.addView(imageView);
                }
            }
        }
    }

    /**
     * 设置页码切换过程的时间长度
     *
     * @param duration 页码切换过程的时间长度
     */
    public void setPageChangeDuration(int duration) {
        if (duration >= 0 && duration <= 2000) {
            mPageChangeDuration = duration;
            if (mViewPager != null) {
                mViewPager.setPageChangeDuration(duration);
            }
        }
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (mAutoPlayAble) {
            postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    }

    public void stopAutoPlay() {
        if (mAutoPlayTask != null) {
            removeCallbacks(mAutoPlayTask);
        }
    }

    private void switchToPoint(int newCurrentPoint) {

        if (mPointRealContainerLl != null) {
            if (mViews != null && mViews.size() > 0 && newCurrentPoint < mViews.size() && mViews.size() > 1) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
                for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
                    mPointRealContainerLl.getChildAt(i).setEnabled(i == newCurrentPoint);
                    // 处理指示器选中和未选中状态图片尺寸不相等
                    mPointRealContainerLl.getChildAt(i).requestLayout();
                }
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }
    }

    private void initViewPager() {
        if (mViewPager != null && this.equals(mViewPager.getParent())) {
            this.removeView(mViewPager);
            mViewPager = null;
        }

        mViewPager = new LightViewPager(getContext());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mViewPager.setAllowUserScrollable(true);
        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

            }
        });
        setPageChangeDuration(mPageChangeDuration);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
        layoutParams.setMargins(0, 0, 0, mContentBottomMargin);
        addView(mViewPager, 0, layoutParams);

        if (mAutoPlayAble) {
            int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size();
            mViewPager.setCurrentItem(zeroItem);
            startAutoPlay();
        } else {
            switchToPoint(0);
        }
    }


    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews == null ? 0 : (mAutoPlayAble ? Integer.MAX_VALUE : mViews.size());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (LightBannerUtil.isCollectionEmpty(mViews)) {
                return null;
            }

            final int finalPosition = position % mViews.size();

            View view;
            view = mViews.get(finalPosition);
            ViewParent viewParent = view.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(view);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
