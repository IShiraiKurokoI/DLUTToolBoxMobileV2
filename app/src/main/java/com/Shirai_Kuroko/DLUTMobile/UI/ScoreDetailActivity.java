package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class ScoreDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView btn_in = requireViewById(R.id.btn_in);
        TextView btn_out = requireViewById(R.id.btn_out);
        RecyclerView recyclerView = requireViewById(R.id.DetailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ToDo:设置适配器

        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        loadingView.dismiss();
        //初始化数据
        btn_in.setOnClickListener(view -> {
            btn_out.setTextColor(getColor(R.color.score_header_text_color));
            btn_out.setBackground(null);
            btn_in.setTextColor(getColor(R.color.black));
            btn_in.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            //ToDo:加载数据
            loadingView.dismiss();
        });
        btn_out.setOnClickListener(view -> {
            btn_in.setTextColor(getColor(R.color.score_header_text_color));
            btn_in.setBackground(null);
            btn_out.setTextColor(getColor(R.color.black));
            btn_out.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            //ToDo:加载数据
            loadingView.dismiss();
        });
    }
}