package com.fptu.android.project.activity.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String NOTIFICATION_CHANNEL = "Order";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    private void createNotification() {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel
                            (NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
    }
}
