package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.content.Intent;
import android.util.Log;

import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

@SuppressWarnings("ALL")
public class ScanQRCodeCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public ScanQRCodeCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        final Intent intent = new Intent(proxy.context, StartScanActivity.class);
        BrowserActivity browserActivity = (BrowserActivity)this.proxy.context;
        browserActivity.startActivityForResultHere(intent, 1, (i, i1, intent1) -> {
            if (intent1 != null) {
                if (intent1.getIntExtra("resultcode", -1) == 1) {//succeed
                    final JSONObject jsonObject1 = new JSONObject();
                    try {
                        Log.i("扫码返回内容", intent1.getStringExtra("content"));
                        jsonObject1.put("resultStr", URLEncoder.encode(intent1.getStringExtra("content").replaceAll("\n", "%5cn").replaceAll("\r", "%5cr").replaceAll("\t", "%5ct").replaceAll("\\\\", "%5c")));
                        jsonObject1.put("type", "raw");
                    } catch (JSONException e) {
                        ScanQRCodeCommand.this.sendFailedResult("Decode Error");
                        e.printStackTrace();
                    }
                    this.sendSucceedResult(jsonObject1);
                    return true;
                } else if (intent1.getIntExtra("resultcode", -1) == 0) {
                    this.sendCancelResult();
                    return false;
                } else {
                    this.sendCancelResult();
                    return false;
                }
            }
            return false;
        });
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
