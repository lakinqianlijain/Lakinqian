package com.qlj.lakinqiandemo.views.pinPasswordView;

/**
 * Created by dejun.xie on 2017/3/7.
 */

public interface PasswordCallback {
    void setPinSuccess(String password);

    void setPinFailed();

    void onCheckSuccess();

    void onCheckFail();

    void onChangePin(String password);

    void findPassword();
}
