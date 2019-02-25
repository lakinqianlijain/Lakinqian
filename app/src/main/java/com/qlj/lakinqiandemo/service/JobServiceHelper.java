package com.qlj.lakinqiandemo.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by lakinqian on 2019/1/16.
 */

public class JobServiceHelper {
    public static final String TAG = "JobServiceHelper";
    public static final int SHOW_NOTIFICATION = 100011;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setJobScheduler(Context context, int id, long startTime, long repeatPeriod, PersistableBundle bundle){
        JobScheduler jobScheduler = (JobScheduler)
                context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        jobScheduler.cancel(id);
        JobInfo.Builder builder = new JobInfo.Builder(id,
                new ComponentName( context,
                        GlobalJobService.class.getName() ) );
        //        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        //设置在设备充电时执行
        builder.setRequiresCharging(true);
//设置在设备空闲时间执行
        builder.setRequiresDeviceIdle(true);

        if (startTime < System.currentTimeMillis()) {
            // 时间过去了，则过三秒执行
            startTime = 3000;
        } else {
            startTime = startTime - System.currentTimeMillis();
        }

        builder.setMinimumLatency(startTime);
        builder.setOverrideDeadline(startTime + 10 * 1000);

        if (bundle != null) {
            builder.setExtras(bundle);
        }

        int code = jobScheduler.schedule(builder.build());
        Log.d(TAG, "JobScheduler result->" + code + ",Single JobScheduler："+id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setRepeatJobScheduler(Context context, int id, long startTime, long repeatPeriod, PersistableBundle bundle){
        JobScheduler jobScheduler = (JobScheduler)
                context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        JobInfo.Builder builder = new JobInfo.Builder(id,
                new ComponentName( context.getPackageName(),
                        GlobalJobService.class.getName() ) );
//        builder.setPersisted(true);
        //设置在设备充电时执行
        builder.setRequiresCharging(true);
//设置在设备空闲时间执行
        builder.setRequiresDeviceIdle(true);

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);

        // JobScheduler的周期不能小于15分钟
//        repeatPeriod = repeatPeriod < 15 * 60 * 1000 ? 20 * 60 * 100 : repeatPeriod;
        builder.setPeriodic(repeatPeriod);

        if (bundle != null) {
            builder.setExtras(bundle);
        }

        int code = jobScheduler.schedule(builder.build());
        Log.d(TAG, "JobScheduler result->" + code + ",Repeat JobScheduler:" + repeatPeriod);
    }
}
