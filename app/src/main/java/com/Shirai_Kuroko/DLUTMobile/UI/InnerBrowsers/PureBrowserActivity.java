package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.QRCodeHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

import java.util.Objects;

public class PureBrowserActivity extends AppCompatActivity {

    private WebView webView;
    private LoadingView loading;
    boolean NoTitle = false;
    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_pure_browser);
        Intent intent = getIntent();
        String Url = intent.getStringExtra("Url");
        String Name = intent.getStringExtra("Name");
        webView=findViewById(R.id.PureBrowser);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(Name);
        }
        if(Objects.equals(Name, ""))
        {
            NoTitle=true;
        }
        loading = new LoadingView(this,R.style.CustomDialog);
        loading.show();
        webView.setWebChromeClient(this.webChromeClient);
        webView.setWebViewClient(this.webViewClient);
        WebSettings webSettings=webView.getSettings();
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
        webView.setBackgroundColor(Color.WHITE); // 设置背景色
        webView.getBackground().setAlpha(125); // 设置透明度 范围：0-255
        SyncCookie(this);
        webView.loadUrl(Url);
    }

    public void SyncCookie(Context context)
    {
        try {
            CookieSyncManager.createInstance(context);
            final CookieManager instance = CookieManager.getInstance();
            instance.setAcceptCookie(true);
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if(UserBean == null)
            {
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
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(UserBean.getData().getTgtinfo().get(0).getName());
            sb3.append("=");
            sb3.append(UserBean.getData().getTgtinfo().get(0).getValue());
            sb3.append("; Max-Age=");
            sb3.append("3600");
            instance.setCookie(".dlut.edu.cn", sb3.toString());
            instance.setCookie("api.dlut.edu.cn", sb3.toString());
            instance.setCookie("webvpn.dlut.edu.cn", sb3.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private final WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            Log.i("加载完成", url);
            loading.dismiss();
            if(NoTitle)
            {
                Objects.requireNonNull(getSupportActionBar()).setTitle(webView.getTitle());
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            loading.show();//显示加载条
            Log.i("开始加载", url);//日志记录加载了什么页面
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =request.getUrl().toString();
            //支付自动拉起外置应用
            if(url.startsWith("weixin://")|| url.startsWith("alipays://") || url.startsWith("upwrp://"))
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(request.getUrl());
                startActivity(intent);
                return true;
            }
            if(!url.startsWith("http://")&&!url.startsWith("https://"))
            {
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
    private final WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            Log.i("TAG", "onJsAlert: "+message);
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
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
            if(NoTitle)
            {
                Objects.requireNonNull(getSupportActionBar()).setTitle(title);
            }
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
    public void  getClient(String str){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        webView.destroy();
        webView.clearHistory();
        webView=null;
    }

    /* 创建菜单 */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新");
        menu.add(0, 1, 0, "浏览器打开");
        menu.add(0, 2, 0, "分享");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        switch (item.getItemId()) {
            case 0: {
                webView.reload();
                return true;
            }
            case 1:
            {
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(webView.getOriginalUrl());
                intent.setData(content_url);
                startActivity(intent);
                return true;
            }
            case 2:
            {
                float scale = webView.getScale();
                int width = webView.getWidth();
                int height = (int) (webView.getContentHeight() * scale + 0.5);
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//设置相应的图片质量
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);
                Bitmap qr = QRCodeHelper.createQRCodeBitmap(webView.getOriginalUrl(),200,200,"UTF-8","H","0", Color.BLACK,Color.WHITE);
                canvas.drawBitmap(qr,bitmap.getWidth()-210,bitmap.getHeight()-210,null);
                return MobileUtils.PureBrowserSharePictureToFriend(this,webView,bitmap);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public Resources getResources() {
        return MobileUtils.getResources(super.getResources());
    }
}