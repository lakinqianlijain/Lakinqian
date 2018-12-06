package com.qlj.lakinqiandemo.banner.BGABanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class BGABanner extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int NO_PLACEHOLDER_DRAWABLE = -1;

    private Drawable mPointContainerBackgroundDrawable;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private LinearLayout mPointRealContainerLl;
    private ImageView mPlaceholderIv;
    private int mPlaceholderDrawableResId = NO_PLACEHOLDER_DRAWABLE;
    private int mContentBottomMargin;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
    private int mPointDrawableResId = R.drawable.bga_banner_selector_point_solid;

    private List<View> mViews;
    private BGAViewPager mViewPager;
    private int mOverScrollMode = OVER_SCROLL_NEVER;
    private boolean mAllowUserScrollable = true;
    private int mPageChangeDuration = 800;
    private boolean mAutoPlayAble = true;
    private AutoPlayTask mAutoPlayTask;
    private int mAutoPlayInterval = 3000;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;


    public BGABanner(Context context) {
        super(context);
    }

    public BGABanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultAttrs(context);
        initViews(context);
    }

    private void initDefaultAttrs(Context context) {
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        mPointLeftRightMargin = DensityUtil.dip2px(context, 3);
        mPointTopBottomMargin = DensityUtil.dip2px(context, 6);
        mPointContainerLeftRightPadding = DensityUtil.dip2px(context, 10);
        mContentBottomMargin = 0;
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
        mPointRealContainerLl.setId(R.id.banner_indicatorId);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        mPointRealContainerLl.setGravity(Gravity.CENTER_VERTICAL);
        pointContainerRl.addView(mPointRealContainerLl, indicatorLp);

//        RelativeLayout.LayoutParams tipLp = new RelativeLayout.LayoutParams(RMP, RWC);
//        tipLp.addRule(CENTER_VERTICAL);
//        mTipTv = new TextView(context);
//        mTipTv.setGravity(Gravity.CENTER_VERTICAL);
//        mTipTv.setSingleLine(true);
//        mTipTv.setEllipsize(TextUtils.TruncateAt.END);
//        mTipTv.setTextColor(mTipTextColor);
//        mTipTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTextSize);
//        pointContainerRl.addView(mTipTv, tipLp);

        int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 处理圆点在左边、右边还是水平居中
        if (horizontalGravity == Gravity.LEFT) {
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            indicatorLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }

        showPlaceholder();
    }

    private void showPlaceholder() {
//        if (mPlaceholderIv == null && mPlaceholderDrawableResId != NO_PLACEHOLDER_DRAWABLE) {
//            mPlaceholderIv = BGABannerUtil.getItemImageView(getContext(), mPlaceholderDrawableResId, new BGALocalImageSize(720, 360, 640, 320), mScaleType);
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
//            layoutParams.setMargins(0, 0, 0, mContentBottomMargin);
//            addView(mPlaceholderIv, layoutParams);
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        handleGuideViewVisibility(position, positionOffset);

        mPageScrollPosition = position;
        mPageScrollPositionOffset = positionOffset;

//        if (mTipTv != null) {
//            if (BGABannerUtil.isCollectionNotEmpty(mTips)) {
//                mTipTv.setVisibility(View.VISIBLE);
//
//                int leftPosition = position % mTips.size();
//                int rightPosition = (position + 1) % mTips.size();
//                if (rightPosition < mTips.size() && leftPosition < mTips.size()) {
//                    if (positionOffset > 0.5) {
//                        mTipTv.setText(mTips.get(rightPosition));
//                        ViewCompat.setAlpha(mTipTv, positionOffset);
//                    } else {
//                        ViewCompat.setAlpha(mTipTv, 1 - positionOffset);
//                        mTipTv.setText(mTips.get(leftPosition));
//                    }
//                }
//            } else {
//                mTipTv.setVisibility(View.GONE);
//            }
//        }

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position % mViews.size(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

//    private void handleGuideViewVisibility(int position, float positionOffset) {
//        if (mEnterView == null && mSkipView == null) {
//            return;
//        }
//
//        if (position == getItemCount() - 2) {
//            if (mEnterView != null) {
//                ViewCompat.setAlpha(mEnterView, positionOffset);
//            }
//            if (mSkipView != null) {
//                ViewCompat.setAlpha(mSkipView, 1.0f - positionOffset);
//            }
//
//            if (positionOffset > 0.8f) {
//                if (mEnterView != null) {
//                    mEnterView.setVisibility(View.VISIBLE);
//                }
//                if (mSkipView != null) {
//                    mSkipView.setVisibility(View.GONE);
//                }
//            } else {
//                if (mEnterView != null) {
//                    mEnterView.setVisibility(View.GONE);
//                }
//                if (mSkipView != null) {
//                    mSkipView.setVisibility(View.VISIBLE);
//                }
//            }
//        } else if (position == getItemCount() - 1) {
//            if (mSkipView != null) {
//                mSkipView.setVisibility(View.GONE);
//            }
//            if (mEnterView != null) {
//                mEnterView.setVisibility(View.VISIBLE);
//                ViewCompat.setAlpha(mEnterView, 1.0f);
//            }
//        } else {
//            if (mSkipView != null) {
//                mSkipView.setVisibility(View.VISIBLE);
//                ViewCompat.setAlpha(mSkipView, 1.0f);
//            }
//            if (mEnterView != null) {
//                mEnterView.setVisibility(View.GONE);
//            }
//        }
//    }

    private static class AutoPlayTask implements Runnable {
        private final WeakReference<BGABanner> mBanner;

        private AutoPlayTask(BGABanner banner) {
            mBanner = new WeakReference<>(banner);
        }

        @Override
        public void run() {
            BGABanner banner = mBanner.get();
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
        if (BGABannerUtil.isCollectionEmpty(views)) {
            mAutoPlayAble = false;
            views = new ArrayList<>();
        }
        if (mAutoPlayAble && views.size() < 3) {
            mAutoPlayAble = false;
        }

        mViews = views;

        initIndicator();
        initViewPager();
//        removePlaceholder();
    }

    /**
     * 设置每一页图片的资源 id，主要针对引导页的情况
     *
     * @param localImageSize 内存优化，Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间，传 null 的话默认为 720, 1280, 320, 640
     * @param scaleType      图片缩放模式，传 null 的话默认为 CENTER_CROP
     * @param resIds         每一页图片资源 id
     */
    public void setData(@Nullable BGALocalImageSize localImageSize, @Nullable ImageView.ScaleType scaleType, @DrawableRes int... resIds) {
        if (localImageSize != null) {
            localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        }
        if (scaleType != null) {
            mScaleType = scaleType;
        }
        List<View> views = new ArrayList<>();
        for (int resId : resIds) {
            views.add(BGABannerUtil.getItemImageView(getContext(), resId, localImageSize, mScaleType));
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
//        if (mNumberIndicatorTv != null) {
//            if (mIsNeedShowIndicatorOnOnlyOnePage || (!mIsNeedShowIndicatorOnOnlyOnePage && mViews.size() > 1)) {
//                mNumberIndicatorTv.setVisibility(View.VISIBLE);
//            } else {
//                mNumberIndicatorTv.setVisibility(View.INVISIBLE);
//            }
//        }
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

        mViewPager = new BGAViewPager(getContext());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOverScrollMode(mOverScrollMode);
        mViewPager.setAllowUserScrollable(mAllowUserScrollable);
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
//            mViewPager.setAutoPlayDelegate(this);
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
            if (BGABannerUtil.isCollectionEmpty(mViews)) {
                return null;
            }

            final int finalPosition = position % mViews.size();

            View view;
            view = mViews.get(finalPosition);

//            if (mDelegate != null) {
//                view.setOnClickListener(new BGAOnNoDoubleClickListener() {
//                    @Override
//                    public void onNoDoubleClick(View view) {
//                        int currentPosition = mViewPager.getCurrentItem() % mViews.size();
//
//                        if (BGABannerUtil.isIndexNotOutOfBounds(currentPosition, mModels)) {
//                            mDelegate.onBannerItemClick(BGABanner.this, view, mModels.get(currentPosition), currentPosition);
//                        } else if (BGABannerUtil.isCollectionEmpty(mModels)) {
//                            mDelegate.onBannerItemClick(BGABanner.this, view, null, currentPosition);
//                        }
//                    }
//                });
//            }

//            if (mAdapter != null) {
//                if (BGABannerUtil.isIndexNotOutOfBounds(finalPosition, mModels)) {
//                    mAdapter.fillBannerItem(BGABanner.this, view, mModels.get(finalPosition), finalPosition);
//                } else if (BGABannerUtil.isCollectionEmpty(mModels)) {
//                    mAdapter.fillBannerItem(BGABanner.this, view, null, finalPosition);
//                }
//            }

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
