package com.qlj.lakinqiandemo.utils;

import android.content.Context;
import android.content.Intent;

import static com.qlj.lakinqiandemo.social.message.DynamicMessageActivity.DYNAMIC_TYPE;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinEditActivity.INPUT_TYPE;

/**
 * Created by lakinqian on 2018/5/29.
 */

public class JumpActivityUtil {

    public static void JumpSelfActivity(Context context, Class className){
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public static void JumpLockActivity(Context context, Class className, String type){
        Intent intent = new Intent(context, className);
        intent.putExtra(INPUT_TYPE, type);
        context.startActivity(intent);
    }

    public static void JumpOtherActivity(Context context, String packageName, String className){
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        context.startActivity(intent);
    }

    public static void startDynamicMessageActivity(Context context, Class className, int type){
        Intent intent = new Intent(context, className);
        intent.putExtra(DYNAMIC_TYPE, type);
        context.startActivity(intent);
    }
}
