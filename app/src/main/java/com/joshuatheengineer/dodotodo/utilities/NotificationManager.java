package com.joshuatheengineer.dodotodo.utilities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.main.MainActivity;

public class NotificationManager {

    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    public NotificationManager(Context context, String customMessage) {
        // Tells where the user opens up to when opening the notifications
        Intent intent = new Intent(context, MainActivity.class);
        // FLAG_ACTIVITY_CLEAR_TASK
        // will cause any existing task that would be associated with
        // the activity to be cleared before the activity is started
        // FLAG_ACTIVITY_NEW_TASK
        // If set, this activity will become the start of a new task on this history stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // pending intent is a wrapper around regular intent that is designed to be used by another application
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // How to respond to a tap from user
        this.notificationManager = NotificationManagerCompat.from(context);
        this.builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_todo_notification)
                .setContentTitle(Constants.APP_TITLE)
                .setContentText("Got to Dodo some tasks!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(customMessage))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                //automatically removes the notification when the user taps on it
                .setAutoCancel(true);
    }

    // to display the notification ID
    public void addNotification(){
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Constants.NOTIFICATIONS_KEY, this.builder.build());
    }

    public void removeNotification(){
        // removes the notification
        notificationManager.cancel(Constants.NOTIFICATIONS_KEY);
    }
}
