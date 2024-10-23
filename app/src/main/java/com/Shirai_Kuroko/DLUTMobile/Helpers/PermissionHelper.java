package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
    private static final int MY_REQUEST_CODE = 10000;

    @SuppressLint("InlinedApi")
    public static void GetAllPermission(Activity mContext) {
        List<String> permissionsList = new ArrayList<>();

        // 添加常见权限
        permissionsList.add(Manifest.permission.INTERNET);
        permissionsList.add(Manifest.permission.ACCESS_WIFI_STATE);
        permissionsList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissionsList.add(Manifest.permission.CHANGE_WIFI_STATE);
        permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.READ_PHONE_STATE);
        permissionsList.add(Manifest.permission.RECORD_AUDIO);
        permissionsList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        permissionsList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        // Android 13 (API 33) 及以上版本的特定权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsList.add(Manifest.permission.READ_MEDIA_AUDIO);
            permissionsList.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissionsList.add(Manifest.permission.READ_MEDIA_VIDEO);
        }

        // Android 12 (API 31) 及以上版本的特定权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsList.add(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC);
        }

        // 检查并请求权限
        requestPermissions(mContext, permissionsList);
    }

    private static void requestPermissions(Activity mContext, List<String> permissionsList) {
        PackageManager packageManager = mContext.getPackageManager();
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissionsList) {
            try {
                // 检查权限是否已经授予
                if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 未获取权限
                    PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
                    CharSequence permissionName = permissionInfo.loadLabel(packageManager);

                    Log.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");
                    LogToFile.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");

                    if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {
                        Log.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                        LogToFile.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                    } else {
                        permissionsToRequest.add(permission);
                    }
                } else {
                    // 已经授予权限
                    PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
                    CharSequence permissionName = permissionInfo.loadLabel(packageManager);

                    Log.d("权限申请", "您已获得了【" + permissionName + "】的权限");
                    LogToFile.d("权限申请", "您已获得了【" + permissionName + "】的权限");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e("权限申请", "权限【" + permission + "】不存在或不可用");
            }
        }

        // 请求权限
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(mContext, permissionsToRequest.toArray(new String[0]), MY_REQUEST_CODE);
        }
    }
}