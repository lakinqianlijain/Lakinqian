package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.GESTURE_PASSWORD;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.ENTER_PIN;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.SET_PIN;

/**
 * Created by Administrator on 2018/8/12.
 */

public class LockActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mPin, mGesture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initView();
    }

    private void initView() {
        mPin = findViewById(R.id.set_pin);
        mPin.setOnClickListener(this);
        mGesture = findViewById(R.id.set_gesture);
        mGesture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_pin:
                String pin = SharedPreferenceUtil.readString(this, DEMO_CONFIG, PIN_PASSWORD, "");
                if (TextUtils.isEmpty(pin)){
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, SET_PIN);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, ENTER_PIN);
                }
                break;
            case R.id.set_gesture:
                String gesture = SharedPreferenceUtil.readString(this, DEMO_CONFIG, GESTURE_PASSWORD, "");
                if (TextUtils.isEmpty(gesture)){
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, SET_PIN);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, ENTER_PIN);
                }
                break;
        }
    }
}
