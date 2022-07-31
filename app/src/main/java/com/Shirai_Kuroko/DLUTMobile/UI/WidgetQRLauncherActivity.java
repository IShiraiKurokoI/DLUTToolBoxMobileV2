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
import com.google.zxing.integration.android.IntentIntegrator;

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
            new IntentIntegrator(this)
                    // 自定义Activity，重点是这行----------------------------
                    .setCaptureActivity(ScanQRCodeActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                    .setPrompt("将二维码/条码放入框内，即可自动扫描")// 设置提示语
                    .setCameraId(0)// 选择摄像头,可使用前置或者后置
                    .setBeepEnabled(false)// 是否开启声音,扫完码之后会"哔"的一声
                    .initiateScan();
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