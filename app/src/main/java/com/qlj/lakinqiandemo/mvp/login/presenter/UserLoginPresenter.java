package com.qlj.lakinqiandemo.mvp.login.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.qlj.lakinqiandemo.mvp.BasePresenter;
import com.qlj.lakinqiandemo.mvp.login.data.IUserBiz;
import com.qlj.lakinqiandemo.mvp.login.data.User;
import com.qlj.lakinqiandemo.mvp.login.data.UserBiz;
import com.qlj.lakinqiandemo.mvp.login.view.ILoginView;


/**
 * Created by Administrator on 2018/6/30.
 */

/**
 *该层即为P层，处理所有的逻辑，起到沟通View和Model层的作用，它会持有ILoginView和UserBiz对象，当最开始需要绑定数据时，
 * View层持有一个UserLoginPresenter对象，通过start方法，间接的通过UserBiz来加载数据，绑定数据过程中产生回调，
 * 再通过持有的ILoginView对象来更新UI,处理逻辑后产生的UI更新，也是通过ILoginView来更新的。总之，view层所有的数据获取，通过UserLoginPresenter对象的
 * UserBiz完成，View层所有的UI更新，通过UserLoginPresenter持有的ILoginView完成
 */

public class UserLoginPresenter implements BasePresenter {
    private ILoginView iLoginView;
    private Context mContext;
    private UserBiz userBiz;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public UserLoginPresenter(ILoginView loginView, Context context) {
        this.iLoginView = loginView;
        this.mContext = context;
        userBiz = new UserBiz();
    }

    @Override
    public void start() {
        iLoginView.startLogin();
        String userName = iLoginView.getUserName();
        String passWord = iLoginView.getPassWord();
        userBiz.checkData(userName, passWord, new IUserBiz.ILoginListener() {
            @Override
            public void loginSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.hideLogin();
                        iLoginView.loginSuccess(user);
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.hideLogin();
                        iLoginView.loginFailed();
                    }
                });
            }
        });

    }

    public void clear() {
        iLoginView.clearUserName();
        iLoginView.clearPassWord();
    }
}
