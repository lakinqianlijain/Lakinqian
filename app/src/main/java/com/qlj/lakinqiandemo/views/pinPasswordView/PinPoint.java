package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinPoint {
    public static final int POINT_STATE_NORMAL = 0; // 正常状态
    public static final int POINT_STATE_SELECTED = 1; // 按下状态
    public static final int POINT_STATE_WRONG = 2; // 错误状态

    public Context context;
    /**
     * 左边x的值
     */
    private int leftX;
    /**
     * 右边x的值
     */
    private int rightX;
    /**
     * 上边y的值
     */
    private int topY;
    /**
     * 下边y的值
     */
    private int bottomY;
    /**
     * 这个点对应的View控件
     */
    private View view;

    /**
     * 状态值
     */
    private int pointState;

    /**
     * 中心x值
     */
    private int centerX;

    /**
     * 中心y值
     */
    private int centerY;

    /**
     * 代表这个Point对象代表的数字，从1开始(直接感觉从1开始)
     */
    private int num;

    private Animation mAnimation;

    public PinPoint (Context context, int leftX, int rightX, int topY, int bottomY, View view, int num){
        this.context = context;
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
        this.view = view;
        this.num = num;
        this.centerX = (leftX + rightX) / 2;
        this.centerY = (topY + bottomY) / 2;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public int getBottomY() {
        return bottomY;
    }

    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getPointState() {
        return pointState;
    }

    public void setPointState(int pointState) {
        this.pointState = pointState;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

//    public void setPointState(int state, int x, int y) {
//
//        pointState = state;
//        switch (state) {
//            case POINT_STATE_NORMAL:
//                setStateNormal();
//                break;
//            case POINT_STATE_SELECTED:
//                setStateSelected();
//                break;
//            case POINT_STATE_WRONG:
//                setStateWrong();
//                break;
//            default:
//                break;
//        }
//    }

    void setStateWrong() {

    }

//    void setStateSelected() {
//        mAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_digital_press);
//        getView().startAnimation(mAnimation);
//        mAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                getView().setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                getView().setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//    }

    void setStateNormal() {
        getView().clearAnimation();
    }
}
