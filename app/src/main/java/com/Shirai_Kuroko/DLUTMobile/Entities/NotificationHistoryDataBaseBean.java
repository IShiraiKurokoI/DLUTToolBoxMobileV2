package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;

import java.io.Serializable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotificationHistoryDataBaseBean implements Serializable {
    private String _id;
    private String msg_id;
    private String create_time;
    private String app_id;
    private int is_read;
    private String title;
    private DLUTNoticeContentBean msg_content;

    public String getTitle() {
        return title;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getApp_id() {
        return app_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public DLUTNoticeContentBean getMsg_content() {
        return msg_content;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public void setMsg_content(DLUTNoticeContentBean msg_content) {
        this.msg_content = msg_content;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
