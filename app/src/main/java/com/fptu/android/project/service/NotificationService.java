package com.fptu.android.project.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.notification.MyApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification=message.getNotification();
        if(notification==null){
            return;
        }
        String title= notification.getTitle();
        String body= notification.getBody();

        getFireBaseMessage(title,body);

    }




    public void getFireBaseMessage(String title, String msg){
        Intent intent= new Intent(this, HomePageActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

               NotificationCompat.Builder builder= new NotificationCompat.Builder(
                       this, MyApplication.NOTIFICATION_CHANNEL
               ).setSmallIcon(R.drawable.ic_launcher_background)
                       .setContentTitle(title)
                       .setContentText(msg)
                       .setContentIntent(pendingIntent);

        Notification notification=builder.build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null){
            notificationManager.notify(1,notification);
        }
    }
}
