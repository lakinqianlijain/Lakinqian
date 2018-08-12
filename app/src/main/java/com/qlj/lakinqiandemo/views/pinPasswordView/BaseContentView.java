package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/10.
 */

/**
 * 密码容器类
 */
public abstract class BaseContentView extends ViewGroup {

    /**
     * 声明一个集合用来封装坐标集合
     */
    public List<BasePoint> listDigital, listBackGround;
    public Context context;
    public BaseDrawView drawView;
    public String passWord;
    public String mType;
    public BaseDrawView.IPassWordCallBack callback;
    public int blockWidth;
    public int blockHigh;

    public int spacingHorizontal, spacingVertical;
    public int backgroudWidth;
    public int backgroudSpaceHorizontal, backgroudSpaceVertical;


    /**
     * ImageView的容器，初始化
     *
     * @param context
     * @param type 进入页面类型 设置密码或者验证密码
     * @param passWord 用户传入的密码
     * @param callBack 密码输入完毕的回调
     */
    public BaseContentView(Context context, String type, String passWord, BaseDrawView.IPassWordCallBack callBack) {
        super(context);
        initData(context, type, passWord, callBack);
        //初始化背景
        initBackGround();
        // 添加内容图标
        addChild();
        // 初始化一个可以画线的view
        drawView = createDrawView();
    }

    private void initData(Context context, String type, String passWord, BaseDrawView.IPassWordCallBack callBack) {
        this.context = context;
        this.mType = type;
        this.passWord = passWord;
        this.callback = callBack;
        this.listBackGround = new ArrayList<>();
        this.listDigital = new ArrayList<>();
        initSize();
    }

    public void setParentView(ViewGroup parent, int width, int height) {
        //得到屏幕的宽度
        LayoutParams layoutParams = new LayoutParams(width, height);
        parent.addView(drawView, layoutParams);
        parent.addView(this, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 保留路径delayTime时间长
     *
     * @param delayTime
     */
    public void clearDrawViewState(long delayTime) {
        drawView.clearDrawViewState(delayTime);
    }

    public void clearDrawViewState(long delayTime, boolean drawErrorPath) {
        drawView.clearDrawViewState(delayTime, drawErrorPath);
    }

    public String getPasswordSb() {
        return drawView.getPassWordSb();
    }

    public void setPasswordSb(String str) {
        drawView.setPassWordSb(new StringBuilder(str));
    }

    public boolean isDrawEnable() {
        return drawView.isDrawEnable();
    }

    public void setDrawEnable(boolean drawEnable) {
        drawView.setDrawEnable(drawEnable);
    }

    public abstract BaseDrawView createDrawView();

    public abstract void initSize();

    public abstract void initBackGround();

    public abstract void addChild();

    public abstract void setParentView(ViewGroup parent);

    public abstract void layoutChildren();
}
