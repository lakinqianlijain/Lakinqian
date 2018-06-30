package com.qlj.lakinqiandemo.mvp.login.data;

/**
 * Created by Administrator on 2018/6/30.
 */

/**
 * 该接口是用来处理数据的，所有的数据处理的相关方法在这里定义，我这里只是做简单的check一下数据，
 * 正常该部分可以是去获取缓存，本地数据库，网络数据等，同时，不同的操作还可以定义其回调
 */

public interface IUserBiz {
    void checkData(String userName, String passWord, ILoginListener iLoginListener);

    interface ILoginListener {
        void loginSuccess(User user);

        void loginFailed();
    }

}
