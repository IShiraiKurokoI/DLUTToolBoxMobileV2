package com.Shirai_Kuroko.DLUTMobile.Common;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.OpenVirtualCardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.WidgetQRLauncherActivity;

public class CourseWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    @SuppressLint("UnspecifiedImmutableFlag")
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("onUpdate", "");
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget);

            views.setOnClickPendingIntent(R.id.root_container, null);

            Intent intent = new Intent(context, WidgetQRLauncherActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.btn_start_qr_scan, pendingIntent);

            Intent intent1 = new Intent(context, OpenVirtualCardActivity.class);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
            views.setOnClickPendingIntent(R.id.btn_start_virtual_card, pendingIntent1);

            Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
            views.setOnClickPendingIntent(R.id.btn_refresh, pendingIntent2);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
