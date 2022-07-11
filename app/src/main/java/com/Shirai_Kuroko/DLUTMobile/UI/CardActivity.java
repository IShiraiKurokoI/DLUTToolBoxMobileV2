package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Entities.IDPhotoResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.bumptech.glide.Glide;

public class CardActivity extends AppCompatActivity {

    private Context context;
    private Handler handler = new Handler();
    private Runnable runnable;

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        context = this;
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        ImageView bg = requireViewById(R.id.iv_school_card);
        LoginResponseBean loginResponseBean = ConfigHelper.GetUserBean(this);
        String Identity = loginResponseBean.getData().getMy_info().getAuthority_identity();
        String Name = loginResponseBean.getData().getMy_info().getName();
        TextView name = requireViewById(R.id.tv_school_card_name);
        String Number = loginResponseBean.getData().getMy_info().getStudentNumber();
        TextView number = requireViewById(R.id.tv_school_card_second_line);
        String Org = loginResponseBean.getData().getMy_info().getOrg().get(0).getName();
        TextView org = requireViewById(R.id.tv_school_card_three_line);
        ImageView idp = requireViewById(R.id.iv_school_card_head);
        IDPhotoResult idPhotoResult = ConfigHelper.GetIDPhoto(this);
        if (idPhotoResult != null) {
            String base64 = idPhotoResult.getData().getId_photo();
            base64 = base64.replace("\\/", "/");
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            idp.setImageBitmap(myBitmap);
        }
        switch (Identity) {
            case "teacher": {
                Glide.with(this).load("https://store.m.dlut.edu.cn/group1/M00/00/08/ynZM2VkeWmaAM-KOAADN7zRo2AM2844791").into(bg);
                break;
            }
            case "master": {
                Glide.with(this).load("https://store.m.dlut.edu.cn/group1/M00/00/08/ynZM2VkeWmiASfbLAAKB81vLQoA7280918").into(bg);
                break;
            }
            default: {
                Glide.with(this).load("https://store.m.dlut.edu.cn/group1/M00/00/08/ynZM2VkeWmeATT1oAAJ416V6N7k0520839").into(bg);
                break;
            }
        }
        name.setText(" 姓 名：" + Name);
        number.setText(" 学 号：" + Number);
        org.setText(" 院 系：" + Org);
        ImageView qrcode = requireViewById(R.id.iv_act_my_card_qrcode);
        BackendUtils.LoadQRCode(this, qrcode);
        qrcode.setOnClickListener(view -> BackendUtils.LoadQRCode(context, qrcode));
        BackendUtils.LoadIDPhoto(this, idp);
        runnable = new Runnable() {
            @Override
            public void run() {
                BackendUtils.LoadQRCode(context, qrcode);
                handler.postDelayed(this, 300000);
            }
        };
        handler.postDelayed(runnable, 300000);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}