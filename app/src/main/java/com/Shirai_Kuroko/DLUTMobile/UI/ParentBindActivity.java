package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;

import java.util.Date;
import java.util.Objects;

public class ParentBindActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_bind);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_intro = requireViewById(R.id.tv_intro);
        tv_intro.setOnClickListener(view -> {
            Intent intent = new Intent(ParentBindActivity.this, PureBrowserActivity.class);
            intent.putExtra("Name", "");
            intent.putExtra("Url", "https://apps-xiaoyuan.m.dlut.edu.cn/explain");
            startActivity(intent);
        });
        TextView tv_activated_wording = requireViewById(R.id.tv_activated_wording);
        TextView tv_activated_time = requireViewById(R.id.tv_activated_time);
        LoginResponseBean userbean = ConfigHelper.GetUserBean(this);
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = userbean.getData().getMy_info();
        if(Objects.equals(infoDTO.getParent_active(), "1"))
        {
            tv_activated_wording.setText("您的家长账号已激活，绑定手机号为"+infoDTO.getParent_bind());
            tv_activated_time.setText("激活时间："+new Date(Long.parseLong(infoDTO.getParent_activetime())*1000).toLocaleString());
        }
        else
        {
            tv_activated_wording.setText("您尚未开通家校通");
            tv_activated_time.setText("i大工+暂不支持家校通相关功能，请使用原版i大工进行操作！");
            tv_activated_time.setTextColor(Color.RED);
        }
    }
}