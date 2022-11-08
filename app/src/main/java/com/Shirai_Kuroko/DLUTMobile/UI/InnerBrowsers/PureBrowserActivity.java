package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.QRCodeHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.BaseActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.BrowserProxy;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.WebDownloadListener;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

import java.util.Date;
import java.util.Objects;

public class PureBrowserActivity extends BaseActivity {

    private WebView webView;
//    private LoadingView loading;
    boolean NoTitle = false;
    private ProgressBar progressBar;

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_pure_browser);
        Intent intent = getIntent();
        String Url = intent.getStringExtra("Url");
        String Name = intent.getStringExtra("Name");
        try {
            String MsgID = intent.getStringExtra("MsgID");
            if (MsgID!=null)
            {
                new MsgHistoryManager(this).SetRead(MsgID);
                new NotificationHelper().Cancel(this, "1919810", "消息通知", Integer.parseInt(MsgID));
            }
        }catch (Exception e)
        {
            Log.i("Purebrowser", "无需设置已读");
        }
        webView = findViewById(R.id.PureBrowser);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_title = requireViewById(R.id.tv_title);
        tv_title.setText(Name);
        TextView tv_more = requireViewById(R.id.tv_more);
        tv_more.setOnClickListener(this::showPopupWindow);
        if (Objects.equals(Name, "")) {
            NoTitle = true;
        }
//        loading = new LoadingView(this, R.style.CustomDialog);
//        loading.show();
        webView.setWebChromeClient(this.webChromeClient);
        webView.setWebViewClient(this.webViewClient);
        webView.setDownloadListener(new WebDownloadListener(this));
        webView.addJavascriptInterface(new BrowserProxy(this, webView), "__nativeWhistleProxy");
        webView.addJavascriptInterface(new PicShareInterFace(), "Share");
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
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(false);
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
//        if (!Url.contains("rj")) {
//            loading.show();
//        }
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(Url.replace("202.118.65.217","webvpn.dlut.edu.cn"));
    }

    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(this).inflate(R.layout.popup_purebrowser_right_more, null);
        PopupWindow window = new PopupWindow(v, 480, 750, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setAnimationStyle(R.style.main_more_anim);
        window.showAsDropDown(view, 0, 0);
        v.findViewById(R.id.btn_refresh).setOnClickListener(view1 -> {
            webView.reload();
            window.dismiss();
        });
        v.findViewById(R.id.btn_open_browser).setOnClickListener(view12 -> {
            if (webView.getOriginalUrl().startsWith("file")) {
                Toast.makeText(getBaseContext(), "此页面无法在浏览器内打开", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(webView.getOriginalUrl());
            intent.setData(content_url);
            startActivity(intent);
            window.dismiss();
        });
        v.findViewById(R.id.btn_share).setOnClickListener(view13 -> {
            Toast.makeText(getBaseContext(), "正在生成分享图片", Toast.LENGTH_SHORT).show();
            webView.evaluateJavascript("window.Share.StartShare(document.getElementsByTagName('html')[0].scrollWidth,document.getElementsByTagName('html')[0].scrollHeight)", null);
            window.dismiss();
        });
        v.findViewById(R.id.btn_share_link).setOnClickListener(view14 -> {
            MobileUtils.ShareTextToFriend(getBaseContext(), "原始链接：" + webView.getOriginalUrl() + "\n当前页面：" + webView.getUrl());
            window.dismiss();
        });
        v.findViewById(R.id.btn_save).setOnClickListener(view15 -> {
            Toast.makeText(getBaseContext(), "正在生成保存图片", Toast.LENGTH_SHORT).show();
            webView.evaluateJavascript("window.Share.StartSave(document.getElementsByTagName('html')[0].scrollWidth,document.getElementsByTagName('html')[0].scrollHeight)", null);
            window.dismiss();
        });
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
//            loading.dismiss();
            progressBar.setVisibility(View.GONE);
            if (NoTitle) {
                TextView tv_title = requireViewById(R.id.tv_title);
                String title = webView.getTitle();
                if (!title.startsWith("https://")&&!title.startsWith("http://"))
                {
                    tv_title.setText(title);
                }
            }
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String Un = prefs.getString("Username", "");
            String Pd = prefs.getString("Password", "");
            final boolean b = Un.length() * Pd.length() != 0;
            if (url.contains("sso.dlut.edu.cn/cas/login?service=") || url.contains("webvpn.dlut.edu.cn%2Flogin%3Fcas_login%3Dtrue"))//sso自动认证
            {
                if (b) {
                    Toast.makeText(getBaseContext(), "正在执行认证，请稍候喵", Toast.LENGTH_SHORT).show();
                    view.evaluateJavascript("$(\"#un\").val('" + Un + "');$(\"#pd\").val('" + Pd + "');login()", value -> {
                    });
                } else {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
                    localBuilder.setMessage("个人信息未配置完全，集成认证失败，请手动认证并前往设置界面补全信息！").setPositiveButton("确定", null);
                    localBuilder.setCancelable(false);
                    localBuilder.create().show();
                }
                return;
            }
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
                        AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
                        localBuilder.setMessage("个人信息未配置完全，集成认证失败，请手动认证并前往设置界面补全信息！").setPositiveButton("确定", null);
                        localBuilder.setCancelable(false);
                        localBuilder.create().show();
                    }
                }
                return;
            }
            if (url.contains("webvpn.dlut.edu.cn/login"))//webvpn跳转处理
            {
                view.evaluateJavascript("document.getElementById('cas-login').click()", value -> {
                });
                return;
            }
            webView.clearHistory();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
//            if (!url.contains("https://api.m.dlut.edu.cn/login?")) {
//                loading.show();//显示加载条
                progressBar.setVisibility(View.VISIBLE);
//            }
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
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress,true);
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (NoTitle) {
                TextView tv_title = requireViewById(R.id.tv_title);
                Log.i("TAG", "onReceivedTitle: "+title);
                if (!title.startsWith("https://")&&!title.startsWith("http://"))
                {
                    tv_title.setText(title);
                }
            }
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.setType("*/*"); // 设置文件类型
            String[] mimeTypes = fileChooserParams.getAcceptTypes();
            i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes); // 设置多种类型
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResultHere(Intent.createChooser(i, "Image Chooser"), 2, (requestCode, resultCode, intent) -> {
                Uri[] results = null;
                if (resultCode == Activity.RESULT_OK) {
                    if (intent != null) {
                        String dataString = intent.getDataString();
                        ClipData clipData = intent.getClipData();
                        if (clipData != null) {
                            results = new Uri[clipData.getItemCount()];
                            for (int i1 = 0; i1 < clipData.getItemCount(); i1++) {
                                ClipData.Item item = clipData.getItemAt(i1);
                                results[i1] = item.getUri();
                            }
                        }
                        if (dataString != null)
                            results = new Uri[]{Uri.parse(dataString)};
                    }
                }
                filePathCallback.onReceiveValue(results);
                return false;
            });
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (!getIntent().getBooleanExtra("NoClean",false))
        {
            webView.destroy();
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            webView = null;
        }
    }

    /* 创建菜单 */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新此页面");
        menu.add(0, 1, 0, "浏览器打开");
        menu.add(0, 2, 0, "分享此页面");
        menu.add(0, 3, 0, "分享原链接");
        menu.add(0, 4, 0, "保存此页面");
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
            case 1: {
                if (webView.getOriginalUrl().startsWith("file")) {
                    Toast.makeText(this, "此页面无法在浏览器内打开", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(webView.getOriginalUrl());
                intent.setData(content_url);
                startActivity(intent);
                return true;
            }
            case 2: {
                Toast.makeText(getBaseContext(),"正在生成分享图片",Toast.LENGTH_SHORT).show();
                webView.evaluateJavascript("window.Share.StartShare(document.getElementsByTagName('html')[0].scrollWidth,document.getElementsByTagName('html')[0].scrollHeight)", null);
                return true;
            }
            case 3: {
                MobileUtils.ShareTextToFriend(getBaseContext(),"原始链接："+webView.getOriginalUrl()+"\n当前页面："+webView.getUrl());
                return true;
            }
            case 4: {
                Toast.makeText(getBaseContext(),"正在生成保存图片",Toast.LENGTH_SHORT).show();
                webView.evaluateJavascript("window.Share.StartSave(document.getElementsByTagName('html')[0].scrollWidth,document.getElementsByTagName('html')[0].scrollHeight)", null);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class PicShareInterFace {
        @JavascriptInterface
        public void StartShare(String s,String s1) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                float scale = webView.getScale();
                int width = (int) (Integer.parseInt(s) * scale);
                int height;
                if (!webView.getOriginalUrl().startsWith("file")) {
                    height = (int) (Integer.parseInt(s1) * scale + 220);
                } else {
                    height = (int) (Integer.parseInt(s1) * scale);
                }
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//设置相应的图片质量
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);
                if (!webView.getOriginalUrl().startsWith("file")) {
                    Bitmap qr = QRCodeHelper.createQRCodeBitmap(webView.getOriginalUrl(), 200, 200, "UTF-8", "L", "0", Color.BLACK, Color.WHITE);
                    canvas.drawBitmap(qr, 10, bitmap.getHeight() - 210, null);
                }
                MobileUtils.PureBrowserSharePictureToFriend(getBaseContext(), webView, bitmap);
            });
        }
        @JavascriptInterface
        public void StartSave(String s,String s1) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                float scale = webView.getScale();
                int width = (int) (Integer.parseInt(s) * scale);
                int height;
                if (!webView.getOriginalUrl().startsWith("file")) {
                    height = (int) (Integer.parseInt(s1) * scale + 220);
                } else {
                    height = (int) (Integer.parseInt(s1) * scale);
                }
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//设置相应的图片质量
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);
                if (!webView.getOriginalUrl().startsWith("file")) {
                    Bitmap qr = QRCodeHelper.createQRCodeBitmap(webView.getOriginalUrl(), 200, 200, "UTF-8", "L", "0", Color.BLACK, Color.WHITE);
                    canvas.drawBitmap(qr, 10, bitmap.getHeight() - 210, null);
                }
                MobileUtils.SaveImageToGallery(getBaseContext(),bitmap,webView.getTitle()+new Date().toLocaleString()+".bmp");
            });
        }
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Resources getResources() {
        return MobileUtils.getResources(super.getResources());
    }
}