package com.Shirai_Kuroko.DLUTMobile.Configurations;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.maning.mlkitscanner.scan.model.MNScanConfig;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName QRCodeScanConfig.java
 * @Description TODO
 * @createTime 2022年12月31日 21:03
 */
public class QRCodeScanConfig {
    //需要判断有没有权限
    public static MNScanConfig scanConfig = new MNScanConfig.Builder()
            //设置完成震动
            .isShowVibrate(true)
            //扫描完成声音
            .isShowBeep(true)
            //显示相册功能
            .isShowPhotoAlbum(true)
            //显示闪光灯
            .isShowLightController(true)
            //打开扫描页面的动画
            .setActivityOpenAnime(R.anim.activity_anmie_in)
            //退出扫描页面动画
            .setActivityExitAnime(R.anim.activity_anmie_out)
            //自定义文案
            .setScanHintText("将条形码/二维码放入框内，即可自动扫描")
            .setScanHintTextColor("#FFFFFF")
            .setScanHintTextSize(16)
            //扫描线的颜色
            .setScanColor("#FFFF00")
            //是否支持手势缩放
            .setSupportZoom(true)
            //扫描线样式
            .setLaserStyle(MNScanConfig.LaserStyle.Line)
            //背景颜色
            .setBgColor("#33FF0000")
            //是否全屏扫描,默认全屏
            .setFullScreenScan(true)
            //单位dp
            .setResultPointConfigs(36, 12, 2, "#FFFFFFFF", "#CC00A81F")
            //状态栏设置
            .setStatusBarConfigs("#00000000", true)
            .builder();



    public static void requestCameraPerm(Activity activity) {
        //判断权限
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 10010);
        }
    }
}
