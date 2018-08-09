package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinContentView extends ViewGroup {

    /**
     * 声明一个集合用来封装坐标集合
     */
    public List<PinPoint> listDigital;
    public Context context;
    public PinDrawView drawView;
    public String passWord;
    private String mType;
    public PinDrawView.IPassWordCallBack callback;

    private int blockWidth;
    private int blockHigh;

    public PinContentView(Context context, String type, String passWord, PinDrawView.IPassWordCallBack callBack) {
        super(context);
        initData(context, type, passWord, callBack);
        addChild();
        // 初始化一个点击的view
        drawView = createDrawLineView();
    }

    private void initData(Context context, String type, String passWord, PinDrawView.IPassWordCallBack callBack) {
        this.context = context;
        this.mType = type;
        this.passWord = passWord;
        this.callback = callBack;
        this.listDigital = new ArrayList<>();
        initSize();
    }

    private void initSize() {
        blockWidth = DensityUtil.getScreenDispaly(context)[0] / 3;
        blockHigh = DensityUtil.dip2px(context, 70);
    }

    private void addChild() {
        TextView text = null;
        ImageView image = null;
        for (int i = 0; i < 12; i++) {
            if (i != 9) {
                if (i < 11) {
                    text = new TextView(context);
                    text.setText((i + 1) % 11 + "");
                    text.setTextColor(getResources().getColor(R.color.colorBlack));
                    text.getPaint().setTextSize(getResources().getDimension(R.dimen.pinNumberSize));
                    text.setGravity(Gravity.CENTER);
                    this.addView(text, new LayoutParams(blockWidth, blockHigh));
                } else {
                    //添加删除键"X"
                    image = new ImageView(context);
                    image.setImageResource(R.drawable.btn_settings_backspace);
                    image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    this.addView(image, new LayoutParams(blockWidth, blockHigh));
                }
            }
            // 第几行
            int row = i / 3;
            // 第几列
            int col = i % 3;
            // 定义点的每个属性
            int leftX = col * blockWidth;
            int topY = row * blockHigh;
            int rightX = leftX + blockWidth;
            int bottomY = topY + blockHigh;

            PinPoint p;
            //圆圈之间线的位置
            if (i != 9) {
                if (i < 11) {
                    p = new PinPoint(context, leftX, rightX, topY, bottomY, text, (i + 1) % 11);
                    this.listDigital.add(p);
                } else {
                    p = new PinPoint(context, leftX, rightX, topY, bottomY, image, -1);
                    this.listDigital.add(p);
                }
            }
        }
    }

    public PinDrawView createDrawLineView() {
        return new PinDrawView(context, listDigital, mType, passWord, callback);
    }

    public void setParentView(ViewGroup parent) {
        //得到屏幕的宽度
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        parent.addView(drawView, layoutParams);
        parent.addView(this, layoutParams);
    }

    public void layoutChildren() {
        int k = 0;
        for (int i = 0; i < listDigital.size(); i++) {
            View v = getChildAt(k++);
            PinPoint pinPoint = listDigital.get(i);
            int leftX = pinPoint.getLeftX();
            int topY = pinPoint.getTopY();
            int rightX = pinPoint.getRightX();
            int bottomY = pinPoint.getBottomY();
            v.layout(leftX, topY, rightX, bottomY);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    public void clearDrawlineState(long delayTime) {
        drawView.clearDrawlineState(delayTime);
    }

    public boolean isDrawEnable() {
        return drawView.isDrawEnable();
    }

    public void setDrawEnable(boolean drawEnable) {
        drawView.setDrawEnable(drawEnable);
    }

    public String getPasswordSb() {
        return drawView.getPassWordSb();
    }

    public void setPasswordSb(String str) {
        drawView.setPassWordSb(new StringBuilder(str));
    }
}
