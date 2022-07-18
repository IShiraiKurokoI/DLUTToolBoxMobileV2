package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.widget.Toast;

import org.json.JSONObject;

public class ChooseImageCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public ChooseImageCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        //ToDo:图片选择器
        Toast.makeText(proxy.context, "抱歉，暂未实现此功能", Toast.LENGTH_SHORT).show();
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
