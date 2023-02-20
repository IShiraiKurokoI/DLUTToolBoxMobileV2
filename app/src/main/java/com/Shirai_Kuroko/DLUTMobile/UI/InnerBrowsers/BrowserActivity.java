package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.QRCodeHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.BaseActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.BrowserProxy;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.WebDownloadListener;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Date;

@SuppressWarnings("ALL")
public class BrowserActivity extends BaseActivity {

    int numid = 0;
    ApplicationConfig thisapp;
    private WebView webView;
    //    private LoadingView loading;
    private Context mContext;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        super.onCreate(savedInstanceState);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_browser);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_title = requireViewById(R.id.tv_title);
        TextView tv_more = requireViewById(R.id.tv_more);
        mContext = this;
        Intent intent = getIntent();
        String id = intent.getStringExtra("App_ID");
        numid = Integer.parseInt(id);
        thisapp = ConfigHelper.Getmlist(this).get(numid);
        tv_title.setText(thisapp.getAppName());
        tv_more.setOnClickListener(this::showPopupWindow);
        if(thisapp.getId()!=78)
        {
            SyncCookie(this);
        }
        webView = findViewById(R.id.BrowserWebView);
//        loading = new LoadingView(this, R.style.CustomDialog);
        progressBar = findViewById(R.id.progressbar);
//        if (!thisapp.getUrl().contains("rj")) {
        progressBar.setVisibility(View.VISIBLE);
//        }
        BrowserProxy browserProxy = new BrowserProxy(this, webView);
        webView.addJavascriptInterface(browserProxy, "__nativeWhistleProxy");
        webView.addJavascriptInterface(new PicShareInterFace(), "Share");
        webView.setWebChromeClient(this.webChromeClient);
        webView.setWebViewClient(this.webViewClient);
        webView.setDownloadListener(new WebDownloadListener(this));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        switch (numid) {
            case 24: {
                webSettings.setUserAgentString(getString(R.string.PCUserAgent));//设置四六级查询所需PCUserAgent
                break;
            }
            default: {
                webSettings.setUserAgentString(getString(R.string.UserAgent));//设置默认UA
                break;
            }
        }
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
        webSettings.setGeolocationEnabled(true);
        if (ConfigHelper.GetThemeType(this)) { //判断如果系统是深色主题
            webSettings.setForceDark(WebSettings.FORCE_DARK_ON);//强制开启webview深色主题模式
        } else {
            webSettings.setForceDark(WebSettings.FORCE_DARK_OFF);
        }
        //背景透明
        webView.setBackgroundColor(0); // 设置背景色
        webView.getBackground().setAlpha(0); // 设置透明度 范围：0-255
        if (thisapp.getId() != 66) {
            webView.loadUrl(thisapp.getUrl());
        } else {
            webView.loadUrl("https://news.dlut.edu.cn/ttgz.htm");
        }
        if(thisapp.getId()!=78)
        {
            SyncCookie(this);
        }
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

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    public final WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            Log.i("加载完成", url);
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
                view.evaluateJavascript("document.getElementById('cas-login').click()", null);
                return;
            }
            SpecialHandle(url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            Log.i("开始加载", url);//日志记录加载了什么页面
            switch (thisapp.getId()) {
                case 51: {
                    if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/home")) {
                        webView.loadUrl("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/eleCostOfDlutPay?projectId=33");
                    }
                    break;
                }
                case 70://选课系统自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.evaluateJavascript("window.location.href='/student/for-std/course-select'", null);
                    }
                    break;
                }
                case 71://考试信息自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.getSettings().setLoadWithOverviewMode(false);
                        webView.getSettings().setUseWideViewPort(false);
                        webView.evaluateJavascript("window.location.href='/student/for-std/exam-arrange'", null);
                    }
                    break;
                }
                case 73://成绩信息自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.getSettings().setLoadWithOverviewMode(false);
                        webView.getSettings().setUseWideViewPort(false);
                        webView.evaluateJavascript("window.location.href='/student/for-std/grade/sheet'", null);
                    }
                    break;
                }
                case 74://评教系统自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.getSettings().setLoadWithOverviewMode(false);
                        webView.getSettings().setUseWideViewPort(false);
                        webView.evaluateJavascript("window.location.href='/student/for-std/evaluation/summative'", null);
                    }
                    break;
                }
                case 75://我的课表自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.evaluateJavascript("window.location.href='/student/for-std/course-table'", null);
                    }
                    break;
                }
                case 76://班级课表自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.evaluateJavascript("window.location.href='/student/for-std/adminclass-course-table'", null);
                    }
                    break;
                }
                case 77://培养方案自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.evaluateJavascript("window.location.href='/student/for-std/program-completion-preview'", null);
                    }
                    break;
                }
                case 79://开课查询自动跳转
                {
                    if (url.contains("/student/home")) {
                        webView.evaluateJavascript("window.location.href='/student/for-std/lesson-search'", null);
                    }
                    break;
                }
                case 82: {
                    if (url.contains("guid.lib.dlut.edu.cn)")) {
                        webView.loadUrl("https://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f7e24898693c6152300c85b98c1b2631a4d2be57/map/index.html");
                    }
                    break;
                }
                case 83: {
                    if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/home")) {
                        webView.loadUrl("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/netCostOfSlPay?projectId=28");
                    }
                    break;
                }
                case 85: {
                    webView.getSettings().setLoadWithOverviewMode(false);
                    webView.getSettings().setUseWideViewPort(false);
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                    ((Activity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    break;
                }
                default://默认加载完成隐藏加载条
                {
//                    if (!url.contains("https://api.m.dlut.edu.cn/login?")) {
                    progressBar.setVisibility(View.VISIBLE);//显示加载条
//                    }
                    break;
                }
            }
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

    private void SpecialHandle(String url) {
        switch (thisapp.getId()) {
            case 14://邮箱自动填充
            {
                if (url.equals("https://mail.dlut.edu.cn/coremail/common/xphone_dllg/index.jsp?locale=zh_CN")) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String MailAddress = prefs.getString("MailAddress", "");
                    String MailPassword = prefs.getString("MailPassword", "").split("@")[0];
                    if (MailAddress.length() * MailPassword.length() != 0) {
                        webView.evaluateJavascript("username.value='" + MailAddress + "'", null);
                        webView.evaluateJavascript("password.value='" + MailPassword + "'", null);
                        webView.evaluateJavascript("domain.value='mail.dlut.edu.cn'", null);
                        webView.evaluateJavascript("document.getElementsByClassName('loginBtn')[0].click()", null);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
                        localBuilder.setMessage("邮件信息未配置完全，集成认证失败，请手动认证并前往设置界面补全信息！").setPositiveButton("确定", null);
                        localBuilder.setCancelable(false);
                        localBuilder.create().show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }

                break;
            }
            case 51: {
                if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/home")) {
                    webView.loadUrl("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/eleCostOfDlutPay?projectId=33");
                }
                if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/eleCostOfDlutPay?projectId=33")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 70://选课系统自动跳转
            {
                if (url.contains("course-select")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 71://考试信息自动跳转
            {
                if (url.contains("exam-arrange")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 73://成绩信息自动跳转
            {
                if (url.contains("for-std/grade/sheet")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 74://评教系统自动跳转
            {
                if (url.contains("evaluation")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 75://我的课表自动跳转
            {
                if (url.contains("course-table")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 76://班级课表自动跳转
            {
                if (url.contains("adminclass-course-table")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 77://培养方案自动跳转
            {
                if (url.contains("program-completion-preview")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
//            case 78://校园网自服务跳转处理
//            {
//                if (Objects.equals(url, "http://172.20.20.1:8800/user/operate/index")) {
//                    webView.evaluateJavascript("document.getElementsByClassName('radio')[0].innerHTML='<label><input type=\"radio\" name=\"OperateForm[shiftType]\" value=\"1\"> 立即生效</label><label><input type=\"radio\" name=\"OperateForm[shiftType]\" value=\"2\"> 下个周期生效</label>'", null);
//                    webView.evaluateJavascript("document.getElementsByTagName('select')[1].innerHTML+='<option value=\"13\">包月限100G</option>'", null);
//                }
//                if (Objects.equals(url, "http://172.20.20.1:8800/")) {
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                    String Un = prefs.getString("Username", "");
//                    String NPd = prefs.getString("NetworkPassword", "");
//                    final boolean c = Un.length() * NPd.length() != 0;
//                    if (c) {
//                        webView.evaluateJavascript("document.getElementById('loginform-username').value=" + Un, null);
//                        webView.evaluateJavascript("document.getElementById('loginform-password').value='" + NPd + "'", null);
//                    } else {
//                        AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
//                        localBuilder.setMessage("校园网信息未配置完全，集成认证失败，请手动认证并前往设置界面补全信息！").setPositiveButton("确定", null);
//                        localBuilder.setCancelable(false);
//                        localBuilder.create().show();
//                    }
//                }
//                break;
//            }
            case 79://开课查询自动跳转
            {
                if (url.contains("lesson-search")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            case 82: {
                if (url.contains("guid.lib.dlut.edu.cn)")) {
                    webView.loadUrl("https://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f7e24898693c6152300c85b98c1b2631a4d2be57/map/index.html");
                }
                break;
            }
            case 83: {
                if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/home")) {
                    webView.loadUrl("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/netCostOfSlPay?projectId=28");
                }
                if (url.contains("http://webvpn.dlut.edu.cn/http/77726476706e69737468656265737421f5f4408e23206949730d87b8d6512f209640763a21f75b0c/mobile/#/netCostOfSlPay?projectId=28")) {
                    webView.clearHistory();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            }
            default://默认加载完成隐藏加载条
            {
                progressBar.setVisibility(View.GONE);
                break;
            }
        }
    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    public final WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            LogToFile.i("Webview:" + consoleMessage.messageLevel().toString(), consoleMessage.message());
            Log.i("Webview:" + consoleMessage.messageLevel().toString(), consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            final boolean remember = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("位置信息");
            builder.setMessage(origin + "允许获取您的地理位置信息吗？").setCancelable(true).setPositiveButton("允许",
                            (dialog, id) -> callback.invoke(origin, true, remember))
                    .setNegativeButton("不允许",
                            (dialog, id) -> callback.invoke(origin, false, remember));
            AlertDialog alert = builder.create();
            alert.show();
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            //直接同意即可     deny是拒绝
            request.grant(request.getResources());
        }

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

        CustomViewCallback customViewCallback;
        Dialog dialog;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (dialog != null) {
                callback.onCustomViewHidden();
                return;
            }
            customViewCallback = callback;
            dialog = new Dialog(mContext, R.style.CustomDialog);
            Window window = dialog.getWindow();
            window.setContentView(view);
            window.setGravity(Gravity.CENTER);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnDismissListener(dialogInterface -> {
                ((BrowserActivity) mContext).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                ((BrowserActivity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            });
            ((BrowserActivity) mContext).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
            ((BrowserActivity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.show();
        }

        @Override
        public void onHideCustomView() {
            if (dialog == null)
                return;
            dialog.dismiss();
            dialog = null;
            customViewCallback.onCustomViewHidden();
        }

        @Nullable
        @Override
        public View getVideoLoadingProgressView() {
            return super.getVideoLoadingProgressView();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress, true);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.setType("*/*"); // 设置文件类型
            String[] mimeTypes = fileChooserParams.getAcceptTypes();
            i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes); // 设置多种类型
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResultHere(Intent.createChooser(i, fileChooserParams.getTitle()), 2, (requestCode, resultCode, intent) -> {
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
        webView.destroy();
        webView = null;
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        if (thisapp.getId() == 1) {
            Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
            sendBroadcast(intent2);
        }
    }

    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(this).inflate(R.layout.popup_browser_right_more, null);
        PopupWindow window = new PopupWindow(v, 480, 900, true);
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
                Toast.makeText(mContext, "此页面无法在浏览器内打开", Toast.LENGTH_SHORT).show();
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
            MobileUtils.ShareTextToFriend(mContext, "原始链接：" + webView.getOriginalUrl() + "\n当前页面：" + webView.getUrl());
            window.dismiss();
        });
        v.findViewById(R.id.btn_save).setOnClickListener(view15 -> {
            Toast.makeText(getBaseContext(), "正在生成保存图片", Toast.LENGTH_SHORT).show();
            webView.evaluateJavascript("window.Share.StartSave(document.getElementsByTagName('html')[0].scrollWidth,document.getElementsByTagName('html')[0].scrollHeight)", null);
            window.dismiss();
        });
        v.findViewById(R.id.btn_pin).setOnClickListener(view16 -> {
            downShortcutICon();
            window.dismiss();
        });
    }

    public class PicShareInterFace {
        @JavascriptInterface
        public void StartShare(String s, String s1) {
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
                MobileUtils.BrowserSharePictureToFriend(mContext, webView, thisapp, bitmap);
            });
        }

        @JavascriptInterface
        public void StartSave(String s, String s1) {
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
                MobileUtils.SaveImageToGallery(getBaseContext(), bitmap, thisapp.getAppName() + new Date().toLocaleString() + ".bmp");
            });
        }
    }

    private void downShortcutICon() {
        final Bitmap[] bitmap = new Bitmap[1]; //先下载图标 转为bitMap
        Glide.with(mContext).asBitmap().load(thisapp.getIcon()).into(new SimpleTarget() {
            @Override
            public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                bitmap[0] = (Bitmap) resource;
                if (bitmap[0] != null) {
                    addShortCutCompact(bitmap[0]);
                }
            }
        });
    }

    /**
     * 添加快捷方式
     */
    public void addShortCutCompact(Bitmap bitmap) {
        //启动器是否支持添加快捷方式
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(mContext)) {
            Intent intent = getIntent();
            intent.setAction(Intent.ACTION_DEFAULT);
            intent.putExtra("Remain", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(mContext, thisapp.getId() + thisapp.getAppName()) //设置图标icon
                    .setIcon(IconCompat.createWithBitmap(bitmap)) //设置名称
                    .setShortLabel(thisapp.getAppName())
                    .setIntent(intent)
                    .build(); //创建快捷方式
            ShortcutManagerCompat.requestPinShortcut(mContext, info, null);
        } else {
            Toast.makeText(mContext, "启动器不支持固定快捷方式", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
        } else {
            if (getIntent().getBooleanExtra("Remain", true)) {
                super.onBackPressed();
                this.finish();
            } else {
                super.onBackPressed();
            }
        }
    }
}