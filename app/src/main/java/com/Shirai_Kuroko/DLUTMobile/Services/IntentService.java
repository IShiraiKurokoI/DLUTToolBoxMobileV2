package com.Shirai_Kuroko.DLUTMobile.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationPayload;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.alibaba.fastjson.JSON;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

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
                msgHistoryManager.insert(Long.valueOf(notificationPayload.getTimestamp()), JSON.toJSONString(notificationPayload));
                new Thread(() -> BackendUtils.GetMsgDetailInfo(context, notificationPayload.getPayload().getBody().getCustom().getMsg_id(), Long.valueOf(notificationPayload.getTimestamp()))).start();

                //发送推送消息
                DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseObject(notificationPayload.getPayload().getBody().getCustom().getContent(),DLUTNoticeContentBean.class);
                String CHANNEL_ONE_ID = "114514";
                String CHANNEL_ONE_NAME = "个推消息通知";
                NotificationChannel notificationChannel;
                notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                        CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(false);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setShowBadge(true);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (manager != null) {
                    manager.createNotificationChannel(notificationChannel);
                }
                Intent intent = new Intent(this, PureBrowserActivity.class);
                intent.putExtra("Name", "");
                intent.putExtra("Url", dlutNoticeContentBean.getUrl());
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification notification = new Notification.Builder(this, CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(dlutNoticeContentBean.getTitle())
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                if (manager != null) {
                    manager.notify(Integer.parseInt(notificationPayload.getPayload().getBody().getCustom().getMsg_id()),notification);
                }
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
}
