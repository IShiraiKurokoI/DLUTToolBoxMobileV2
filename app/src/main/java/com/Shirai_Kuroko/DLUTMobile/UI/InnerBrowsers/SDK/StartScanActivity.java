package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.UI.ApiQRLoginActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginConfirmActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StartScanActivity extends AppCompatActivity {
    public String cmdName;
    public String cmdId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cmdName = getIntent().getStringExtra("cmdName");
        cmdId = getIntent().getStringExtra("cmdId");
        new IntentIntegrator(this)
                .setCaptureActivity(BrowserScanQRCodeActivity.class)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                .setPrompt("将二维码/条码放入框内，即可自动扫描")// 设置提示语
                .setCameraId(0)// 选择摄像头,可使用前置或者后置
                .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Intent intent = new Intent();
            if (result.getContents() == null) {
                intent.putExtra("resultcode", 0);
            } else {
                String ScanResult = result.getContents();
                if (ScanResult.contains("whistle_info")) {
                    intent.putExtra("resultcode", 0);
                    Intent intent1 = new Intent(this, ApiQRLoginActivity.class);
                    intent1.putExtra("whistle_info", ScanResult);
                    startActivity(intent1);
                    finish();
                } else if (ScanResult.contains("qrLogin")) {
                    intent.putExtra("resultcode", 0);
                    Intent intent1 = new Intent(this, LoginConfirmActivity.class);
                    intent1.putExtra("UUID", ScanResult);
                    startActivity(intent1);
                    finish();
                } else if (ScanResult.startsWith("http")) {
                    intent.putExtra("resultcode", 0);
                    Intent intent1 = new Intent(this, PureBrowserActivity.class);
                    intent1.putExtra("Name", "");
                    intent1.putExtra("Url", ScanResult);
                    startActivity(intent1);
                    finish();
                } else {
                    intent.putExtra("resultcode", 1);
                    intent.putExtra("content", result.getContents());
                }
            }
            this.setResult(RESULT_OK, intent);
            this.finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("resultcode", 0);
            this.setResult(RESULT_OK, intent);
            this.finish();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
