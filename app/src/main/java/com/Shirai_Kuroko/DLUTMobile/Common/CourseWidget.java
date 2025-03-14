package com.Shirai_Kuroko.DLUTMobile.Common;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.Shirai_Kuroko.DLUTMobile.Entities.AccessTokenResponse;
import com.Shirai_Kuroko.DLUTMobile.Entities.CourseResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.CourseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.SplashActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.OpenVirtualCardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.WidgetQRLauncherActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class CourseWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        LogToFile.init(context);
        final String action = intent.getAction();
        Log.i("收到广播", action);
        if ("android.appwidget.action.APPWIDGET_UPDATE".equals(action)) {
            LogToFile.init(context);
            this.c(context, State.LOADING);
            this.a(context);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), CourseWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_course);
        }
    }

    public static final String a = "";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogToFile.init(context);
        for (int length = appWidgetIds.length, i = 0; i < length; ++i) {
            b(context, appWidgetManager, appWidgetIds[i], State.LOADING);
        }
        this.a(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    public static void b(final Context context, final AppWidgetManager appWidgetManager, final int i, final State state) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget);
        remoteViews.setOnClickPendingIntent(R.id.root_container, null);

        //新增点击标题进入程序主界面
        Intent TitleBarIntent = new Intent(context, SplashActivity.class);
        TitleBarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent TitleBarPendingIntent = PendingIntent.getActivity(context, 0, TitleBarIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.CourseWidgetTitleBar, TitleBarPendingIntent);

        Intent intent = new Intent(context, WidgetQRLauncherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.btn_start_qr_scan, pendingIntent);
        Intent intent1 = new Intent(context, OpenVirtualCardActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.btn_start_virtual_card, pendingIntent1);

        final int list_course = R.id.list_course;
        final Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_UPDATE");

        remoteViews.setOnClickPendingIntent(R.id.btn_refresh, PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE));

        if (ConfigHelper.NeedLogin(context)) {
            final int content_container = R.id.content_container;
            remoteViews.removeAllViews(content_container);
            final RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget_login_view);
            final Intent intent4 = new Intent(context, SplashActivity.class);
            remoteViews2.setOnClickPendingIntent(R.id.btn_login, PendingIntent.getActivity(context, 0, intent4, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));
            remoteViews.addView(content_container, remoteViews2);
            WidgetHelper.M(remoteViews, false);
        } else {
            if (state != State.DEFAULT) {
                final int content_container2 = R.id.content_container;
                remoteViews.removeAllViews(content_container2);
                remoteViews.addView(content_container2, WidgetHelper.v(context, state.txt));
                WidgetHelper.M(remoteViews, false);
            } else {
                Log.i("课表微件", "初始化列表");
                WidgetHelper.M(remoteViews, true);
                Intent adapter = new Intent(context, WidgetService.class);
                adapter.putExtra("appWidgetId", i);
                adapter.setData(Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME)));
                remoteViews.setRemoteAdapter(list_course, adapter);
                final Intent intent6 = new Intent(context, BrowserActivity.class);
                intent6.putExtra("App_ID", String.valueOf(1));
                remoteViews.setPendingIntentTemplate(list_course, PendingIntent.getActivity(context, 0, intent6, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));
                appWidgetManager.notifyAppWidgetViewDataChanged(i, list_course);
            }
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(i, list_course);
        appWidgetManager.updateAppWidget(i, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(i, list_course);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public static void b(final Context context, final AppWidgetManager appWidgetManager, final int i, final String state) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget);
        remoteViews.setOnClickPendingIntent(R.id.root_container, null);

        //新增点击标题进入程序主界面
        Intent TitleBarIntent = new Intent(context, SplashActivity.class);
        TitleBarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent TitleBarPendingIntent = PendingIntent.getActivity(context, 0, TitleBarIntent, PendingIntent.FLAG_ONE_SHOT);
        remoteViews.setOnClickPendingIntent(R.id.CourseWidgetTitleBar, TitleBarPendingIntent);

        Intent intent = new Intent(context, WidgetQRLauncherActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.btn_start_qr_scan, pendingIntent);

        Intent intent1 = new Intent(context, OpenVirtualCardActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.btn_start_virtual_card, pendingIntent1);

        final int list_course = R.id.list_course;
        final Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        remoteViews.setOnClickPendingIntent(R.id.btn_refresh, PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE));

        if (ConfigHelper.NeedLogin(context)) {
            final int content_container = R.id.content_container;
            remoteViews.removeAllViews(content_container);
            final RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget_login_view);
            final Intent intent4 = new Intent(context, SplashActivity.class);
            remoteViews2.setOnClickPendingIntent(R.id.btn_login, PendingIntent.getActivity(context, 0, intent4, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));
            remoteViews.addView(content_container, remoteViews2);
            WidgetHelper.M(remoteViews, false);
        } else {
            final int content_container2 = R.id.content_container;
            remoteViews.removeAllViews(content_container2);
            remoteViews.addView(content_container2, WidgetHelper.v(context, state));
            WidgetHelper.M(remoteViews, false);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(i, list_course);
        appWidgetManager.updateAppWidget(i, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(i, list_course);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        context.stopService(new Intent(context, WidgetService.class));
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    public final void c(final Context context, final State state) {
        final AppWidgetManager instance = AppWidgetManager.getInstance(context);
        final int[] appWidgetIds = instance.getAppWidgetIds(new ComponentName(context, CourseWidget.class));
        for (int length = appWidgetIds.length, i = 0; i < length; ++i) {
            b(context, instance, appWidgetIds[i], state);
        }
    }

    public final void c(final Context context, final String state) {
        final AppWidgetManager instance = AppWidgetManager.getInstance(context);
        final int[] appWidgetIds = instance.getAppWidgetIds(new ComponentName(context, CourseWidget.class));
        for (int length = appWidgetIds.length, i = 0; i < length; ++i) {
            b(context, instance, appWidgetIds[i], state);
        }
    }

    public List duplicateRemovalByCircle(List<CourseBean> list){

        List newList = new ArrayList();
        for (int i = 0;i < list.size();i++){
            for (int j = 0;j < i;j++){
                if(list.get(i).equals(list.get(j))){
                    newList.remove(list.get(j));
                }
            }
            newList.add(list.get(i));
        }
        return newList;
    }

    public final void a(final Context context) {
        final CourseWidget b = this;
        if (ConfigHelper.NeedLogin(context)) {
            this.c(context, State.NOT_LOGIN);
            return;
        }
        final LoginResponseBean e = ConfigHelper.GetUserBean(context);
        if (!Objects.equals(e.getData().getMy_info().getIdentity(), "student")) {
            this.c(context, State.VISITOR_OR_PARENT);
            return;
        }

        @SuppressLint("HandlerLeak")
        Handler UpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Response result = (Response) msg.obj;
                try {
                    String response = result.body().string();
                    CourseResult courseResult = JSON.parseObject(response, CourseResult.class);
                    if (courseResult.getErrcode() == 0) {
                        if (courseResult.getData() != null) {
                            CourseBean.updateLocalList(duplicateRemovalByCircle(courseResult.getData()));
                            b.c(context, State.DEFAULT);
                            Log.i("课表微件", "加载成功");
                            LogToFile.i("课表微件", "加载成功");
                        } else {
                            final State data_NULL = State.DATA_NULL;
                            b.c(context, data_NULL);
                        }
                    } else {
                        b.c(context, "服务器端错误消息：\n"+courseResult.getErrmsg());
                        Log.e("课表微件", "服务器端错误消息："+courseResult.getErrmsg());
                        LogToFile.e("课表微件", "服务器端错误消息："+courseResult.getErrmsg());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    b.c(context, ex.getLocalizedMessage());
                }
            }
        };

        @SuppressLint("HandlerLeak")
        Handler TokenHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Response result = (Response) msg.obj;
                try {
                    AccessTokenResponse accessTokenResponse = JSON.parseObject(result.body().string(), AccessTokenResponse.class);
                    if (accessTokenResponse.getAccess_token() != null) {
                        new Thread(() -> {
                            Response response = BackendUtils.GetCourseList(context, accessTokenResponse.getAccess_token());
                            if (response!=null)
                            {
                                Message message = new Message();
                                message.obj = response;
                                UpdateHandler.sendMessage(message);
                            }
                            else
                            {
                                b.c(context, "连接服务器失败");
                                Log.e("课表微件", "连接服务器失败");
                                LogToFile.e("课表微件", "连接服务器失败");
                                if(CourseBean.getLocalList()!=null){
                                    if (!CourseBean.getLocalList().isEmpty()){
                                        Log.i("课表微件", "显示缓存数据");
                                        LogToFile.i("课表微件", "显示缓存数据");
                                        b.c(context, State.DEFAULT);
                                    }
                                }
                            }
                        }).start();
                    } else {
                        final State data_ERROR = State.DATA_ERROR;
                        b.c(context, data_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    b.c(context, e.getLocalizedMessage());
                }
            }
        };

        new Thread(() -> {
            Response response = BackendUtils.GetAccessToken(context);
            if (response!=null)
            {
                Message message = new Message();
                message.obj = response;
                TokenHandler.sendMessage(message);
            }
            else
            {
                b.c(context, "连接服务器失败");
                Log.e("课表微件", "连接服务器失败");
                LogToFile.e("课表微件", "连接服务器失败");
                if(CourseBean.getLocalList()!=null){
                    if (!CourseBean.getLocalList().isEmpty()){
                        Log.i("课表微件", "显示缓存数据");
                        LogToFile.i("课表微件", "显示缓存数据");
                        b.c(context, State.DEFAULT);
                    }
                }
            }
        }).start();
    }

    public void onAppWidgetOptionsChanged(final Context context, final AppWidgetManager appWidgetManager, final int n, final Bundle bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, n, bundle);
    }

    public enum State {
        DATA_ERROR(CourseBean.CourseEmptyWords.DATA_ERR.txt),
        DATA_NULL(CourseBean.CourseEmptyWords.DATA_NO.txt),
        DEFAULT(""),
        LOADING(CourseBean.CourseEmptyWords.DATA_LOADING.txt),
        NOT_LOGIN("用户未登录"),
        VISITOR_OR_PARENT(CourseBean.CourseEmptyWords.USER_FORBIDDEN.txt);
        public String txt;

        State(final String txt) {
            this.txt = txt;
        }
    }
}
