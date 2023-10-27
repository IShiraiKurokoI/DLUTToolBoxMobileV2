package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Adapters.GiftDetailBannerAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.PresentListResult;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.alibaba.fastjson.JSON;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import java.net.URLDecoder;

public class GiftDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ALL")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);
        PresentListResult.DataDTO.ListDTO Gift = JSON.parseObject(getIntent().getStringExtra("GiftObject"),PresentListResult.DataDTO.ListDTO.class);
        Log.i("礼物数据", JSON.toJSONString(Gift));
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());
        Banner banner = findViewById(R.id.viewpager);
        banner.setAdapter(new GiftDetailBannerAdapter(Gift.getImage(),this));
        banner.addBannerLifecycleObserver(this);
        banner.setIndicator(new RectangleIndicator(this));
        TextView tv_gift_name = findViewById(R.id.tv_gift_name);
        tv_gift_name.setText(Gift.getName());
        TextView tv_gift_score = findViewById(R.id.tv_gift_score);
        tv_gift_score.setText(Gift.getNeedPoints());
        TextView tv_gift_price = findViewById(R.id.tv_gift_price);
        tv_gift_price.setText("市场价"+Gift.getPrice());
        tv_gift_price.setPaintFlags(tv_gift_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tv_gift_store_count = findViewById(R.id.tv_gift_store_count);
        tv_gift_store_count.setText("已售出"+Gift.getSaleCount()+"件，库存"+Gift.getStoreCount()+"件");
        WebView tv_gift_intro = findViewById(R.id.tv_gift_intro);
        tv_gift_intro.getSettings().setTextZoom(100);
        tv_gift_intro.setForceDarkAllowed(true);
        tv_gift_intro.getSettings().setDisabledActionModeMenuItems(DEFAULT_KEYS_DISABLE);
        tv_gift_intro.setLongClickable(true);
        tv_gift_intro.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        tv_gift_intro.loadDataWithBaseURL(null, URLDecoder.decode(Gift.getRemark()), "text/html", "utf-8", null);
        tv_gift_intro.setScrollBarSize(0);
        Button btn_exchange= findViewById(R.id.btn_exchange);
        if(ConfigHelper.GetUserScoreBean(this).getData().getUser_points()<Integer.parseInt(Gift.getNeedPoints()))
        {
            btn_exchange.setEnabled(false);
            btn_exchange.setText("积分不足");
        }
        btn_exchange.setOnClickListener(view -> Toast.makeText(getBaseContext(), "请使用原版i大工进行兑换！", Toast.LENGTH_LONG).show());

    }
}