package com.qlj.lakinqiandemo.video.ListVideoPlay;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.qlj.lakinqiandemo.R;

import static com.google.android.exoplayer2.PlaybackParameters.DEFAULT;

/**
 * Created by Administrator on 2018/8/25.
 */

public class ListVideoPlayerHelper {
    private Context mContext;
    private SimpleExoPlayer mSimpleExoPlayer;//播放器
    private SimpleExoPlayerView mPlayerView;
    //ExoPlayer三件套
    private DefaultDataSourceFactory mDataSourceFactory;
    private final DefaultExtractorsFactory mExtractorsFactory = new DefaultExtractorsFactory();
    private final DefaultBandwidthMeter mBandwidthMeter = new DefaultBandwidthMeter();

    public ListVideoPlayerHelper(Context context, SimpleExoPlayerView videoPlayer){
        mContext = context;
        mPlayerView = videoPlayer;
        initPlayer();
    }

    private void initPlayer() {
        mDataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, mContext.getResources().getString(R.string.app_name)));

        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(trackSelectionFactory);
        DefaultLoadControl loadControl = new DefaultLoadControl();

        final RenderersFactory renderFactory = new DefaultRenderersFactory(mContext);
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(renderFactory, defaultTrackSelector, loadControl);
        mPlayerView.setPlayer(mSimpleExoPlayer);
    }

    /**
     * 播放视频
     *
     * @param url 链接
     */
    public void playUrl(String url, String format, boolean autoPlay) {
        if (url == null || mSimpleExoPlayer == null) {
            RuntimeException runtimeException = new RuntimeException((url == null ? "Url " : "Player ") + "cannot be null");
            onError(runtimeException);
            throw runtimeException;
        }
        MediaSource mediaSource = buildMediaSource(url, format);

        if (mSimpleExoPlayer.getPlaybackState() != Player.STATE_IDLE) mSimpleExoPlayer.stop();
        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(autoPlay);
        mSimpleExoPlayer.setPlaybackParameters(DEFAULT);//重置播放速度
    }

    private void onError(Exception runtimeException) {
//        mLocalPlayer.onPaused();
        Toast.makeText(mContext, mContext.getResources().getString(R.string.text_unsupported_format), Toast.LENGTH_SHORT).show();
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
}
