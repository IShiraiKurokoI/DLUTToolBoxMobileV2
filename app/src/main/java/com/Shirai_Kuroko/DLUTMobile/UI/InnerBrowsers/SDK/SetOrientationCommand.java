package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.annotation.SuppressLint;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;

import org.json.JSONException;
import org.json.JSONObject;

public class SetOrientationCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;

    public SetOrientationCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    @SuppressLint("WrongConstant")
    public void execute(JSONObject jsonObject) {
        final int n = 0;
        final int j0;
        try {
            j0 = jsonObject.getInt("toOrientation");
        } catch (JSONException e) {
            e.printStackTrace();
            LogToFile.e("SetOrientationCommand",e.getLocalizedMessage());
            return;
        }
        int requestedOrientation;
        if (j0 != 1) {
            requestedOrientation = n;
            if (j0 != 2) {
                if (j0 != 3) {
                    if (j0 == 4) {
                        requestedOrientation = 4;
                    }
                }
                else {
                    requestedOrientation = 8;
                }
            }
        }
        else {
            requestedOrientation = 1;
        }
        this.proxy.context.setRequestedOrientation(requestedOrientation);
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
