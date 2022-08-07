package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Managers.GlideEngine;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.ApiQRLoginActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginConfirmActivity;
import com.google.zxing.client.android.model.MNScanConfig;
import com.google.zxing.client.android.other.OnScanCallback;
import com.google.zxing.client.android.utils.ZXingUtils;
import com.google.zxing.client.android.view.ScanSurfaceView;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;

import java.util.ArrayList;

public class StartScanActivity extends AppCompatActivity {
    public String cmdName;
    public String cmdId;
    private ScanSurfaceView mScanSurfaceView;
    private boolean Light_On = false;
    private MNScanConfig QRscanConfig = new MNScanConfig.Builder()
            //设置完成震动
            .isShowVibrate(false)
            //扫描完成声音
            .isShowBeep(true)
            //显示相册功能
            .isShowPhotoAlbum(true)
            //显示闪光灯
            .isShowLightController(true)
            //自定义文案
            .setScanHintText("将二维码放入框内，即可自动扫描")
            //自定义文案颜色
            .setScanHintTextColor("#FFFFFF")
            //自定义文案大小（单位sp）
            .setScanHintTextSize(16)
            //扫描线的颜色
            .setScanColor("#FFFF00")
            //支持缩放
            .setSupportZoom(true)
            .isShowZoomController(true)
            //扫描线样式
            .setLaserStyle(MNScanConfig.LaserStyle.Line)
            //背景颜色
            .setBgColor("#33FF0000")
            .setScanColor("#0073BD")
            //高度偏移值（单位px）+向上偏移，-向下偏移
            .setScanFrameHeightOffsets(100)
            //是否全屏范围扫描
            .setFullScreenScan(false)
            //二维码标记点
            .isShowResultPoint(true)
            .setResultPointConfigs(36, 12, 2, "#FFFFFFFF", "#CC00A81F")
            //状态栏设置：颜色，是否黑色字体
            .setStatusBarConfigs("#00000000", true)
            //是否支持多二维码同时扫出,默认false,多二维码状态不支持条形码
            .setSupportMultiQRCode(true)
            .builder();
    private MNScanConfig OnescanConfig = new MNScanConfig.Builder()
            //设置完成震动
            .isShowVibrate(false)
            //扫描完成声音
            .isShowBeep(true)
            //显示相册功能
            .isShowPhotoAlbum(true)
            //显示闪光灯
            .isShowLightController(true)
            //自定义文案
            .setScanHintText("将条形码放入框内，即可自动扫描")
            //自定义文案颜色
            .setScanHintTextColor("#FFFFFF")
            //自定义文案大小（单位sp）
            .setScanHintTextSize(16)
            //扫描线的颜色
            .setScanColor("#FFFF00")
            //支持缩放
            .setSupportZoom(true)
            .isShowZoomController(true)
            //扫描线样式
            .setLaserStyle(MNScanConfig.LaserStyle.Line)
            //背景颜色
            .setBgColor("#33FF0000")
            .setScanColor("#0073BD")
            //高度偏移值（单位px）+向上偏移，-向下偏移
            .setScanFrameHeightOffsets(100)
            //是否全屏范围扫描
            .setFullScreenScan(false)
            //二维码标记点
            .isShowResultPoint(true)
            .setResultPointConfigs(36, 12, 2, "#FFFFFFFF", "#CC00A81F")
            //状态栏设置：颜色，是否黑色字体
            .setStatusBarConfigs("#00000000", true)
            //是否支持多二维码同时扫出,默认false,多二维码状态不支持条形码
            .builder();
    private boolean one = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PackageManager packageManager = this.getPackageManager();
        PermissionInfo permissionInfo = null;
        try {
            permissionInfo = packageManager.getPermissionInfo(Manifest.permission.CAMERA, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CharSequence permissionName = null;
        if (permissionInfo != null) {
            permissionName = permissionInfo.loadLabel(packageManager);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 未获取权限
            Log.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");
            LogToFile.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");
            Toast.makeText(this, "请开启应用相机权限后重试！", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // 这是一个坑，某些手机弹出提示时没有永不询问的复选框，点击拒绝就默认勾上了这个复选框，而某些手机上即使勾选上了永不询问的复选框也不起作用
                Log.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                LogToFile.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 10000);
                Intent intent = new Intent();
                intent.putExtra("resultcode", -1);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 10000);
                Intent intent = new Intent();
                intent.putExtra("resultcode", -1);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        cmdName = getIntent().getStringExtra("cmdName");
        cmdId = getIntent().getStringExtra("cmdId");
        setContentView(R.layout.activity_scan_qrcode);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("resultcode", -1);
            setResult(RESULT_OK, intent);
            finish();
        });
        mScanSurfaceView = findViewById(R.id.scan_surface_view);
        initView();
        AppCompatButton button = findViewById(R.id.btn_scan_light);
        Drawable drawable1 = this.getResources().getDrawable(R.drawable.icon_custom_light_close);
        drawable1.setBounds(0, 0, 100, 100);
        button.setCompoundDrawables(null, drawable1, null, null);
        button.setOnClickListener(view -> {
            if (Light_On) {
                Light_On = false;
                mScanSurfaceView.getCameraManager().offLight();
                Drawable drawable = this.getResources().getDrawable(R.drawable.icon_custom_light_close);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                button.setCompoundDrawables(null, drawable, null, null);
            } else {
                Light_On = true;
                mScanSurfaceView.getCameraManager().openLight();
                Drawable drawable = this.getResources().getDrawable(R.drawable.icon_custom_light_open);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                button.setCompoundDrawables(null, drawable, null, null);
            }
        });
        AppCompatButton button1 = findViewById(R.id.btn_scan_album);
        Drawable drawable2 = this.getResources().getDrawable(R.drawable.icon_custom_photo);
        drawable2.setBounds(0, 0, 100, 100);
        button1.setCompoundDrawables(null, drawable2, null, null);
        button1.setOnClickListener(view -> PictureSelector.create(StartScanActivity.this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectionMode(SelectModeConfig.SINGLE)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        for (LocalMedia media : result) {
                            if (media.getWidth() == 0 || media.getHeight() == 0) {
                                if (PictureMimeType.isHasImage(media.getMimeType())) {
                                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(StartScanActivity.this, media.getPath());
                                    media.setWidth(imageExtraInfo.getWidth());
                                    media.setHeight(imageExtraInfo.getHeight());
                                }
                            }
                            Log.i("选择的图片", "路径：" + media.getRealPath());
                            final String resultTxt = ZXingUtils.syncDecodeQRCode(media.getRealPath());
                            if (resultTxt == null) {
                                Toast.makeText(StartScanActivity.this, "未检测到二维码", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i("扫码结果", resultTxt);
                            if (resultTxt.contains("whistle_info")) {
                                Intent intent1 = new Intent(StartScanActivity.this, ApiQRLoginActivity.class);
                                intent1.putExtra("whistle_info", resultTxt);
                                startActivity(intent1);
                                finish();
                            } else if (resultTxt.contains("qrLogin")) {
                                Intent intent1 = new Intent(StartScanActivity.this, LoginConfirmActivity.class);
                                intent1.putExtra("UUID", resultTxt);
                                startActivity(intent1);
                                finish();
                            } else if (resultTxt.startsWith("http") || resultTxt.startsWith("www")) {
                                Intent intent1 = new Intent(StartScanActivity.this, PureBrowserActivity.class);
                                intent1.putExtra("Name", "");
                                intent1.putExtra("Url", resultTxt);
                                startActivity(intent1);
                                finish();
                            } else {
                                Toast.makeText(StartScanActivity.this, "无法解析的二维码", Toast.LENGTH_SHORT).show();
                                mScanSurfaceView.hideResultPointView();
                                Handler handler = new Handler();
                                handler.postDelayed(() -> mScanSurfaceView.restartScan(), 1000);
                            }
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }));
        TextView switcher = findViewById(R.id.tv_switch);
        switcher.setOnClickListener(view -> {
            if (one) {
                one = false;
                switcher.setText("切换至条形码识别");
                initView();
            } else {
                one = true;
                switcher.setText("切换至二维码识别");
                initView();
            }
        });
    }

    private void initView() {
        mScanSurfaceView.init(this);
        if (one) {
            mScanSurfaceView.setScanConfig(OnescanConfig);
        } else {
            mScanSurfaceView.setScanConfig(QRscanConfig);
        }
        mScanSurfaceView.setOnScanCallback(new OnScanCallback() {
            @Override
            public void onScanSuccess(String resultTxt, Bitmap barcode) {
                Log.i("扫码结果", resultTxt);
                Intent intent = new Intent();
                if (resultTxt.contains("whistle_info")) {
                    intent.putExtra("resultcode", 0);
                    Intent intent1 = new Intent(StartScanActivity.this, ApiQRLoginActivity.class);
                    intent1.putExtra("whistle_info", resultTxt);
                    startActivity(intent1);
                } else if (resultTxt.contains("qrLogin")) {
                    intent.putExtra("resultcode", 0);
                    Intent intent1 = new Intent(StartScanActivity.this, LoginConfirmActivity.class);
                    intent1.putExtra("UUID", resultTxt);
                    startActivity(intent1);
                } else {
                    intent.putExtra("resultcode", 1);
                    intent.putExtra("content", resultTxt);
                }
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onStopScan() {
            }

            @Override
            public void onRestartScan() {
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(StartScanActivity.this, msg, Toast.LENGTH_SHORT).show();
                mScanSurfaceView.restartScan();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanSurfaceView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //多点扫描结果点显示，可以取消
        if (mScanSurfaceView != null && mScanSurfaceView.isResultPointViewShow()) {
            mScanSurfaceView.hideResultPointView();
            mScanSurfaceView.restartScan();
        } else {
            Intent intent = new Intent();
            intent.putExtra("resultcode", -1);
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }
}
