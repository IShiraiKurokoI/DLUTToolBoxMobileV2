package com.Shirai_Kuroko.DLUTMobile.Common;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private String DownloadPath;

    private final String RealFileName;

    private final DownloadListener downloadListener;

    private boolean isCanceled = false;

    private boolean isPaused = false;

    private int lastProgress;

    //通过DownLoadTask回调下载状态
    public DownLoadTask(DownloadListener listener, String FileName) {
        RealFileName = FileName;
        downloadListener = listener;
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

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (!filename.isEmpty())) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    @Override//后台执行具体的下载逻辑
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile;
        File file = null;
        try {
            long downloadLength = 0;
            String downloadUrl = params[0];
            String derectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();//获取本地的下载文件夹的路径
            DownloadPath = derectory + "/" + RealFileName;
            file = new File(DownloadPath);
            int i = 1;
            while (file.exists()) {
                file = new File(derectory + "/" + getFileNameNoEx(RealFileName) + "(" + i + ")." + getExtensionName(RealFileName));
                i++;
            }
            DownloadPath = file.getAbsolutePath();
            long contentLength = getContentLength(downloadUrl);//读取下载文件的字节数
            Log.i("文件下载", "本地大小：" + downloadLength + " 远程大小:" + contentLength);
            if (contentLength == 0) {
                return TYPE_FAILED;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().addHeader("cookie", CookieManager.getInstance().getCookie(downloadUrl)).addHeader("RANGE", "bytes=" + downloadLength + "-").url(downloadUrl).build();//head用于告诉服务器我们从那个字节开始下载
            Response response = client.newCall(request).execute();
            is = Objects.requireNonNull(response.body()).byteStream();
            savedFile = new RandomAccessFile(file, "rw");
            savedFile.seek(downloadLength);//跳过已经下载的字节
            byte[] b = new byte[1024];
            int total = 0;
            int len;
            while ((len = is.read(b)) != -1) {
                if (isCanceled) {
                    return TYPE_CANCELED;
                } else if (isPaused) {
                    return TYPE_PAUSED;
                } else {
                    total += len;
                    savedFile.write(b, 0, len);
                    int progress = (int) ((total + downloadLength) * 100 / contentLength);
                    publishProgress(progress);
                }
            }
            Objects.requireNonNull(response.body()).close();
            return TYPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                } else if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override//用于比较下载进度然后使用onProgress来更改下载进度的通知
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            downloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override//根据传入的参数进行回调
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_SUCCESS:
                downloadListener.onSuccess(DownloadPath, RealFileName);
                break;
            case TYPE_FAILED:
                downloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                downloadListener.onPaused();
                break;
            case TYPE_CANCELED:
                downloadListener.inCanceled();
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    //获取需要下载的文件长度
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("cookie", CookieManager.getInstance().getCookie(downloadUrl)).url(downloadUrl).build();//head用于告诉服务器我们从那个字节开始下载
        Response response = client.newCall(request).execute();
        return response.body().contentLength();
    }
}
