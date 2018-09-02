package com.qlj.lakinqiandemo.video;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.android.exoplayer2.PlaybackParameters.DEFAULT;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.VIDEO_GUIDE_CONTROL;
import static com.qlj.lakinqiandemo.video.VideoPlayer.LEVEL_LOCK;
import static com.qlj.lakinqiandemo.video.VideoPlayer.LEVEL_UNLOCK;

/**
 * Created by lakinqian on 2018/8/15.
 */

public class VideoPlayerHelper {
    private static final long DELAY_PROGRESS_LOOP_INTERVAL = 100;

    private Context mContext;
    private VideoPlayer mVideoPlayer;//播放器view
    private SimpleExoPlayer mSimpleExoPlayer;//播放器

    //音量管理
    private AudioManager mAudioManager;
    //音量相关
    private int mCurrentVolume, mMaxVolume, mMinVolume = 0;
    private float mUnitVolume;

    //亮度相关
    private float mCurrentBrightness = .5f;
    private final float mUnitBrightness = (1f / 15);

    //加载
    private Handler mProgressChangeHandler;
    private AtomicBoolean mProgressChangeBoolean = new AtomicBoolean();
    private Runnable mProgressChangeRunnable;

    //监听器
    private Player.EventListener mPlayerEventListener;
    private OnVideoPlayerListener mOnVideoPlayerListener;
    private SimpleExoPlayer.VideoListener mVideoListener;

    //播放状态
    private int mCurrentState = -1;
    private static final int STATE_LOADING = 101;
    private static final int STATE_PLAYING = 102;
    private static final int STATE_BUFFERING = 103;
    private static final int STATE_PAUSED = 104;
    private static final int STATE_SEEKING = 105;
    private static final int STATE_COMPLETED = 106;

    //播放模式
    private int mCurrentRepeatMode = Player.REPEAT_MODE_ALL;

    private boolean mIsPrepared = false;
    private boolean mWasPlaying = false;//拖拽进度条之前，是否正在播放
    private long mVideoStartPosition = -1;

    //耳机拔出监听
    private BroadcastReceiver mBroadcastReceiver;

    //ExoPlayer三件套
    private DefaultDataSourceFactory mDataSourceFactory;
    private final DefaultExtractorsFactory mExtractorsFactory = new DefaultExtractorsFactory();
    private final DefaultBandwidthMeter mBandwidthMeter = new DefaultBandwidthMeter();

    private List<String> mPlayList = new ArrayList<>();
    private String mCurrentUrl;

    static final int MODE_FIT = 1;
    static final int MODE_FILL = 2;
    static final int MODE_CENTER = 3;
    static final int MODE_16_9 = 4;
    static final int MODE_4_3 = 5;
    private int mCurrentResizeMode = MODE_FIT;
    private float mNormalRatio;


    public VideoPlayerHelper(Context context, VideoPlayer videoPlayer) {
        mContext = context;
        mVideoPlayer = videoPlayer;
        mAudioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
        mMaxVolume = mAudioManager != null ? mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) : 15;
        mUnitVolume = (float) Math.ceil(mMaxVolume / 15);
        mProgressChangeHandler = new Handler();
        initPlayer();
    }

    /**
     * 与destroyPlayer对应，初始化播放器
     */
    public void initPlayer() {
        mDataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, mContext.getResources().getString(R.string.app_name)));

        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(trackSelectionFactory);
        DefaultLoadControl loadControl = new DefaultLoadControl();

        final RenderersFactory renderFactory = new DefaultRenderersFactory(mContext);
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(renderFactory, defaultTrackSelector, loadControl);
        mSimpleExoPlayer.setVideoSurfaceView(mVideoPlayer.getSurfaceView());

        registerBroadcast();
        addListeners();
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);//监听耳机拔出
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && TextUtils.equals(intent.getAction(), AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                    if (isPlaying()) mSimpleExoPlayer.setPlayWhenReady(false);
                }
            }
        };
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    private void addListeners() {
        //视频播放监听
        mPlayerEventListener = new SimpleEventListener() {

            @Override
            public void onLoadingChanged(boolean isLoading) {
                if (!isLoading && mCurrentState == STATE_PAUSED) notifyProgressChanged(false);
                else if (isLoading) notifyProgressChanged(true);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (mCurrentState == STATE_SEEKING) return;
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        mIsPrepared = false;
                        break;
                    case Player.STATE_BUFFERING:
                        if (mIsPrepared && mCurrentState != STATE_LOADING)
                            changeState(STATE_BUFFERING);
                        break;
                    case Player.STATE_READY:
                        if (!mIsPrepared) {
                            mIsPrepared = true;
                            onPrepared(playWhenReady);
                            break;
                        }
                        changeState(playWhenReady ? STATE_PLAYING : STATE_PAUSED);
                        break;
                    case Player.STATE_ENDED:
                        changeState(STATE_COMPLETED);
                        mIsPrepared = false;
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                mCurrentRepeatMode = repeatMode;
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                onError(error);
            }

        };
        mSimpleExoPlayer.addListener(mPlayerEventListener);

        //view的监听事件
        mOnVideoPlayerListener = new OnVideoPlayerListener() {

            @Override
            public void onPlaybackClick() {
                if (mCurrentState == STATE_COMPLETED) {
                    replay();
                    return;
                }
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
            }

            @Override
            public void onForwardClick() {
                playNext();
            }

            @Override
            public void onRewindClick() {
                playLast();
            }

            @Override
            public void onLockClick(boolean lock) {
                int lockLevel;
                if (!lock) {
                    lockLevel = LEVEL_UNLOCK;
                } else {
                    lockLevel = LEVEL_LOCK;
                }
                mVideoPlayer.onScreenLocked(lockLevel);
            }

            @Override
            public void onBackClick() {
                if (mContext instanceof Activity) {
                    ((Activity) mContext).onBackPressed();
                }
            }

            @Override
            public void onBrightnessChanged(boolean up) {
                if (!(mContext instanceof Activity)) return;
                WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
                mCurrentBrightness += up ? mUnitBrightness : -mUnitBrightness;
                if (mCurrentBrightness >= 1f) mCurrentBrightness = 1f;
                if (mCurrentBrightness <= .01f) mCurrentBrightness = .01f;
                lp.screenBrightness = mCurrentBrightness;
                ((Activity) mContext).getWindow().setAttributes(lp);
                mVideoPlayer.onBrightnessChanged(mCurrentBrightness);
            }

            @Override
            public void onVolumeChanged(boolean up) {
                double floor = Math.floor(up ? mUnitVolume : -mUnitVolume);
                mCurrentVolume = (int) (mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + floor);
                if (mCurrentVolume >= mMaxVolume) mCurrentVolume = mMaxVolume;
                if (mCurrentVolume <= mMinVolume) mCurrentVolume = mMinVolume;
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
                mVideoPlayer.onVolumeChanged(((float) mCurrentVolume) / mMaxVolume);
            }

            @Override
            public void onVideoSingleTap() {
                mVideoPlayer.showOrHideControlView();
            }

            @Override
            public void onGestureFinish() {
                mVideoPlayer.onGestureFinish();
            }

            @Override
            public void onStartTracking() {
                if (mCurrentState != STATE_SEEKING) changeState(STATE_SEEKING);
                notifyProgressChanged(false);
                mWasPlaying = isPlaying();
                if (isPlaying()) mSimpleExoPlayer.setPlayWhenReady(false);
                mVideoPlayer.startTracking();
            }

            @Override
            public void onTracking(boolean fromBar, int progress, float movePercentage) {
                if (!fromBar) {
                    progress = (int) ((mSimpleExoPlayer.getDuration() * movePercentage) + mSimpleExoPlayer.getCurrentPosition());
                }
                if (progress < 0) progress = 0;
                progress = (int) Math.min(progress, mSimpleExoPlayer.getDuration());
                mVideoPlayer.onTracking(progress);
            }

            @Override
            public void onStopTracking(boolean fromBar, int progress, float movePercentage) {
                if (!fromBar) {
                    progress = (int) ((mSimpleExoPlayer.getDuration() * movePercentage) + mSimpleExoPlayer.getCurrentPosition());
                }
                if (progress < 0) progress = 0;
                progress = progress < mSimpleExoPlayer.getDuration() ? progress : (int) (mSimpleExoPlayer.getDuration() - 1000);
                mSimpleExoPlayer.seekTo(progress);
                if (mWasPlaying) mSimpleExoPlayer.setPlayWhenReady(true);
                if (mCurrentState == STATE_SEEKING) changeState(STATE_BUFFERING);
                notifyProgressChanged(true);
                mVideoPlayer.stopTracking(progress);
            }

            @Override
            public void onRatioClick() {
                mCurrentResizeMode = mCurrentResizeMode == MODE_4_3 ? MODE_FIT : mCurrentResizeMode + 1;
                if (mSimpleExoPlayer.getVideoFormat() != null) {
                    mNormalRatio = (float) mSimpleExoPlayer.getVideoFormat().width / mSimpleExoPlayer.getVideoFormat().height;
                }
                mVideoPlayer.setResizeMode(mCurrentResizeMode, mNormalRatio);
            }

            @Override
            public void onPlaybackSpeedChanged(float speed) {
                mSimpleExoPlayer.setPlaybackParameters(new PlaybackParameters(speed, 1f));
            }
        };
        mVideoPlayer.setOnVideoPlayerListener(mOnVideoPlayerListener);

        mVideoListener = new SimpleExoPlayer.VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                mNormalRatio = (float) width / height;
                mVideoPlayer.onVideoSizeChanged(width, height);
            }

            @Override
            public void onRenderedFirstFrame() {
                mVideoPlayer.onRenderedFirstFrame();
            }
        };
        mSimpleExoPlayer.addVideoListener(mVideoListener);

        // 读条runnable
        mProgressChangeRunnable = new Runnable() {
            @Override
            public void run() {
                mVideoPlayer.onProgressChanged((int) mSimpleExoPlayer.getCurrentPosition(),
                        (int) mSimpleExoPlayer.getDuration(),
                        mSimpleExoPlayer.getBufferedPercentage());
                if (mProgressChangeBoolean.get())
                    mProgressChangeHandler.postDelayed(this, DELAY_PROGRESS_LOOP_INTERVAL);
            }
        };
    }

    private void onPrepared(boolean playWhenReady) {
        mVideoPlayer.onPrepared((int) mVideoStartPosition >= 0 ? (int) mVideoStartPosition : 0, (int) mSimpleExoPlayer.getDuration());
        changeState(playWhenReady ? STATE_PLAYING : STATE_PAUSED);
    }

    private void changeState(int state) {
        mCurrentState = state;
        switch (state) {
            case STATE_LOADING:
                notifyProgressChanged(true);
                mVideoPlayer.onLoading();
                break;
            case STATE_PLAYING:
                notifyProgressChanged(true);
                mVideoPlayer.onPlaying();
                break;
            case STATE_BUFFERING:
                break;
            case STATE_PAUSED:
                notifyProgressChanged(false);
                mVideoPlayer.onPaused();
                break;
            case STATE_SEEKING:
                break;
            case STATE_COMPLETED:
                notifyProgressChanged(false);
                switch (mCurrentRepeatMode) {
                    case Player.REPEAT_MODE_OFF:
                        mVideoPlayer.onCompleted((int) mSimpleExoPlayer.getDuration());
                        break;
                    case Player.REPEAT_MODE_ALL:
                        mVideoPlayer.onPaused();
                        playNext();
                        break;
                    case Player.REPEAT_MODE_ONE:
                        mSimpleExoPlayer.seekTo(0);
                        playUrl(mCurrentUrl, true);
                        break;
                }
                break;
        }
    }

    private void play() {
        mSimpleExoPlayer.setPlayWhenReady(true);
        changeState(STATE_PLAYING);
    }

    private void pause() {
        mSimpleExoPlayer.setPlayWhenReady(false);
        changeState(STATE_PAUSED);
    }

    private void replay() {
        changeState(STATE_LOADING);
        setVideoStartPos(0);
        mSimpleExoPlayer.seekTo(0);
        mSimpleExoPlayer.setPlayWhenReady(true);
        changeState(STATE_PLAYING);
    }

    private void playLast() {
        int index = mPlayList.indexOf(mCurrentUrl);
        if (index < 0) return;
        index = index - 1 < 0 ? mPlayList.size() - 1 : index - 1;
        if (mPlayList.get(index).equals(mCurrentUrl)) return;
        setVideoStartPos(0);
        playUrl(mPlayList.get(index), true);
    }

    private void playNext() {
        int index = mPlayList.indexOf(mCurrentUrl);
        if (index < 0) return;
        index = index + 1 >= mPlayList.size() ? 0 : index + 1;
        if (mPlayList.get(index).equals(mCurrentUrl)) return;
        setVideoStartPos(0);
        playUrl(mPlayList.get(index), true);
    }

    /**
     * 设置播放开始位置
     *
     * @param position 开始位置
     */
    public void setVideoStartPos(long position) {
        mVideoStartPosition = position;
    }

    /**
     * 获取SimpleExoPlayer对象
     *
     * @return 播放器
     */
    public SimpleExoPlayer getExoPlayer() {
        return mSimpleExoPlayer;
    }

    /**
     * @return 当前视频位置
     */
    public long getCurrentVideoPos() {
        return mSimpleExoPlayer.getCurrentPosition();
    }

    /**
     * 销毁，释放内存，调用后如需使用此对象需重新创建
     */
    public void destroy() {
        destroyPlayer();
        mSimpleExoPlayer = null;
        mAudioManager = null;
    }


    /**
     * 播放列表视频
     *
     * @param urls     播放列表
     * @param autoPlay 是否自动播放
     */
    public void playUrls(List<String> urls, boolean autoPlay) {
        if (urls == null || urls.isEmpty()) return;
        mPlayList = urls;
        playUrl(mPlayList.get(0), autoPlay);
    }

    /**
     * 再次播放当前视频，activity重新唤醒时调用
     *
     * @param autoPlay 是否播放
     */
    public void playAgain(boolean autoPlay) {
        if (mCurrentUrl == null || mSimpleExoPlayer == null) {
            RuntimeException runtimeException = new RuntimeException((mCurrentUrl == null ? "Url " : "Player ") + "cannot be null");
            onError(runtimeException);
            throw runtimeException;
        }
        playUrl(mCurrentUrl, autoPlay);
    }

    /**
     * 播放单个视频
     *
     * @param url      链接
     * @param autoPlay 是否自动播放
     */
    public void playUrl(String url, boolean autoPlay) {
        playUrl(url, "video title", autoPlay);
    }

    /**
     * 播放视频
     *
     * @param url      链接
     * @param title    视频名称
     * @param autoPlay 是否自动播放
     */
    public void playUrl(String url, String title, boolean autoPlay) {
        if (url == null || mSimpleExoPlayer == null) {
            RuntimeException runtimeException = new RuntimeException((url == null ? "Url " : "Player ") + "cannot be null");
            onError(runtimeException);
            throw runtimeException;
        }
        mCurrentUrl = url;

        mVideoPlayer.setTitle(title);
        showGuideViewIfNecessary();

        changeState(STATE_LOADING);

        mIsPrepared = false;
        MediaSource mediaSource = buildMediaSource(url, "mp4");

        if (mSimpleExoPlayer.getPlaybackState() != Player.STATE_IDLE) mSimpleExoPlayer.stop();
        if (mVideoStartPosition > 0) mSimpleExoPlayer.seekTo(mVideoStartPosition);
        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(autoPlay);
        mSimpleExoPlayer.setPlaybackParameters(DEFAULT);//重置播放速度
        mVideoPlayer.resetPlaybackSpeed();
    }

    private void showGuideViewIfNecessary() {
        boolean shown = SharedPreferenceUtil.readBoolean(mContext, DEMO_CONFIG, VIDEO_GUIDE_CONTROL, false);
        if (!shown) {
            mVideoPlayer.showGuideView();
            SharedPreferenceUtil.saveBoolean(mContext, DEMO_CONFIG, VIDEO_GUIDE_CONTROL, true);
        }
    }

    private void onError(Exception runtimeException) {
//        mLocalPlayer.onPaused();
        Toast.makeText(mContext, mContext.getResources().getString(R.string.text_unsupported_format), Toast.LENGTH_SHORT).show();
    }

    private void notifyProgressChanged(boolean changed) {
        if (changed) {
            if (!mProgressChangeBoolean.get()) {
                mProgressChangeHandler.removeCallbacksAndMessages(null);
                mProgressChangeBoolean.set(true);
                mProgressChangeHandler.post(mProgressChangeRunnable);
            }
        } else {
            if (mProgressChangeBoolean.get()) {
                mProgressChangeHandler.removeCallbacksAndMessages(null);
                mProgressChangeBoolean.set(false);
            }
        }
    }

    private MediaSource buildMediaSource(String url, String overrideExtension) {
        Uri uri = Uri.parse(url);//encode为了处理特殊字符
        int type = TextUtils.isEmpty(overrideExtension)
                ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        MediaSource mediaSource;
        switch (type) {
            case C.TYPE_SS:
                mediaSource = new SsMediaSource(uri, mDataSourceFactory,
                        new DefaultSsChunkSource.Factory(mDataSourceFactory), null, null);
                break;
            case C.TYPE_DASH:
                mediaSource = new DashMediaSource(uri, mDataSourceFactory,
                        new DefaultDashChunkSource.Factory(mDataSourceFactory), null, null);
                break;
            case C.TYPE_HLS:
                mediaSource = new HlsMediaSource(uri, mDataSourceFactory, null, null);
                break;
            case C.TYPE_OTHER:
                mediaSource = new ExtractorMediaSource(uri, mDataSourceFactory, mExtractorsFactory, null, null);
                break;
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
        return mediaSource;
    }

    /**
     * 是否正在播放视频
     *
     * @return 是否正在播放
     */
    public boolean isPlaying() {
        return mSimpleExoPlayer.getPlaybackState() == Player.STATE_READY && mSimpleExoPlayer.getPlayWhenReady();
    }

    public void destroyPlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer.removeVideoListener(mVideoListener);
            mSimpleExoPlayer.removeListener(mPlayerEventListener);
            mVideoListener = null;
            mPlayerEventListener = null;
        }
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnVideoPlayerListener(null);
            mOnVideoPlayerListener = null;
        }
        if (mProgressChangeHandler != null) {
            notifyProgressChanged(false);
        }
        unregisterBroadcastReceiver();
    }

    private void unregisterBroadcastReceiver() {
        if (mBroadcastReceiver != null && mContext != null) {
            mContext.unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    public boolean close() {
        if (mVideoPlayer.onClosed()) {
            destroy();
            return true;
        }
        return false;
    }

}
