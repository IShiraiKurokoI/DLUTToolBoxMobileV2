package com.Shirai_Kuroko.DLUTMobile.Utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.GithubLatestBean;
import com.Shirai_Kuroko.DLUTMobile.Managers.CacheManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MobileUtils {
    public static String GetAppVersion(Context context)
    {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = null;
        if (info != null) {
            version = info.versionName;
        }
        return version;
    }

    public static Handler handler;
    public static Handler Layouthandler;
    public static Handler LayoutDefaulthandler;
    public static Handler Failurehandler;
    public static Handler NoNeedhandler;

    @SuppressLint("HandlerLeak")
    public static void CheckUpDate(Context context, TextView textView, RelativeLayout relativeLayout, boolean DoNotice)
    {
        new Thread(() -> {
            String Version = MobileUtils.GetAppVersion(context);
            String UpdateJson = GithubUtils.GetGithubHttpRequest("https://api.github.com/repos/MuoRanLY/DLUTToolBoxMobileV2/releases/latest");
            if(UpdateJson.equals(""))
            {
                Log.i("错误", "API 请求超限");
                Failurehandler.sendMessage(new Message());
                return;
            }
            GithubLatestBean githubLatestBean = JSON.parseObject(UpdateJson,GithubLatestBean.class);
            String LastestVersion = githubLatestBean.getTagName();
            if(LastestVersion.equals(Version))
            {
                Log.i("无需更新", " 当前版本"+LastestVersion);
                LayoutDefaulthandler.sendMessage(new Message());
                if(DoNotice)
                {
                    NoNeedhandler.sendMessage(new Message());
                }
            }
            else
            {
                Layouthandler.sendMessage(new Message());
                String UpdateContent = githubLatestBean.getBody();
                String UpdateTime = githubLatestBean.getPublishedAt();
                String UpdateDownloadUrl = githubLatestBean.getAssets().get(0).getBrowserDownloadUrl();
                String UpdateSize = CacheManager.getFormatSize(githubLatestBean.getAssets().get(0).getSize());
                Log.i("需要更新", "新版本："+LastestVersion+"\n更新时间："+UpdateTime+"\n更新内容："+UpdateContent+"\n下载地址："+UpdateDownloadUrl);
                if(DoNotice)
                {
                    List<String> msgList = new ArrayList<>();
                    msgList.add(LastestVersion);
                    msgList.add(UpdateSize);
                    msgList.add(UpdateContent);
                    msgList.add(UpdateDownloadUrl);
                    Message msg =new Message();
                    msg.obj=msgList;
                    handler.sendMessage(msg);
                }
            }
        }).start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(context,"发现新版本！",Toast.LENGTH_LONG).show();
                ArrayList<String> list = (ArrayList<String>) msg.obj;   //实例化对接收数据
                ShowUpdateDialog(context,list.get(0),list.get(1),list.get(2),list.get(3));//自定义的方法，真正需要参数的地方
            }
        };
        Layouthandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView.setText("发现新版本");
                relativeLayout.setVisibility(View.VISIBLE);
            }
        };
        LayoutDefaulthandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView.setText("已是最新版本");
                relativeLayout.setVisibility(View.GONE);
            }
        };
        Failurehandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView.setText("检查更新失败");
                relativeLayout.setVisibility(View.GONE);
                Toast.makeText(context,"API访问请求过多，请稍后再试",Toast.LENGTH_LONG).show();
            }
        };
        NoNeedhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);Toast.makeText(context,"你使用的是最新版本！",Toast.LENGTH_SHORT).show();
            }
        };
    }

    @SuppressLint("HandlerLeak")
    public static void CheckUpDateOnStartUp(Context context)
    {
        new Thread(() -> {
            String Version = MobileUtils.GetAppVersion(context);
            String UpdateJson = GithubUtils.GetGithubHttpRequest("https://api.github.com/repos/MuoRanLY/DLUTToolBoxMobileV2/releases/latest");
            if(UpdateJson.equals(""))
            {
                return;
            }
            GithubLatestBean githubLatestBean = JSON.parseObject(UpdateJson,GithubLatestBean.class);
            String LastestVersion = githubLatestBean.getTagName();
            if(Objects.equals(LastestVersion, Version))
            {
                Log.i("无需更新", " 当前版本"+LastestVersion);
            }
            else
            {
                String UpdateContent = githubLatestBean.getBody();
                String UpdateTime = githubLatestBean.getPublishedAt();
                String UpdateDownloadUrl = githubLatestBean.getAssets().get(0).getBrowserDownloadUrl();
                String UpdateSize = CacheManager.getFormatSize(githubLatestBean.getAssets().get(0).getSize());
                Log.i("需要更新", "新版本："+LastestVersion+"\n更新时间："+UpdateTime+"\n更新内容："+UpdateContent+"\n下载地址："+UpdateDownloadUrl);
                List<String> msgList = new ArrayList<>();
                msgList.add(LastestVersion);
                msgList.add(UpdateSize);
                msgList.add(UpdateContent);
                msgList.add(UpdateDownloadUrl);
                Message msg =new Message();
                msg.obj=msgList;
                handler.sendMessage(msg);
            }
        }).start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ArrayList<String> list = (ArrayList<String>) msg.obj;   //实例化对接收数据
                ShowUpdateDialog(context,list.get(0),list.get(1),list.get(2),list.get(3));//自定义的方法，真正需要参数的地方
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public static void ShowUpdateDialog(Context context, String version, String size, String content, String downloadUrl)
    {
        Dialog UpdateDialog =new Dialog(context, R.style.CustomDialog);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_update, null);
        final TextView tv_version = view.findViewById(R.id.tv_version);
        tv_version.setText("版本： "+version);
        final TextView tv_apk_size = view.findViewById(R.id.tv_apk_size);
        tv_apk_size.setText("大小： "+size);
        final TextView tv_update_msg = view.findViewById(R.id.tv_update_msg);
        tv_update_msg.setText(content);
        final Button btn_ignore = view.findViewById(R.id.btn_ignore);
        btn_ignore.setOnClickListener(v -> UpdateDialog.dismiss());
        final Button btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(v -> {
            Uri uri = Uri.parse(downloadUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            Toast.makeText(context,"正在为您打开下载页面",Toast.LENGTH_LONG).show();
            UpdateDialog.dismiss();
        });
        final View btn_update_dialog_close = view.findViewById(R.id.btn_update_dialog_close);
        btn_update_dialog_close.setOnClickListener(v -> UpdateDialog.dismiss());
        Window window =UpdateDialog.getWindow();
        window.setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        UpdateDialog.setCanceledOnTouchOutside(false);
        UpdateDialog.show();
    }




    public static void ShareToFriend(Context context)
    {
        Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
    }

    public static boolean BrowserSharePictureToFriend(Context context, WebView webView, ApplicationConfig thisapp,Bitmap bitmap)
    {
        try {
            String path = context.getExternalFilesDir(null).getAbsolutePath()+"/Share";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dirFile.mkdirs();
            }
            String title=webView.getTitle();
            if(title.length()>10)
            {
                title= thisapp.getAppName();
            }
            String Date =" " + new Date().toLocaleString();
            String fileName =path+"/"+title+Date+".png";
            FileOutputStream fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Log.i("文件状态 路径：", fileName+" 是否存在："+new File(fileName).exists());
            Uri contentUri = FileProvider.getUriForFile(context, "com.Shirai_Kuroko.DLUTMobile.fileProvider", new File(fileName));
            Log.i("Uri路径：", contentUri.toString());
            Log.i("Uri文件是否存在", new File(contentUri.toString()).exists()+"");
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 给目标应用一个临时授权
            intent.putExtra(Intent.EXTRA_STREAM,contentUri);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(intent,0);
            if(resInfo.isEmpty()){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return false;
            }
            List<Intent> targetIntents = new ArrayList<>();
            for (ResolveInfo resolveInfo : resInfo) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo.packageName.contains("com.tencent.mm")
                        || activityInfo.packageName.contains("com.tencent.mobileqq")){
                    //过滤掉qq收藏
                    if (resolveInfo.loadLabel(pm).toString().contains("QQ收藏")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("微信收藏")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("微信状态")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("面对面快传")){
                        continue;
                    }
                    Log.i("找到符合的程序", resolveInfo.loadLabel(pm).toString());
                    Intent target = new Intent();
                    target.setAction(Intent.ACTION_SEND);
                    target.setComponent(new ComponentName(activityInfo.packageName,activityInfo.name));
                    target.putExtra(Intent.EXTRA_STREAM, contentUri);
                    target.setType("image/*");//必须设置，否则选定分享类型后不能跳转界面
                    targetIntents.add(new LabeledIntent(target,activityInfo.packageName,resolveInfo.loadLabel(pm),resolveInfo.icon));
                }
            }
            if (targetIntents.size()<= 0){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return false;
            }
            Intent chooser = Intent.createChooser(targetIntents.remove(targetIntents.size() - 1), "选择分享");
            if (chooser == null) return false;
            LabeledIntent[] labeledIntents = targetIntents.toArray(new LabeledIntent[targetIntents.size()]);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,labeledIntents);
            context.startActivity(chooser);
        } catch (Exception e) {
            Log.i("产生错误", e.toString());
            return false;
        }
        return false;
    }

    public static boolean PureBrowserSharePictureToFriend(Context context, WebView webView, Bitmap bitmap)
    {
        try {
            String path = context.getExternalFilesDir(null).getAbsolutePath()+"/Share";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dirFile.mkdirs();
            }
            String Date =" " + new Date().toLocaleString();
            String fileName =path+"/"+webView.getTitle()+Date+".png";
            FileOutputStream fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Log.i("文件状态 路径：", fileName+" 是否存在："+new File(fileName).exists());
            Uri contentUri = FileProvider.getUriForFile(context, "com.Shirai_Kuroko.DLUTMobile.fileProvider", new File(fileName));
            Log.i("Uri路径：", contentUri.toString());
            Log.i("Uri文件是否存在", new File(contentUri.toString()).exists()+"");
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 给目标应用一个临时授权
            intent.putExtra(Intent.EXTRA_STREAM,contentUri);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(intent,0);
            if(resInfo.isEmpty()){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return false;
            }
            List<Intent> targetIntents = new ArrayList<>();
            for (ResolveInfo resolveInfo : resInfo) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo.packageName.contains("com.tencent.mm")
                        || activityInfo.packageName.contains("com.tencent.mobileqq")){
                    //过滤掉qq收藏
                    if (resolveInfo.loadLabel(pm).toString().contains("QQ收藏")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("微信收藏")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("微信状态")){
                        continue;
                    }
                    if (resolveInfo.loadLabel(pm).toString().contains("面对面快传")){
                        continue;
                    }
                    Log.i("找到符合的程序", resolveInfo.loadLabel(pm).toString());
                    Intent target = new Intent();
                    target.setAction(Intent.ACTION_SEND);
                    target.setComponent(new ComponentName(activityInfo.packageName,activityInfo.name));
                    target.putExtra(Intent.EXTRA_STREAM, contentUri);
                    target.setType("image/*");//必须设置，否则选定分享类型后不能跳转界面
                    targetIntents.add(new LabeledIntent(target,activityInfo.packageName,resolveInfo.loadLabel(pm),resolveInfo.icon));
                }
            }
            if (targetIntents.size()<= 0){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return false;
            }
            Intent chooser = Intent.createChooser(targetIntents.remove(targetIntents.size() - 1), "选择分享");
            if (chooser == null) return false;
            LabeledIntent[] labeledIntents = targetIntents.toArray(new LabeledIntent[targetIntents.size()]);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,labeledIntents);
            context.startActivity(chooser);
        } catch (Exception e) {
            Log.i("产生错误", e.toString());
        }
        return false;
    }

    public static void ShareTextToFriend(Context context,String text)
    {
        //ToDo:通过QQ微信分享文本
    }

    public static int getDefaultDisplayDensity()
    {
        try {
            Class  aClass = Class.forName("android.view.WindowManagerGlobal");
            Method method = aClass.getMethod("getWindowManagerService");
            method.setAccessible(true);
            Object iwm = method.invoke(aClass);
            Method getInitialDisplayDensity = null;
            if (iwm != null) {
                getInitialDisplayDensity = iwm.getClass().getMethod("getInitialDisplayDensity", int.class);
            }
            if (getInitialDisplayDensity != null) {
                getInitialDisplayDensity.setAccessible(true);
            }
            Object densityDpi = null;
            if (getInitialDisplayDensity != null) {
                densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY);
            }
            return (int) densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Resources getResources(Resources res)
    {
        Configuration config = new Configuration();
        config.densityDpi = getDefaultDisplayDensity();
        config.setToDefaults();   // 禁止系统字体缩放
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public static void CheckConfigUpdates()
    {
        //ToDo:检测已存在的config与defconfig之间的差别，更新信息;
    }
}
