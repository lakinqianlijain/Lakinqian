package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinContentView extends BaseContentView {

    public PinContentView(Context context, String type, String passWord, BaseDrawView.IPassWordCallBack callBack) {
        super(context, type, passWord, callBack);
    }

    @Override
    public void initSize() {
        blockWidth = DensityUtil.getScreenDispaly(context)[0] / 3;
        blockHigh = DensityUtil.dip2px(context, 70);
    }


    @Override
    public void initBackGround() {

    }

    @Override
    public void addChild() {
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

    @Override
    public BaseDrawView createDrawView() {
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
            BasePoint pinPoint = listDigital.get(i);
            int leftX = pinPoint.getLeftX();
            int topY = pinPoint.getTopY();
            int rightX = pinPoint.getRightX();
            int bottomY = pinPoint.getBottomY();
            v.layout(leftX, topY, rightX, bottomY);
        }
    }
}
