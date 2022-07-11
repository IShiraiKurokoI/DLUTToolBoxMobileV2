package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.GridAppID;
import com.Shirai_Kuroko.DLUTMobile.Entities.IDPhotoResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationPayload;
import com.Shirai_Kuroko.DLUTMobile.Entities.UserScoreBean;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigHelper {

    public static ArrayList<ApplicationConfig> getmlist(Context context)
    {
        String defconfig=getdefconfigString(context);
        ArrayList<ApplicationConfig> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String acfjson = prefs.getString("APPCONFIG", defconfig);
        List<ApplicationConfig> jsonlist = JSON.parseArray(acfjson,ApplicationConfig.class);
        ApplicationConfig[] acfs =jsonlist.toArray(new ApplicationConfig[0]);
        mList = new ArrayList<>(Arrays.asList(acfs));
        return mList;
    }

    public static String getdefconfigString(Context context) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("defconfig")));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str);
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addsubscription(Context context, int appnumid)
    {
        ArrayList<ApplicationConfig> mlist =getmlist(context);
        mlist.get(appnumid).setIssubscription(1);
        String json = JSON.toJSONString(mlist);
        SavePrefJson(context,json);
        AddtoGrid(context,appnumid);
        Toast.makeText(context, getmlist(context).get(appnumid).getAppName()+"添加成功", Toast.LENGTH_SHORT).show();
    }

    public static void removesubscription(Context context, int appnumid)
    {
        ArrayList<ApplicationConfig> mlist =getmlist(context);
        mlist.get(appnumid).setIssubscription(0);
        String json = JSON.toJSONString(mlist);
        SavePrefJson(context,json);
        DeleteFromGrid(context,appnumid);
        Toast.makeText(context, getmlist(context).get(appnumid).getAppName()+"移除成功", Toast.LENGTH_SHORT).show();
    }

    public static void SavePrefJson(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("APPCONFIG",json).apply();
    }
    public static void SaveGridPrefJson(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("HomeGridConfig",json).apply();
    }

    public static boolean GetThemeType(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean pref = prefs.getBoolean("Dark",false);
        int app = AppCompatDelegate.getDefaultNightMode();
        if(app==1)
        {
            return false;
        }
        else if(app==2)
        {
            return true;
        }
        return pref;
    }

    public static void AddtoGrid(Context context,int id)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig","[]");
        List<GridAppID> jsonlist = JSON.parseArray(GridConfig,GridAppID.class);
        for(GridAppID ga : jsonlist)
        {
            if(ga.getId().equals(id))
            {
                return;
            }
        }
        jsonlist.add(new GridAppID(id));
        String json = JSON.toJSONString(jsonlist);
        SaveGridPrefJson(context,json);
    }

    public static void DeleteFromGrid(Context context,int id)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig","[]");
        List<GridAppID> jsonlist = JSON.parseArray(GridConfig,GridAppID.class);
        for (int i=0;i<jsonlist.size();i++)
        {
            if (jsonlist.get(i).getId().equals(id))
            {
                jsonlist.remove(i);
                i--;
            }
        }
        String json = JSON.toJSONString(jsonlist);
        SaveGridPrefJson(context,json);
    }

    public static ArrayList<GridAppID> GetGridIDList(Context context)
    {
        ArrayList<GridAppID> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig","[]");
        List<GridAppID> jsonlist = JSON.parseArray(GridConfig,GridAppID.class);
        GridAppID[] gais =jsonlist.toArray(new GridAppID[0]);
        mList = new ArrayList<>(Arrays.asList(gais));
        return mList;
    }

    public static void SaveNotificationPayloadPrefJson(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("NotificationPayloadHistory",json).apply();
    }

    public static void AddToNotificationHistoryList(Context context,String payload)
    {
        ArrayList<NotificationPayload> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String PayloadHistory = prefs.getString("NotificationPayloadHistory","[]");
        List<NotificationPayload> jsonlist = JSON.parseArray(PayloadHistory,NotificationPayload.class);
        NotificationPayload[] nphs =jsonlist.toArray(new NotificationPayload[0]);
        mList = new ArrayList<>(Arrays.asList(nphs));
        mList.add(JSON.parseObject(payload, NotificationPayload.class));
        prefs.edit().putBoolean("unread",true).apply();
        prefs.edit().putInt("unreadcount",prefs.getInt("unreadcount",0)+1).apply();
        SaveNotificationPayloadPrefJson(context,JSON.toJSONString(mList));
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("com.Shirai_Kuroko.DLUTMobile.ReceivedNew"));
    }

    public static List<NotificationPayload> GetNotificationHistoryList(Context context)
    {
        ArrayList<NotificationPayload> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String PayloadHistory = prefs.getString("NotificationPayloadHistory","[]");
        List<NotificationPayload> jsonlist = JSON.parseArray(PayloadHistory,NotificationPayload.class);
        NotificationPayload[] nphs =jsonlist.toArray(new NotificationPayload[0]);
        mList = new ArrayList<>(Arrays.asList(nphs));
        return mList;
    }

    public static void SaveDebugInfoPrefJson(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("DebugInfo",json+prefs.getString("DebugInfo","DebugInfo:")).apply();
    }

    public static String getcatogoryname(String cat)
    {
        if(cat.equals("study"))
        {
            return "学习类";
        }
        if(cat.equals("office"))
        {
            return "工作类";
        }
        if(cat.equals("life"))
        {
            return "生活类";
        }
        if(cat.equals("social"))
        {
            return "社交类";
        }
        if(cat.equals("game"))
        {
            return "游戏类";
        }
        return "未知分类";
    }

    public static void SaveLoginResultToPref(String result,Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("UserBean",result).apply();
    }

    public static Boolean NeedLogin(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(Objects.equals(prefs.getString("UserBean", ""), ""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static LoginResponseBean GetUserBean(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("UserBean", "");
        LoginResponseBean loginResponseBean = new LoginResponseBean();
        loginResponseBean = JSON.parseObject(Json,LoginResponseBean.class);
        return loginResponseBean;
    }

    public static UserScoreBean GetUserScoreBean(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("UserScoreBean", "");
        UserScoreBean userScoreBean = new UserScoreBean();
        userScoreBean = JSON.parseObject(Json,UserScoreBean.class);
        return userScoreBean;
    }

    public static void SaveUserScoreBean(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("UserScoreBean",json).apply();
    }

    public static IDPhotoResult GetIDPhoto(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("IDPhotoResult", "");
        IDPhotoResult idPhotoResult = new IDPhotoResult();
        idPhotoResult = JSON.parseObject(Json,IDPhotoResult.class);
        return idPhotoResult;
    }

    public static void SaveIDPhoto(Context context,String json)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("IDPhotoResult",json).apply();
    }
}
