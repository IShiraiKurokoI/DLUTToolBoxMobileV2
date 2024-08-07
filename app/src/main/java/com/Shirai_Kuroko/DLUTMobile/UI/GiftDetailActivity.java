package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Adapters.GiftDetailBannerAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.PresentListResult;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
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
        TextView back = requireViewById(R.id.back);
        back.setOnClickListener(view -> finish());
        Banner banner = requireViewById(R.id.viewpager);
        banner.setAdapter(new GiftDetailBannerAdapter(Gift.getImage(),this));
        banner.addBannerLifecycleObserver(this);
        banner.setIndicator(new RectangleIndicator(this));
        TextView tv_gift_name = requireViewById(R.id.tv_gift_name);
        tv_gift_name.setText(Gift.getName());
        TextView tv_gift_score = requireViewById(R.id.tv_gift_score);
        tv_gift_score.setText(Gift.getNeedPoints());
        TextView tv_gift_price = requireViewById(R.id.tv_gift_price);
        tv_gift_price.setText("市场价"+Gift.getPrice());
        tv_gift_price.setPaintFlags(tv_gift_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tv_gift_store_count = requireViewById(R.id.tv_gift_store_count);
        tv_gift_store_count.setText("已售出"+Gift.getSaleCount()+"件，库存"+Gift.getStoreCount()+"件");
        WebView tv_gift_intro = requireViewById(R.id.tv_gift_intro);
        tv_gift_intro.getSettings().setTextZoom(100);
        tv_gift_intro.setForceDarkAllowed(true);
        tv_gift_intro.getSettings().setDisabledActionModeMenuItems(DEFAULT_KEYS_DISABLE);
        tv_gift_intro.setLongClickable(true);
        tv_gift_intro.setOnLongClickListener(v -> true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            tv_gift_intro.getSettings().setAlgorithmicDarkeningAllowed(ConfigHelper.GetThemeType(this));
        }else {
            if (ConfigHelper.GetThemeType(this)) {
                tv_gift_intro.getSettings().setForceDark(WebSettings.FORCE_DARK_ON);
            } else {
                tv_gift_intro.getSettings().setForceDark(WebSettings.FORCE_DARK_OFF);
            }
        }
        tv_gift_intro.loadDataWithBaseURL(null, URLDecoder.decode(Gift.getRemark()), "text/html", "utf-8", null);
        tv_gift_intro.setScrollBarSize(0);
        Button btn_exchange= requireViewById(R.id.btn_exchange);
        if(ConfigHelper.GetUserScoreBean(this).getData().getUser_points()<Integer.parseInt(Gift.getNeedPoints()))
        {
            btn_exchange.setEnabled(false);
            btn_exchange.setText("积分不足");
        }
        btn_exchange.setOnClickListener(v -> {
            Dialog Dialog = new Dialog(this, R.style.CustomDialog);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(
                    R.layout.dialog_confirm_center, null);
            final TextView title = view.findViewById(R.id.title);
            title.setText("请确认兑换");
            final TextView msg = view.findViewById(R.id.msg);
            msg.setText("是否使用" + Gift.getNeedPoints() + "积分兑换"+Gift.getName()+"?");
            final Button ok = view.findViewById(R.id.ok);
            ok.setOnClickListener(view1 -> {
                BackendUtils.GiftExchange(this,Integer.parseInt(Gift.getPresentId()));
                Dialog.dismiss();
            });
            final Button cancel = view.findViewById(R.id.cancel);
            cancel.setOnClickListener(view12 -> Dialog.dismiss());
            Window window = Dialog.getWindow();
            window.setContentView(view);
            window.setGravity(Gravity.CENTER);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    android.view.WindowManager.LayoutParams.WRAP_CONTENT);
            Dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.alpha = 0.5f;
            this.getWindow().setAttributes(lp);
            Dialog.setOnDismissListener(dialogInterface -> {
                WindowManager.LayoutParams lp1 = this.getWindow().getAttributes();
                lp1.alpha = 1f;
                this.getWindow().setAttributes(lp1);
            });
            Dialog.show();
        });

    }
}