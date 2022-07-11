package com.Shirai_Kuroko.DLUTMobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Services.BackgroudWIFIMonitorService;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.main_theme_color));
        com.igexin.sdk.PushManager.getInstance().initialize(this);
        setBaseTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView Version = requireViewById(R.id.Version);
        Version.setText("版本 "+ MobileUtils.GetAppVersion(this));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Autologin = prefs.getBoolean("AutoLogin",false);
        if (Autologin)
        {
            Intent ServiceIntent = new Intent(this, BackgroudWIFIMonitorService.class);
            startForegroundService(ServiceIntent);
        }


        Handler handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        int SPLASH_DISPLAY_LENGTH = 2000;
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this,
                    MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void setBaseTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ThemeType = prefs.getBoolean("Dark",false);
        if (ThemeType) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}