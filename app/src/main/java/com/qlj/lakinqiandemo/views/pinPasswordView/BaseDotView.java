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

    public static final long RETENTION_TIME = 500;//轨迹线的驻留时间
    private String mType;

    public BaseDotView(Context context) {
        super(context);
    }

    public BaseDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, String type){
        initView(context);
        mType = type;
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
