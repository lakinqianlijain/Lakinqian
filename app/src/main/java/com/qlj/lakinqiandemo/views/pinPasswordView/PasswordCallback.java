package com.qlj.lakinqiandemo.views.pinPasswordView;

public interface PasswordCallback {
    void setPinSuccess(String password);

    void setPinFailed();

    void onCheckSuccess();

    void onCheckFail();

    void onChangePin(String password);
}
