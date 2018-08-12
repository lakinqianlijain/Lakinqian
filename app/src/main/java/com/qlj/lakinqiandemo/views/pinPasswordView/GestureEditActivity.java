package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.GESTURE_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinEditActivity.INPUT_TYPE;

/**
 * Created by Administrator on 2018/8/12.
 */

public class GestureEditActivity extends BaseActivity {
    private String mType;
    private GestureContentViewDot mGestureContentViewDot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_edit);
        mType = getIntent().getStringExtra(INPUT_TYPE);
        findViews();
        initListeners();
    }

    private void initListeners() {
        mGestureContentViewDot.setPasswordCallback(new PasswordCallback() {
            @Override
            public void setPinSuccess(String password) {
                SharedPreferenceUtil.saveString(GestureEditActivity.this, DEMO_CONFIG, GESTURE_PASSWORD, password);
                Toast.makeText(GestureEditActivity.this, "set pin success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void setPinFailed() {

            }

            @Override
            public void onCheckSuccess() {
                Toast.makeText(GestureEditActivity.this, "check pin success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCheckFail() {
                Toast.makeText(GestureEditActivity.this, "pin not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangePin(String password) {

            }
        });
    }

    private void findViews() {
        mGestureContentViewDot = findViewById(R.id.pin_edit_view_dot);
        mGestureContentViewDot.init(this, mType);
        findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
