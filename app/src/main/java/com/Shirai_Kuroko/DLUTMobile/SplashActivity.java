package com.Shirai_Kuroko.DLUTMobile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.CrashHandler;
import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.AutoCleaner;
import com.Shirai_Kuroko.DLUTMobile.Services.BackgroudWIFIMonitorService;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogToFile.init(this);
        AutoCleaner.Clean(this);
        CrashHandler.getInstance().init(this);
        MobileUtils.SetShortCuts(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.main_theme_color));
        com.igexin.sdk.PushManager.getInstance().initialize(this);
        setBaseTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView Version = requireViewById(R.id.Version);
        Version.setText("Version " + MobileUtils.GetAppVersion(this));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Autologin = prefs.getBoolean("AutoLogin", false);
        if (Autologin) {
            Intent ServiceIntent = new Intent(this, BackgroudWIFIMonitorService.class);
            startForegroundService(ServiceIntent);
        }
        if (ConfigHelper.FPStatus(this)) {
            Handler handler = new Handler();
            // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
            int SPLASH_DISPLAY_LENGTH = 2000;
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            Dialog UpdateDialog = new Dialog(this, R.style.CustomDialog);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(
                    R.layout.core_dialog_show_privacy_fragment, null);
            Window window1 = UpdateDialog.getWindow();
            window1.setContentView(view);
            window1.setGravity(Gravity.CENTER);
            window1.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    android.view.WindowManager.LayoutParams.WRAP_CONTENT);
            UpdateDialog.setCanceledOnTouchOutside(false);
            TextView txt_title = view.findViewById(R.id.txt_title);
            txt_title.setText("隐私保护提示");
            TextView txt_content = view.findViewById(R.id.txt_content);
            txt_content.setText(getString(R.string.PrivacyContent));
            TextView PrivacyText = view.findViewById(R.id.txt_privacy_span);
            PrivacyText.setText("您可通过阅读完整版");
            SpannableString spannableString2 = new SpannableString("《服务协议》");
            spannableString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SplashActivity.this, PureBrowserActivity.class);
                    intent.putExtra("Name", "服务协议");
                    intent.putExtra("Url", "https://its.dlut.edu.cn/upload/app/agreements/index.html");
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 0, spannableString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            PrivacyText.append(spannableString2);
            PrivacyText.append("和");
            SpannableString spannableString1 = new SpannableString("《隐私政策》");
            spannableString1.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SplashActivity.this, PureBrowserActivity.class);
                    intent.putExtra("Name", "隐私政策");
                    intent.putExtra("Url", "https://its.dlut.edu.cn/upload/app/privacy/index.html");
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 0, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            PrivacyText.append(spannableString1);
            PrivacyText.append("了解全部的条款内容");
            PrivacyText.setMovementMethod(LinkMovementMethod.getInstance());
            Button btn_reject = view.findViewById(R.id.btn_reject);
            btn_reject.setOnClickListener(view1 -> {
                UpdateDialog.dismiss();
                finish();
            });
            Button btn_agree = view.findViewById(R.id.btn_agree);
            btn_agree.setOnClickListener(view12 -> {
                UpdateDialog.dismiss();
                ConfigHelper.AgreeFP(SplashActivity.this);
                Handler handler = new Handler();
                // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
                int SPLASH_DISPLAY_LENGTH = 2000;
                handler.postDelayed(() -> {
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }, SPLASH_DISPLAY_LENGTH);
            });
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.5f;
            getWindow().setAttributes(lp);
            UpdateDialog.setOnDismissListener(dialogInterface -> {
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.alpha = 1f;
                getWindow().setAttributes(lp1);
            });
            UpdateDialog.show();
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