package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetVersionCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public GetVersionCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(JSONObject jsonObject) {
        try {
            final JSONObject jsonObject2 = new JSONObject();
            String s;
            final String input = s = D(this.proxy.context);
            if (!TextUtils.isEmpty((CharSequence) input)) {
                final Matcher matcher = Pattern.compile(".*\\..*\\..*\\.").matcher(input);
                s = input;
                if (matcher.find()) {
                    final String group = matcher.group();
                    s = group.substring(0, group.length() - 1);
                }
            }
            try {
                jsonObject2.put("whistle_version", s);
            } catch (JSONException e) {
                e.printStackTrace();
                this.sendFailedResult(e.getMessage());
                return;
            }
            this.sendSucceedResult(jsonObject2);
        } catch (Exception e) {
            this.sendFailedResult(e.getMessage());
        }
    }

    public static String D(final Context context) {
        final PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void sendCancelResult() {
        this.proxy.sendCancelResult(this.cmdId, this.cmdName);
    }

    public void sendFailedResult(String replace) {
        replace = replace.replace(":", " ").replace("'", " ").replace("\"", " ");
        this.proxy.sendFailedResult(this.cmdId, this.cmdName, replace);
    }

    public void sendSucceedResult(final JSONObject jsonObject) {
        this.proxy.sendSucceedResult(this.cmdId, this.cmdName, jsonObject);
    }
}
