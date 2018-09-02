package com.qlj.lakinqiandemo.video;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/15.
 */

public class VideoPlayActivity extends BaseActivity {
    private static final String url1 = "http://mp4.vjshi.com/2016-12-22/e54d476ad49891bd1adda49280a20692.mp4";
    private static final String url2 = "http://mp4.vjshi.com/2017-08-16/af83af63d018816474067b51a835f4a2.mp4";
    private static final String url3 = "http://mp4.vjshi.com/2017-10-19/53bfeb9eb92c1748596eaf2a1e649020.mp4";
    private static final String url4 = "http://mp4.vjshi.com/2016-10-23/a0511ea830bb0620f94a5340a1879800.mp4";
    private static final String url5 = "http://mp4.vjshi.com/2016-10-21/84bafe60ef0af95a5292f66b9f692504.mp4";
    private static final String url6 = "http://mp4.vjshi.com/2018-01-09/40f3f1edc1cc4aacad958f3e8acbf4ce.mp4";
    private static final String url7 = "http://mp4.vjshi.com/2016-12-22/3ccab5a78036fa933c8585f4a1e57a44.mp4";
    private static final String url8 = "http://mp4.vjshi.com/2016-04-05/add12db77c7c5cd6dfef4c1955b36a80.mp4";
    String format = "mp4";

    private VideoPlayer mVideoPlayer;
    private VideoPlayerHelper mVideoPlayerHelper;
    private boolean mActivityPaused;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initViews();
        setData();
    }

    private void setData() {
        mVideoPlayerHelper = new VideoPlayerHelper(this, mVideoPlayer);
        List<String> urls = getUrls();
        mVideoPlayerHelper.playUrls(urls, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mActivityPaused) {
            mVideoPlayerHelper.initPlayer();
            mVideoPlayerHelper.playAgain(true);
            mActivityPaused = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityPaused = true;
        if (mVideoPlayerHelper.getExoPlayer() != null) {
            mVideoPlayerHelper.setVideoStartPos(mVideoPlayerHelper.getCurrentVideoPos());
            mVideoPlayerHelper.destroyPlayer();
        }
    }

    private List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        urls.add(url1);
        urls.add(url2);
        urls.add(url3);
        urls.add(url4);
        urls.add(url5);
        urls.add(url6);
        urls.add(url7);
        urls.add(url8);
        return urls;
    }

    private void initViews() {
        mVideoPlayer = findViewById(R.id.video_player);
    }

    @Override
    protected void onDestroy() {
        mVideoPlayerHelper.destroy();
        mVideoPlayerHelper = null;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//避免再次切换屏幕
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mVideoPlayerHelper.close()) return;
        super.onBackPressed();
    }
}
