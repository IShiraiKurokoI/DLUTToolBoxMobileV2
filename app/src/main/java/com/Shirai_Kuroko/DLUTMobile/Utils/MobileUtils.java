package com.Shirai_Kuroko.DLUTMobile.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Adapters.ADBannerAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.ADBannerBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.GithubLatestBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.CacheManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.PersonalInfoActivity;
import com.Shirai_Kuroko.DLUTMobile.Widgets.PreferenceRightDetailView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    @SuppressWarnings("unchecked")
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
                ShowUpdateDialog(context,list.get(0),list.get(1),list.get(2),list.get(3),(Activity) context);//自定义的方法，真正需要参数的地方
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
    @SuppressWarnings("unchecked")
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
                ShowUpdateDialog(context,list.get(0),list.get(1),list.get(2),list.get(3),(Activity)context );//自定义的方法，真正需要参数的地方
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public static void ShowUpdateDialog(Context context, String version, String size, String content, String downloadUrl,Activity activity)
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
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        UpdateDialog.setOnDismissListener(dialogInterface -> {
            WindowManager.LayoutParams lp1 = activity.getWindow().getAttributes();
            lp1.alpha = 1f;
            activity.getWindow().setAttributes(lp1);
        });
        UpdateDialog.show();
    }




    public static void ShareToFriend(Context context)
    {
        ShareTextToFriend(context,"i大工+ \n民间自制增强版i大工\n下载地址：\nhttps://github.com/MuoRanLY/DLUTToolBoxMobileV2/releases/latest");
        Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
    }
    @SuppressWarnings("ALL")
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
    @SuppressWarnings("ALL")
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

    @SuppressWarnings({"ConstantConditions"})
    public static int getDefaultDisplayDensity()
    {
        try {
            @SuppressLint("PrivateApi") Class<?> aClass = Class.forName("android.view.WindowManagerGlobal");
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
    @SuppressWarnings("ALL")
    public static void ShareTextToFriend(Context context,String text)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 给目标应用一个临时授权
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(intent,0);
            if(resInfo.isEmpty()){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return;
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
                    target.putExtra(Intent.EXTRA_TEXT, text);
                    target.setType("text/plain");
                    targetIntents.add(new LabeledIntent(target,activityInfo.packageName,resolveInfo.loadLabel(pm),resolveInfo.icon));
                }
            }
            if (targetIntents.size()<= 0){
                Toast.makeText(context,"未找到合适的分享应用！",Toast.LENGTH_LONG).show();
                return;
            }
            Intent chooser = Intent.createChooser(targetIntents.remove(targetIntents.size() - 1), "选择分享");
            if (chooser == null) return;
            LabeledIntent[] labeledIntents = targetIntents.toArray(new LabeledIntent[targetIntents.size()]);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,labeledIntents);
            context.startActivity(chooser);
        } catch (Exception e) {
            Log.i("产生错误", e.toString());
        }
    }

    public static void CheckConfigUpdates(Context context)
    {
        try {
            ArrayList<ApplicationConfig> ConfigNow = ConfigHelper.Getmlist(context);
            ArrayList<ApplicationConfig> DefaultList;
            List<ApplicationConfig> jsonlist = JSON.parseArray(ConfigHelper.GetDefaultConfigString(context),ApplicationConfig.class);
            ApplicationConfig[] acfs = new ApplicationConfig[0];
            if (jsonlist != null) {
                acfs = jsonlist.toArray(new ApplicationConfig[0]);
            }
            DefaultList = new ArrayList<>(Arrays.asList(acfs));
            for (int i =0;i<ConfigNow.size();i++)
            {
                ApplicationConfig DefaultNow = DefaultList.get(i);
                ApplicationConfig Now = ConfigNow.get(i);
                if(!Now.getAppName().equals(DefaultNow.getAppName()))
                {
                    Now.setApp_name(DefaultNow.getAppName());
                }
                if(!Now.getCategory().equals(DefaultNow.getCategory()))
                {
                    Now.setCategory(DefaultNow.getCategory());
                }
                if(!Now.getIcon().equals(DefaultNow.getIcon()))
                {
                    Now.setIcon(DefaultNow.getIcon());
                }
                if(!Now.getDescribe().equals(DefaultNow.getDescribe()))
                {
                    Now.setDescribe(DefaultNow.getDescribe());
                }
                if(!Now.getPopularity().equals(DefaultNow.getPopularity()))
                {
                    Now.setPopularity(DefaultNow.getPopularity());
                }
                if(!Now.getUrl().equals(DefaultNow.getUrl()))
                {
                    Now.setUrl(DefaultNow.getUrl());
                }
                ConfigNow.set(i,Now);
            }
            for(int i =ConfigNow.size();i< DefaultList.size();i++)
            {
                ConfigNow.add(DefaultList.get(i));
            }
            ConfigHelper.SavePrefJson(context, JSON.toJSONString(ConfigNow));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void InitializeMeFragmentInfo(ImageView StudentHeader, TextView StudentName, ImageView StudentSex, ImageView StudentIdentity, TextView StudentOrg, TextView StudentScore, Context context)
    {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if(UserBean == null)
        {
            return;
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        Glide.with(context).load(infoDTO.getHead()).into(StudentHeader);
        StudentHeader.setOnClickListener(view -> {
            Intent intent = new Intent(context, PersonalInfoActivity.class);
            context.startActivity(intent);
        });
        StudentName.setText(infoDTO.getName());
        switch (infoDTO.getAuthority_identity())
        {
            case "bachelor":
            {
                StudentIdentity.setImageResource(R.drawable.icon_flag_bachelor);
                break;
            }
            case "master":
            {
                StudentIdentity.setImageResource(R.drawable.icon_flag_master);
            }
            default:
            {
                StudentIdentity.setImageResource(R.drawable.icon_flag_teacher);
            }
        }
        if(infoDTO.getSex().equals("boy"))
        {
            StudentSex.setImageResource(R.drawable.icon_sex_boy);
        }else
        {
            StudentSex.setImageResource(R.drawable.icon_sex_girl);
        }
        StudentOrg.setText(infoDTO.getOrg().get(0).getName());
        if(ConfigHelper.GetUserScoreBean(context)!=null)
        {
            StudentScore.setText(String.valueOf(ConfigHelper.GetUserScoreBean(context).getData().getUser_points()));
        }
        else
        {
            BackendUtils.GetScore(context,StudentScore);
        }
    }

    public static void GetScore(Context context,TextView Score)
    {
        if(ConfigHelper.GetUserScoreBean(context)!=null)
        {
            Score.setText(String.valueOf(ConfigHelper.GetUserScoreBean(context).getData().getUser_points()));
        }
    }

    public static void  InitializePersonalInfo(Context context, ScrollView InfoScrollView)
    {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if(UserBean == null)
        {
            return;
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        RelativeLayout head_panel = InfoScrollView.findViewById(R.id.head_panel);
        ImageView user_head = head_panel.findViewById(R.id.user_head);
        user_head.setImageResource(R.drawable.me_defaulthead);
        Glide.with(context).load(infoDTO.getHead()).into(user_head);
        PreferenceRightDetailView my_info_name = InfoScrollView.findViewById(R.id.my_info_name);
        my_info_name.SetContentText("学生姓名");
        my_info_name.SetContentText(infoDTO.getName());
        PreferenceRightDetailView my_info_nickname = InfoScrollView.findViewById(R.id.my_info_nickname);
        my_info_nickname.SetContentText("学生昵称");
        my_info_nickname.SetContentText(infoDTO.getNick_name());
        my_info_nickname.setOnClickListener(view -> {
            final EditText inputServer = new EditText(context);
            inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("输入新昵称").setView(inputServer)
                    .setNegativeButton("取消", null);
            inputServer.setText(infoDTO.getNick_name());
            builder.setPositiveButton("确定", (dialog, which) -> {
                String NewNickName = inputServer.getText().toString();
                if(!NewNickName.isEmpty())
                {
                    BackendUtils.ChangeNickName(context,NewNickName,my_info_nickname);
                }
                else
                {
                    Toast.makeText(context,"不能设置空昵称！",Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        });
        PreferenceRightDetailView my_info_student_number = InfoScrollView.findViewById(R.id.my_info_student_number);
        my_info_student_number.SetContentText("学生学号");
        my_info_student_number.SetContentText(infoDTO.getStudentNumber());
        PreferenceRightDetailView my_info_org = InfoScrollView.findViewById(R.id.my_info_org);
        my_info_org.SetContentText("学生单位名称");
        my_info_org.SetContentText(infoDTO.getOrg().get(0).getName());
        PreferenceRightDetailView my_info_identity = InfoScrollView.findViewById(R.id.my_info_identity);
        my_info_identity.SetContentText("学生");
        if(my_info_identity.GetContentText()=="学生")
        {
            PreferenceRightDetailView my_info_jobs = InfoScrollView.findViewById(R.id.my_info_jobs);
            my_info_jobs.setVisibility(View.GONE);
            PreferenceRightDetailView my_info_office_phone = InfoScrollView.findViewById(R.id.my_info_office_phone);
            my_info_office_phone.setVisibility(View.GONE);
            PreferenceRightDetailView my_info_fixed_line = InfoScrollView.findViewById(R.id.my_info_fixed_line);
            my_info_fixed_line.setVisibility(View.GONE);
        }
        PreferenceRightDetailView my_info_mobile = InfoScrollView.findViewById(R.id.my_info_mobile);
        my_info_mobile.SetContentText("手机号");
        my_info_mobile.SetContentText(infoDTO.getCelphone());
        PreferenceRightDetailView my_info_wechat = InfoScrollView.findViewById(R.id.my_info_wechat);//微信号，如果为空则移除
        my_info_wechat.SetContentText(infoDTO.getWechatcode());
        if(my_info_wechat.GetContentText().length()<4)
        {
            my_info_wechat.setVisibility(View.GONE);
        }
        PreferenceRightDetailView my_info_qq = InfoScrollView.findViewById(R.id.my_info_qq);//QQ号，如果为空则移除
        my_info_qq.SetContentText(infoDTO.getQqcode());
        if(my_info_qq.GetContentText().length()<4)
        {
            my_info_qq.setVisibility(View.GONE);
        }
        PreferenceRightDetailView my_info_email = InfoScrollView.findViewById(R.id.my_info_email);
        my_info_email.SetContentText("邮箱");
        my_info_email.SetContentText(infoDTO.getEmail());
    }

    @SuppressWarnings("ALL")
    public static void GetGalllery(Context context, Banner banner)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Cache = prefs.getString("GalleryCache","");
        if(!Cache.equals(""))
        {
            List<ADBannerBean> CacheGallery = JSON.parseArray(Cache,ADBannerBean.class);
            banner.setAdapter(new ADBannerAdapter(CacheGallery,context));
            banner.addBannerLifecycleObserver((FragmentActivity)context);
            banner.setIndicator(new RectangleIndicator(context));
        }
        if(!prefs.getString("GalleryCacheDate","").contains(LocalDate.now().toString()))
        {
            BackendUtils.GetGallery(context,banner);
        }
    }
}
