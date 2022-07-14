package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanQRCodeActivity extends AppCompatActivity {
    private com.Shirai_Kuroko.DLUTMobile.Managers.CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        barcodeScannerView = findViewById(R.id.dbv_custom);
        capture = new com.Shirai_Kuroko.DLUTMobile.Managers.CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.setResultCallBack((requestCode, resultCode, intent) -> {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (null != result && null != result.getContents()) {
                String ScanResult = result.getContents();
                Log.i("扫码结果", ScanResult);
                if (ScanResult.contains("whistle_info")) {
                    Intent intent1 = new Intent(ScanQRCodeActivity.this, ApiQRLoginActivity.class);
                    intent1.putExtra("whistle_info", ScanResult);
                    startActivity(intent1);
                    finish();
                } else if (ScanResult.contains("qrLogin")) {
                    Intent intent1 = new Intent(ScanQRCodeActivity.this, LoginConfirmActivity.class);
                    intent1.putExtra("UUID", ScanResult);
                    startActivity(intent1);
                    finish();
                } else if (ScanResult.startsWith("http")) {
                    Intent intent1 = new Intent(ScanQRCodeActivity.this, PureBrowserActivity.class);
                    intent1.putExtra("Name", "");
                    intent1.putExtra("Url", ScanResult);
                    startActivity(intent1);
                    finish();
                } else {
                    Toast.makeText(ScanQRCodeActivity.this, "无法解析的二维码", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        capture.onResume();
                        capture.decode();
                    }, 1500);
                }
            }
        });
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}