package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Entities.UserScoreBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

public class GiftActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView Score = findViewById(R.id.tv_gift_home_my_score);
        TextView Rank = findViewById(R.id.tv_gift_home_rank);
        TextView Income = findViewById(R.id.tv_gift_home_income);
        TextView Outcome = findViewById(R.id.tv_gift_home_pay);
        UserScoreBean userScoreBean = ConfigHelper.GetUserScoreBean(this);
        Score.setText(String.valueOf(userScoreBean.getData().getUser_points()));
        Rank.setText("超过了全校" + userScoreBean.getData().getRank() + "的人");
        Income.setText(String.valueOf(userScoreBean.getData().getIn_points()));
        Outcome.setText(String.valueOf(userScoreBean.getData().getOut_points()));
        BackendUtils.GetScore(this, Score, Rank, Income, Outcome);
        TextView tv_intro = findViewById(R.id.tv_intro);
        tv_intro.setOnClickListener(view -> {
            Intent intent = new Intent(GiftActivity.this, PureBrowserActivity.class);
            intent.putExtra("Name", "积分说明");
            intent.putExtra("Url", "file:///android_asset/ScoreIntro.html");
            startActivity(intent);
        });
        RelativeLayout Score_detail = findViewById(R.id.Score_detail);
        Score_detail.setOnClickListener(view -> {
            Intent intent = new Intent(GiftActivity.this, ScoreDetailActivity.class);
            startActivity(intent);
        });
        RelativeLayout Gift_Record = findViewById(R.id.Gift_Record);
        Gift_Record.setOnClickListener(view -> {
            Intent intent = new Intent(GiftActivity.this, ExchangeRecordActivity.class);
            startActivity(intent);
        });
        RelativeLayout Gift_Shop = findViewById(R.id.Gift_Shop);
        Gift_Shop.setOnClickListener(view -> {
            Intent intent = new Intent(GiftActivity.this, GiftShopActivity.class);
            startActivity(intent);
        });
        RelativeLayout Score_Rank = findViewById(R.id.Score_Rank);
        Score_Rank.setOnClickListener(view -> {
            Intent intent = new Intent(GiftActivity.this, ScoreRankActivity.class);
            startActivity(intent);
        });
        RelativeLayout Score_lottery = findViewById(R.id.Score_lottery);
        Score_lottery.setOnClickListener(view -> Toast.makeText(getBaseContext(), "虽然但是，这个功能好像原版就没用？", Toast.LENGTH_LONG).show());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}