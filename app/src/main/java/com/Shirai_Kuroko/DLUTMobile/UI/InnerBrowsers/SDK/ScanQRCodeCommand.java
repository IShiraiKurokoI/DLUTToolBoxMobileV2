package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.Configurations.QRCodeScanConfig;
import com.Shirai_Kuroko.DLUTMobile.Managers.CustomMNScanManager;
import com.Shirai_Kuroko.DLUTMobile.UI.ApiQRLoginActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginConfirmActivity;
import com.maning.mlkitscanner.scan.MNScanManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ScanQRCodeCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public ScanQRCodeCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(final JSONObject jsonObject) {
        PackageManager packageManager = proxy.context.getPackageManager();
        PermissionInfo permissionInfo = null;
        this.jsonObject = jsonObject;
        final Intent intent = new Intent(proxy.context, StartScanActivity.class);
        BrowserActivity browserActivity = (BrowserActivity)this.proxy.context;
        Handler handler =new Handler(Looper.getMainLooper());
        handler.post(()->{
            CustomMNScanManager.startScan(proxy.context, QRCodeScanConfig.scanConfig, (resultCode, data) -> {
                switch (resultCode) {
                    case MNScanManager.RESULT_SUCCESS:
                        ArrayList<String> results = data.getStringArrayListExtra(MNScanManager.INTENT_KEY_RESULT_SUCCESS);
                        String resultTxt = results.get(0);
                        Log.i("扫码结果", resultTxt);
                        if (resultTxt.contains("whistle_info")) {
                            Intent intent1 = new Intent(proxy.context, ApiQRLoginActivity.class);
                            intent1.putExtra("whistle_info", resultTxt);
                            proxy.context.startActivity(intent1);
                        } else if (resultTxt.contains("qrLogin")) {
                            Intent intent1 = new Intent(proxy.context, LoginConfirmActivity.class);
                            intent1.putExtra("UUID", resultTxt);
                            proxy.context.startActivity(intent1);
                        } else {
                            final JSONObject jsonObject1 = new JSONObject();
                            try {
                                Log.i("扫码返回内容", resultTxt);
                                jsonObject1.put("resultStr", URLEncoder.encode(resultTxt.replaceAll("\n", "%5cn").replaceAll("\r", "%5cr").replaceAll("\t", "%5ct").replaceAll("\\\\", "%5c")));
                                jsonObject1.put("type", "raw");
                                this.sendSucceedResult(jsonObject1);
                            } catch (JSONException e) {
                                this.sendFailedResult("Decode Error");
                                e.printStackTrace();
                            }
                        }
                        break;
                    case MNScanManager.RESULT_FAIL:
                        String resultError = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_ERROR);
                        this.sendFailedResult(resultError);
                        break;
                    case MNScanManager.RESULT_CANCLE:
                        this.sendCancelResult();
                        break;
                }
            });
        });
    }

    private void showToast(String msg) {
        Toast.makeText(proxy.context, msg, Toast.LENGTH_SHORT).show();
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
