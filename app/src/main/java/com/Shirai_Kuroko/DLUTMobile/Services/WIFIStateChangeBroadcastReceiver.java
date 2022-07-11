package com.Shirai_Kuroko.DLUTMobile.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

public class WIFIStateChangeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                if (state == NetworkInfo.State.CONNECTED) {
                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    assert mWifiManager != null;
                    WifiInfo info = mWifiManager.getConnectionInfo();
                    String ssid = info.getSSID();
                    Log.i("连接到的WIFI为", ssid);
                    if (ssid.contains("DLUT-EDA")) {
                        Log.i("校园网WIFI连接", "开发区校区");
                        //ToDo:自动连接开发区校区校园网
                    } else if (ssid.contains("DLUT-L")) {
                        Log.i("校园网WIFI连接", "主校区");
                        //ToDo:自动连接主校区校园网
                    }
                }
            }
        }
    }
}
