package com.qlj.lakinqiandemo.optimize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

/**
 * Created by lakinqian on 2018/11/21.
 */

public class MemoryOptimizeActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_optimize);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.tv_image_load).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_image_load:
                JumpActivityUtil.JumpSelfActivity(this, ImageLoadActivity.class);
                break;
        }
    }
}
