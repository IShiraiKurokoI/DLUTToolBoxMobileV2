package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("ALL")
public class BrowserProxy{
    public Activity context;
    private boolean isAuthed;
    private Handler mainHandler;
    public WebView webview;
    private static final String TAG = "BrowserProxy";

    public BrowserProxy(final Activity context, final WebView webview) {
        super();
        this.isAuthed = true;
        this.context = context;
        this.webview = webview;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @JavascriptInterface
    public boolean configApp(final String s) {
        return this.isAuthed;
    }

    @JavascriptInterface
    public void debugOut(final String str) {
        String sb = "debugOut=====>" +
                str;
        Log.d("内置浏览器", sb);
    }

    @SuppressWarnings("ALL")
    public Class findClass(final String s) throws ClassNotFoundException {
        final String substring = s.substring(0, 1);
        String ClassName = "com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK." +substring.toUpperCase()+s.substring(1)+"Command";
        return Class.forName(ClassName);
    }

    @JavascriptInterface
    @SuppressWarnings("ALL")
    public void issueCommand(String tag, String s, String tag2) {
        Log.i("内置浏览器", "调用指令 CMDID:"+tag+" CMDNAME: "+s+" PARAS:"+tag2);

        Object o = null;
        try {
            o = new JSONObject(tag2);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        try {
            final Class class1 = this.findClass(s);
            final Object instance = class1.getConstructor(BrowserProxy.class, String.class, String.class).newInstance(this, tag, s);
            final Method method = class1.getMethod("execute", JSONObject.class);
            if (method != null) {
                method.invoke(instance, o);
            }
        }
        catch (ClassNotFoundException | InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        {
            Toast.makeText(context, "方法暂未实现"+s+"\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "issueCommand: ", e);
        }
    }

    public void runOnUiThread(final Runnable runnable) {
        this.mainHandler.post(runnable);
    }

    @SuppressWarnings("ALL")
    public void sendCancelResult(final String s, String tag) {
        final JSONObject jsonObject = new JSONObject();
        final StringBuilder sb = new StringBuilder();
        sb.append(tag);
        sb.append(":cancel");
        try {
            jsonObject.put("errMsg",sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sb2 = "Whistle.__onCommandCancelCallback('" +
                s +
                "', '" +
                jsonObject +
                "');";
        Log.i(TAG, "sendSucceedResult: " +sb2);
        this.runOnUiThread(() -> webview.evaluateJavascript(sb2, s1 -> {
        }));
    }

    @SuppressWarnings("ALL")
    public void sendFailedResult(final String s, String s2, final int n, String string) {
        JSONObject _s2 =  new JSONObject();
        if (n != 0) {
            try {
                _s2.put("errCode", n);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        try {
            _s2.put("errMsg",string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sb = "Whistle.__onCommandFailCallback('" +
                s +
                "', '" +
                _s2 +
                "');";
        Log.i(TAG, "sendFailedResult: "+sb);
        this.runOnUiThread(() -> webview.evaluateJavascript(sb, s1 -> {
        }));
    }

    @SuppressWarnings("ALL")
    public void sendSucceedResult(final String s, String string, final JSONObject jsonObject) {
        final StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(":ok");
        try {
            jsonObject.put("errMsg",sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sb2 = "Whistle.__onCommandSuccessCallback('" +
                s +
                "', '" +
                jsonObject +
                "');";
        Log.i(TAG, "sendSucceedResult: " +sb2);
        this.runOnUiThread(() -> webview.evaluateJavascript(sb2, s1 -> {
        }));
    }

    public void sendFailedResult(final String s, final String s2, final String s3) {
        this.sendFailedResult(s, s2, 0, s3);
    }

    @SuppressWarnings("ALL")
    public void onScanResult(final JSONObject jsonObject) {
        String sb = "Whistle.__onScanCallback('" +
                jsonObject +
                "');";
        Log.i(TAG, "sendSucceedResult: " +sb);
        this.runOnUiThread(() -> webview.evaluateJavascript(sb, s1 -> {
        }));
    }
}
