package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;
import com.qlj.lakinqiandemo.views.MyToast;
import com.qlj.lakinqiandemo.views.ScrollingHintView;

import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.GESTURE_PASSWORD;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.ENTER_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.SET_PASSWORD;

/**
 * Created by Administrator on 2018/8/12.
 */

public class CustomizeViewActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mPin, mGesture;
    private TextView mToast;
    private ScrollingHintView mScrollingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_view);
        initView();
        initData();
    }

    private void initView() {
        mPin = findViewById(R.id.set_pin);
        mPin.setOnClickListener(this);
        mGesture = findViewById(R.id.set_gesture);
        mGesture.setOnClickListener(this);
        mToast = findViewById(R.id.tv_my_toast);
        mToast.setOnClickListener(this);
        mScrollingView = findViewById(R.id.shv_notice_tips);
    }

    private void initData() {
        mScrollingView.startWithList(getStringList());
    }

    private List<CharSequence> getStringList() {
        List<CharSequence> list = new ArrayList<>();
        SpannableString ss1 = new SpannableString("Dave got a football and a book，and a bag.");
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss1);
        SpannableString ss2 = new SpannableString("Dave got a football and a book，and a bag.");
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss2);
        SpannableString ss3 = new SpannableString("Dave got a football and a book，and a bag.");
        ss3.setSpan(new URLSpan("http://sunfusheng.com/"), 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss3);
        list.add("Dave got a football and a book，and a bag.");
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_pin:
                String pin = SharedPreferenceUtil.readString(this, DEMO_CONFIG, PIN_PASSWORD, "");
                if (TextUtils.isEmpty(pin)) {
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, SET_PASSWORD);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, ENTER_PASSWORD);
                }
                break;
            case R.id.set_gesture:
                String gesture = SharedPreferenceUtil.readString(this, DEMO_CONFIG, GESTURE_PASSWORD, "");
                if (TextUtils.isEmpty(gesture)) {
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, SET_PASSWORD);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, ENTER_PASSWORD);
                }
                break;
            case R.id.tv_my_toast:
                MyToast myToast = new MyToast(R.layout.toast_layout, this, Toast.LENGTH_SHORT);
                myToast.show();
                break;
        }
    }
}
