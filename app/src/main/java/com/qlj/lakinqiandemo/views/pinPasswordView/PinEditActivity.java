package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;

/**
 * Created by lakinqian on 2018/7/26.
 */

public class PinEditActivity extends BaseActivity {

    public static final int RESULT_CODE_PRIVATE = 202;

    private PinContentViewDot mPinContentView;
    private String mType;
    public static final String INPUT_TYPE = "input_type";

    public static final String FIND_PASSWORD = "find_password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_edit);
        mType = getIntent().getStringExtra(INPUT_TYPE);
        findViews();
        initListeners();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPinContentView.initData();
    }

    private void findViews() {
        mPinContentView = findViewById(R.id.pin_edit_view_dot);
        mPinContentView.init(this, mType);
        findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListeners() {
        mPinContentView.setPasswordCallback(new PasswordCallback() {
            @Override
            public void setPinSuccess(String password) {
                SharedPreferenceUtil.saveString(PinEditActivity.this, DEMO_CONFIG, PIN_PASSWORD, password);
                Toast.makeText(PinEditActivity.this, "set pin success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void setPinFailed() {

            }

            @Override
            public void onCheckSuccess() {
                Toast.makeText(PinEditActivity.this, "check pin success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCheckFail() {
                Toast.makeText(PinEditActivity.this, "pin not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangePin(String password) {

            }
        });
    }
}
