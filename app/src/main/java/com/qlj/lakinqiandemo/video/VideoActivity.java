package com.qlj.lakinqiandemo.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.video.ListVideoPlay.ListVideoPlayActivity;

/**
 * Created by Administrator on 2018/9/2.
 */

public class VideoActivity extends BaseActivity implements View.OnClickListener {
    private TextView mVideoPlay, mListPlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initViews();
    }

    private void initViews() {
        mVideoPlay = findViewById(R.id.video_play);
        mVideoPlay.setOnClickListener(this);
        mListPlay = findViewById(R.id.list_video_play);
        mListPlay.setOnClickListener(this);
        mListPlay.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_play:
                JumpActivityUtil.JumpSelfActivity(this, VideoPlayActivity.class);
                break;
            case R.id.list_video_play:
                JumpActivityUtil.JumpSelfActivity(this, ListVideoPlayActivity.class);
                break;
        }
    }
}
