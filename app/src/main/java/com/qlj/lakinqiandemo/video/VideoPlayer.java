package com.qlj.lakinqiandemo.video;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2018/8/15.
 */

public class VideoPlayer extends RelativeLayout {
    private static final int LAYOUT_ID = R.layout.view_video_player;

    private Context mContext;
    private View mRootView;
    private SurfaceView mSurfaceView;


    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {
        setBackgroundColor(Color.BLACK);
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
        mSurfaceView = mRootView.findViewById(R.id.sv_video);

    }

    public SurfaceView getSurfaceView(){
        return mSurfaceView;
    }

}
