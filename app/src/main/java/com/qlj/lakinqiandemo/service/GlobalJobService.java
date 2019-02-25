package com.qlj.lakinqiandemo.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.qlj.lakinqiandemo.common.notification.NotificationHelper;

import static com.qlj.lakinqiandemo.service.JobServiceHelper.SHOW_NOTIFICATION;

/**
 * Created by lakinqian on 2019/1/16.
 */

public class GlobalJobService extends JobService {
    public static final String TAG = "GlobalJobService";
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob: " );
        switch (params.getJobId()){
            case SHOW_NOTIFICATION:
                NotificationHelper helper = new NotificationHelper(this);
                helper.showNotification();
                break;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.v(TAG, "on stop job: " + params.getJobId());
        return false;
    }
}
