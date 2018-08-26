package com.qlj.lakinqiandemo.video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2018/8/15.
 */

public class VideoPlayActivity extends BaseActivity {
//    String url = "https://r2---sn-i3beln7r.googlevideo.com/videoplayback?signature=919D39D826CAABE9F81B3A30F26C8D19B9F4A2E6.AFA6D2C882A3FE2718DF95F349FDF593164D8974&dur=1122.986&fvip=6&initcwndbps=853750&ei=a-BzW7CcMsnJqAHb-ZGYDQ&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&lmt=1516423817211498&gir=yes&expire=1534342347&mt=1534320650&mv=m&id=o-ABqDjsmd0bnedmwaIYP_dEndeX-9FDw3ALxaqRG8lCEz&ms=au%2Crdu&mm=31%2C29&source=youtube&pl=24&mn=sn-i3beln7r%2Csn-i3b7kn7k&mime=video%2Fmp4&ip=14.136.152.77&requiressl=yes&c=WEB&itag=18&clen=57382464&ratebypass=yes&ipbits=0&key=yt6";
    String url = "http://mp4.vjshi.com/2016-12-22/e54d476ad49891bd1adda49280a20692.mp4";
    String format = "mp4";

    private VideoPlayer mVideoPlayer;
    private VideoPlayerHelper mVideoPlayerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initViews();
        setData();
    }

    private void setData() {
        mVideoPlayerHelper = new VideoPlayerHelper(this, mVideoPlayer);
        mVideoPlayerHelper.playUrl(url, format, true);
    }

    private void initViews() {
        mVideoPlayer = findViewById(R.id.video_player);
    }
}
