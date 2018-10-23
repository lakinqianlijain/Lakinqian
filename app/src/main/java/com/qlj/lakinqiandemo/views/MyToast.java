package com.qlj.lakinqiandemo.views;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2018/10/22.
 */

public class MyToast {
    private Toast mToast;
    private int mResource;
    private Context mContext;
    private int mDuration;

    public MyToast(int resource, Context context, int duration) {
        mToast = new Toast(context);
        mResource = resource;
        mContext = context;
        mDuration = duration;
    }

    public void show() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View toast_layout = inflater.inflate(mResource, null);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(toast_layout);
        mToast.setDuration(mDuration);
        mToast.show();
    }
}
