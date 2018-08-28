package com.qlj.lakinqiandemo.video;

import android.view.View;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by lakinqian on 2018/8/28.
 */

public interface OnItemClickListener {
    void onItemClick(View view);

    void onPlayVideo(View view, SimpleExoPlayerView playerView);
}
