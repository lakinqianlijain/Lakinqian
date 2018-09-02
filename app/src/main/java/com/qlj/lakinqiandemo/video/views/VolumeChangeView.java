package com.qlj.lakinqiandemo.video.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.R;

public class VolumeChangeView extends LinearLayout {

    public static final int LAYOUT_ID = R.layout.view_video_progress;

    public static final float STEP = 1f / 15;
    private Context mContext;
    private ImageView mVolumeImage;
    private LinearLayout mVolumeLL;
    private View mRootView;

    public VolumeChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private void initViewsOnlyAtFirst() {
        if (mRootView != null) return;
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_round_white);
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
        mVolumeImage = mRootView.findViewById(R.id.iv_icon);
        mVolumeLL = mRootView.findViewById(R.id.ll_progress);

        //手动计算宽度，以此规避dp2px方法转型导致的误差
        int width = (dp2px(6) + dp2px(1)) * 15 + dp2px(3) + dp2px(2);
        LayoutParams lp = (LayoutParams) mVolumeLL.getLayoutParams();
        lp.width = width;
        mVolumeLL.setPadding(dp2px(3), dp2px(1), dp2px(2), dp2px(1));
        mVolumeLL.requestLayout();
    }

    public void setProgress(float progress) {
        initViewsOnlyAtFirst();
        if (progress > STEP * 10) {
            mVolumeImage.setImageResource(R.drawable.ic_video_volume_l);
        } else if (progress > STEP * 5) {
            mVolumeImage.setImageResource(R.drawable.ic_video_volume_m);
        } else if (progress != 0) {
            mVolumeImage.setImageResource(R.drawable.ic_video_volume_s);
        } else {
            mVolumeImage.setImageResource(R.drawable.ic_video_mute);
        }
        mVolumeLL.removeAllViews();
        for (int i = 0; i < Math.ceil(progress / STEP); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.bg_round_white_ss);
            view.setLayoutParams(new ViewGroup.LayoutParams(dp2px(6), dp2px(5)));
            mVolumeLL.addView(view);
            View gap = new View(mContext);
            gap.setLayoutParams(new ViewGroup.LayoutParams(dp2px(1), dp2px(5)));
            mVolumeLL.addView(gap);
        }
    }

    private int dp2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
