package com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.CommentActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.BaseActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.bumptech.glide.Glide;

public class AppDetailActivity extends BaseActivity {

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
        thisapp = ConfigHelper.Getmlist(this).get(numid);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        ImageView di = findViewById(R.id.DetailImage);
        Glide.with(this).load(thisapp.getIcon()).into(di);
        TextView dt = findViewById(R.id.DetailName);
        dt.setText(thisapp.getAppName());
        TextView dd = findViewById(R.id.app_introduction);
        dd.setText(thisapp.getDescribe());
        dd.setTextColor(Color.GRAY);
        Button bo = findViewById(R.id.open);
        TextView tdc = findViewById(R.id.app_style);
        tdc.setText(ConfigHelper.GetCatogoryName(thisapp.getCategory()));

        TextView tdh = findViewById(R.id.app_hot);
        tdh.setText(String.valueOf(thisapp.getPopularity()));

        TextView tda = findViewById(R.id.app_author);

        RadioButton app_user_comment = findViewById(R.id.app_user_comment);
        RadioButton app_instruction = findViewById(R.id.app_instruction);
        if (thisapp.getType() != null) {
            if (thisapp.getType().contains("custom")) {
                tda.setText("自制应用");
            } else {
                tda.setText("校方应用");
            }
            app_user_comment.setOnClickListener(view -> {
                CenterToast.makeText(this, "抱歉，该应用暂不支持评论", Toast.LENGTH_SHORT).show();
                app_instruction.setChecked(true);
                app_user_comment.setChecked(false);
            });
            app_user_comment.setEnabled(true);
        } else {
            if (thisapp.getApp_id() == null) {
                tda.setText("替换应用");
                app_user_comment.setOnClickListener(view -> {
                    CenterToast.makeText(this, "抱歉，该应用暂不支持评论", Toast.LENGTH_SHORT).show();
                    app_instruction.setChecked(true);
                    app_user_comment.setChecked(false);
                });
                app_user_comment.setEnabled(true);
            } else {
                tda.setText("校方应用");
                BackendUtils.GetAppConfigNow(this, thisapp.getApp_id(), this);
            }
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

    public void CommentApp() {
        Intent Commentintent = new Intent(this, CommentActivity.class);
        startActivityForResultHere(Commentintent, 100, (i, i1, intent) -> {
            if (intent.getIntExtra("resultcode", -1) == 1) {//succeed
                LogToFile.i("CommentApp","完成评论\n分数："+intent.getIntExtra("score", 5)+"\n内容："+intent.getStringExtra("comment"));
                BackendUtils.CommentAppFunInfo(this, thisapp.getApp_id(), intent.getStringExtra("comment"), intent.getIntExtra("score", 5),this);
                return true;
            } else if (intent.getIntExtra("resultcode", -1) == 0) {
                Log.i("TAG", "CommentApp: 取消评论");
                CenterToast.makeText(this, "取消评论", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return false;
            }
        });
    }

    public void onAddClick(View v) {
        int App_ID = thisapp.getId();
        if (ConfigHelper.Getmlist(this).get(App_ID).getIssubscription() == 0) {
            ConfigHelper.addsubscription(this, App_ID);
        } else {
            ConfigHelper.removesubscription(this, App_ID);
        }
        thisapp = ConfigHelper.Getmlist(this).get(App_ID);
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