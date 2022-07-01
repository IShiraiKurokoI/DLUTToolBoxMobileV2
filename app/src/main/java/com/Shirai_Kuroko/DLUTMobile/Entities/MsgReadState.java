package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;
import java.util.*;

public class MsgReadState implements Serializable
{
    private int count;
    private int delete_flag;
    private int isSendFlag;
    private String msg_id;
    private int readed_count;
    private List<UserBean> readed_usrlist;
    private int receipted_count;
    private List<UserBean> receipted_usrlist;
    private int unread_count;
    private List<UserBean> unread_usrlist;
    private int unreceipt_count;
    private List<UserBean> unreceipt_usrlist;
    private long update_time;

    public MsgReadState() {
        this.unread_count = -1;
        this.readed_count = -1;
        this.count = -1;
        this.unreceipt_count = -1;
        this.receipted_count = -1;
        this.isSendFlag = 1;
        this.update_time = -1L;
    }

    public int getCount() {
        return this.count;
    }

    public int getDelete_flag() {
        return this.delete_flag;
    }

    public int getIsSendFlag() {
        return this.isSendFlag;
    }

    public String getMsg_id() {
        return this.msg_id;
    }

    public int getReaded_count() {
        return this.readed_count;
    }

    public List<UserBean> getReaded_usrlist() {
        return this.readed_usrlist;
    }

    public int getReceipted_count() {
        return this.receipted_count;
    }

    public List<UserBean> getReceipted_usrlist() {
        return this.receipted_usrlist;
    }

    public int getUnread_count() {
        return this.unread_count;
    }

    public List<UserBean> getUnread_usrlist() {
        return this.unread_usrlist;
    }

    public int getUnreceipt_count() {
        return this.unreceipt_count;
    }

    public List<UserBean> getUnreceipt_usrlist() {
        return this.unreceipt_usrlist;
    }

    public long getUpdate_time() {
        return this.update_time;
    }

    public boolean isDeleted() {
        final int delete_flag = this.delete_flag;
        boolean b = true;
        if (delete_flag != 1) {
            b = false;
        }
        return b;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public void setDelete_flag(final int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public void setIsSendFlag(final int isSendFlag) {
        this.isSendFlag = isSendFlag;
    }

    public void setMsg_id(final String msg_id) {
        this.msg_id = msg_id;
    }

    public void setReaded_count(final int readed_count) {
        this.readed_count = readed_count;
    }

    public void setReaded_usrlist(final List<UserBean> readed_usrlist) {
        this.readed_usrlist = readed_usrlist;
    }

    public void setReceipted_count(final int receipted_count) {
        this.receipted_count = receipted_count;
    }

    public void setReceipted_usrlist(final List<UserBean> receipted_usrlist) {
        this.receipted_usrlist = receipted_usrlist;
    }

    public void setUnread_count(final int unread_count) {
        this.unread_count = unread_count;
    }

    public void setUnread_usrlist(final List<UserBean> unread_usrlist) {
        this.unread_usrlist = unread_usrlist;
    }

    public void setUnreceipt_count(final int unreceipt_count) {
        this.unreceipt_count = unreceipt_count;
    }

    public void setUnreceipt_usrlist(final List<UserBean> unreceipt_usrlist) {
        this.unreceipt_usrlist = unreceipt_usrlist;
    }

    public void setUpdate_time(final long update_time) {
        this.update_time = update_time;
    }
}

