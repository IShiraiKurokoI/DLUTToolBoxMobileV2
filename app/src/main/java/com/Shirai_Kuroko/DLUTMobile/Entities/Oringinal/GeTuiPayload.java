package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import java.io.*;

public class GeTuiPayload implements Serializable
{
    private String appkey;
    private String deviceToken;
    private PayloadBean payload;
    private boolean production_mode;
    private String timestamp;
    private String type;

    public GeTuiPayload() {
        super();
    }

    public String getAppkey() {
        return this.appkey;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public String getMsgType() {
        final PayloadBean payload = this.payload;
        if (payload != null && payload.getBody() != null) {
            return this.payload.getBody().getMsg_type();
        }
        return null;
    }

    public PayloadBean getPayload() {
        return this.payload;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getType() {
        return this.type;
    }

    public boolean isProduction_mode() {
        return this.production_mode;
    }

    public void setAppkey(final String appkey) {
        this.appkey = appkey;
    }

    public void setDeviceToken(final String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setPayload(final PayloadBean payload) {
        this.payload = payload;
    }

    public void setProduction_mode(final boolean production_mode) {
        this.production_mode = production_mode;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public static class Body implements Serializable
    {
        public static final String TYPE_NOTICE = "gift_notice";
        private String after_open;
        private Custom custom;
        private String msg_type;
        private boolean play_lights;
        private boolean play_sound;
        private boolean play_vibrate;
        private String ticker;
        private String title;

        public Body() {
            super();
        }

        public String getAfter_open() {
            return this.after_open;
        }

        public Custom getCustom() {
            return this.custom;
        }

        public String getMsg_type() {
            return this.msg_type;
        }

        public String getTicker() {
            return this.ticker;
        }

        public String getTitle() {
            return this.title;
        }

        public boolean isPlay_lights() {
            return this.play_lights;
        }

        public boolean isPlay_sound() {
            return this.play_sound;
        }

        public boolean isPlay_vibrate() {
            return this.play_vibrate;
        }

        public void setAfter_open(final String after_open) {
            this.after_open = after_open;
        }

        public void setCustom(final Custom custom) {
            this.custom = custom;
        }

        public void setMsg_type(final String msg_type) {
            this.msg_type = msg_type;
        }

        public void setPlay_lights(final boolean play_lights) {
            this.play_lights = play_lights;
        }

        public void setPlay_sound(final boolean play_sound) {
            this.play_sound = play_sound;
        }

        public void setPlay_vibrate(final boolean play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public void setTicker(final String ticker) {
            this.ticker = ticker;
        }

        public void setTitle(final String title) {
            this.title = title;
        }
    }

    public static class Custom implements Serializable
    {
        private String content;
        private String domain;
        private String msg_id;
        private String user_id;

        public Custom() {
            super();
        }

        public String getContent() {
            return this.content;
        }

        public String getDomain() {
            return this.domain;
        }

        public String getMsg_id() {
            return this.msg_id;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setContent(final String content) {
            this.content = content;
        }

        public void setDomain(final String domain) {
            this.domain = domain;
        }

        public void setMsg_id(final String msg_id) {
            this.msg_id = msg_id;
        }

        public void setUser_id(final String user_id) {
            this.user_id = user_id;
        }
    }

    public static class PayloadBean implements Serializable
    {
        private Body body;
        private String display_type;

        public PayloadBean() {
            super();
        }

        public Body getBody() {
            return this.body;
        }

        public String getDisplay_type() {
            return this.display_type;
        }

        public void setBody(final Body body) {
            this.body = body;
        }

        public void setDisplay_type(final String display_type) {
            this.display_type = display_type;
        }
    }
}
