package com.itecknologi.iteckapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "heads Up Notification", NotificationManager.IMPORTANCE_HIGH

        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID).setContentTitle(title).
                setContentText(text).
                setSmallIcon(R.drawable.icon).
                setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());

        super.onMessageReceived(message);
    }
}
