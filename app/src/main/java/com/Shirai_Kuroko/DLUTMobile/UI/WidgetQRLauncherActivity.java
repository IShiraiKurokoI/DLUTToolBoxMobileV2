package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Configurations.QRCodeScanConfig;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.PermissionHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.CustomMNScanManager;
import com.Shirai_Kuroko.DLUTMobile.Services.BackgroudWIFIMonitorService;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.maning.mlkitscanner.scan.MNScanManager;

import java.util.ArrayList;

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
            CustomMNScanManager.startScan(this, QRCodeScanConfig.scanConfig, (resultCode, data) -> {
                switch (resultCode) {
                    case MNScanManager.RESULT_SUCCESS:
                        ArrayList<String> results = data.getStringArrayListExtra(MNScanManager.INTENT_KEY_RESULT_SUCCESS);
                        String resultTxt = results.get(0);
                        Log.i("扫码结果", resultTxt);
                        if (resultTxt.contains("whistle_info")) {
                            Intent intent1 = new Intent(this, ApiQRLoginActivity.class);
                            intent1.putExtra("whistle_info", resultTxt);
                            startActivity(intent1);
                        } else if (resultTxt.contains("qrLogin")) {
                            Intent intent1 = new Intent(this, LoginConfirmActivity.class);
                            intent1.putExtra("UUID", resultTxt);
                            startActivity(intent1);
                        } else if (resultTxt.startsWith("http") || resultTxt.startsWith("www")) {
                            Intent intent1 = new Intent(this, PureBrowserActivity.class);
                            intent1.putExtra("Name", "");
                            intent1.putExtra("Url", resultTxt);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(this, "无法解析的二维码", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MNScanManager.RESULT_FAIL:
                        String resultError = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_ERROR);
                        showToast(resultError);
                        break;
                    case MNScanManager.RESULT_CANCLE:
                        break;
                }
                finish();
            });
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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