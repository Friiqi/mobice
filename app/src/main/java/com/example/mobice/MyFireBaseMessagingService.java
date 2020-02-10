package com.example.mobice;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;
/*import com.google.firebase.quickstart.fcm.R;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;*/

public class MyFireBaseMessagingService extends FirebaseMessagingService {
@Override
public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
        }

        private void sendRegistrationToServer(String token) {
        }





        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
                // ...

                // TODO(developer): Handle FCM messages here.
                // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
                Log.d(TAG, "From: " + remoteMessage.getFrom());

                // Check if message contains a data payload.


                if (remoteMessage.getNotification() != null) {
                        Log.d(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());

                    
                        Log.d(TAG, "Notification Message: " + remoteMessage.getNotification().getBody());
                }

                // Check if message contains a notification payload.

               if (remoteMessage.getData().size() > 0) {
                        Log.d(TAG, "msg "+ remoteMessage.getData().get("message"));

                       NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "101")
                                .setContentTitle(remoteMessage.getNotification().getTitle())
                                .setContentText(remoteMessage.getNotification().getBody())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle())
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setAutoCancel(true);

                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(0, notificationBuilder.build());
                   //Toast.makeText(this, "match found!", Toast.LENGTH_LONG).show();


                }



                // Also if you intend on generating your own notifications as a result of a received FCM
                // message, here is where that should be initiated. See sendNotification method below.
        }
}
