package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

public class AboutActivity extends AppCompatActivity {

    private Context mContext;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = this;
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView version = requireViewById(R.id.version_tv);
        version.setText("V" + MobileUtils.GetAppVersion(mContext));
        TextView UpdateLabel = requireViewById(R.id.version_update_label);
        RelativeLayout HasNewVersion = requireViewById(R.id.has_new_version);
        MobileUtils.CheckUpDate(mContext, UpdateLabel, HasNewVersion, false);
        RelativeLayout update = requireViewById(R.id.version_update_panel);
        update.setOnClickListener(v -> {
            Toast.makeText(mContext, "正在检查更新", Toast.LENGTH_SHORT).show();
            MobileUtils.CheckUpDate(mContext, UpdateLabel, HasNewVersion, true);
        });
        RelativeLayout Invite = requireViewById(R.id.share_to_friends);
        Invite.setOnClickListener(v -> MobileUtils.ShareToFriend(mContext));
        RelativeLayout Intro = requireViewById(R.id.version_intro);
        Intro.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PureBrowserActivity.class);
            intent.putExtra("Name", "版本介绍");
            intent.putExtra("Url", "file:///android_asset/version_intro.html");
            startActivity(intent);
        });
        RelativeLayout DisClaimer = requireViewById(R.id.service_disclaimer);
        DisClaimer.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PureBrowserActivity.class);
            intent.putExtra("Name", "服务协议");
            intent.putExtra("Url", "https://its.dlut.edu.cn/upload/app/agreements/index.html");
            startActivity(intent);
        });
        RelativeLayout Privacy = requireViewById(R.id.privacy_policy);
        Privacy.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PureBrowserActivity.class);
            intent.putExtra("Name", "隐私政策");
            intent.putExtra("Url", "https://its.dlut.edu.cn/upload/app/privacy/index.html");
            startActivity(intent);
        });
        RelativeLayout github = requireViewById(R.id.github);
        github.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/IShiraiKurokoI/DLUTToolBoxMobileV2");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}