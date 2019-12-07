package com.example.andriodproject2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class broadCastFunction extends BroadcastReceiver {

    // set value to notification
    @Override
    public void onReceive(Context context,Intent broadcastIntent) {
        int notificationID = broadcastIntent.getIntExtra("NotificationID", 0);

        String StoreMessage = broadcastIntent.getStringExtra("StringValue");
        Intent mainIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0 );

        NotificationManager myNotificationHanding = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder getNotification = new Notification.Builder(context);
        getNotification.setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Current Reminder!").setContentText(StoreMessage).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(contentIntent);
        myNotificationHanding.notify(notificationID,getNotification.build());

    }
}

