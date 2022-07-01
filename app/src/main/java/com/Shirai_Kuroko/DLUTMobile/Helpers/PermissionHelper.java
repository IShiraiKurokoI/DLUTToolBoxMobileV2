package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionHelper {
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    @SuppressLint("InlinedApi")
    public static void GetAllPermission(Activity mContext)
    {
        String[] perms = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(mContext, perms)) {
            // Do not have permissions, request them now
            ActivityCompat.requestPermissions(
                    mContext,
                    perms,
                    MY_PERMISSION_REQUEST_CODE
            );
        }
    }
}
