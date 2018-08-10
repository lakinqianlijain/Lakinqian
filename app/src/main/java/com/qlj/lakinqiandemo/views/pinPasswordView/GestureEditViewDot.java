package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2018/8/10.
 */

public class GestureEditViewDot extends BasePasswordView {

    private BaseContentView mGestureContentView;

    // 第一次输入的密码
    private String mFirstPassword = null;
    // 是不是第一次输入
    private boolean mIsFirstInput = true;

    Handler mHandler = new Handler();

    public GestureEditViewDot(Context context) {
        super(context);
    }

    public GestureEditViewDot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureEditViewDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView(Context context){
        inflate(getContext(), R.layout.gesture_container_view,this);
        findViews();
        initDatas();
        mGestureContentView = new GestureEditContentView(getContext(), false, "",
                new BaseDrawline.DrawlineCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                        if (mIsFirstInput) { // 第一次输入手势密码
                            if (!isInputPassValidate(inputCode)) {
                                // 至少连接4个点
                                mTvTip.setText(getResources().getString(
                                        R.string.draw_gesture_warning));
                                // 2秒后清除界面
                                mGestureContentView.clearDrawlineState(RETENTION_TIME*2,true);
                                // 2秒后还原提示
                                mHandler.postDelayed(connect4Dot, RETENTION_TIME*2);
                                return;
                            }else {
                                mFirstPassword = inputCode;
                                mTvTip.setText(getResources().getString(
                                        R.string.gesture_record));
                                mHandler.postDelayed(clearGreenLine, RETENTION_TIME*2);
                                mHandler.postDelayed(drawagain,RETENTION_TIME*2);
                                mIsFirstInput = false;
                            }
                        } else {
                            if (inputCode.equals(mFirstPassword)) {
                                // 第二次输入手势密码与第一次一致
                                mTvTip.setText(getResources().getString(
                                        R.string.your_gesture_code));

//                                boolean count = mPasswordSQLiteOpenHelper.isPasswordExist(database);
//                                String compoundPassword = mFirstPassword + SpUtils.getInitialTime(getContext());
//                                if (count) {
//                                    //如果有记录
//                                    mPasswordSQLiteOpenHelper.updatePassword(database,MD5EncodeUtil.MD5Ecode(compoundPassword), Constants.ERROR_TIMES);
//                                }else {
//                                    //如果没有记录
//                                    mPasswordSQLiteOpenHelper.insertPassword(database,MD5EncodeUtil.MD5Ecode(compoundPassword), Constants.ERROR_TIMES);
//                                }

                                mPasswordCallback.setPinSuccess("");
                            } else {

                                if (!isInputPassValidate(inputCode)) {
                                    // 至少连接4个点
                                    mTvTip.setText(getResources().getString(
                                            R.string.draw_gesture_warning));
                                    // 1秒后清除界面
                                    mGestureContentView.clearDrawlineState(RETENTION_TIME*2,true);
                                    // 1秒后还原提示
                                    mHandler.postDelayed(connect4Dot, RETENTION_TIME*2);
                                } else { // 第二次输入手势密码与第一次不一致
                                    mTvTip.setText(getResources()
                                            .getString(R.string.draw_again));
                                    // 保持绘制的线，1秒后清除
                                    mGestureContentView
                                            .clearDrawlineState(RETENTION_TIME*2,true);
                                    mHandler.postDelayed(drawagain, RETENTION_TIME*2);
                                }

                                mPasswordCallback.setPinFailed();
                            }
                        }
                    }

                    @Override
                    public void checkedFail() {
                        mPasswordCallback.setPinFailed();
                    }

                    @Override
                    public void onGestureLineMove() {
                        mTvTip.setText(getResources().getString(
                                R.string.release_hande_when_finish));
                    }

                    @Override
                    public void onPointDown() {
                        mTvTip.setText(getResources().getString(
                                R.string.release_hande_when_finish));
                    }

                    @Override
                    public void showNum(int num, int pos) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mPasswordCallback.setPinSuccess("");
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mContainerView);
    }

    private void findViews() {
        mTvTip = (TextView) findViewById(R.id.tv_edit_texttip);
        mContainerView = (RelativeLayout) findViewById(R.id.rl_container_view);
    }

    private void initDatas() {

//        mPasswordSQLiteOpenHelper=GestureSQLiteOpenHelper.getInstance(getContext());
//        database = mPasswordSQLiteOpenHelper.getWritableDatabase();
        mTvTip.setTextColor(getResources().getColor(R.color.text_back_70));
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }

    Runnable connect4Dot = new Runnable() {
        public void run() {
            mTvTip.setText(getResources().getString(
                    R.string.set_gesture_pattern));
        }
    };

    Runnable clearGreenLine = new Runnable() {
        public void run() {
            mGestureContentView.clearDrawlineState(10,false);
        }
    };

    Runnable drawagain = new Runnable() {
        public void run() {
            mTvTip.setText(getResources().getString(
                    R.string.drawguestureagain));
        }
    };

    Runnable changeText2again = new Runnable() {
        public void run() {
            mTvTip.setText(getResources().getString(
                    R.string.drawguestureagain));
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
