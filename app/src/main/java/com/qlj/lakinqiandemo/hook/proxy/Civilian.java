package com.qlj.lakinqiandemo.hook.proxy;

import android.util.Log;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by lakinqian on 2018/3/20.
 */

public class Civilian implements ILawsuit {
    @Override
    public void submit() {
        Log.d(TAG, "起诉" );
    }

    @Override
    public void burden() {
        Log.d(TAG, "举证" );
    }

    @Override
    public void defend() {
        Log.d(TAG, "辩护" );
    }

    @Override
    public void finish() {
        Log.d(TAG, "胜诉" );
    }
}
