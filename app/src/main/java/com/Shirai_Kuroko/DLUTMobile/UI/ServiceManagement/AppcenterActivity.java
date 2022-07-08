package com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments.ServiceCenterFragment;

public class AppcenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcenter);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.appcenter, new ServiceCenterFragment(1))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}