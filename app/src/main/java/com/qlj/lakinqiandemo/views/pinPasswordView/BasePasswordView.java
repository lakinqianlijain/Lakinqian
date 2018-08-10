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

public abstract class BasePasswordView extends FrameLayout {

    public static final long RETENTION_TIME = 500;//轨迹线的驻留时间

    public BasePasswordView(Context context) {
        super(context);
        initView(context);
    }

    public BasePasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BasePasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public abstract void initView(Context context);

    public TextView mTvTip;
    public RelativeLayout mContainerView;
    public SQLiteDatabase database;

    //输入结果的回调
    public PasswordCallback mPasswordCallback;
    public void setPasswordCallback(PasswordCallback mPasswordCallback){
        this.mPasswordCallback=mPasswordCallback;
    }

    public void clearTip(){
        mTvTip.setText("");
    }
}
