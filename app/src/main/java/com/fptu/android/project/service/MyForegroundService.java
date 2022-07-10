package com.fptu.android.project.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.fptu.android.project.R;

public class MyForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static final String NOTIFICATION_CHANNEL = "NOTIFICATION";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL);
        Intent i = new Intent();
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_IMMUTABLE);
        String dataText = intent.getStringExtra("data");

        Notification notification = builder.setContentTitle("Notification")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(dataText)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pi)
                .build();
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel
                            (NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        startForeground(1,notification);
        //TODO: do job  here
//        for (int j = 0; j < 1000000000; j++) {
//            Toast.makeText(getApplicationContext(), j+"", Toast.LENGTH_SHORT).show();
//        }

//        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
