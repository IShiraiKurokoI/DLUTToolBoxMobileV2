package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.R;

public class NotificationHelper {
    public void Notify(Context context,PendingIntent pendingIntent,String CHANNEL_ONE_ID,String CHANNEL_ONE_NAME,String title,int id)
    {
        NotificationChannel notificationChannel;
        notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Notification notification = new Notification.Builder(context, CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setShowWhen(true)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        if(sharedPreferences.getBoolean("Vibration",false))
        {
            notification.flags |= Notification.DEFAULT_VIBRATE;
        }
        if(sharedPreferences.getBoolean("Beep",false))
        {
            notification.flags |= Notification.DEFAULT_SOUND;
        }
        if (manager != null) {
            manager.notify(id, notification);
        }
    }
}
