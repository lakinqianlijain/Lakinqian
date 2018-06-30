package com.qlj.lakinqiandemo.mvp.login.data;

import android.util.Log;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by Administrator on 2018/6/30.
 */

public class UserBiz implements IUserBiz {
    @Override
    public void checkData(final String userName, final String passWord, final ILoginListener loginListener) {
        // 模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ( "lakinqian".equals(userName) && "123".equals(passWord)) {
                    User user = new User();
                    user.setUserName(userName);
                    user.setPassWord(passWord);
                    loginListener.loginSuccess(user);
                    Log.e(TAG, "登录成功");
                } else {
                    loginListener.loginFailed();
                    Log.e(TAG, "登录失败，请验证账号密码");
                }
            }
        }.start();
    }
}
