package com.qlj.lakinqiandemo.banner.BGABanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class BGABanner extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int NO_PLACEHOLDER_DRAWABLE = -1;

    private Drawable mPointContainerBackgroundDrawable;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private LinearLayout mPointRealContainerLl;
    private ImageView mPlaceholderIv;
    private int mPlaceholderDrawableResId = NO_PLACEHOLDER_DRAWABLE;


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
        if (mPlaceholderIv == null && mPlaceholderDrawableResId != NO_PLACEHOLDER_DRAWABLE) {
            mPlaceholderIv = BGABannerUtil.getItemImageView(getContext(), mPlaceholderDrawableResId, new BGALocalImageSize(720, 360, 640, 320), mScaleType);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
            layoutParams.setMargins(0, 0, 0, mContentBottomMargin);
            addView(mPlaceholderIv, layoutParams);
        }
    }
}
