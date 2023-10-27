package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.ExchangeRecordAdapter;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class ExchangeRecordActivity extends AppCompatActivity {

    private int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_record);
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView btn_to_get = findViewById(R.id.btn_to_get);
        TextView btn_geted = findViewById(R.id.btn_geted);
        RecyclerView recyclerView = findViewById(R.id.RecordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayout emptyview = findViewById(R.id.RecordEmptyView);
        ExchangeRecordAdapter exchangeRecordAdapter = new ExchangeRecordAdapter(this,null,recyclerView,emptyview);
        recyclerView.setAdapter(exchangeRecordAdapter);
        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        BackendUtils.GetExchangeRecord(this,"0",exchangeRecordAdapter,loadingView);
        //初始化数据
        btn_to_get.setOnClickListener(view -> {
            if(type==0)
            {
                return;
            }
            type=0;
            btn_geted.setTextColor(getColor(R.color.score_header_text_color));
            btn_geted.setBackground(null);
            btn_to_get.setTextColor(getColor(R.color.black));
            btn_to_get.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            BackendUtils.GetExchangeRecord(this,"0",exchangeRecordAdapter,loadingView);
        });
        btn_geted.setOnClickListener(view -> {
            if(type==1)
            {
                return;
            }
            type=1;
            btn_to_get.setTextColor(getColor(R.color.score_header_text_color));
            btn_to_get.setBackground(null);
            btn_geted.setTextColor(getColor(R.color.black));
            btn_geted.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            BackendUtils.GetExchangeRecord(this,"1",exchangeRecordAdapter,loadingView);
        });
    }
}