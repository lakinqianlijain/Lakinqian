package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.GESTURE_PASSWORD;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;

/**
 * Created by lakinqian on 2018/8/10.
 */

public class GestureContentViewDot extends BaseDotView {

    private BaseContentView mGestureContentView;

    Handler mHandler = new Handler();

    public GestureContentViewDot(Context context) {
        super(context);
    }

    public GestureContentViewDot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureContentViewDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView(Context context) {
        inflate(getContext(), R.layout.gesture_container_view, this);
        findView();
        initData();
    }

    private void findView() {
        mTvTip = findViewById(R.id.tv_edit_texttip);
        mContainerView = findViewById(R.id.rl_container_view);
    }

    private void initData() {
        mPassword = SharedPreferenceUtil.readString(mContext, DEMO_CONFIG, GESTURE_PASSWORD, "");
        if (mType.equals(SET_PASSWORD)) {
            mTvTip.setText(getResources().getString(
                    R.string.text_set_gesture_password));
        } else {
            mTvTip.setText(getResources().getString(
                    R.string.text_enter_gesture));
        }

        mGestureContentView = new GestureContentView(getContext(), mType, mPassword,
                new BaseDrawView.IPassWordCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {
                        if (!isInputPassValidate(inputCode)) {
                            // 至少连接4个点
                            mTvTip.setText(getResources().getString(
                                    R.string.draw_gesture_warning));
                            // 2秒后还原提示
                            mHandler.postDelayed(connect4Dot, RETENTION_TIME * 2);
                            return;
                        }
                    }

                    @Override
                    public void onGestureLineMove() {
                        mTvTip.setText(getResources().getString(
                                R.string.release_hande_when_finish));
                    }

                    @Override
                    public void checkPinSuccess() {
                        mPasswordCallback.onCheckSuccess();
                    }

                    @Override
                    public void checkPinFail() {
                        // 2秒后清除界面
                        mGestureContentView.clearDrawViewState(RETENTION_TIME * 2, true);
                        mPasswordCallback.onCheckFail();
                    }

                    @Override
                    public void setPinSuccess(String password) {
                        if (mIsFirstInput && mType.equals(SET_PASSWORD)) {
                            mFirstPassword = password;
                            mIsFirstInput = false;
                            mHandler.postDelayed(clearGreenLine, RETENTION_TIME * 2);
                            mTvTip.setText(getResources().getString(R.string.text_enter_pin_again));
                        } else {
                            if (!mFirstPassword.equals(password)) {
                                mTvTip.setText(getResources().getString(R.string.text_gesture_not_match));
                                mHandler.postDelayed(clearGreenLine, RETENTION_TIME * 2);
                            } else {
                                mPasswordCallback.setPinSuccess(password);
                            }
                        }
                    }

                    @Override
                    public void setPinFailed() {

                    }

                    @Override
                    public void changePin(String password) {

                    }

                    @Override
                    public void showNum(int num, int pos) {

                    }
                });
        mGestureContentView.setParentView(mContainerView);
    }

    private boolean  isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }

    Runnable connect4Dot = new Runnable() {
        public void run() {
            mTvTip.setText(getResources().getString(
                    R.string.set_gesture_pattern));
        }
    };

    Runnable clearGreenLine = new Runnable() {
        public void run() {
            mGestureContentView.clearDrawViewState(10, false);
        }
    };

    Runnable drawagain = new Runnable() {
        public void run() {
            mTvTip.setText(getResources().getString(
                    R.string.drawguestureagain));
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
