package com.qlj.lakinqiandemo.video;

import android.view.SurfaceView;

/**
 * 定义播放器view的接口方法
 */
public interface IVideoPlayerView {

    /**
     * @param startPos 开始播放的位置
     * @param duration 总时长
     */
    void onPrepared(int startPos, int duration);

    /**
     * play后的ui变化
     */
    void onPlaying();

    /**
     * pause后的ui变化
     */
    void onPaused();

    /**
     * 播放完后的ui变化
     */
    void onCompleted(int duration);

    /**
     * loading时的ui
     */
    void onLoading();

    /**
     * 音量改变
     *
     * @param progress 音量
     */
    void onVolumeChanged(float progress);

    /**
     * 亮度改变
     *
     * @param progress 亮度
     */
    void onBrightnessChanged(float progress);

    /**
     * 结束亮度、音量调节
     */
    void onGestureFinish();

    /**
     * 进度改变
     *
     * @param currentPosition  当前位置
     * @param duration         总时长
     * @param bufferPercentage 缓冲比例
     */
    void onProgressChanged(int currentPosition, int duration, int bufferPercentage);

    /**
     * 显示/隐藏ControlView
     */
    void showOrHideControlView();

    /**
     * 屏幕锁打开/关闭
     *
     * @param lockLevel 锁的类型
     */
    void onScreenLocked(int lockLevel);

    /**
     * @return surfaceView
     */
    SurfaceView getSurfaceView();

    /**
     * 开始拖动进度
     */
    void startTracking();

    /**
     * 正在拖动进度
     *
     * @param progress 当前进度
     */
    void onTracking(int progress);

    /**
     * 结束拖动进度
     *
     * @param progress 当前进度
     */
    void stopTracking(int progress);

    /**
     * video尺寸改变
     *
     * @param width  宽
     * @param height 高
     */
    void onVideoSizeChanged(int width, int height);

    /**
     * 第一帧渲染完成
     */
    void onRenderedFirstFrame();

    /**
     * 设置屏幕比例
     *
     * @param resizeMode 模式：fit、fill、center等
     * @param ratio      原始视频比例
     */
    void setResizeMode(int resizeMode, float ratio);

    /**
     * 设置标题
     *
     * @param title 标题
     */
    void setTitle(String title);

    /**
     * 显示引导视图
     */
    void showGuideView();

    /**
     * 返回键点击时
     *
     * @return 是否拦截
     */
    boolean onClosed();

    /**
     * 重置播放速度UI
     */
    void resetPlaybackSpeed();
}
