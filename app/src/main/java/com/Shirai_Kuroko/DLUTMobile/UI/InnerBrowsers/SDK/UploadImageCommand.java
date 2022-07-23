package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadImageCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public UploadImageCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        String Path;
        try {
            Path = jsonObject.getString("localId");
        } catch (JSONException e) {
            Toast.makeText(proxy.context, "出现错误"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            sendCancelResult();
            return;
        }
        BackendUtils.UploadImage(Path,this);
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
