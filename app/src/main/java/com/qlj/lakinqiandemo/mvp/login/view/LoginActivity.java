package com.qlj.lakinqiandemo.mvp.login.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.mvp.login.data.User;
import com.qlj.lakinqiandemo.mvp.login.presenter.UserLoginPresenter;

/**
 * Created by Administrator on 2018/6/30.
 */

/**
 * 所有的UI更新都将在这一层完成，而且UI更新都市通过ILoginView这个借口里面实现的。其他层对象不会持有里面的view去设置view的状态。
 * 但是该层不会有任何的逻辑判断，这样就是view层显得简洁，所有的更新view的逻辑是在Presenter里面实现的，Presenter会持有一个ILoginView对象，
 * 这样，当逻辑处理完后，通过持有的ILoginView对象，去调用在View实现的方法来更新UI，这样做到View层和Model层没有任何交互。
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    private EditText userEdit, passWordEdit;
    private Button loginBnt, clearBnt;
    private ProgressBar progressBar;
    private UserLoginPresenter userLoginPresenter;
    private TextView successText;
    private View login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        userLoginPresenter = new UserLoginPresenter(this, this);
    }


    private void initView() {
        login = findViewById(R.id.id_rl_login);
        userEdit = findViewById(R.id.id_et_username);
        passWordEdit = findViewById(R.id.id_et_password);
        loginBnt = findViewById(R.id.id_btn_login);
        clearBnt = findViewById(R.id.id_btn_clear);
        progressBar = findViewById(R.id.id_pb_loading);
        successText = findViewById(R.id.id_tv_login_success);
        loginBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginPresenter.start();
            }
        });
        clearBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginPresenter.clear();
            }
        });
    }

    @Override
    public void startLogin() {
        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
    }

    @Override
    public void hideLogin() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginSuccess(User user) {
        successText.setVisibility(View.VISIBLE);
        Toast.makeText(this, user.getUserName() +
                " login success ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        login.setVisibility(View.VISIBLE);
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearUserName() {
        userEdit.setText("");
    }

    @Override
    public void clearPassWord() {
        passWordEdit.setText("");
    }

    @Override
    public String getUserName() {
        return userEdit.getText().toString();
    }

    @Override
    public String getPassWord() {
        return passWordEdit.getText().toString();
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
