package com.qlj.lakinqiandemo.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by lakinqian on 2018/5/29.
 */

public class JumpActivityUtil {
    public static void JumpSelfActivity(Context context, Class className){
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public static void JumpOtherActivity(Context context, String packageName, String className){
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        context.startActivity(intent);
    }
}
