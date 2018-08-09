package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import java.util.List;

import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.MODIFY_PIN;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.SET_PIN;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinDrawView extends View {

    public int mov_x;// 声明起点坐标
    public int mov_y;

    public List<PinPoint> list;

    private boolean isDrawEnable = true; // 是否允许绘制
    public PinPoint currentPoint;
    public IPassWordCallBack callBack;
    public StringBuilder passWordSb;
    public String mType;
    public String passWord;
    public final int DIGITAL_PASSWORD_LEN = 4;
    private boolean mIsConfirmPin = true;


    public PinDrawView(Context context, List<PinPoint> list, String type, String passWord, IPassWordCallBack callBack) {
        super(context);
        this.list = list;
        this.callBack = callBack;
        this.mType = type;
        this.passWord = passWord;
        //密码集
        this.passWordSb = new StringBuilder();
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
                if (mType.equals(SET_PIN)) {
                    callBack.setPinSuccess(passWordSb.toString());
                } else if (mType.equals(MODIFY_PIN)) {
                    if (mIsConfirmPin){
                        if (passWord.equals(passWordSb.toString())){
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * @param x x轴坐标值
     * @param y y轴坐标值
     */
    public PinPoint getPointAt(int x, int y) {

        for (PinPoint point : list) {
            // 先判断x
            int leftX = point.getLeftX();
            int rightX = point.getRightX();
            if (!(x >= leftX && x < rightX)) {
                continue;
            }
            int topY = point.getTopY();
            int bottomY = point.getBottomY();
            if (!(y >= topY && y < bottomY)) {
                continue;
            }
            // 如果执行到这，那么说明当前点击的点的位置在point
            return point;
        }
        return null;
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

    /**
     * @param delayTime 延迟执行时间
     */
    public void clearDrawlineState(long delayTime) {
        if (delayTime > 0) {
            isDrawEnable = false;
        }
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
    }

    public boolean isDrawEnable() {
        return isDrawEnable;
    }

    public void setDrawEnable(boolean drawEnable) {
        isDrawEnable = drawEnable;
    }

    public String getPassWordSb() {
        return passWordSb.toString();
    }

    public void setPassWordSb(StringBuilder passWordSb) {
        this.passWordSb = passWordSb;
    }

    final class clearStateRunnable implements Runnable {
        public void run() {
            // 重置passWordSb
            passWordSb = new StringBuilder();
            isDrawEnable = true;
        }
    }

    public interface IPassWordCallBack {
        void onGestureCodeInput(String inputCode);

        void checkPinSuccess();

        void checkPinFail();

        void setPinSuccess(String password);

        void setPinFailed();

        void changePin(String password);

        void showNum(int num, int pos);
    }
}
