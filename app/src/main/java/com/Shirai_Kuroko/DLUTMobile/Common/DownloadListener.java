package com.Shirai_Kuroko.DLUTMobile.Common;

public interface DownloadListener {
    void onProgress(int progress);//通知当前下载进度
    void onSuccess(String FilePath,String FileName);//通知下载成功
    void onFailed();//通知下载失败
    void onPaused();//下载暂停
    void inCanceled();//下载取消
}
