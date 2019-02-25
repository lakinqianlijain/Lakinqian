package com.qlj.lakinqiandemo.common.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by lakinqian on 2019/1/16.
 */

public class TimeTaskUtil {
    public static final String TIME_TASK_ACTION = "com.qlj.lakinqiandemo.TIMER_ACTION";

    /**
     * @param context
     * @param action
     * @param requestCode
     * @param startTime    毫秒
     * @param repeatPeriod 毫秒，如果<=0，则执行不repeat的Alarm
     */
    public static void startAlarmSchedule(Context context, String action, int requestCode,
                                          long startTime, long repeatPeriod) {

        try {
            Intent intent = new Intent();
            intent.setAction(action);
            PendingIntent sender =
                    PendingIntent.getBroadcast(context, requestCode, intent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (repeatPeriod > 0) {
                Log.e(TAG, "startAlarmSchedule: Repeat" );
                am.setRepeating(AlarmManager.RTC, startTime, repeatPeriod, sender);
            } else {
                am.set(AlarmManager.RTC, startTime, sender);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void stopAlarmSchedule(Context context, String action, int requestCode) {
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            PendingIntent sender =
                    PendingIntent.getService(context, requestCode, intent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(sender);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
