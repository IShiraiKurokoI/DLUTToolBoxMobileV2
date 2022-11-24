package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

public class LoginConfirmActivity extends AppCompatActivity {

    private Button a;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);
        String URL = getIntent().getStringExtra("UUID");
        Log.i("二维码地址", URL);
        WebView d1 = requireViewById(R.id.webView);
        Button btn_login_qrcode =requireViewById(R.id.btn_login_qrcode);
        a=btn_login_qrcode;
        btn_login_qrcode.setOnClickListener(view -> BackendUtils.QRLogin(this,URL));
        TextView tv_qrcode_login_cancel =requireViewById(R.id.tv_qrcode_login_cancel);
        tv_qrcode_login_cancel.setOnClickListener(view -> finish());
        d1.setWebChromeClient(new initWebView$1());
        d1.setWebViewClient(new initWebView$2(this));
        final WebSettings settings = d1.getSettings();
        settings.setUserAgentString(getString(R.string.UserAgent));
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
        settings.setMixedContentMode(0);
        d1.loadUrl(URL);
    }

    public static final class initWebView$1 extends WebChromeClient {}

    public static final class initWebView$2 extends WebViewClient {
        public final LoginConfirmActivity a;
        public initWebView$2(LoginConfirmActivity paramQRSsoLoginActivity) {
            a = paramQRSsoLoginActivity;
        }
        public void onPageFinished(WebView paramWebView, String paramString) {
            super.onPageFinished(paramWebView, paramString);
            Button button = this.a.a;
            if (button != null) {
                button.setVisibility(View.VISIBLE);
            }
        }

        public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
            super.onPageStarted(paramWebView, paramString, paramBitmap);
        }

        public void onReceivedError(WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceError paramWebResourceError) {
            super.onReceivedError(paramWebView, paramWebResourceRequest, paramWebResourceError);
            Toast.makeText(a, "错误"+paramWebResourceError.toString(), Toast.LENGTH_SHORT).show();
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, WebResourceRequest paramWebResourceRequest) {
            return super.shouldOverrideUrlLoading(paramWebView, paramWebResourceRequest);
        }
    }
}