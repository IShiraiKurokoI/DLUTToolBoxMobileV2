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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Entities.DrcomStatusBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.alibaba.fastjson.JSON;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WIFIStateChangeBroadcastReceiver extends BroadcastReceiver {

    private Context _context;

    public final WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            Log.i("TAG", "onPageFinished: " + url);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
            String Un = prefs.getString("Username", "");
            String Pd = prefs.getString("Password", "");
            final boolean b = Un.length() * Pd.length() != 0;
            if (url.contains("https://sso.dlut.edu.cn/cas/login?service=http%3A%2F%2F172.20.30.2%3A8080%2FSelf%2Fsso_login"))//sso自动认证
            {
                if (b) {
                    view.evaluateJavascript("$(\"#un\").val('" + Un + "');$(\"#pd\").val('" + Pd + "');login()", value -> {
                    });
                }
            }
            if (url.startsWith("http://172.20.30.2:8080/Self/dashboard;jsessionid=")) {
                Runnable GetInfo = () -> {

                    Request result = new Request.Builder()
                            .url("http://172.20.30.1/drcom/chkstatus?callback=")
                            .get()
                            .build();
                    try {
                        Response response = new OkHttpClient().newCall(result).execute();
                        if (response.isSuccessful()) {
                            String NetInfo = Objects.requireNonNull(response.body()).string();
                            String InfoJson = NetInfo.replace("     (", "").replace(")", "");
                            DrcomStatusBean drcomStatusBean = JSON.parseObject(InfoJson, DrcomStatusBean.class);
                            if (NetInfo.contains("\"result\":1,")) {
                                WifiManager mWifiManager = (WifiManager) _context.getSystemService(Context.WIFI_SERVICE);
                                assert mWifiManager != null;
                                WifiInfo info = mWifiManager.getConnectionInfo();
                                String ssid = info.getSSID();
                                DoNotification(_context, ssid + "已连接", "账户余额:" + ((double) drcomStatusBean.getFee() / 10000) + " | 已用流量:" + formatdataflow(drcomStatusBean.getFlow()));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                new Thread(GetInfo).start();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("TAG", "onPageStarted: " + url);
            if (url.startsWith("http://172.20.30.2:8080/Self/dashboard;jsessionid=")) {
                Runnable GetInfo = () -> {

                    Request result = new Request.Builder()
                            .url("http://172.20.30.1/drcom/chkstatus?callback=")
                            .get()
                            .build();
                    try {
                        Response response = new OkHttpClient().newCall(result).execute();
                        if (response.isSuccessful()) {
                            String NetInfo = Objects.requireNonNull(response.body()).string();
                            String InfoJson = NetInfo.replace("     (", "").replace(")", "");
                            DrcomStatusBean drcomStatusBean = JSON.parseObject(InfoJson, DrcomStatusBean.class);
                            if (NetInfo.contains("\"result\":1,")) {
                                WifiManager mWifiManager = (WifiManager) _context.getSystemService(Context.WIFI_SERVICE);
                                assert mWifiManager != null;
                                WifiInfo info = mWifiManager.getConnectionInfo();
                                String ssid = info.getSSID();
                                DoNotification(_context, ssid + "已连接", "账户余额:" + ((double) drcomStatusBean.getFee() / 10000) + " | 已用流量:" + formatdataflow(drcomStatusBean.getFlow()));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                new Thread(GetInfo).start();
            }
            super.onPageStarted(view, url, favicon);
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        _context = context;
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.DetailedState state = networkInfo.getDetailedState();
                LogToFile.init(context);
                LogToFile.i("网络状态", "onReceive: " + state);
                if (state == NetworkInfo.DetailedState.AUTHENTICATING || state == NetworkInfo.DetailedState.CONNECTED) {
                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    assert mWifiManager != null;
                    WifiInfo info = mWifiManager.getConnectionInfo();
                    String ssid = info.getSSID();
                    Log.i("连接到的WIFI为", ssid);
                    if (ssid.contains("DLUT-EDA")) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        String un = prefs.getString("Username", "");
                        String pd = prefs.getString("Password", "");
                        if (un.length() * pd.length() != 0) {
                            Runnable Login = new Runnable() {
                                int TriedTimes = 0;

                                @SuppressLint("SetJavaScriptEnabled")
                                @Override
                                public void run() {
                                    if (isWifiAvailable(context)) {
                                        return;
                                    }
                                    if (TriedTimes > 5) {
                                        new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "", (int) (System.currentTimeMillis() + Math.random()));
                                        return;
                                    }
                                    Request result = new Request.Builder()
                                            .url("http://172.20.30.1/drcom/chkstatus?callback=")
                                            .get()
                                            .build();
                                    try {
                                        Response response = new OkHttpClient().newCall(result).execute();
                                        if (response.isSuccessful()) {
                                            String NetInfo = Objects.requireNonNull(response.body()).string();
                                            String InfoJson = NetInfo.replace("     (", "").replace(")", "");
                                            DrcomStatusBean drcomStatusBean = JSON.parseObject(InfoJson, DrcomStatusBean.class);
                                            if (!NetInfo.contains("\"result\":1,")) {
                                                Handler handler = new Handler(Looper.getMainLooper());
                                                handler.post(() -> {
                                                    CookieSyncManager cookieSyncMngr =
                                                            CookieSyncManager.createInstance(context);
                                                    CookieManager cookieManager = CookieManager.getInstance();
                                                    cookieManager.removeAllCookie();
                                                    WebView webView = new WebView(context);
                                                    webView.getSettings().setJavaScriptEnabled(true);
                                                    webView.getSettings().setSupportMultipleWindows(false);
                                                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                                                    webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                                                    webView.getSettings().setDomStorageEnabled(true);
                                                    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                                                    webView.setWebViewClient(webViewClient);
                                                    webView.loadUrl("http://172.20.20.1");
                                                });
                                            } else {
                                                DoNotification(context, ssid + "已连接", "账户余额:" + ((double) drcomStatusBean.getFee() / 10000) + " | 已用流量:" + formatdataflow(drcomStatusBean.getFlow()));
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        TriedTimes++;
                                        run();
                                    }
                                }
                            };
                            Log.i("校园网WIFI连接", "开发区校区");
                            new Thread(Login).start();
                        } else {
                            DoNotification(context, ssid + "已连接", "未配置认证信息,无法自动认证");
                            Log.i("校园网WIFI连接", "开发区校区,未配置账户密码，停止认证");
                            new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "未配置校园网账户密码", (int) (System.currentTimeMillis() + Math.random()));
                        }
                    } else if (ssid.contains("DLUT-L")) {
                        DoNotification(context, ssid + "已连接", "抱歉，暂不支持主校区自动认证");
                        Log.i("校园网WIFI连接", "主校区");
                        //ToDo:自动连接主校区校园网
                    }
                } else if (state == NetworkInfo.DetailedState.DISCONNECTED) {
                    DoNotification(context, "正在后台监测校园网网络连接", null);
                }
            }
        } else if (intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.ManualConnect")) {
            Toast.makeText(context, "正在尝试连接", Toast.LENGTH_LONG).show();
            WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            String ssid = info.getSSID();
            Log.i("连接到的WIFI为", ssid);
            DoNotification(context, ssid + "已连接", "正在尝试执行手动登录");
            if (ssid.contains("DLUT-EDA")) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String un = prefs.getString("Username", "");
                String pd = prefs.getString("Password", "");
                if (un.length() * pd.length() != 0) {
                    Runnable Login = new Runnable() {
                        int TriedTimes = 0;

                        @SuppressLint("SetJavaScriptEnabled")
                        @Override
                        public void run() {
                            if (isWifiAvailable(context)) {
                                return;
                            }
                            if (TriedTimes > 5) {
                                new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "", (int) (System.currentTimeMillis() + Math.random()));
                                return;
                            }
                            Request result = new Request.Builder()
                                    .url("http://172.20.30.1/drcom/chkstatus?callback=")
                                    .get()
                                    .build();
                            try {
                                Response response = new OkHttpClient().newCall(result).execute();
                                if (response.isSuccessful()) {
                                    String NetInfo = Objects.requireNonNull(response.body()).string();
                                    String InfoJson = NetInfo.replace("     (", "").replace(")", "");
                                    DrcomStatusBean drcomStatusBean = JSON.parseObject(InfoJson, DrcomStatusBean.class);
                                    if (!NetInfo.contains("\"result\":1,")) {
                                        Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(() -> {
                                            CookieSyncManager cookieSyncMngr =
                                                    CookieSyncManager.createInstance(context);
                                            CookieManager cookieManager = CookieManager.getInstance();
                                            cookieManager.removeAllCookie();
                                            WebView webView = new WebView(context);
                                            webView.getSettings().setJavaScriptEnabled(true);
                                            webView.getSettings().setSupportMultipleWindows(false);
                                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                                            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                                            webView.getSettings().setDomStorageEnabled(true);
                                            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                                            webView.setWebViewClient(webViewClient);
                                            webView.loadUrl("http://172.20.20.1");
                                        });
                                    } else {
                                        DoNotification(context, ssid + "已连接", "账户余额:" + ((double) drcomStatusBean.getFee() / 10000) + " | 已用流量:" + formatdataflow(drcomStatusBean.getFlow()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                TriedTimes++;
                                run();
                            }
                        }
                    };
                    Log.i("校园网WIFI连接", "开发区校区");
                    new Thread(Login).start();
                } else {
                    DoNotification(context, ssid + "已连接", "未配置认证信息,无法自动认证");
                    Log.i("校园网WIFI连接", "开发区校区,未配置账户密码，停止认证");
                    new NotificationHelper().Notify(context, null, "2042", "联网消息通知", "校园网连接失败", "未配置校园网账户密码", (int) (System.currentTimeMillis() + Math.random()));
                }
            } else if (ssid.contains("DLUT-L")) {
                DoNotification(context, ssid + "已连接", "抱歉，暂不支持主校区自动认证");
                Log.i("校园网WIFI连接", "主校区");
                //ToDo:自动连接主校区校园网
            }
        } else if (intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect")) {
            WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            String ssid = info.getSSID();
            DoNotification(context, ssid + "已连接", "正在尝试执行手动注销");
            if (ssid.contains("DLUT-EDA")) {
                @SuppressLint("SetJavaScriptEnabled") Runnable Login = () -> {
                    if (isWifiAvailable(context)) {
                        return;
                    }
                    Request result = new Request.Builder()
                            .url("http://172.20.30.1/drcom/chkstatus?callback=")
                            .get()
                            .build();
                    try {
                        Response response = new OkHttpClient().newCall(result).execute();
                        if (response.isSuccessful()) {
                            String NetInfo = Objects.requireNonNull(response.body()).string();
                            if (NetInfo.contains("\"result\":1,")) {
                                String InfoJson = NetInfo.replace("     (", "").replace(")", "");
                                DrcomStatusBean drcomStatusBean = JSON.parseObject(InfoJson, DrcomStatusBean.class);
                                Request Logout = new Request.Builder()
                                        .url("http://172.20.30.1:801/eportal/portal/logout?callback=&wlan_user_ip=" + drcomStatusBean.getV4ip())
                                        .get()
                                        .build();
                                Response response1 = new OkHttpClient().newCall(Logout).execute();
                                if (response1.isSuccessful())
                                {
                                    String re = Objects.requireNonNull(response1.body()).string();
                                    if (re.contains("成功"))
                                    {
                                        DoNotification(_context, ssid + "已连接", "校园网尚未认证");
                                    }
                                }
                            } else {
                                DoNotification(context, ssid + "已连接", "校园网尚未认证");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                Log.i("校园网WIFI注销", "开发区校区");
                new Thread(Login).start();
            } else if (ssid.contains("DLUT-L")) {
                DoNotification(context, ssid + "已连接", "抱歉，暂不支持主校区注销功能");
            }
            DoNotification(context, "正在后台监测校园网网络连接", null);
        } else if (intent.getAction().equals("com.Shirai_Kuroko.DLUTMobile.OpenService")) {
            Intent OpenServiceIntent = new Intent(context, BrowserActivity.class);
            OpenServiceIntent.setAction(Intent.ACTION_DEFAULT);
            OpenServiceIntent.putExtra("App_ID", "78");
            OpenServiceIntent.putExtra("Remain", false);
            OpenServiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(OpenServiceIntent);
        }
    }

    @SuppressLint("DefaultLocale")
    String formatdataflow(long num) {
        double temp = num;
        String re;
        if (temp > 1048576) {
            temp /= 1024 * 1024;
            re = String.format("%.4f", temp) + "GB";
        } else if (temp > 1024) {
            temp /= 1024;
            re = String.format("%.4f", temp) + "MB";
        } else {
            re = String.format("%.4f", temp) + "KB";
        }
        return re;
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
                builder.addAction(R.drawable.icon, "手动连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualConnect"), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon, "断开连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect"), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon, "打开自服务", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.OpenService"), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));
            } else {
                builder.addAction(R.drawable.icon, "手动连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualConnect"), PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon, "断开连接", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.ManualDisconnect"), PendingIntent.FLAG_IMMUTABLE));
                builder.addAction(R.drawable.icon, "打开自服务", PendingIntent.getBroadcast(context, 0, new Intent("com.Shirai_Kuroko.DLUTMobile.OpenService"), PendingIntent.FLAG_IMMUTABLE));
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
