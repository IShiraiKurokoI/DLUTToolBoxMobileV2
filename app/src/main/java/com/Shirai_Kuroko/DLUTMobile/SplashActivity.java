package com.Shirai_Kuroko.DLUTMobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setBaseTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView Version = requireViewById(R.id.Version);
        TextView Name = requireViewById(R.id.splashname);
        Version.setText("版本 "+ MobileUtils.GetAppVersion(this));
        if(ConfigHelper.GetThemeType(this))
        {
            Version.setTextColor(Color.WHITE);
            Name.setTextColor(Color.WHITE);
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        }
        else
        {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
        Handler handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        int SPLASH_DISPLAY_LENGTH = 1000;
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}