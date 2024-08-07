package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;

@Deprecated
public class BrowserScanQRCodeActivity extends AppCompatActivity {
//    private ScanSurfaceView mScanSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
//        initView();
    }

}