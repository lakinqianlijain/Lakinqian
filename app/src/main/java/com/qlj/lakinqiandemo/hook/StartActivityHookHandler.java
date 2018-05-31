package com.qlj.lakinqiandemo.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by lakinqian on 2018/5/30.
 */

public class StartActivityHookHandler implements InvocationHandler {
    private Object iActivityManagerObject;

    public StartActivityHookHandler(Object iActivityManagerObject) {
        this.iActivityManagerObject = iActivityManagerObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //我要在这里搞点事情
        if ("startActivity".contains(method.getName())) {
            Log.e(TAG,"Activity已经开始启动");
            Log.e(TAG,"但是activity启动已经被hook了！！！");
        }
        return method.invoke(iActivityManagerObject, args);
    }
}
