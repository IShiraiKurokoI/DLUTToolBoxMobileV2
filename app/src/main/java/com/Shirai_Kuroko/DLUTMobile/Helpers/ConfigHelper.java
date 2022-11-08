package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Entities.IDPhotoResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.MainCardBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.UserScoreBean;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigHelper {

    public static ArrayList<ApplicationConfig> Getmlist(Context context) {
        String defconfig = GetDefaultConfigString(context);
        ArrayList<ApplicationConfig> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String acfjson = prefs.getString("APPCONFIG", defconfig);
        if (Objects.equals(acfjson, "")) {
            acfjson = defconfig;
        }
        List<ApplicationConfig> jsonlist = JSON.parseArray(acfjson, ApplicationConfig.class);
        ApplicationConfig[] acfs = new ApplicationConfig[0];
        if (jsonlist != null) {
            acfs = jsonlist.toArray(new ApplicationConfig[0]);
        }
        mList = new ArrayList<>(Arrays.asList(acfs));
        return mList;
    }

    public static ArrayList<MainCardBean.MainCardDataBean> GetCardList(Context context) {
        String defconfig = GetDefaultCardConfig(context);
        ArrayList<MainCardBean.MainCardDataBean> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String acfjson = prefs.getString("AppCardConfig", defconfig);
        if (Objects.equals(acfjson, "")) {
            acfjson = defconfig;
        }
        List<MainCardBean.MainCardDataBean> jsonlist = JSON.parseObject(acfjson, MainCardBean.class).getList_data();
        MainCardBean.MainCardDataBean[] acfs = new MainCardBean.MainCardDataBean[0];
        if (jsonlist != null) {
            acfs = jsonlist.toArray(new MainCardBean.MainCardDataBean[0]);
        }
        mList = new ArrayList<>(Arrays.asList(acfs));
        return mList;
    }

    public static String GetDefaultConfigString(Context context) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("DefaultAppConfig")));

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

    public static String GetDefaultCardConfig(Context context) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("DefaultCardConfig")));

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

    public static void addsubscription(Context context, int appnumid) {
        ArrayList<ApplicationConfig> mlist = Getmlist(context);
        mlist.get(appnumid).setIssubscription(1);
        String json = JSON.toJSONString(mlist);
        SavePrefJson(context, json);
        AddtoGrid(context, appnumid);
        CenterToast.makeText(context, Getmlist(context).get(appnumid).getAppName() + "添加成功", Toast.LENGTH_SHORT).show();
    }

    public static void removesubscription(Context context, int appnumid) {
        ArrayList<ApplicationConfig> mlist = Getmlist(context);
        mlist.get(appnumid).setIssubscription(0);
        String json = JSON.toJSONString(mlist);
        SavePrefJson(context, json);
        DeleteFromGrid(context, appnumid);
        CenterToast.makeText(context, Getmlist(context).get(appnumid).getAppName() + "移除成功", Toast.LENGTH_SHORT).show();
    }

    public static void addCardsubscription(Context context, int id) {
        AddtoCard(context, id);
        CenterToast.makeText(context, GetCardList(context).get(id).getApp_name() + "添加成功", Toast.LENGTH_SHORT).show();
    }

    public static void removeCardsubscription(Context context, int id) {
        DeleteFromCard(context, id);
        CenterToast.makeText(context, GetCardList(context).get(id).getApp_name() + "移除成功", Toast.LENGTH_SHORT).show();
    }

    public static void SavePrefJson(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("APPCONFIG", json).apply();
    }

    public static void SaveGridPrefJson(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("HomeGridConfig", json).apply();
    }

    public static void SaveCardPrefJson(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("HomeCardConfig", json).apply();
    }

    public static boolean GetThemeType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean pref = prefs.getBoolean("Dark", false);
        int app = AppCompatDelegate.getDefaultNightMode();
        if (app == 1) {
            return false;
        } else if (app == 2) {
            return true;
        }
        if (app == -1) {
            UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
            if (uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES) {
                return true;
            }
        }
        return pref;
    }

    public static void AddtoGrid(Context context, int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        for (ID ga : jsonlist) {
            if (ga.getId().equals(id)) {
                return;
            }
        }
        jsonlist.add(new ID(id));
        String json = JSON.toJSONString(jsonlist);
        SaveGridPrefJson(context, json);
    }

    public static void DeleteFromGrid(Context context, int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        for (int i = 0; i < jsonlist.size(); i++) {
            if (jsonlist.get(i).getId().equals(id)) {
                jsonlist.remove(i);
                i--;
            }
        }
        String json = JSON.toJSONString(jsonlist);
        SaveGridPrefJson(context, json);
    }

    public static ArrayList<ID> GetGridIDList(Context context) {
        ArrayList<ID> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeGridConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        ID[] gais = jsonlist.toArray(new ID[0]);
        mList = new ArrayList<>(Arrays.asList(gais));
        return mList;
    }

    public static void AddtoCard(Context context, int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeCardConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        for (ID ga : jsonlist) {
            if (ga.getId().equals(id)) {
                return;
            }
        }
        jsonlist.add(new ID(id));
        String json = JSON.toJSONString(jsonlist);
        SaveCardPrefJson(context, json);
    }

    public static void DeleteFromCard(Context context, int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeCardConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        for (int i = 0; i < jsonlist.size(); i++) {
            if (jsonlist.get(i).getId().equals(id)) {
                jsonlist.remove(i);
                i--;
            }
        }
        String json = JSON.toJSONString(jsonlist);
        SaveCardPrefJson(context, json);
    }

    public static ArrayList<ID> GetCardIDList(Context context) {
        ArrayList<ID> mList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String GridConfig = prefs.getString("HomeCardConfig", "[]");
        List<ID> jsonlist = JSON.parseArray(GridConfig, ID.class);
        ID[] gais = jsonlist.toArray(new ID[0]);
        mList = new ArrayList<>(Arrays.asList(gais));
        return mList;
    }

    public static List duplicateRemovalByCircle(List<NotificationHistoryDataBaseBean> list){

        List newList = new ArrayList();
        for (int i = 0;i < list.size();i++){
            for (int j = 0;j < i;j++){
                if(list.get(i).getMsg_id().equals(list.get(j).getMsg_id())){
                    newList.remove(list.get(j));
                }
            }
            newList.add(list.get(i));
        }
        return newList;
    }

    public static List<NotificationHistoryDataBaseBean> GetNotificationHistoryList(Context context) {
        List<NotificationHistoryDataBaseBean> list = new MsgHistoryManager(context).select();
        return duplicateRemovalByCircle(list);
    }

    public static List<Integer> GetUnreadCount(Context context)
    {
        List<NotificationHistoryDataBaseBean> list = GetNotificationHistoryList(context);
        int count = 0;
        int top = -1;
        for (int i = 0;i< list.size();i++) {
            NotificationHistoryDataBaseBean notificationHistoryDataBaseBean = list.get(i);
            if (notificationHistoryDataBaseBean.getIs_read()==0)
            {
                count++;
                Log.i("TAG", "GetUnreadCount: "+i);
                if (top == -1)
                {
                    top = i;
                }
            }
        }
        List<Integer> integerList = new ArrayList<>();
        Log.i("TAG", "GetUnreadCount: "+top);
        Log.i("TAG", "GetUnreadCount: "+count);
        integerList.add(top);
        integerList.add(count);
        return integerList;
    }

    public static void SaveDebugInfoPrefJson(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("DebugInfo", json + prefs.getString("DebugInfo", "DebugInfo:")).apply();
    }

    public static String GetCatogoryName(String cat) {
        if (cat.equals("study")) {
            return "学习类";
        }
        if (cat.equals("office")) {
            return "工作类";
        }
        if (cat.equals("life")) {
            return "生活类";
        }
        if (cat.equals("social")) {
            return "社交类";
        }
        if (cat.equals("game")) {
            return "游戏类";
        }
        return "未知分类";
    }

    @SuppressLint("ApplySharedPref")
    public static void SaveLoginResultToPref(String result, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("UserBean", result).apply();
    }

    public static Boolean NeedLogin(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Objects.equals(prefs.getString("UserBean", ""), "");
    }

    public static LoginResponseBean GetUserBean(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("UserBean", "");
        LoginResponseBean loginResponseBean = new LoginResponseBean();
        loginResponseBean = JSON.parseObject(Json, LoginResponseBean.class);
        return loginResponseBean;
    }

    public static UserScoreBean GetUserScoreBean(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("UserScoreBean", "");
        UserScoreBean userScoreBean = new UserScoreBean();
        userScoreBean = JSON.parseObject(Json, UserScoreBean.class);
        return userScoreBean;
    }

    public static void SaveUserScoreBean(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("UserScoreBean", json).apply();
    }

    public static IDPhotoResult GetIDPhoto(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Json = prefs.getString("IDPhotoResult", "");
        IDPhotoResult idPhotoResult = new IDPhotoResult();
        idPhotoResult = JSON.parseObject(Json, IDPhotoResult.class);
        return idPhotoResult;
    }

    public static void SaveIDPhoto(Context context, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("IDPhotoResult", json).apply();
    }

    public static boolean FPStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("FP", false);
    }

    public static void AgreeFP(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("FP", true).apply();
    }
}
