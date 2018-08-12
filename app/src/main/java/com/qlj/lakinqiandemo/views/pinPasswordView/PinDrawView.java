package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewParent;

import java.util.List;

import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.MODIFY_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.SET_PASSWORD;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinDrawView extends BaseDrawView {
    private boolean mIsConfirmPin = true;

    public PinDrawView(Context context, List<BasePoint> list, String type, String passWord, BaseDrawView.IPassWordCallBack callBack) {
        super(context, list, type, passWord, callBack);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewParent viewParent = getParent();
        if (event.getAction() == MotionEvent.ACTION_DOWN && viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(true);
        }
        if (!isDrawEnable) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mov_x = (int) event.getX();
                mov_y = (int) event.getY();
                /**获取按下的点位*/
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    /**设置按下的点位的状态，保存密码*/
                    setSelectedState();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (passWordSb.length() < DIGITAL_PASSWORD_LEN) {
                    callBack.onGestureCodeInput(passWordSb.toString());
                    return true;
                }
                if (type.equals(SET_PASSWORD)) {
                    callBack.setPinSuccess(passWordSb.toString());
                } else if (type.equals(MODIFY_PASSWORD)) {
                    if (mIsConfirmPin) {
                        if (passWord.equals(passWordSb.toString())) {
                            callBack.checkPinSuccess();
                            mIsConfirmPin = false;
                        } else {
                            callBack.checkPinFail();
                        }
                    } else {
                        callBack.changePin(passWordSb.toString());
                    }
                } else {
                    if (TextUtils.isEmpty(passWord)) {
                        return true;
                    }
                    String encodeString = passWordSb.toString();
                    if (passWord.equals(encodeString)) {
                        callBack.checkPinSuccess();
                    } else {
                        callBack.checkPinFail();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void setSelectedState() {
        if (currentPoint.getNum() != -1) {
            addNum(currentPoint.getNum());
        }
    }

    public void addNum(int num) {
        passWordSb.append(num);
        callBack.showNum(num, passWordSb.length() - 1);
    }
}
