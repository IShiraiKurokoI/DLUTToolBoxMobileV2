package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import static android.content.Context.BIND_AUTO_CREATE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.webkit.DownloadListener;

import com.Shirai_Kuroko.DLUTMobile.Services.DownloadService;

public class WebDownloadListener implements DownloadListener {
    private Context mContext;
    private DownloadService.DownloadBinder downloadBinder;
    private final ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取实例以便于在活动中调用其中的方法
            downloadBinder=(DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {        }
    };

    public WebDownloadListener(Context context) {
        mContext = context;
        Intent intent=new Intent(mContext, DownloadService.class);
        mContext.bindService(intent,connection,BIND_AUTO_CREATE);//绑定服务保证数据在服务和活动中传递
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        String FileName = contentDisposition.split("filename=\"")[1];
        FileName = FileName.substring(0,FileName.length()-1);
        if(downloadBinder==null)
        {
            return;
        }
        downloadBinder.startDownload(url,FileName);
    }
}
