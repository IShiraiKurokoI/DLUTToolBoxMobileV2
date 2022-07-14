package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

public class LoginConfirmActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);
        String URL = getIntent().getStringExtra("UUID");
        Log.i("二维码地址", URL);
        WebView webView = requireViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(getString(R.string.UserAgent));//设置默认UA
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setSavePassword(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setTextZoom(100);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadUrl(URL);
        Button btn_login_qrcode =requireViewById(R.id.btn_login_qrcode);
        btn_login_qrcode.setOnClickListener(view -> BackendUtils.QRLogin(this,URL));
        TextView tv_qrcode_login_cancel =requireViewById(R.id.tv_qrcode_login_cancel);
        tv_qrcode_login_cancel.setOnClickListener(view -> finish());
    }
}