package com.qlj.lakinqiandemo;

import android.app.Application;
import android.content.Context;

import static com.qlj.lakinqiandemo.hook.StartActivityHookHelper.hookStartActivity;

/**
 * Created by lakinqian on 2018/5/30.
 */

public class JianApplication extends Application {
    private static JianApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        StartActivityHookHelper.hookStartActivity();
        sInstance = this;
        hookStartActivity();
    }

    public static JianApplication get() {
        return sInstance;
    }
}
