package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.app.Activity;
import android.content.Intent;

import com.Shirai_Kuroko.DLUTMobile.UI.CustomScanPreviewActivity;
import com.maning.mlkitscanner.scan.callback.act.ActResultRequest;
import com.maning.mlkitscanner.scan.callback.act.MNScanCallback;
import com.maning.mlkitscanner.scan.model.MNScanConfig;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CustomMNScanManager.java
 * @Description TODO
 * @createTime 2023年01月01日 00:43
 */
public class CustomMNScanManager {
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAIL = 1;
    public static final int RESULT_CANCLE = 2;
    public static final String INTENT_KEY_RESULT_SUCCESS = "INTENT_KEY_RESULT_SUCCESS";
    public static final String INTENT_KEY_RESULT_ERROR = "INTENT_KEY_RESULT_ERROR";
    public static final boolean isDebugMode = false;
    public static final String INTENT_KEY_CONFIG_MODEL = "INTENT_KEY_CONFIG_MODEL";

    public static void startScan(Activity activity, MNScanCallback scanCallback) {
        startScan(activity, (MNScanConfig)null, scanCallback);
    }

    public static void startScan(Activity activity, MNScanConfig mnScanConfig, MNScanCallback scanCallback) {
        if (mnScanConfig == null) {
            mnScanConfig = (new MNScanConfig.Builder()).builder();
        }

        Intent intent = new Intent(activity.getApplicationContext(), CustomScanPreviewActivity.class);
        intent.putExtra("INTENT_KEY_CONFIG_MODEL", mnScanConfig);
        ActResultRequest actResultRequest = new ActResultRequest(activity);
        actResultRequest.startForResult(intent, scanCallback);
        activity.overridePendingTransition(mnScanConfig.getActivityOpenAnime(), 17432577);
    }

    public static void closeScanPage() {
        CustomScanPreviewActivity.closeScanPage();
    }

    public static void openAlbumPage() {
        CustomScanPreviewActivity.openAlbumPage();
    }

    public static void openScanLight() {
        CustomScanPreviewActivity.openScanLight();
    }

    public static void closeScanLight() {
        CustomScanPreviewActivity.closeScanLight();
    }

    public static boolean isLightOn() {
        return CustomScanPreviewActivity.isLightOn();
    }
}
