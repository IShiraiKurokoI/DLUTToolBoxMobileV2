package com.Shirai_Kuroko.DLUTMobile.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("收到开机广播", intent.getAction());
        Intent ServiceIntent = new Intent(context, BackgroudWIFIMonitorService.class);
        context.startForegroundService(ServiceIntent);
    }
}
