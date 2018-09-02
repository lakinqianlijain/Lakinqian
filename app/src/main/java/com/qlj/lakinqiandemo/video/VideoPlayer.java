package com.qlj.lakinqiandemo.video;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.AnimationUtil;
import com.qlj.lakinqiandemo.utils.FormatUtil;
import com.qlj.lakinqiandemo.video.views.BrightnessChangeView;
import com.qlj.lakinqiandemo.video.views.PlaybackSpeedControlView;
import com.qlj.lakinqiandemo.video.views.VideoGestureGuideView;
import com.qlj.lakinqiandemo.video.views.VolumeChangeView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL;
import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT;
import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH;
import static com.qlj.lakinqiandemo.utils.AnimationUtil.animateView;
import static com.qlj.lakinqiandemo.video.VideoPlayerHelper.MODE_16_9;
import static com.qlj.lakinqiandemo.video.VideoPlayerHelper.MODE_4_3;
import static com.qlj.lakinqiandemo.video.VideoPlayerHelper.MODE_CENTER;
import static com.qlj.lakinqiandemo.video.VideoPlayerHelper.MODE_FILL;
import static com.qlj.lakinqiandemo.video.VideoPlayerHelper.MODE_FIT;

/**
 * Created by lakinqian on 2018/8/15.
 */

/**
 * 本地视频播放器,View,只关心view相关的内容，不关心逻辑
 */

public class VideoPlayer extends RelativeLayout implements View.OnClickListener, IVideoPlayerView {
    private static final int LAYOUT_ID = R.layout.view_video_player;

    private class VideoPlayerViewHolder {
        AspectRatioFrameLayout aspectRatioFL;
        SurfaceView videoSurface;
        RelativeLayout playerControlRL;
        TextView titleText, currentTimeText, totalTimeText;
        SeekBar playbackSeekBar;
        ImageView playbackImage, unlockImage;
        TextView playbackTimeText, ratioText, menuSpeedText;
        View mask;
        PlaybackSpeedControlView speedControlView;
        VolumeChangeView volume;
        BrightnessChangeView brightness;
        VideoGestureGuideView guideView;
    }

    private Context mContext;
    private View mRootView;
    private VideoPlayerViewHolder mHolder;
    private GestureDetector mGestureDetector;
    private OnVideoPlayerListener mVideoPlayerListener;
    private int mLockLevel = LEVEL_UNLOCK;//这里只涉及显示隐藏View的逻辑，所以放在这边
    public static int LEVEL_UNLOCK = 0;
    public static int LEVEL_LOCK = 1;

    private Handler mControlViewHandler = new Handler();


    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        initListeners();
    }

    private void initViews() {
        setBackgroundColor(Color.BLACK);
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
        mHolder = new VideoPlayerViewHolder();
        mHolder.aspectRatioFL = mRootView.findViewById(R.id.arfl_video);
        mHolder.videoSurface = mRootView.findViewById(R.id.sv_video);
        mHolder.playerControlRL = mRootView.findViewById(R.id.rl_player_control);
        mHolder.titleText = mRootView.findViewById(R.id.tv_title);
        mHolder.currentTimeText = mRootView.findViewById(R.id.tv_time_current);
        mHolder.totalTimeText = mRootView.findViewById(R.id.tv_time_total);
        mHolder.playbackSeekBar = mRootView.findViewById(R.id.sb_playback);
        mHolder.playbackImage = mRootView.findViewById(R.id.iv_playback);
        mHolder.volume = mRootView.findViewById(R.id.vcv_volume);
        mHolder.brightness = mRootView.findViewById(R.id.bcv_brightness);
        mHolder.playbackTimeText = mRootView.findViewById(R.id.tv_playback_time);
        mHolder.unlockImage = mRootView.findViewById(R.id.iv_unlock);
        mHolder.ratioText = mRootView.findViewById(R.id.tv_aspect_ratio);
        mHolder.menuSpeedText = mRootView.findViewById(R.id.tv_menu_speed);
        mHolder.mask = mRootView.findViewById(R.id.v_mask);
        mRootView.setKeepScreenOn(true);
    }

    private void initListeners() {
        mHolder.playbackImage.setOnClickListener(this);
        mHolder.unlockImage.setOnClickListener(this);
        mHolder.menuSpeedText.setOnClickListener(this);
        mRootView.findViewById(R.id.iv_forward).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_aspect_ratio).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_rewind).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_lock).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_back).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_menu).setOnClickListener(this);

        VideoGestureListener listener = new VideoGestureListener();
        mGestureDetector = new GestureDetector(mContext, listener);
        mGestureDetector.setIsLongpressEnabled(false);
        mRootView.setOnTouchListener(listener);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mVideoPlayerListener != null)
                    mVideoPlayerListener.onTracking(true, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mVideoPlayerListener != null) mVideoPlayerListener.onStartTracking();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mVideoPlayerListener != null)
                    mVideoPlayerListener.onStopTracking(true, seekBar.getProgress(), 0);
            }
        };
        mHolder.playbackSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    public void onClick(View v) {
        if (mVideoPlayerListener == null) return;
        switch (v.getId()) {
            case R.id.iv_playback:
                mVideoPlayerListener.onPlaybackClick();
                break;
            case R.id.iv_forward:
                mVideoPlayerListener.onForwardClick();
                break;
            case R.id.iv_rewind:
                mVideoPlayerListener.onRewindClick();
                break;
            case R.id.iv_lock:
                mVideoPlayerListener.onLockClick(true);
                break;
            case R.id.iv_unlock:
                mVideoPlayerListener.onLockClick(false);
                break;
            case R.id.iv_aspect_ratio:
                mVideoPlayerListener.onRatioClick();
                break;
            case R.id.iv_back:
                mVideoPlayerListener.onBackClick();
                break;
            case R.id.iv_menu:
                mHolder.menuSpeedText.setVisibility(VISIBLE);
                break;
            case R.id.tv_menu_speed:
                mHolder.menuSpeedText.setVisibility(GONE);
                mHolder.mask.setVisibility(VISIBLE);
                getSpeedControlView().show();
                break;
        }
    }

    private PlaybackSpeedControlView getSpeedControlView() {
        if (mHolder.speedControlView == null) {
            mHolder.speedControlView = ((ViewStub) findViewById(R.id.rl_speed)).inflate().findViewById(R.id.psc);
            mHolder.speedControlView.setListener(new PlaybackSpeedControlView.OnDismissListener() {
                @Override
                public void onDismiss(float speed) {
                    mHolder.mask.setVisibility(GONE);
                    mVideoPlayerListener.onPlaybackSpeedChanged(speed);
                }
            });
        }
        return mHolder.speedControlView;
    }

    /**
     * 设置屏幕事件监听
     *
     * @param listener 监听器
     */
    public void setOnVideoPlayerListener(OnVideoPlayerListener listener) {
        mVideoPlayerListener = listener;
    }

    @Override
    public void onPrepared(int startPos, int duration) {
        mHolder.playbackSeekBar.setMax(duration);
        mHolder.playbackSeekBar.setProgress(startPos);
        mHolder.currentTimeText.setText(FormatUtil.getTimeString(startPos));
        mHolder.totalTimeText.setText(FormatUtil.getTimeString(duration));
    }

    @Override
    public void onPlaying() {
        hideRedundant();
        mHolder.playbackImage.setBackgroundResource(R.drawable.ic_video_pause);
        showControlViewThenHide(mLockLevel);
        mRootView.setKeepScreenOn(true);
    }

    @Override
    public void onPaused() {
        mHolder.playbackImage.setBackgroundResource(R.drawable.ic_video_play);
        showControlView(400, mLockLevel);
        mRootView.setKeepScreenOn(false);
    }

    @Override
    public void onCompleted(int duration) {
        showControlView(500, mLockLevel);
        mHolder.playbackImage.setBackgroundResource(R.drawable.ic_video_play);
        mHolder.playbackSeekBar.setMax(duration);
        mHolder.playbackSeekBar.setProgress(mHolder.playbackSeekBar.getMax());
        mHolder.playbackSeekBar.setEnabled(false);
        mHolder.totalTimeText.setText(FormatUtil.getTimeString(mHolder.playbackSeekBar.getMax()));
        mHolder.currentTimeText.setText(mHolder.totalTimeText.getText());

        mRootView.setKeepScreenOn(false);
    }

    @Override
    public void onLoading() {
        hideControlView(300, 0, mLockLevel);
        mHolder.playbackSeekBar.setEnabled(true);
        mHolder.playbackSeekBar.setProgress(0);
        mRootView.setKeepScreenOn(true);
    }

    @Override
    public void onVolumeChanged(float progress) {
        if (mHolder.volume.getVisibility() != VISIBLE)
            animateView(mHolder.volume, true, 200);
        mHolder.volume.setProgress(progress);
    }

    @Override
    public void onBrightnessChanged(float progress) {
        if (mHolder.brightness.getVisibility() != View.VISIBLE)
            animateView(mHolder.brightness, true, 200);
        mHolder.brightness.setProgress(progress);
    }

    @Override
    public void onGestureFinish() {
        if (mHolder.volume.getVisibility() == VISIBLE) {
            animateView(mHolder.volume, false, 200);
        }
        if (mHolder.brightness.getVisibility() == VISIBLE) {
            animateView(mHolder.brightness, false, 200);
        }
    }

    @Override
    public void onProgressChanged(int currentPosition, int duration, int bufferPercentage) {
        mHolder.playbackSeekBar.setProgress(currentPosition);
        mHolder.currentTimeText.setText(FormatUtil.getTimeString(currentPosition));
        mHolder.playbackSeekBar.setSecondaryProgress((int) (mHolder.playbackSeekBar.getMax() * ((float) bufferPercentage / 100)));
    }

    @Override
    public void showOrHideControlView() {
        boolean isLocked = mLockLevel == LEVEL_LOCK;
        boolean show = isLocked ? mHolder.unlockImage.getVisibility() != VISIBLE :
                mHolder.playerControlRL.getVisibility() != VISIBLE;
        if (show) {
            showControlViewThenHide(mLockLevel);
        } else {
            hideControlView(300, 0, mLockLevel);
        }
    }

    @Override
    public void onScreenLocked(int lockLevel) {
        hideControlView(0, 0, lockLevel == LEVEL_UNLOCK ? LEVEL_LOCK : LEVEL_UNLOCK);
        mLockLevel = lockLevel;
    }

    public SurfaceView getSurfaceView() {
        return mHolder.videoSurface;
    }


    @Override
    public void startTracking() {
        showControlView(0, mLockLevel);
        mHolder.mask.setVisibility(VISIBLE);
        mHolder.playbackSeekBar.setThumb(mContext.getDrawable(R.drawable.shape_thumb_orange_pressed));
        animateView(mHolder.playbackTimeText, AnimationUtil.Type.SCALE_AND_ALPHA, true, 300);
    }

    @Override
    public void onTracking(int progress) {
        showControlView(0, mLockLevel);
        mHolder.mask.setVisibility(VISIBLE);
        mHolder.playbackSeekBar.setThumb(mContext.getDrawable(R.drawable.shape_thumb_orange_pressed));
        animateView(mHolder.playbackTimeText, AnimationUtil.Type.SCALE_AND_ALPHA, true, 300);
    }

    @Override
    public void stopTracking(int progress) {
        mHolder.mask.setVisibility(GONE);
        mHolder.playbackTimeText.setText(FormatUtil.getTimeString(progress));
        mHolder.playbackSeekBar.setThumb(mContext.getDrawable(R.drawable.shape_thumb_orange_normal));
        animateView(mHolder.playbackTimeText, AnimationUtil.Type.SCALE_AND_ALPHA, false, 200);
    }

    @Override
    public void onVideoSizeChanged(int width, int height) {
        mHolder.aspectRatioFL.setAspectRatio(((float) width) / height);
    }

    @Override
    public void onRenderedFirstFrame() {

    }

    @Override
    public void setResizeMode(int resizeMode, float ratio) {
        if (mHolder.ratioText.getVisibility() != VISIBLE) mHolder.ratioText.setVisibility(VISIBLE);
        showControlViewThenHide(mLockLevel);
        LayoutParams lp = (LayoutParams) mHolder.aspectRatioFL.getLayoutParams();
        switch (resizeMode) {
            case MODE_FIT:
                mHolder.ratioText.setText(R.string.video_ratio_fit);
                mHolder.aspectRatioFL.setAspectRatio(ratio);
                mHolder.aspectRatioFL.setResizeMode(RESIZE_MODE_FIT);
                break;
            case MODE_FILL:
                mHolder.ratioText.setText(R.string.video_ratio_fill);
                mHolder.aspectRatioFL.setAspectRatio(ratio);
                mHolder.aspectRatioFL.setResizeMode(RESIZE_MODE_FILL);
                break;
            case MODE_CENTER:
                mHolder.ratioText.setText(R.string.video_ratio_center);
                lp.width = mRootView.getWidth() / 2;
                mHolder.aspectRatioFL.setAspectRatio(ratio);
                mHolder.aspectRatioFL.setResizeMode(RESIZE_MODE_FIXED_WIDTH);
                break;
            case MODE_16_9:
                mHolder.ratioText.setText(R.string.video_ratio_sixteen);
                lp.width = MATCH_PARENT;
                mHolder.aspectRatioFL.setAspectRatio(((float) 16) / 9);
                mHolder.aspectRatioFL.setResizeMode(RESIZE_MODE_FIT);
                break;
            case MODE_4_3:
                mHolder.ratioText.setText(R.string.video_ratio_four);
                lp.width = MATCH_PARENT;
                mHolder.aspectRatioFL.setAspectRatio(((float) 4) / 3);
                mHolder.aspectRatioFL.setResizeMode(RESIZE_MODE_FIT);
                break;
        }
    }

    @Override
    public void setTitle(String title) {
        mHolder.titleText.setText(title);
    }

    @Override
    public void showGuideView() {
        if (mHolder.guideView == null) {
            mHolder.guideView = ((ViewStub) findViewById(R.id.rl_guide)).inflate().findViewById(R.id.vgg);
        }
        mHolder.guideView.show();
    }

    @Override
    public boolean onClosed() {
        mControlViewHandler.removeCallbacksAndMessages(null);
        mControlViewHandler = null;
        return true;
    }

    @Override
    public void resetPlaybackSpeed() {
        if (mHolder.speedControlView != null) mHolder.speedControlView.reset();
    }

    private void hideRedundant() {
        if (mHolder.guideView != null && mHolder.guideView.getVisibility() == VISIBLE)
            mHolder.guideView.dismiss();
        if (mHolder.menuSpeedText.getVisibility() == VISIBLE)
            mHolder.menuSpeedText.setVisibility(GONE);
        if (mHolder.speedControlView != null && mHolder.speedControlView.getVisibility() == VISIBLE)
            mHolder.speedControlView.dismiss();
        if (mHolder.ratioText.getVisibility() == VISIBLE)
            mHolder.ratioText.setVisibility(GONE);
    }

    private void showControlViewThenHide(final int lockLevel) {
        final boolean isLocked = lockLevel == 1;
        animateView(isLocked ? mHolder.unlockImage : mHolder.playerControlRL, true, 300, 0, new Runnable() {
            @Override
            public void run() {
                hideControlView(300, 3000, lockLevel);
            }
        });
    }

    private void showControlView(long duration, int lockLevel) {
        boolean isLocked = lockLevel == 1;
        mControlViewHandler.removeCallbacksAndMessages(null);
        animateView(isLocked ? mHolder.unlockImage : mHolder.playerControlRL, true, duration);
    }

    private void hideControlView(final long duration, long delay, int lockLevel) {
        final boolean isLocked = lockLevel == 1;
        mControlViewHandler.removeCallbacksAndMessages(null);
        mControlViewHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateView(isLocked ? mHolder.unlockImage : mHolder.playerControlRL, false, duration);
                mHolder.ratioText.setVisibility(GONE);
            }
        }, delay);
    }

    private class VideoGestureListener extends GestureDetector.SimpleOnGestureListener
            implements OnTouchListener {

        private static final int EDGE_PADDING = 50;//边缘区域不响应
        private final int MOVEMENT_THRESHOLD = 40; //限制滑动距离，避免误触
        private final int EVENT_THRESHOLD = 5;//音量、亮度响应刻度

        private boolean triggered = false;
        private int eventCount;
        private float trackingX = 0;//横向移动值
        private boolean isTransverseMoving;//是否正在横向滑动
        private boolean isLongitudinalMoving;//是否正在纵向滑动

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mVideoPlayerListener != null) {
                mVideoPlayerListener.onVideoSingleTap();
            }
            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            hideRedundant();
            mGestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                triggered = false;
                if (isLongitudinalMoving) {
                    if (mVideoPlayerListener != null) {
                        mVideoPlayerListener.onGestureFinish();
                    }
                    isLongitudinalMoving = false;
                    eventCount = 0;
                }
                if (isTransverseMoving) {
                    mVideoPlayerListener.onStopTracking(false, 0, trackingX / mRootView.getWidth());
                    trackingX = 0;
                    isTransverseMoving = false;
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float abs = Math.max(Math.abs(e2.getY() - e1.getY()), Math.abs(e2.getX() - e1.getX()));
            if (!triggered) {
                triggered = abs > MOVEMENT_THRESHOLD;
                return false;
            }

            // 是否有效手势区域
            int effectXEnd = mRootView.getWidth() - EDGE_PADDING;
            int effectYEnd = mRootView.getHeight() - EDGE_PADDING;
            if (e1.getX() < EDGE_PADDING || e1.getY() < EDGE_PADDING) return false;
            if (e1.getX() > effectXEnd || e1.getY() > effectYEnd) return false;

            if (mLockLevel != LEVEL_UNLOCK) return false;
            if (mVideoPlayerListener == null) return false;

            //是否横向手势
            boolean isTransverse = (Math.abs(e2.getX() - e1.getX())) > (Math.abs(e2.getY() - e1.getY()));

            if (isTransverse) {
                if (isLongitudinalMoving) return false;
                if (!isTransverseMoving) mVideoPlayerListener.onStartTracking();//只调用一次
                isTransverseMoving = true;
                trackingX -= distanceX;
                mVideoPlayerListener.onTracking(false, 0, trackingX / mRootView.getWidth());
            } else {
                if (isTransverseMoving) return false;
                if (eventCount++ % EVENT_THRESHOLD != 0)
                    return false;//刻度
                isLongitudinalMoving = true;
                boolean up = distanceY > 0;
                if (e1.getX() > mRootView.getWidth() / 2) {
                    mVideoPlayerListener.onVolumeChanged(up);
                } else {
                    mVideoPlayerListener.onBrightnessChanged(up);
                }
            }
            return true;
        }
    }
}
