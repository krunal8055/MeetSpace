package com.example.meetspace;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

        private static final String TAG = "FMS";

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {

                String notificationTitle = remoteMessage.getNotification().getTitle();
                String notificationBody = remoteMessage.getNotification().getBody();
                String token = remoteMessage.getData().get("token");

                Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))//Automatically delete the notification
                        .setSmallIcon(R.mipmap.ic_launcher) //Notification icon
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationBody)
                        .setSound(defaultSoundUri);

                Intent resultintent = new Intent(getApplicationContext(),NotificationPage.class);
                resultintent.putExtra("Token",token);
                PendingIntent  resultPendingIntent=
                     PendingIntent.getActivity
                             (this, 0, resultintent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificationBuilder.setContentIntent(resultPendingIntent);

                int notification_id = (int) System.currentTimeMillis();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(notification_id, notificationBuilder.build());
        }
}
