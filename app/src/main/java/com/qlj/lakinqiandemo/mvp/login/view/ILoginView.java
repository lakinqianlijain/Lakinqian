package com.qlj.lakinqiandemo.mvp.login.view;

import com.qlj.lakinqiandemo.mvp.BaseView;
import com.qlj.lakinqiandemo.mvp.login.data.User;

/**
 * Created by Administrator on 2018/6/30.
 */

/**
 * 登录界面更新UI的借口堵在这里定义
 */

public interface ILoginView extends BaseView{
    void startLogin();

    void hideLogin();

    void loginSuccess(User user);

    void loginFailed();

    void clearUserName();

    void clearPassWord();

    String getUserName();

    String getPassWord();

}
