package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import org.json.JSONObject;

public class StartNativePageCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public StartNativePageCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(JSONObject jsonObject) {
        sendFailedResult(cmdName+"命令尚未实现");
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
