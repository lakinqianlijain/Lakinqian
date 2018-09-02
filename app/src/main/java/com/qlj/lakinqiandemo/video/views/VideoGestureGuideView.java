package com.qlj.lakinqiandemo.video.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.R;

public class VideoGestureGuideView extends RelativeLayout {

    public static final int LAYOUT_ID = R.layout.view_video_gesture_guide;
    private Context mContext;
    private View mRootView;

    public VideoGestureGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void show() {
        setVisibility(VISIBLE);
        if (mRootView != null) return;
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
    }

    public void dismiss() {
        setVisibility(GONE);
        removeAllViews();
        mRootView = null;
    }
}
