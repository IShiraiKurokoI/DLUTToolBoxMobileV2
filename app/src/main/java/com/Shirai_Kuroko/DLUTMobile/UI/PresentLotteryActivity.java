package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;

public class PresentLotteryActivity extends AppCompatActivity {

    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_lottery);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        webView = requireViewById(R.id.LoteryWebview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setUserAgentString(getString(R.string.UserAgent));//设置默认UA
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        //混合内容加载
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setDomStorageEnabled(true);
        //禁止系统缩放字体
        webSettings.setTextZoom(100);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webView.setDrawingCacheEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setSavePassword(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(false);
        if (ConfigHelper.GetThemeType(this)) { //判断如果系统是深色主题
            webSettings.setForceDark(WebSettings.FORCE_DARK_ON);//强制开启webview深色主题模式
        } else {
            webSettings.setForceDark(WebSettings.FORCE_DARK_OFF);
        }
        String url ="https://lightapp.m.dlut.edu.cn/lottery/?score="+ConfigHelper.GetUserScoreBean(this).getData().getUser_points();
        synCookies(url);
        webView.setWebViewClient(this.webViewClient);
        webView.loadUrl(url);
        Toast.makeText(this, "嘶。。。。这个页面还没做完", Toast.LENGTH_SHORT).show();
    }

    public final WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            synCookies(url);
            super.onPageStarted(view, url, favicon);
        }
    };

    public void synCookies(final String s) {
        final CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        final String string = "serverUrl=" +
                "https://service.m.dlut.edu.cn/whistlenew/index.php" +
                ";";
        String sb2 = string +
                "; path=/; ";
        instance.setCookie(s, sb2);
        final String string2 = "school=" +
                "dlut" +
                ";";
        String sb4 = string2 +
                "; path=/; ";
        instance.setCookie(s, sb4);
        final String string3 = "verify=" +
                ConfigHelper.GetUserBean(getBaseContext()).getData().getVerify() +
                ";";
        String sb6 = string3 +
                "; path=/; ";
        instance.setCookie(s, sb6);
        instance.setCookie(s, "deviceType=android;; path=/; ");
        final String string4 = "app_version=" +
                "3.2.74625" +
                ";";
        String sb8 = string4 +
                "; path=/; ";
        instance.setCookie(s, sb8);
        final String string5 = "identity=" +
                "student" +
                ";";
        String sb10 = string5 +
                "; path=/; ";
        instance.setCookie(s, sb10);
        final String string6 = "aid=" +
                ConfigHelper.GetUserBean(getBaseContext()).getData().getMy_info().getUser_id() +
                "";
        String sb12 = string6 +
                "; path=/; ";
        instance.setCookie(s, sb12);
        instance.flush();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        webView.destroy();
        webView = null;
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
        } else {
            super.onBackPressed();
        }
    }
}