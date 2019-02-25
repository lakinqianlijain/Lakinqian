package com.qlj.lakinqiandemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.qlj.lakinqiandemo.common.notification.TimeTaskUtil;

/**
 * Created by lakinqian on 2019/1/16.
 */

public class GlobalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.isEmpty(intent.getAction())) return;
        switch (intent.getAction()){
            case TimeTaskUtil.TIME_TASK_ACTION:
                Log.e("6666", "onReceive: 收到广播" );
                break;
        }
    }
}
