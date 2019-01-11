package com.qlj.lakinqiandemo.common.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.video.VideoActivity;

/**
 * Created by lakinqian on 2019/1/10.
 */

public class NotificationHelper {
    public static final String CHANNEL_ID = "1001";
    private static final String NOTI_CHANNEL_LAKIN = "noti_channel_lakin"; // channel name
    private static final int REQUEST_CODE = 1001;
    public int NOTIFICATION_ID = 10000;

    private int REQUEST_CODE1 = 1;

    private Context mContext;
    private Notification mNotification;
    private NotificationManager mNotificationManager;

    public NotificationHelper(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification = new Notification();
        mContext = context;
    }

    public void showNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, NOTI_CHANNEL_LAKIN, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(mContext, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(mContext);
        }
        Bitmap largeIcon =
                ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.tw_ic_launcher_notification)).getBitmap();
        Intent click = new Intent(mContext, VideoActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(mContext, REQUEST_CODE1, click, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_act_notice).setLargeIcon(largeIcon)
                .setContentTitle("hi, title").setContentText("hi, content").setContentIntent(pendingIntent).setAutoCancel(true);
        mNotification = builder.build();
//        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
//        mNotification.flags |= Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
}
