package com.qlj.lakinqiandemo.video;

public interface OnVideoPlayerListener {
    void onPlaybackClick();

    void onForwardClick();

    void onRewindClick();

    void onLockClick(boolean lock);

    void onBackClick();

    void onBrightnessChanged(boolean up);

    void onVolumeChanged(boolean up);

    void onGestureFinish();

    void onVideoSingleTap();

    void onStartTracking();

    void onStopTracking(boolean fromBar, int progress, float movePercentage);

    void onTracking(boolean fromBar, int progress, float movePercentage);

    void onRatioClick();

    void onPlaybackSpeedChanged(float speed);
}
