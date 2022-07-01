package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

public class ContextHelper extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static Context getContext() {
        return context;
    }
}