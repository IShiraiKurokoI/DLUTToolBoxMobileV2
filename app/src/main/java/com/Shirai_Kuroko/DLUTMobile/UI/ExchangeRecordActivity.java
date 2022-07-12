package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class ExchangeRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_record);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView btn_to_get = requireViewById(R.id.btn_to_get);
        TextView btn_geted = requireViewById(R.id.btn_geted);
        RecyclerView recyclerView = requireViewById(R.id.RecordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ToDo:设置适配器

        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        loadingView.dismiss();
        //初始化数据
        btn_to_get.setOnClickListener(view -> {
            btn_geted.setTextColor(getColor(R.color.score_header_text_color));
            btn_geted.setBackground(null);
            btn_to_get.setTextColor(getColor(R.color.black));
            btn_to_get.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            //ToDo:加载数据
            loadingView.dismiss();
        });
        btn_geted.setOnClickListener(view -> {
            btn_to_get.setTextColor(getColor(R.color.score_header_text_color));
            btn_to_get.setBackground(null);
            btn_geted.setTextColor(getColor(R.color.black));
            btn_geted.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            //ToDo:加载数据
            loadingView.dismiss();
        });
        Toast.makeText(this, "抱歉，此功能暂未实现", Toast.LENGTH_SHORT).show();
    }
}