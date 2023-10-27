package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;

public class ApiQRLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apilogin_confirm);
        String URL = getIntent().getStringExtra("whistle_info");
        String url = "";
        try {
            url = URL.split("whistle_info=")[1];
        }catch (Exception e)
        {
            Toast.makeText(this, "无效的二维码", Toast.LENGTH_SHORT).show();
            finish();
        }
        if(url.length()<5)
        {
            Toast.makeText(this, "无效的二维码", Toast.LENGTH_SHORT).show();
            finish();
        }
        String LoginUrl = url;
        Button btn_login_qrcode =findViewById(R.id.btn_login_qrcode);
        btn_login_qrcode.setOnClickListener(view -> BackendUtils.ApiQRPreLogin(this,LoginUrl));
        TextView tv_qrcode_login_cancel =findViewById(R.id.tv_qrcode_login_cancel);
        tv_qrcode_login_cancel.setOnClickListener(view -> finish());
    }
}