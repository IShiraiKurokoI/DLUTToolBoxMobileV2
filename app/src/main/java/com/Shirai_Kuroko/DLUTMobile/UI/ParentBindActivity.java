package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;

public class ParentBindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_bind);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        Toast.makeText(this,"抱歉，此功能暂未实现！",Toast.LENGTH_SHORT).show();
    }
}