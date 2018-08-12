package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by lakinqian on 2018/7/25.
 */

public class PinPoint extends BasePoint{

    public PinPoint(Context context, int leftX, int rightX, int topY, int bottomY, View view, int num) {
        super(context, leftX, rightX, topY, bottomY, view, num);
    }

    @Override
    void setStateWrong() {

    }

    @Override
    void setStateSelected() {

    }
}
