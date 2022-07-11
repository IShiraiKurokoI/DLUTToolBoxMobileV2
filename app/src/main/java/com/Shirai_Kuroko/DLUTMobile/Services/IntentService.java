package com.Shirai_Kuroko.DLUTMobile.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
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
                ConfigHelper.AddToNotificationHistoryList(context, str);
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
