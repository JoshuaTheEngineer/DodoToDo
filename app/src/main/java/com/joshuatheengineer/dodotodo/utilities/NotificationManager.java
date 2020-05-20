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
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        this.notificationManager = NotificationManagerCompat.from(context);
        this.builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_todo_notification)
                .setContentTitle(Constants.APP_TITLE)
                .setContentText("Got to Dodo some tasks!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(customMessage))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }

    public void addNotification(){
        notificationManager.notify(Constants.NOTIFICATIONS_KEY, this.builder.build());
    }

    public void removeNotification(){
        notificationManager.cancel(Constants.NOTIFICATIONS_KEY);
    }
}
