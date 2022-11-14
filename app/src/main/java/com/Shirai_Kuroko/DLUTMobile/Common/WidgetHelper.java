package com.Shirai_Kuroko.DLUTMobile.Common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.CourseBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.SplashActivity;

public class WidgetHelper {
    public static void M(final RemoteViews remoteViews, final boolean b) {
        final int list_course = R.id.list_course;
        final int n = View.GONE;
        int n2;
        if (b) {
            n2 = View.VISIBLE;
        }
        else {
            n2 = View.GONE;
        }
        remoteViews.setViewVisibility(list_course, n2);
        final int content_container = R.id.content_container;
        int n3 = n;
        if (!b) {
            n3 = View.VISIBLE;
        }
        remoteViews.setViewVisibility(content_container, n3);
    }

    public static RemoteViews v(final Context context, final String anObject) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_course_widget_empty);
        remoteViews.setTextViewText(R.id.empty_wording, (CharSequence)anObject);
        if (CourseBean.CourseEmptyWords.USER_FORBIDDEN.txt.equals(anObject)) {
            final Intent intent = new Intent(context, SplashActivity.class);
            remoteViews.setOnClickPendingIntent(R.id.widget_item_container, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE));
        }
        return remoteViews;
    }
}
