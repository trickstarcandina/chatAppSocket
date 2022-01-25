package com.example.chattogether.ui.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.chattogether.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class FirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Received Message","done");

        if(remoteMessage.getNotification() != null){
            Log.d("MessageNotificationBody", remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }


    }

    private void sendNotification(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "PVT";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // O is Oreo - Android 8.0
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID,
                    "Chanel 1",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Chanel from Firebase Cloud Messaging");
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_email)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        notificationManager.notify((int)(new Date()).getTime(),notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.d("Token",s);
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String s) {
    }
}
