package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import static android.content.Context.BIND_AUTO_CREATE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.webkit.DownloadListener;

import com.Shirai_Kuroko.DLUTMobile.Services.DownloadService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDownloadListener implements DownloadListener {
    private DownloadService.DownloadBinder downloadBinder;

    public WebDownloadListener(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        //获取实例以便于在活动中调用其中的方法
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //获取实例以便于在活动中调用其中的方法
                downloadBinder = (DownloadService.DownloadBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        context.bindService(intent, connection, BIND_AUTO_CREATE);//绑定服务保证数据在服务和活动中传递
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (!filename.isEmpty())) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public String findfilename(String urlPath,String suffixes)
    {
        Pattern pat= Pattern.compile("[\\w]+[.]("+suffixes+")");//正则判断
        Matcher mc=pat.matcher(urlPath);//条件匹配
        while(mc.find()) {
            return mc.group();
        }
        return "file";
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        String FileName;
        try {
            FileName = contentDisposition.split("filename=\"")[1];
            FileName = FileName.substring(0, FileName.length() - 1);
        } catch (Exception e) {
            FileName = findfilename(url,getExtensionName(url));
        }
        if (downloadBinder == null) {
            return;
        }
        downloadBinder.startDownload(url, FileName);
    }
}
