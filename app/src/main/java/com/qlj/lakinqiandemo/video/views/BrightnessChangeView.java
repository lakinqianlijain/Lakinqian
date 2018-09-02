package com.qlj.lakinqiandemo.video.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.R;

public class BrightnessChangeView extends LinearLayout {
    public static final int LAYOUT_ID = R.layout.view_video_progress;

    public static final float STEP = 1f / 15;
    private Context mContext;
    private ImageView mBrightnessImage;
    private LinearLayout mBrightnessLL;
    private View mRootView;

    public BrightnessChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private void initViewsOnlyAtFirst() {
        if (mRootView != null) return;
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_round_white);
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
        mBrightnessImage = mRootView.findViewById(R.id.iv_icon);
        mBrightnessLL = mRootView.findViewById(R.id.ll_progress);

        //手动计算宽度，以此规避dp2px方法转型导致的误差
        int width = (dp2px(6) + dp2px(1)) * 15 + dp2px(3) + dp2px(2);
        LayoutParams lp = (LayoutParams) mBrightnessLL.getLayoutParams();
        lp.width = width;
        mBrightnessLL.setPadding(dp2px(3), dp2px(1), dp2px(2), dp2px(1));
        mBrightnessLL.requestLayout();
    }

    public void setProgress(float progress) {
        initViewsOnlyAtFirst();
        mBrightnessLL.removeAllViews();
        if (progress > .01f) {
            mBrightnessImage.setImageResource(R.drawable.ic_video_bright);
        } else {
            mBrightnessImage.setImageResource(R.drawable.ic_video_dark);
            return;
        }
        for (int i = 0; i < Math.ceil(progress / STEP); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.bg_round_white_ss);
            view.setLayoutParams(new ViewGroup.LayoutParams(dp2px(6), dp2px(5)));
            mBrightnessLL.addView(view);
            View gap = new View(mContext);
            gap.setLayoutParams(new ViewGroup.LayoutParams(dp2px(1), dp2px(5)));
            mBrightnessLL.addView(gap);
        }
    }

    private int dp2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
