package com.qlj.lakinqiandemo.video.MediaEdit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

public class MediaEditActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_edit);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.bt_video_edit).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_video_edit:
                JumpActivityUtil.JumpSelfActivity(this, VideoEditActivity.class);
                break;
        }
    }
}
