package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;


/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinContentViewDot extends BaseDotView {
    private ImageView mPin_1, mPin_2, mPin_3, mPin_4;
    //四个数字密码
    List<ImageView> ivList = new ArrayList<>();
    private PinContentView mPinContentView;
    public PasswordCallback mPasswordCallback;
    Handler mHandler = new Handler();

    Runnable pinAgain = new Runnable() {
        public void run() {
            if (mType.equals(MODIFY_PASSWORD)) {
                mTvTip.setText(R.string.text_enter_current_pin);
                clearPinTextView();
            } else {
                mTvTip.setText(R.string.text_enter_pin);
                clearPinTextView();
            }
        }
    };


    public PinContentViewDot(Context context) {
        super(context);

    }

    public PinContentViewDot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinContentViewDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView(Context context) {
        inflate(mContext, R.layout.pin_content_view_dot, this);
        findView();
        initData();
    }

    public void initData() {
        mPassword = SharedPreferenceUtil.readString(mContext, DEMO_CONFIG, PIN_PASSWORD, "");
        if (mType.equals(SET_PASSWORD)) {
            mTvTip.setText(getResources().getString(R.string.text_set_pin_password));
        } else if (mType.equals(MODIFY_PASSWORD)) {
            mTvTip.setText(getResources().getString(R.string.text_enter_current_pin));
        } else {
            mTvTip.setText(getResources().getString(R.string.text_enter_pin));
        }
        if (mPinContentView == null) {
            mPinContentView = new PinContentView(mContext, mType, mPassword, new BaseDrawView.IPassWordCallBack() {
                @Override
                public void onGestureCodeInput(String inputCode) {
                    if (!isInputPassValidate(inputCode)) {
                        mTvTip.setText(getResources().getString(R.string.text_pin_need_4_digits));
                        return;
                    } else {
                        mPinContentView.clearDrawlineState(RETENTION_TIME * 2);
                    }
                }

                @Override
                public void onGestureLineMove() {

                }

                @Override
                public void checkPinSuccess() {
                    if (mType.equals(MODIFY_PASSWORD)) {
                        mTvTip.setText(R.string.text_enter_new_pin);
                        clearCache();
                    } else {
                        mPasswordCallback.onCheckSuccess();
                    }
                }

                @Override
                public void checkPinFail() {
                    inputErrorPin();
                    mTvTip.setText(R.string.text_pin_error);
                    mPinContentView.clearDrawlineState(RETENTION_TIME * 2);
                    mHandler.postDelayed(pinAgain, RETENTION_TIME * 2);
                    mPasswordCallback.onCheckFail();
                }

                @Override
                public void changePin(String password) {
                    if (mIsFirstInput) {
                        mFirstPassword = password;
                        mIsFirstInput = false;
                        mTvTip.setText(R.string.text_confirm_new_pin);
                        clearCache();
                        return;
                    }
                    if (TextUtils.isEmpty(mFirstPassword)) {
                        return;
                    }
                    if (mFirstPassword.equals(password)) {
                        mPasswordCallback.onChangePin(password);
                    } else {
                        mTvTip.setText(R.string.text_pin_not_match);
                        inputErrorPin();
                        mPinContentView.clearDrawlineState(RETENTION_TIME * 2);
                        mHandler.postDelayed(pinAgain, RETENTION_TIME * 2);
                    }
                }

                @Override
                public void setPinSuccess(String password) {
                    if (mIsFirstInput && mType.equals(SET_PASSWORD)) {
                        mFirstPassword = password;
                        mIsFirstInput = false;
                        clearCache();
                        mTvTip.setText(getResources().getString(R.string.text_enter_pin_again));
                    } else {
                        if (!mFirstPassword.equals(password)) {
                            mTvTip.setText(getResources().getString(R.string.text_pin_not_match));
                            clearCache();
                        } else {
                            mPasswordCallback.setPinSuccess(password);
                        }
                    }
                }

                @Override
                public void setPinFailed() {
                    mPasswordCallback.setPinFailed();
                }

                @Override
                public void showNum(int num, int pos) {
                    if (pos < 4) {
                        ivList.get(pos).setImageResource(R.drawable.ic_pin_on);
                    }
                }
            });
            mPinContentView.setParentView(mContainerView);
            initListeners();
        } else {
            clearCache();
        }
    }

    private void initListeners() {
        ImageView delView = (ImageView) mPinContentView.getChildAt(mPinContentView.getChildCount() - 1);
        delView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPinContentView.isDrawEnable()) {
                    return;
                }

                String password = mPinContentView.getPasswordSb();

                if (password.length() > 0) {
                    ivList.get(password.length() - 1).setImageResource(R.drawable.ic_pin_default);
                    if (password.length() - 1 == 0) {
                        mPinContentView.setPasswordSb("");
                    } else {
                        mPinContentView.setPasswordSb(password.substring(0, password.length() - 1));
                    }
                }
            }
        });
    }

    public void setPointVisible() {
        for (ImageView iv : ivList) {
            iv.setVisibility(VISIBLE);
        }
    }


    private void findView() {
        mTvTip = findViewById(R.id.tv_edit_tip);
        mPin_1 = findViewById(R.id.pin_1);
        mPin_2 = findViewById(R.id.pin_2);
        mPin_3 = findViewById(R.id.pin_3);
        mPin_4 = findViewById(R.id.pin_4);
        mContainerView = findViewById(R.id.rl_container_view);
        ivList.add(mPin_1);
        ivList.add(mPin_2);
        ivList.add(mPin_3);
        ivList.add(mPin_4);
        setPointVisible();
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }

    public void setPasswordCallback(PasswordCallback mPasswordCallback) {
        this.mPasswordCallback = mPasswordCallback;
    }

    private void inputErrorPin() {
        for (ImageView iv : ivList) {
            iv.setImageResource(R.drawable.ic_pin_error);
        }
    }

    private void clearPinTextView() {
        for (ImageView iv : ivList) {
            iv.setImageResource(R.drawable.ic_pin_default);
        }
    }

    public void clearCache() {
        clearPinTextView();
        if (mPinContentView != null) {
            mPinContentView.clearDrawlineState(0);
        }
    }
}
