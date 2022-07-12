package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.RankAdapter;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class ScoreRankActivity extends AppCompatActivity {

    private int type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_rank);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView btn_received_gifts = requireViewById(R.id.btn_received_gifts);
        TextView btn_sent_gifts = requireViewById(R.id.btn_sent_gifts);
        RecyclerView recyclerView = requireViewById(R.id.RankList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RankAdapter rankAdapter = new RankAdapter(this, null);
        recyclerView.setAdapter(rankAdapter);
        recyclerView.addItemDecoration(new SimplePaddingDecoration(this));
        LoadingView loadingView = new LoadingView(this, R.style.CustomDialog);
        loadingView.show();
        BackendUtils.GetRank(this, "1", rankAdapter, loadingView);
        btn_received_gifts.setOnClickListener(view -> {
            if(type==1)
            {
                return;
            }
            type=1;
            btn_sent_gifts.setTextColor(getColor(R.color.score_header_text_color));
            btn_sent_gifts.setBackground(null);
            btn_received_gifts.setTextColor(getColor(R.color.black));
            btn_received_gifts.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            rankAdapter.setcoin(true);
            loadingView.show();
            BackendUtils.GetRank(this, "1", rankAdapter, loadingView);
        });
        btn_sent_gifts.setOnClickListener(view -> {
            if(type==2)
            {
                return;
            }
            type=2;
            btn_received_gifts.setTextColor(getColor(R.color.score_header_text_color));
            btn_received_gifts.setBackground(null);
            btn_sent_gifts.setTextColor(getColor(R.color.black));
            btn_sent_gifts.setBackground(AppCompatResources.getDrawable(getBaseContext(), R.drawable.round_edge));
            rankAdapter.setcoin(false);
            loadingView.show();
            BackendUtils.GetRank(this, "2", rankAdapter, loadingView);
        });

    }

    public static class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

        public SimplePaddingDecoration(Context context) {
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
        }
    }
}