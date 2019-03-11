package com.qlj.lakinqiandemo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

/**
 * Created by lakinqian on 2019/3/1.
 */

public abstract class StandardTitleActivity extends BaseActivity {
    private TextView mTitleView;
    private ViewStub mContentPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.standard_title_activity_ly);
        mTitleView = findViewById(R.id.title_view);
        findViewById(R.id.title_back_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleBackPressed();
            }
        });
        mContentPanel = findViewById(R.id.content_panel);
    }

    public abstract void onTitleBackPressed();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mContentPanel.setLayoutResource(layoutResID);
        mContentPanel.inflate();
    }

    public void setTitleText(String text) {
        mTitleView.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
