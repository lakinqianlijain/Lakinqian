package com.qlj.lakinqiandemo.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class EventActivity2 extends BaseActivity implements View.OnClickListener{
    private TextView mEventBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);
        mEventBack = findViewById(R.id.tv_event_back);
        mEventBack.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.tv_event_back:
//                EventBus.getDefault().post(666);
//                finish();
                JumpActivityUtil.JumpSelfActivity(this, EventActivity3.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Integer num) {
        if (num != null) {
            //这里拿到事件之后吐司一下
            mEventBack.setText(num + "");
            Toast.makeText(this, "num" + num, Toast.LENGTH_SHORT).show();
        }
    }
}
