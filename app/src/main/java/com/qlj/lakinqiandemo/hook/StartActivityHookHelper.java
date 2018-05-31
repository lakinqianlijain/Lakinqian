package com.qlj.lakinqiandemo.hook;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by lakinqian on 2018/5/30.
 */

public class StartActivityHookHelper {

    public static void hookStartActivity() {
        if (Build.VERSION.SDK_INT <= 25) {
            hookStartActivityBelow26();
        } else {
            hookStartActivity26();
        }

    }

    private static void hookStartActivityBelow26() {
        Log.d(TAG, "hookStartActivity Below26!!");
        //一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);

            //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            StartActivityHookHandler handler = new StartActivityHookHandler(iActivityManagerObject);
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);

            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);

        } catch (Exception e) {
            Log.e(TAG, "hookStartActivity failed!!" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void hookStartActivity26() {
        Log.d(TAG, "hookStartActivity above26!!");
        try {
            Class<?> clazz = Class.forName("android.app.ActivityManager");
            Field defaultFiled = clazz.getDeclaredField("IActivityManagerSingleton");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);
            StartActivityHookHandler handler = new StartActivityHookHandler(iActivityManagerObject);
            Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");
            final Object object = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{iActivityManagerClazz}, handler);
            mInstance.set(defaultValue, object);

        } catch (Exception e) {
            Log.e(TAG, "hookStartActivity failed!!" + e.getMessage());
            e.printStackTrace();
        }
    }
}
