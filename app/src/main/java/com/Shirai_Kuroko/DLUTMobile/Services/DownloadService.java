package com.Shirai_Kuroko.DLUTMobile.Services;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.Shirai_Kuroko.DLUTMobile.Common.DownLoadTask;
import com.Shirai_Kuroko.DLUTMobile.Common.DownloadListener;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.io.File;

public class DownloadService extends Service {
    private DownLoadTask downLoadTask;
    private String downloadUrl;
    //创建一个DownloadListener并实现其中的方法
    private DownloadListener downloadListener=new DownloadListener() {
        @Override//以通知的方式显示进度条
        public void onProgress(int progress) {
            //使用getNotificationManager函数构建一个用于显示下载进度的通知
            //使用notify去触发这个通知
            getNotificationManager().notify(1,getNotification("正在下载",progress,downloadUrl));
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        @Override
        public void onSuccess(String FilePath,String FileName) {
            downLoadTask=null;
            //关闭前台服务通知
            File file = new File(FilePath);
            stopForeground(true);
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            Uri contentUri = FileProvider.getUriForFile(getBaseContext(), "com.Shirai_Kuroko.DLUTMobile.fileProvider", file);
            intent.setData(contentUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            new NotificationHelper().Notify(DownloadService.this, PendingIntent.getActivity(DownloadService.this,(int) (Math.random()*200), intent, FLAG_UPDATE_CURRENT),"1314","下载完成通知","下载完成",FileName+"\n已保存至Download目录下",(int) System.currentTimeMillis());
            Toast.makeText(DownloadService.this,FileName+"\n下载成功\n已保存至Download目录下",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            downLoadTask=null;
            //关闭前台服务通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败",-1,downloadUrl));
            Toast.makeText(DownloadService.this,"下载失败",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            downLoadTask=null;
        }

        @Override
        public void inCanceled() {
            downLoadTask=null;
            //关闭前台服务通知
            stopForeground(true);
            Toast.makeText(DownloadService.this,"取消下载",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }
    //用于使服务可以和活动通信
    public class DownloadBinder extends Binder{
        public void startDownload(String url,String FileName){
            if(downLoadTask==null){
                downloadUrl=url;
                downLoadTask=new DownLoadTask(downloadListener,FileName);
                //使用execute开启下载
                downLoadTask.execute(downloadUrl);
                //startForeground使服务成为一个前台服务以创建持续运行的通知
                startForeground(1,getNotification("开始下载",0,FileName));
                Toast.makeText(DownloadService.this,"开始下载",Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if (downLoadTask!=null){
                downLoadTask.pauseDownload();
            }
        }
        //取消下载后需要将下载中的任务取消
        public void cancelDownload(){
            if(downLoadTask!=null){
                downLoadTask.cancelDownload();
            }else {
                if (downloadUrl!=null)
                {
                    //取消需要将文件删除并将通知关闭
                    String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file=new File(directory+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"下载取消",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title,int progress,String downloadUrl){
        String CHANNEL_ONE_ID = "1911";
        String CHANNEL_ONE_NAME = "文件下载服务";
        NotificationChannel notificationChannel;
        notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setChannelId("1911");
        builder.setContentInfo(downloadUrl);
        if(progress>0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);//最大进度，当前进度，是否使用模糊进度条
        }
        return builder.build();
    }
}