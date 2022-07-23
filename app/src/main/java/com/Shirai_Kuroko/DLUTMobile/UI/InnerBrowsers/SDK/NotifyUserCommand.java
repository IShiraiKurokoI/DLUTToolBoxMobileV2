package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.text.TextUtils;

import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.Serializable;

public class NotifyUserCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public NotifyUserCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(JSONObject jsonObject) {
        try {
            if (!jsonObject.has("playSound") && !jsonObject.has("vibrate") && (!jsonObject.has("toast") || TextUtils.isEmpty(jsonObject.getString("toast")))) {
                this.sendFailedResult("参数错误！");
                return;
            }
            final NotifyBean notifyBean = JSON.parseObject(jsonObject.toString(), NotifyBean.class);
            if (!TextUtils.isEmpty(notifyBean.getToast())) {
                if (!TextUtils.isEmpty(notifyBean.getToastSize())) {
                    new CenterToast(proxy.context).b(notifyBean.getToast(), notifyBean.getToastColor());
                }
                else {
                    new CenterToast(proxy.context).a(notifyBean.getToast());
                }
            }
            this.sendSucceedResult(new JSONObject());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.sendFailedResult("收到的数据格式有问题！");
        }
    }
    public class NotifyBean implements Serializable
    {
        private boolean playSound;
        public final NotifyUserCommand this$0;
        private String toast;
        private String toastColor;
        private String toastSize;
        private boolean vibrate;

        public NotifyBean(final NotifyUserCommand this$0) {
            super();
            this.this$0 = this$0;
        }

        public String getToast() {
            return this.toast;
        }

        public String getToastColor() {
            return this.toastColor;
        }

        public String getToastSize() {
            return this.toastSize;
        }

        public boolean isPlaySound() {
            return this.playSound;
        }

        public boolean isVibrate() {
            return this.vibrate;
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
