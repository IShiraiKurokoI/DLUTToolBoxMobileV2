package com.Shirai_Kuroko.DLUTMobile.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.MainActivity;
import com.Shirai_Kuroko.DLUTMobile.R;

public class BackgroudWIFIMonitorService extends Service {
    public BackgroudWIFIMonitorService() {
        //注册网络监听
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Autologin = prefs.getBoolean("AutoLogin", false);
        if (!Autologin) {
            return;
        }

        String CHANNEL_ONE_ID = "114514";
        String CHANNEL_ONE_NAME = "校园网连接监测服务";
        NotificationChannel notificationChannel;
//进行8.0的判断
        notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
        Notification notification = new Notification.Builder(this, CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("正在后台监测校园网网络连接")
                .setShowWhen(false)
                .setAllowSystemGeneratedContextualActions(false)
                .setVisibility(Notification.VISIBILITY_SECRET)
                .setContentIntent(null)
                .build();

        BroadcastReceiver Receiver = new WIFIStateChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction("com.Shirai_Kuroko.DLUTMobile.ManualConnect");
        intentFilter.addAction("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect");
        intentFilter.addAction("com.Shirai_Kuroko.DLUTMobile.OpenService");
        registerReceiver(Receiver, intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(Receiver, intentFilter);
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}