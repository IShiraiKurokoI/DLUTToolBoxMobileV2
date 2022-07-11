package com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.bumptech.glide.Glide;

public class AppDetailActivity extends AppCompatActivity {

    int numid = 0;
    ApplicationConfig thisapp;
    boolean ThemeType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Intent intent = getIntent();
        numid = intent.getIntExtra("App_ID", 0);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ThemeType = prefs.getBoolean("Dark", false);
        thisapp = ConfigHelper.getmlist(this).get(numid);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView title = requireViewById(R.id.tv_title);
        title.setText(thisapp.getAppName());
        ImageView di = findViewById(R.id.DetailImage);
        Glide.with(this).load(thisapp.getIcon()).into(di);
        TextView dt = findViewById(R.id.DetailName);
        dt.setText(thisapp.getAppName());
        TextView dd = findViewById(R.id.DetailDescription);
        dd.setText(thisapp.getDescribe());
        dd.setTextColor(Color.GRAY);
        Button bo = findViewById(R.id.open);
        TextView tdc = findViewById(R.id.detailcatagory);
        tdc.setText(ConfigHelper.getcatogoryname(thisapp.getCategory()));
        if (ThemeType) {
            bo.setTextColor(Color.WHITE);
        } else {
            bo.setTextColor(Color.BLACK);
        }
        ImageButton ba = findViewById(R.id.add);
        if (thisapp.getIssubscription() == 1) {
            ba.setBackgroundResource(R.drawable.btn_cancel_app);
        } else {
            ba.setBackgroundResource(R.drawable.btn_add_app);
        }
        bo.setOnClickListener(this::onOpenClick);
        ba.setOnClickListener(this::onAddClick);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddClick(View v) {
        int App_ID = thisapp.getId();
        if (ConfigHelper.getmlist(this).get(App_ID).getIssubscription() == 0) {
            ConfigHelper.addsubscription(this, App_ID);
        } else {
            ConfigHelper.removesubscription(this, App_ID);
        }
        thisapp = ConfigHelper.getmlist(this).get(App_ID);
        ImageButton ba = v.findViewById(R.id.add);
        if (thisapp.getIssubscription() == 1) {
            ba.setBackgroundResource(R.drawable.btn_cancel_app);
        } else {
            ba.setBackgroundResource(R.drawable.btn_add_app);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onOpenClick(View v) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("App_ID", thisapp.getId().toString());
        startActivity(intent);
    }
}