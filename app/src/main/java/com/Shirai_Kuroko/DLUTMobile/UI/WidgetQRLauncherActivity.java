package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.PermissionHelper;
import com.Shirai_Kuroko.DLUTMobile.Services.BackgroudWIFIMonitorService;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

public class WidgetQRLauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        com.igexin.sdk.PushManager.getInstance().initialize(this);
        setBaseTheme();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Autologin = prefs.getBoolean("AutoLogin", false);
        if (Autologin) {
            Intent ServiceIntent = new Intent(this, BackgroudWIFIMonitorService.class);
            startForegroundService(ServiceIntent);
        }
        super.onCreate(savedInstanceState);
        PermissionHelper.GetAllPermission(this);
        MobileUtils.CheckUpDateOnStartUp(this);
        MobileUtils.CheckConfigUpdates(this);
        if (ConfigHelper.NeedLogin(this)) {
            Log.i("无法认证", "需要登陆");
            String Un = prefs.getString("Username", "");
            String Pd = prefs.getString("Password", "");
            if (Un.length() * Pd.length() != 0) {
                Log.i("认证失败", "静默登陆");
                BackendUtils.Login(this, Un, Pd);
            } else {
                Log.i("认证信息为空", "弹出登陆");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else {
            Log.i("启动初始化", "刷新用户数据");
            BackendUtils.ReSendUserInfo(this);
            Intent intent = new Intent(this, ScanQRCodeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setBaseTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ThemeType = prefs.getBoolean("Dark", false);
        if (ThemeType) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}