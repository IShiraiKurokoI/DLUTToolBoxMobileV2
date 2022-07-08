package com.Shirai_Kuroko.DLUTMobile.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;

public class GiftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        Toast.makeText(this,"抱歉，此功能暂未实现！",Toast.LENGTH_SHORT).show();
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