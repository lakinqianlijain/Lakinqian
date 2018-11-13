package com.qlj.lakinqiandemo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2018/11/13.
 */

public class SlideViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);
//        initViews();
    }

    private void initViews() {
        CircleProgressBar circleProgressBar = new CircleProgressBar(this);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.addView(circleProgressBar, params);
        setContentView(frameLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
