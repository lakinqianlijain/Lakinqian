package com.qlj.lakinqiandemo.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/9/16.
 */

public class EventbusActivity extends BaseActivity implements View.OnClickListener {
    private TextView mEventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        mEventBus = findViewById(R.id.tv_eventbus);
        mEventBus.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_eventbus:
                JumpActivityUtil.JumpSelfActivity(this, EventActivity2.class);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Integer num) {
        if (num != null) {
            //这里拿到事件之后吐司一下
            mEventBus.setText("我也收到了"+num);
            Toast.makeText(this, "我也收到了" + num, Toast.LENGTH_SHORT).show();
        }
    }
}
