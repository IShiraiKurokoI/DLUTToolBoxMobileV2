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
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
//        initView();
    }

//    private void initView() {
//        mScanSurfaceView = findViewById(R.id.scan_surface_view);
//        mScanSurfaceView.init(this);
//        MNScanConfig scanConfig = new MNScanConfig.Builder()
//                //设置完成震动
//                .isShowVibrate(false)
//                //扫描完成声音
//                .isShowBeep(true)
//                //显示相册功能
//                .isShowPhotoAlbum(true)
//                //显示闪光灯
//                .isShowLightController(true)
//                //自定义文案
//                .setScanHintText("将二维码放入框内，即可自动扫描")
//                //自定义文案颜色
//                .setScanHintTextColor("#FFFFFF")
//                //自定义文案大小（单位sp）
//                .setScanHintTextSize(16)
//                //扫描线的颜色
//                .setScanColor("#FFFF00")
//                //支持缩放
//                .setSupportZoom(true)
//                .isShowZoomController(false)
//                //扫描线样式
//                .setLaserStyle(MNScanConfig.LaserStyle.Line)
//                //背景颜色
//                .setBgColor("#33FF0000")
//                .setScanColor("#0073BD")
//                //高度偏移值（单位px）+向上偏移，-向下偏移
//                .setScanFrameHeightOffsets(100)
//                //是否全屏范围扫描
//                .setFullScreenScan(false)
//                //二维码标记点
//                .isShowResultPoint(true)
//                .setResultPointConfigs(36, 12, 2, "#FFFFFFFF", "#CC00A81F")
//                //状态栏设置：颜色，是否黑色字体
//                .setStatusBarConfigs("#00000000", true)
//                //是否支持多二维码同时扫出,默认false,多二维码状态不支持条形码
//                .setSupportMultiQRCode(true)
//                .builder();
//        mScanSurfaceView.setScanConfig(scanConfig);
//        mScanSurfaceView.setOnScanCallback(new OnScanCallback() {
//            @Override
//            public void onScanSuccess(String resultTxt, Bitmap barcode) {
//                Log.i("扫码结果", resultTxt);
//                if (resultTxt.contains("whistle_info")) {
//                    Intent intent1 = new Intent(BrowserScanQRCodeActivity.this, ApiQRLoginActivity.class);
//                    intent1.putExtra("whistle_info", resultTxt);
//                    startActivity(intent1);
//                    finish();
//                } else if (resultTxt.contains("qrLogin")) {
//                    Intent intent1 = new Intent(BrowserScanQRCodeActivity.this, LoginConfirmActivity.class);
//                    intent1.putExtra("UUID", resultTxt);
//                    startActivity(intent1);
//                    finish();
//                } else if (resultTxt.startsWith("http")||resultTxt.startsWith("www")) {
//                    Intent intent1 = new Intent(BrowserScanQRCodeActivity.this, PureBrowserActivity.class);
//                    intent1.putExtra("Name", "");
//                    intent1.putExtra("Url", resultTxt);
//                    startActivity(intent1);
//                    finish();
//                } else {
//                    Toast.makeText(BrowserScanQRCodeActivity.this, "无法解析的二维码", Toast.LENGTH_SHORT).show();
//                    Handler handler = new Handler();
//                    handler.postDelayed(() -> {
//                        mScanSurfaceView.onResume();
//                    }, 1000);
//                }
//            }
//
//            @Override
//            public void onStopScan() {
//
//            }
//
//            @Override
//            public void onRestartScan() {
//            }
//
//            @Override
//            public void onFail(String msg) {
//                Toast.makeText(BrowserScanQRCodeActivity.this, msg+"扫码失败", Toast.LENGTH_SHORT).show();
//                mScanSurfaceView.onResume();
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mScanSurfaceView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mScanSurfaceView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mScanSurfaceView.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
//        //多点扫描结果点显示，可以取消
//        if (mScanSurfaceView != null && mScanSurfaceView.isResultPointViewShow()) {
//            mScanSurfaceView.hideResultPointView();
//            mScanSurfaceView.restartScan();
//            return;
//        }
//        super.onBackPressed();
//    }
}