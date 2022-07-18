package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;

public class OpenVirtualCardActivity extends AppCompatActivity {

    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_virtual_card);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        String Url = "https://card.m.dlut.edu.cn/virtualcardrj/openRjQrcodePage";
        webView = findViewById(R.id.VirtualCardWebview);
        webView.setWebChromeClient(this.webChromeClient);
        webView.setWebViewClient(this.webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUserAgentString(getString(R.string.UserAgent));//设置默认UA
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webView.setHorizontalScrollBarEnabled(false);
        //混合内容加载
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setDomStorageEnabled(true);
        //禁止系统缩放字体
        webSettings.setTextZoom(100);
        webView.setDrawingCacheEnabled(true);
        if (ConfigHelper.GetThemeType(this)) { //判断如果系统是深色主题
            webSettings.setForceDark(WebSettings.FORCE_DARK_ON);//强制开启webview深色主题模式
        } else {
            webSettings.setForceDark(WebSettings.FORCE_DARK_OFF);
        }
        //背景透明
        SyncCookie(this);
        webView.loadUrl(Url);
    }

    public void SyncCookie(Context context) {
        try {
            final CookieManager instance = CookieManager.getInstance();
            instance.setAcceptCookie(true);
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            String skey = infoDTO.getSkey();
            final StringBuilder sb = new StringBuilder();
            sb.append("whistlekey");
            sb.append('=');
            sb.append(skey);
            instance.setCookie(".dlut.edu.cn", sb.toString());
            instance.setCookie("api.dlut.edu.cn", sb.toString());
            instance.setCookie("webvpn.dlut.edu.cn", sb.toString());
            instance.setCookie("sso.dlut.edu.cn", sb.toString());
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(UserBean.getData().getTgtinfo().get(0).getName());
            sb3.append("=");
            sb3.append(UserBean.getData().getTgtinfo().get(0).getValue());
            sb3.append("; Max-Age=");
            sb3.append("3600");
            instance.setCookie(".dlut.edu.cn", sb3.toString());
            instance.setCookie("api.dlut.edu.cn", sb3.toString());
            instance.setCookie("webvpn.dlut.edu.cn", sb3.toString());
            instance.setCookie("sso.dlut.edu.cn", sb3.toString());
            instance.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            Log.i("加载完成", url);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String Un = prefs.getString("Username", "");
            String Pd = prefs.getString("Password", "");
            final boolean b = Un.length() * Pd.length() != 0;
            if (url.contains("api.m.dlut.edu.cn/login?client_id="))//api自动认证
            {
                LoginResponseBean.DataDTO.MyInfoDTO myInfoDTO = ConfigHelper.GetUserBean(getBaseContext()).getData().getMy_info();
                if (myInfoDTO != null) {
                    if (myInfoDTO.getSkey() != null) {
                        view.evaluateJavascript("getSsoKey('" + myInfoDTO.getSkey().replace("%3D", "") + "')", value -> {
                        });
                    }
                } else {
                    if (b) {
                        Toast.makeText(getBaseContext(), "正在执行认证，请稍候喵", Toast.LENGTH_SHORT).show();
                        view.evaluateJavascript("username.value='" + Un + "';password.value='" + Pd + "';submit.disabled='';submit.click()", value -> {
                        });
                    } else {
                        Log.i("认证信息为空", "弹出登陆");
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
                return;
            }
            if (url.contains("https://card.m.dlut.edu.cn/virtualcard/openVirtualcard?"))
            {
                view.evaluateJavascript("document.getElementsByClassName('top gradient2')[0].outerHTML=''", value -> {
                });
                return;
            }
            webView.clearHistory();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if(url.startsWith("https://card.m.dlut.edu.cn/home/openHomePage?"))
            {
                finish();
            }
            if (url.contains("https://card.m.dlut.edu.cn/virtualcard/openVirtualcard?"))
            {
                view.evaluateJavascript("document.getElementsByClassName('top gradient2')[0].outerHTML=''", value -> {
                });
                return;
            }
            Log.i("开始加载", url);//日志记录加载了什么页面
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            //支付自动拉起外置应用
            if (url.startsWith("weixin://") || url.startsWith("alipays://") || url.startsWith("upwrp://")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(request.getUrl());
                startActivity(intent);
                return true;
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(request.getUrl());
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private final WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            Log.i("TAG", "onJsAlert: " + message);
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();
            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }
    };

    /**
     * JS调用android的方法
     */
    @JavascriptInterface //仍然必不可少
    public void getClient(String str) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.destroy();
        webView = null;
    }
}