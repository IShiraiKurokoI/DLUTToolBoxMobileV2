package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;

public class NotificationManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manage);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_more = requireViewById(R.id.tv_more);
        tv_more.setOnClickListener(this::showPopupWindow);
    }

    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(this).inflate(R.layout.popup_notification_manage_right_more, null);
        PopupWindow window = new PopupWindow(v, 360, 750, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setAnimationStyle(R.style.main_more_anim);
        window.showAsDropDown(view, 0, 0);
        v.findViewById(R.id.btn_delete).setOnClickListener(view1 -> {

            window.dismiss();
        });
        v.findViewById(R.id.btn_select_all).setOnClickListener(view12 -> {

            window.dismiss();
        });
        v.findViewById(R.id.btn_reverse).setOnClickListener(view13 -> {

            window.dismiss();
        });
        v.findViewById(R.id.btn_cancel).setOnClickListener(view14 -> {

            window.dismiss();
        });
    }
}