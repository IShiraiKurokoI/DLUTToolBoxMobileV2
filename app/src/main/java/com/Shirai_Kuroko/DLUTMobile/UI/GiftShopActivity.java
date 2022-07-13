package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.PresentListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class GiftShopActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_shop);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_user_points = requireViewById(R.id.tv_user_points);
        tv_user_points.setText(ConfigHelper.GetUserScoreBean(this).getData().getUser_points().toString());
        RecyclerView recyclerView = requireViewById(R.id.PresentList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        PresentListAdapter presentListAdapter = new PresentListAdapter(this,null);
        recyclerView.setAdapter(presentListAdapter);
        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        BackendUtils.GetGiftList(this,presentListAdapter,loadingView);
    }
}