package com.Shirai_Kuroko.DLUTMobile.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class StartUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("收到开机广播", intent.getAction());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean Autologin = prefs.getBoolean("AutoLogin", false);
        if (Autologin)
        {
            Intent ServiceIntent = new Intent(context, BackgroudWIFIMonitorService.class);
            context.startForegroundService(ServiceIntent);
        }
    }
}
