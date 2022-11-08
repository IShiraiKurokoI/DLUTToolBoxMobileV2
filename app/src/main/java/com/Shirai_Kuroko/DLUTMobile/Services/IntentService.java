package com.Shirai_Kuroko.DLUTMobile.Services;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationPayload;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.alibaba.fastjson.JSON;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.net.URLDecoder;

/**
 * 继承 GTIntentService 接收来自个推的消息，所有消息在主线程中回调，如果注册了该服务，则务必要在 AndroidManifest 中声明，否则无法接受消息
 */
public class IntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        final byte[] payload = msg.getPayload();
        if (payload != null) {
            final String str = new String(payload);
            Log.i("个推SDK", "收到推送信息  " + str);
            ConfigHelper.SaveDebugInfoPrefJson(context, str);
            try {
                MsgHistoryManager msgHistoryManager = new MsgHistoryManager(context);
                NotificationPayload notificationPayload = JSON.parseObject(str, NotificationPayload.class);
                try {
                    msgHistoryManager.insert(notificationPayload.getPayload().getBody().getCustom().getMsg_id(),notificationPayload.getTimestamp(),notificationPayload.getAppkey(),0,notificationPayload.getPayload().getBody().getTitle(),notificationPayload.getPayload().getBody().getCustom().getContent());
                } catch (Exception e) {
                    msgHistoryManager.closedb();
                }
                try {
                    new Thread(() -> BackendUtils.GetMsgDetailInfo(context, notificationPayload.getPayload().getBody().getCustom().getMsg_id())).start();
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                prefs.edit().putBoolean("unread", true).putInt("unreadcount", prefs.getInt("unreadcount", 0) + 1).apply();
                Intent intent3 = new Intent("com.Shirai_Kuroko.DLUTMobile.ReceivedNew");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent3);

                //发送推送消息
                DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseObject(notificationPayload.getPayload().getBody().getCustom().getContent(), DLUTNoticeContentBean.class);
                Intent intent = new Intent(this, PureBrowserActivity.class);
                intent.putExtra("Name", "");
                intent.putExtra("Url", dlutNoticeContentBean.getUrl());
                intent.putExtra("MsgID", notificationPayload.getPayload().getBody().getCustom().getMsg_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                @SuppressLint("UnspecifiedImmutableFlag")
                PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random()*200), intent, FLAG_UPDATE_CURRENT);
                new NotificationHelper().Notify(context,pendingIntent,"1919810","消息通知", URLDecoder.decode(dlutNoticeContentBean.getTitle()),Integer.parseInt(notificationPayload.getPayload().getBody().getCustom().getMsg_id()));
            } catch (Exception e) {
                Log.e("个推SDK", "Payload处理错误: ", e);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                prefs.edit().putString("Debuger", str).apply();
            }
        } else {
            Log.i("个推SDK", "收到空推送信息！");
        }
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.i("个推SDK", "收到Clientid  " + clientid);
        final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString("device_token", clientid);
        edit.apply();
        BackendUtils.SetClientID(context, clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.i("个推SDK", "在线状态变化  " + online);
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        super.onNotificationMessageArrived(context, gtNotificationMessage);
    }
}
