package com.Shirai_Kuroko.DLUTMobile.Services;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WIFIStateChangeBroadcastReceiver extends BroadcastReceiver {
    private boolean datawarn;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.DetailedState state = networkInfo.getDetailedState();
                LogToFile.init(context);
                LogToFile.i("网络状态","onReceive: "+state);
                if (state == NetworkInfo.DetailedState.AUTHENTICATING||state == NetworkInfo.DetailedState.CONNECTED) {
                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    assert mWifiManager != null;
                    WifiInfo info = mWifiManager.getConnectionInfo();
                    String ssid = info.getSSID();
                    Log.i("连接到的WIFI为", ssid);
                    if (ssid.contains("DLUT-EDA")) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        String un = prefs.getString("Username", "");
                        String pd = prefs.getString("NetworkPassword", "");
                        if (un.length() * pd.length() != 0) {
                            Runnable Login = new Runnable() {
                                int TriedTimes = 0;
                                @Override
                                public void run() {
                                    if (isWifiAvailable(context)) {
                                        return;
                                    }
                                    if (TriedTimes > 5) {
                                        new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "", (int) (System.currentTimeMillis() + Math.random()));
                                        return;
                                    }
                                    Request request = new Request.Builder()
                                            .url("http://172.20.20.1:801/srun_portal_pc.php?ac_id=3&")
                                            .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "action=login&ac_id=3&user_ip=&nas_ip=&user_mac=&url=&username=" + un + "&password=" + pd + "&save_me=1"))
                                            .build();//创建Request 对象
                                    try {
                                        new OkHttpClient().newCall(request).execute();
                                    } catch (Exception e) {
                                        TriedTimes++;
                                        run();
                                        return;
                                    }
                                    Request result = new Request.Builder()
                                            .url("http://172.20.20.1:801/include/auth_action.php?action=get_online_info")
                                            .get()
                                            .build();
                                    try {
                                        Response response = new OkHttpClient().newCall(result).execute();
                                        if (response.isSuccessful()) {
                                            String NetInfo = Objects.requireNonNull(response.body()).string();
                                            if (NetInfo.equals("not_online")) {
                                                Thread.sleep(4000);
                                                TriedTimes++;
                                                run();
                                                return;
                                            }
                                            String[] data = NetInfo.split(",");
                                            String Text;
                                            if (data.length > 2) {
                                                Text = "套餐余额:" + data[2] + " | 已用流量:" + formatdataflow(data[0]);
                                                if (datawarn) {
                                                    Text += "\n|本月流量使用已超过90G，请留意！！|";
                                                }
                                                DoNotification(context,ssid+"已连接并自动认证",Text);
                                            } else {
                                                Thread.sleep(4000);
                                                TriedTimes++;
                                                run();
                                            }
                                        } else {
                                            Thread.sleep(4000);
                                            TriedTimes++;
                                            run();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        DoNotification(context,ssid+"认证出错",e.getLocalizedMessage());
                                        try {
                                            Thread.sleep(4000);
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                        TriedTimes++;
                                        run();
                                    }
                                }
                            };
                            Log.i("校园网WIFI连接", "开发区校区");
                            new Thread(Login).start();
                        } else {
                            DoNotification(context,ssid+"已连接","未配置认证信息,无法自动认证");
                            Log.i("校园网WIFI连接", "开发区校区,未配置账户密码，停止认证");
                            new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "未配置校园网账户密码", (int) (System.currentTimeMillis() + Math.random()));
                        }
                    } else if (ssid.contains("DLUT-L")) {
                        DoNotification(context,ssid+"已连接","抱歉，暂不支持主校区自动认证");
                        Log.i("校园网WIFI连接", "主校区");
                        //ToDo:自动连接主校区校园网
                    }
                } else if (state == NetworkInfo.DetailedState.DISCONNECTED) {
                    DoNotification(context,"正在后台监测校园网网络连接",null);
                }
            }
        }else if (intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.ManualConnect"))
        {
            Toast.makeText(context, "正在尝试连接", Toast.LENGTH_LONG).show();
            WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            String ssid = info.getSSID();
            Log.i("连接到的WIFI为", ssid);
            DoNotification(context,ssid+"已连接","正在尝试执行手动登录");
            if (ssid.contains("DLUT-EDA")) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String un = prefs.getString("Username", "");
                String pd = prefs.getString("NetworkPassword", "");
                if (un.length() * pd.length() != 0) {
                    Runnable Login = new Runnable() {
                        int TriedTimes = 0;
                        @Override
                        public void run() {
                            if (isWifiAvailable(context)) {
                                return;
                            }
                            if (TriedTimes > 5) {
                                new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "", (int) (System.currentTimeMillis() + Math.random()));
                                return;
                            }
                            Request request = new Request.Builder()
                                    .url("http://172.20.20.1:801/srun_portal_pc.php?ac_id=3&")
                                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "action=login&ac_id=3&user_ip=&nas_ip=&user_mac=&url=&username=" + un + "&password=" + pd + "&save_me=1"))
                                    .build();//创建Request 对象
                            try {
                                new OkHttpClient().newCall(request).execute();
                            } catch (Exception e) {
                                TriedTimes++;
                                run();
                                return;
                            }
                            Request result = new Request.Builder()
                                    .url("http://172.20.20.1:801/include/auth_action.php?action=get_online_info")
                                    .get()
                                    .build();
                            try {
                                Response response = new OkHttpClient().newCall(result).execute();
                                if (response.isSuccessful()) {
                                    String NetInfo = Objects.requireNonNull(response.body()).string();
                                    if (NetInfo.equals("not_online")) {
                                        Thread.sleep(4000);
                                        TriedTimes++;
                                        run();
                                        return;
                                    }
                                    String[] data = NetInfo.split(",");
                                    String Text;
                                    if (data.length > 2) {
                                        Text = "套餐余额:" + data[2] + " | 已用流量:" + formatdataflow(data[0]);
                                        if (datawarn) {
                                            Text += "\n|本月流量使用已超过90G，请留意！！|";
                                        }
                                        DoNotification(context,ssid+"已连接并自动认证",Text);
                                    } else {
                                        Thread.sleep(4000);
                                        TriedTimes++;
                                        run();
                                    }
                                } else {
                                    Thread.sleep(4000);
                                    TriedTimes++;
                                    run();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                DoNotification(context,ssid+"认证出错",e.getLocalizedMessage());
                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                TriedTimes++;
                                run();
                            }
                        }
                    };
                    Log.i("校园网WIFI连接", "开发区校区");
                    new Thread(Login).start();
                } else {
                    DoNotification(context,ssid+"已连接","未配置认证信息,无法自动认证");
                    Log.i("校园网WIFI连接", "开发区校区,未配置账户密码，停止认证");
                    new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "未配置校园网账户密码", (int) (System.currentTimeMillis() + Math.random()));
                }
            } else if (ssid.contains("DLUT-L")) {
                DoNotification(context,ssid+"已连接","抱歉，暂不支持主校区自动认证");
                Log.i("校园网WIFI连接", "主校区");
                //ToDo:自动连接主校区校园网
            }
        }
        else if (intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect"))
        {
            WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            String ssid = info.getSSID();
            if (ssid.contains("DLUT-EDA")) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String un = prefs.getString("Username", "");
                String pd = prefs.getString("NetworkPassword", "");
                if (un.length() * pd.length() != 0) {
                    Runnable Login = () -> {
                        if (isWifiAvailable(context)) {
                            return;
                        }
                        Request request = new Request.Builder()
                                .url("http://172.20.20.1:801/include/auth_action.php")
                                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "action=logout&username=" + un + "&password=" + pd + "&ajax=1"))
                                .build();//创建Request 对象
                        try {
                            Response response = new OkHttpClient().newCall(request).execute();
                            String respon = response.body().string();
                            DoNotification(context,ssid+"已连接",respon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    };
                    Log.i("校园网WIFI注销", "开发区校区");
                    new Thread(Login).start();
                } else {
                    DoNotification(context,ssid+"已连接","未配置认证信息,无法执行注销");
                    Log.i("校园网WIFI注销", "开发区校区,未配置账户密码，停止注销");
                    new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "未配置校园网账户密码", (int) (System.currentTimeMillis() + Math.random()));
                }
            } else if (ssid.contains("DLUT-L")) {
                DoNotification(context,ssid+"已连接","抱歉，暂不支持主校区注销功能");
            }
            DoNotification(context,"正在后台监测校园网网络连接",null);
        }
        else if(intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.OpenService"))
        {
            Intent OpenServiceIntent = new Intent(context, BrowserActivity.class);
            OpenServiceIntent.setAction(Intent.ACTION_DEFAULT);
            OpenServiceIntent.putExtra("App_ID", "78");
            OpenServiceIntent.putExtra("Remain", false);
            OpenServiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(OpenServiceIntent);
        }
    }

    @SuppressLint("DefaultLocale")
    String formatdataflow(String num) {
        double temp = Double.parseDouble(num);
        String re;
        if (temp > 96636764160D) {
            datawarn = true;
        }
        if (temp > 1000000000) {
            temp /= 1024 * 1024 * 1024;
            re = String.format("%.4f", temp) + "G";
        } else if (temp > 1000000) {
            temp /= 1024 * 1024;
            re = String.format("%.4f", temp) + "M";
        } else {
            temp /= 1024;
            re = String.format("%.4f", temp) + "K";
        }
        return re + "B";
    }

    public boolean isWifiAvailable(Context ctx) {
        ConnectivityManager conMan = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return NetworkInfo.State.CONNECTED != wifi;
    }

    public void DoNotification(Context context, String title, String content) {
        String CHANNEL_ONE_ID = "114514";
        String CHANNEL_ONE_NAME = "校园网连接监测服务";
        NotificationChannel notificationChannel;
        notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);
            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title);
            if (content != null) {
                builder.setContentText(content);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                builder.addAction(R.drawable.icon,"手动连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualConnect"), PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE));
                builder.addAction(R.drawable.icon,"断开连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect"), PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE));
                builder.addAction(R.drawable.icon,"打开自服务", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.OpenService"), PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE));
            }else
            {
                builder.addAction(R.drawable.icon,"手动连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualConnect"), PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon,"断开连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect"), PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon,"打开自服务", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.OpenService"), PendingIntent.FLAG_IMMUTABLE));
            }
            Notification NewNotification = builder
                    .setShowWhen(false)
                    .setContentIntent(null)
                    .setAllowSystemGeneratedContextualActions(false)
                    .setVisibility(Notification.VISIBILITY_SECRET)
                    .build();
            manager.notify(2, NewNotification);
        }
    }
}
