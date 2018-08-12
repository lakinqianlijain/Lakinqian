package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lakinqian on 2018/8/10.
 */

public abstract class BaseDotView extends FrameLayout {
    public static final String SET_PASSWORD = "set_password";
    public static final String MODIFY_PASSWORD = "modify_password";
    public static final String ENTER_PASSWORD = "enter_password";

    public static final long RETENTION_TIME = 500;//轨迹线的驻留时间
    protected Context mContext;
    protected String mType;
    public TextView mTvTip;
    public RelativeLayout mContainerView;
    protected String mPassword;

    // 第一次输入的密码
    protected String mFirstPassword = null;
    // 是不是第一次输入
    protected boolean mIsFirstInput = true;

    public PasswordCallback mPasswordCallback;

    public BaseDotView(Context context) {
        super(context);
    }

    public BaseDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, String type) {
        mContext = context;
        mType = type;
        initView(context);
    }

    public abstract void initView(Context context);

    public void setPasswordCallback(PasswordCallback mPasswordCallback) {
        this.mPasswordCallback = mPasswordCallback;
    }

    public void clearTip() {
        mTvTip.setText("");
    }
}
