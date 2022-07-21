package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;

public class WebDownloadListener implements DownloadListener {
    private Context mContext;

    public WebDownloadListener(Context context) {
        mContext = context;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("正在下载文件");
        String cookie = CookieManager.getInstance().getCookie(url);
        if (cookie != null) {
            request.addRequestHeader("cookie", cookie);
        }
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(request);
    }
}
