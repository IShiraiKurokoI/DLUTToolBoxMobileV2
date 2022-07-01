package com.Shirai_Kuroko.DLUTMobile.Utils;

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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static void CheckUpDate(Context context, TextView textView, RelativeLayout relativeLayout,boolean DoNotice)
    {
        //ToDo:检查更新的实现方法
        textView.setText("已是最新版本");
        relativeLayout.setVisibility(View.GONE);
        TextView Unread = relativeLayout.findViewById(R.id.unread_no);
        Unread.setText("");
        if(DoNotice)
        {
            Toast.makeText(context,"你使用的是最新版本！",Toast.LENGTH_SHORT).show();
        }
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
