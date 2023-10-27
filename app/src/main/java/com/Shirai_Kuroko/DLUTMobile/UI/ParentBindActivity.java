package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

import java.util.Date;
import java.util.Objects;

public class ParentBindActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_bind);
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_intro = findViewById(R.id.tv_intro);
        tv_intro.setOnClickListener(view -> {
            Intent intent = new Intent(ParentBindActivity.this, PureBrowserActivity.class);
            intent.putExtra("Name", "");
            intent.putExtra("Url", "https://apps-xiaoyuan.m.dlut.edu.cn/explain");
            startActivity(intent);
        });
        TextView tv_activated_wording = findViewById(R.id.tv_activated_wording);
        TextView tv_activated_time = findViewById(R.id.tv_activated_time);
        LoginResponseBean userbean = ConfigHelper.GetUserBean(this);
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = userbean.getData().getMy_info();
        if(Objects.equals(infoDTO.getParent_active(), "1"))
        {
            tv_activated_wording.setText("您的家长账号已激活，绑定手机号为"+infoDTO.getParent_bind().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            tv_activated_time.setText("激活时间："+new Date(Long.parseLong(infoDTO.getParent_activetime())*1000).toLocaleString());
        }
        else
        {
            if (!infoDTO.getParent_bind().equals(""))
            {
                tv_activated_wording.setText("您已邀请"+infoDTO.getParent_bind().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")+"开通为您的家长账号");
                tv_activated_time.setText("重新邀请");
                tv_activated_time.setOnClickListener(view -> BackendUtils.ParentBindTestFuction(this,infoDTO.getParent_bind()));
            }
            else
            {
                tv_activated_wording.setText("您尚未开通家校通\ni大工+暂不稳定支持家校通相关功能，以下代码未经测试，建议使用原版i大工进行操作！");
                tv_activated_time.setText("点此开通");
                tv_activated_time.setTextColor(Color.RED);
                tv_activated_time.setOnClickListener(view ->{
                        Dialog dialog = new Dialog(this, R.style.CustomDialog);
                        @SuppressLint("InflateParams") View view2 = LayoutInflater.from(this).inflate(
                                R.layout.dialog_parent_bind, null);
                        final View btn_close = view2.findViewById(R.id.btn_close);
                        btn_close.setOnClickListener(view1 -> dialog.dismiss());
                        final EditText Phone = view2.findViewById(R.id.Phone);
                        final AppCompatButton submit = view2.findViewById(R.id.submit);
                        submit.setOnClickListener(view12 -> {
                            if (!Phone.getText().toString().isEmpty()) {
                                BackendUtils.ParentBindTestFuction(this, Phone.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Window window = dialog.getWindow();
                        window.setContentView(view2);
                        window.setGravity(Gravity.CENTER);
                        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.setCanceledOnTouchOutside(true);
                        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
                        lp.alpha = 0.5f;
                    this.getWindow().setAttributes(lp);
                        dialog.setOnDismissListener(dialogInterface -> {
                            WindowManager.LayoutParams lp1 = this.getWindow().getAttributes();
                            lp1.alpha = 1f;
                            this.getWindow().setAttributes(lp1);
                        });
                        dialog.show();
                    });
            }
        }
    }
}