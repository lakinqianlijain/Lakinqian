package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.graphics.Paint;

/**
 * Created by lakinqian on 2018/8/10.
 */

public class DrawLineInfoBean {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public Paint mPaint;

    public DrawLineInfoBean(float startX, float startY, float endX, float endY, Paint mPaint){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.mPaint = mPaint;
    }
}
