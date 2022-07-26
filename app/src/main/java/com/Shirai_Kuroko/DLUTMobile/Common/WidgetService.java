package com.Shirai_Kuroko.DLUTMobile.Common;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.CourseBean;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WidgetService extends RemoteViewsService {

    public WidgetService()
    {
        super();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ViewFactory(getApplicationContext(), intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static class ViewFactory implements RemoteViewsFactory {

        public Context a;
        public List<CourseBean> b;

        public ViewFactory(final Context a, final Intent intent) {
            super();
            this.b = new ArrayList<>();
            this.a = a;
            this.b.addAll(CourseBean.getLocalList());
        }

        @Override
        public void onCreate() {
            Log.d("ViewFactory", "onCreate ");
        }

        @Override
        public void onDataSetChanged() {
            this.b.clear();
            this.b.addAll(CourseBean.getLocalList());
        }

        @Override
        public void onDestroy() {
            this.b.clear();
        }

        @Override
        public int getCount() {
            return this.b.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (i >= this.b.size()) {
                return null;
            }
            final CourseBean courseBean = this.b.get(i);
            final int itemType = courseBean.getItemType();
            RemoteViews remoteViews;
            if (itemType != 1) {
                if (itemType != 2) {
                    remoteViews = new RemoteViews(this.a.getPackageName(), R.layout.item_widget_course);
                    remoteViews.setTextViewText(R.id.tv_date, courseBean.getLessonSuccession());
                    remoteViews.setTextViewText(R.id.tv_time, courseBean.getBeginTime());
                    remoteViews.setTextViewText(R.id.tv_course, courseBean.getCourseName());
                    remoteViews.setTextViewText(R.id.tv_location, courseBean.getClassRoom());

                    final int itemType2 = courseBean.getItemType();
                    int n = View.INVISIBLE;
                    if (3 == itemType2) {
                        remoteViews.setViewVisibility(R.id.divider, View.INVISIBLE);
                    } else {
                        final int divider = R.id.divider;
                        if (i != 1) {
                            n = View.VISIBLE;
                        }
                        remoteViews.setViewVisibility(divider, n);
                    }
                } else {
                    remoteViews = new RemoteViews(this.a.getPackageName(), R.layout.layout_course_widget_empty);
                    remoteViews.setTextViewText(R.id.empty_wording, courseBean.getCourseName());
                }
            } else {
                remoteViews = new RemoteViews(this.a.getPackageName(), R.layout.item_widget_date);
                final Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日",Locale.PRC);
                remoteViews.setTextViewText(R.id.text_today,simpleDateFormat.format(new Date()));
                final int text_today_week = R.id.text_today_week;
                final Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                remoteViews.setTextViewText(text_today_week, (new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"})[instance.get(7) - 1]);
            }
            final Intent intent = new Intent();
            intent.putExtra("position", i);
            remoteViews.setOnClickFillInIntent(R.id.widget_item_container, intent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
