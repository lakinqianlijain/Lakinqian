package com.qlj.lakinqiandemo.views.canvas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

public class CanvasActivity extends BaseActivity implements View.OnClickListener {
    private CanvasHelper mCanvasHelper = CanvasHelper.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.tv_canvas1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_canvas1:
                CanvasUtil.drawRect(mCanvasHelper.getNormalPaint(), mCanvasHelper.getNormalCanvas());
                break;
        }
    }
}
