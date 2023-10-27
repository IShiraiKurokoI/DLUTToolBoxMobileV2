package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.ScoreDetailAdapter;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class ScoreDetailActivity extends AppCompatActivity {

    private int type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView btn_in = findViewById(R.id.btn_in);
        TextView btn_out = findViewById(R.id.btn_out);
        RecyclerView recyclerView = findViewById(R.id.DetailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayout emptyview = findViewById(R.id.RecordEmptyView);
        ScoreDetailAdapter scoreDetailAdapter = new ScoreDetailAdapter(this,null,recyclerView,emptyview);
        recyclerView.setAdapter(scoreDetailAdapter);
        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        BackendUtils.GetScoreDetail(this,"1",scoreDetailAdapter,loadingView);
        //初始化数据
        btn_in.setOnClickListener(view -> {
            if(type==1)
            {
                return;
            }
            type=1;
            btn_out.setTextColor(getColor(R.color.score_header_text_color));
            btn_out.setBackground(null);
            btn_in.setTextColor(getColor(R.color.black));
            btn_in.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            scoreDetailAdapter.setPrefix("+");
            BackendUtils.GetScoreDetail(this,"1",scoreDetailAdapter,loadingView);
        });
        btn_out.setOnClickListener(view -> {
            if(type==2)
            {
                return;
            }
            type=2;
            btn_in.setTextColor(getColor(R.color.score_header_text_color));
            btn_in.setBackground(null);
            btn_out.setTextColor(getColor(R.color.black));
            btn_out.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            loadingView.show();
            scoreDetailAdapter.setPrefix("-");
            BackendUtils.GetScoreDetail(this,"2",scoreDetailAdapter,loadingView);
        });
    }
}