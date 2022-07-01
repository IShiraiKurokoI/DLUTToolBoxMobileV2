package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;
import android.text.*;

public class NoticeBean implements Serializable
{
    public static final int MSG_TYPE_APP_MESSAGE = 2;
    public static final int MSG_TYPE_APP_MESSAGE_ENTRY = 10086;
    public static final int MSG_TYPE_DLUT_APP_MESSAGE = 4;
    public static final int MSG_TYPE_DLUT_DEFAULT_MESSAGE = 100;
    public static final int MSG_TYPE_INNER_MAIL = 1;
    public static final int MSG_TYPE_NEW_DIVIDER = 50;
    public static final int MSG_TYPE_NEW_UN_SUPPORT = 60;
    public static final int MSG_TYPE_NOTICE = 0;
    public static final int MSG_TYPE_PRIVATE_MESSAGE = 1;
    public static final int MSG_TYPE_SCHEDULE_MESSAGE = 2;
    public static final int MSG_TYPE_TO_DO_LIST_MESSAGE = 3;
    public static final int MSG_TYPE_UNKNOWN = 0;
    public static final int MSG_VIEW_TYPE_LINK_NOTICE = 1;
    public static final int MSG_VIEW_TYPE_MULTIPLE_NOTICE = 3;
    public static final int MSG_VIEW_TYPE_NEW_DIVIDER = 4;
    public static final int MSG_VIEW_TYPE_PIC_NOTICE = 2;
    public static final int MSG_VIEW_TYPE_UN_SUPPORT = 5;
    private static final long serialVersionUID = 1L;
    private String app_id;
    private AppBean app_info;
    private long create_time;
    private EntryEntity extraForEntry;
    private int isCancel;
    private int isSendFlag;
    private int is_authoritative;
    private boolean is_collected;
    private boolean is_read;
    private int is_receipt;
    private int is_receipted_flag;
    private String msg_content;
    private String msg_id;
    private int msg_type;
    private MsgReadState readState;
    private String receipt_opt;
    private String receipt_opt_info;
    private String receipt_opt_json;
    private long recv_time;
    private boolean selected;
    private long send_time;
    private String send_uid;
    private boolean showTime;
    private String title;
    private UserBean user_info;

    public NoticeBean() {
        this.selected = false;
        this.user_info = new UserBean();
        this.readState = new MsgReadState();
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public final AppBean getApp_info() {
        if (this.app_info == null) {
            this.app_info = new AppBean();
        }
        return this.app_info;
    }

    public long getCreate_time() {
        return this.create_time;
    }

    public EntryEntity getExtraForEntry() {
        return this.extraForEntry;
    }

    public int getIsCancel() {
        return this.isCancel;
    }

    public int getIsSendFlag() {
        return this.isSendFlag;
    }

    public int getIs_authoritative() {
        return this.is_authoritative;
    }

    public int getIs_receipt() {
        return this.is_receipt;
    }

    public int getIs_receipted_flag() {
        return this.is_receipted_flag;
    }

    public String getMsg_content() {
        return this.msg_content;
    }

    public String getMsg_id() {
        return this.msg_id;
    }

    public int getMsg_type() {
        return this.msg_type;
    }

    public MsgReadState getReadState() {
        return this.readState;
    }

    public String getReceipt_opt() {
        return this.receipt_opt;
    }

    public String getReceipt_opt_info() {
        if (TextUtils.isEmpty((CharSequence)this.receipt_opt_info)) {
            return "";
        }
        return this.receipt_opt_info;
    }

    public String getReceipt_opt_json() {
        String receipt_opt_json;
        if ((receipt_opt_json = this.receipt_opt_json) == null) {
            receipt_opt_json = "{}";
        }
        return receipt_opt_json;
    }

    public long getRecv_time() {
        return this.recv_time;
    }

    public long getSend_time() {
        return this.send_time;
    }

    public String getSend_uid() {
        return this.send_uid;
    }

    public String getShowName() {
        final UserBean user_info = this.user_info;
        if (user_info != null) {
            return user_info.getName();
        }
        return "\u795e\u79d8\u4eba";
    }

    public String getTitle() {
        return this.title;
    }

    public UserBean getUser_info() {
        return this.user_info;
    }

    public boolean isAuthoritative() {
        final int is_authoritative = this.is_authoritative;
        boolean b = true;
        if (is_authoritative != 1) {
            b = false;
        }
        return b;
    }

    public boolean isCancel() {
        final int isCancel = this.isCancel;
        boolean b = true;
        if (isCancel != 1) {
            b = false;
        }
        return b;
    }

    public boolean isCollected() {
        return this.is_collected;
    }

    public boolean isMark() {
        final int is_receipt = this.is_receipt;
        boolean b = true;
        if (is_receipt != 1) {
            b = false;
        }
        return b;
    }

    public boolean isMarked() {
        final int is_receipted_flag = this.is_receipted_flag;
        boolean b = true;
        if (is_receipted_flag != 1) {
            b = false;
        }
        return b;
    }

    public boolean isRead() {
        return this.is_read;
    }

    public boolean isReceipt() {
        return this.is_receipt == 2;
    }

    public boolean isReceipted() {
        return this.is_receipted_flag == 2;
    }

    public boolean isScheduleSend() {
        return this.isSendFlag == 0;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isShowTime() {
        return this.showTime;
    }

    public void setApp_id(final String app_id) {
        this.app_id = app_id;
    }

    public final void setApp_info(final AppBean app_info) {
        this.app_info = app_info;
    }

    public void setCreate_time(final long create_time) {
        this.create_time = create_time;
    }

    public void setExtraForEntry(final EntryEntity extraForEntry) {
        this.extraForEntry = extraForEntry;
    }

    public void setIsCancel(final int isCancel) {
        this.isCancel = isCancel;
    }

    public void setIsSendFlag(final int isSendFlag) {
        this.isSendFlag = isSendFlag;
    }

    public void setIs_authoritative(final int is_authoritative) {
        this.is_authoritative = is_authoritative;
    }

    public void setIs_collected(final boolean is_collected) {
        this.is_collected = is_collected;
    }

    public void setIs_read(final boolean is_read) {
        this.is_read = is_read;
    }

    public void setIs_receipt(final int is_receipt) {
        this.is_receipt = is_receipt;
    }

    public void setIs_receipted_flag(final int is_receipted_flag) {
        this.is_receipted_flag = is_receipted_flag;
    }

    public void setMsg_content(final String msg_content) {
        this.msg_content = msg_content;
    }

    public void setMsg_id(final String msg_id) {
        this.msg_id = msg_id;
    }

    public void setMsg_type(final int msg_type) {
        this.msg_type = msg_type;
    }

    public void setReadState(final MsgReadState readState) {
        this.readState = readState;
    }

    public void setReceipt_opt(final String receipt_opt) {
        this.receipt_opt = receipt_opt;
    }

    public void setReceipt_opt_info(final String receipt_opt_info) {
        this.receipt_opt_info = receipt_opt_info;
    }

    public void setReceipt_opt_json(final String receipt_opt_json) {
        this.receipt_opt_json = receipt_opt_json;
    }

    public void setRecv_time(final long recv_time) {
        this.recv_time = recv_time;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public void setSend_time(final long send_time) {
        this.send_time = send_time;
    }

    public void setSend_uid(final String send_uid) {
        this.send_uid = send_uid;
    }

    public void setShowTime(final boolean showTime) {
        this.showTime = showTime;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUser_info(final UserBean user_info) {
        this.user_info = user_info;
    }

    public static class EntryEntity
    {
        public NoticeBean relatedAppMessage;
        public int unreadCount;
    }
}

