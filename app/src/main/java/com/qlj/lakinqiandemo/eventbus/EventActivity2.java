package com.qlj.lakinqiandemo.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/9/16.
 */

public class EventActivity2 extends BaseActivity implements View.OnClickListener{
    private TextView mEventBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);
        mEventBack = findViewById(R.id.tv_event_back);
        mEventBack.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_event_back:
                EventBus.getDefault().post(666);
                finish();
                break;
        }
    }
}
