package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.net.URLDecoder;
import java.util.Date;

public class GiftExchangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_exchange);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        ExchangeRecordResult.DataDTO.ListDTO DTO = JSON.parseObject(data,ExchangeRecordResult.DataDTO.ListDTO.class);

        Glide.with(this).load(DTO.getImage().get(0)).into((ImageView)findViewById(R.id.iv_exchange_gift_icon));
        ((TextView)findViewById(R.id.tv_exchange_gift_name)).setText(DTO.getName());
        ((TextView)findViewById(R.id.tv_gift_exchange_score)).setText(DTO.getPoints());
        // 中奖文本
        if (DTO.getType() == 1){
            ((TextView)findViewById(R.id.tv_gift_exchange_tips)).setText("恭喜你抽中了奖品！请按下方提示进行领取！");
            ((TextView)findViewById(R.id.tv_exchange_gift_third_title)).setText("中奖时间");
        }
        ImageView iv_gift_exchange_mark = findViewById(R.id.iv_gift_exchange_mark);
        TextView tv_gift_exchange_tips = findViewById(R.id.tv_gift_exchange_tips);
        switch (DTO.getIs_exchange()){
            case 1:{
                iv_gift_exchange_mark.setImageResource(R.drawable.mark_gift_exchange_receive);
                tv_gift_exchange_tips.setVisibility(View.GONE);
                break;
            }
            case 2:{
                iv_gift_exchange_mark.setImageResource(R.drawable.icon_out_date);
                tv_gift_exchange_tips.setVisibility(View.GONE);
                break;
            }
            case 4:{
                iv_gift_exchange_mark.setImageResource(R.drawable.icon_gift_exchange_undo);
                tv_gift_exchange_tips.setVisibility(View.GONE);
                break;
            }
            default:{
                // 3是啥我不知道
                break;
            }
        }
        ((TextView)findViewById(R.id.tv_gift_exchange_number)).setText(DTO.getId());
        ((TextView)findViewById(R.id.tv_exchange_gift_valid_date)).setText(new Date(DTO.getEnd_time()*1000).toLocaleString());
        ((TextView)findViewById(R.id.tv_exchange_gift_third_value)).setText(new Date(DTO.getCreate_time()*1000).toLocaleString());
        if (DTO.getIs_exchange() == 1){
            findViewById(R.id.ll_gift_exchange_fourth_line).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.tv_gift_exchange_fourth_value)).setText(new Date(DTO.getExchange_time()*1000).toLocaleString());
        }
        else {
            findViewById(R.id.ll_gift_exchange_fourth_line).setVisibility(View.GONE);
        }
        WebView webView = findViewById(R.id.wv_receive_explain);
        webView.setForceDarkAllowed(true);
        webView.setLongClickable(true);
        webView.setOnLongClickListener(v -> true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisabledActionModeMenuItems(WebSettings.MENU_ITEM_NONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            webSettings.setAlgorithmicDarkeningAllowed(ConfigHelper.GetThemeType(this));
        }else {
            if (ConfigHelper.GetThemeType(this)) {
                webSettings.setForceDark(WebSettings.FORCE_DARK_ON);
            } else {
                webSettings.setForceDark(WebSettings.FORCE_DARK_OFF);
            }
        }
        webView.loadDataWithBaseURL("about:blank", URLDecoder.decode(ConfigHelper.GetIntros(this,"ExchangeIntro")), "text/html", "utf-8", null);
        BackendUtils.SetGiftExchangeHeaderClickListener(this, Long.parseLong(DTO.getPresent_id()));
    }
}