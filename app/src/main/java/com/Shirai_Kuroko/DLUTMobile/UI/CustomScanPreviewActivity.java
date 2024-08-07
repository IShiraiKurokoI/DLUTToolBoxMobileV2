package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.maning.mlkitscanner.scan.camera.CameraManager;
import com.maning.mlkitscanner.scan.model.MNScanConfig;
import com.maning.mlkitscanner.scan.utils.ImageUtils;
import com.maning.mlkitscanner.scan.utils.StatusBarUtil;
import com.maning.mlkitscanner.scan.view.ViewfinderView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CustomScanPreviewActivity.java
 * @Description TODO
 * @createTime 2023年01月01日 00:44
 */
public class CustomScanPreviewActivity extends AppCompatActivity {
    private static WeakReference<CustomScanPreviewActivity> sActivityRef;
    private static final int REQUEST_CODE_PICK_IMAGE = 10010;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 10011;
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 10012;
    private Context mContext;
    private boolean is_light_on = false;
    private MNScanConfig mScanConfig;
    private CameraManager cameraManager;
    private View fakeStatusBar;
    private PreviewView mPreviewView;
    private ViewfinderView viewfinderView;
    private CustomScanResultPointView result_point_view;
    private CustomScanActionMenuView action_menu_view;
    private RelativeLayout rl_act_root;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(128);
        this.setContentView(R.layout.mn_scan_activity_scan_preview_custom);
        this.mContext = this;
        sActivityRef = new WeakReference(this);
        this.initConfig();
        this.initViews();
        this.initCamera();
        this.initStatusBar();
        this.initPermission();
    }

    private void initStatusBar() {
        {
            StatusBarUtil.setTransparentForWindow(this);
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
            ViewGroup.LayoutParams fakeStatusBarLayoutParams = this.fakeStatusBar.getLayoutParams();
            fakeStatusBarLayoutParams.height = statusBarHeight;
            this.fakeStatusBar.setLayoutParams(fakeStatusBarLayoutParams);
            if (this.mScanConfig.isStatusBarDarkMode()) {
                StatusBarUtil.setDarkMode(this);
            }

            String statusBarColor = this.mScanConfig.getStatusBarColor();
            this.fakeStatusBar.setBackgroundColor(Color.parseColor(statusBarColor));
        }

    }

    private void initPermission() {
        if (this.checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{"android.permission.CAMERA"}, 10011);
        } else {
            this.startCamera();
        }

    }

    private void initCamera() {
        this.cameraManager = CameraManager.getInstance((Context)sActivityRef.get(), this.mPreviewView);
        this.cameraManager.setScanConfig(this.mScanConfig);
        this.cameraManager.setOnCameraAnalyserCallback((bitmap, barcodes) -> {
            CustomScanPreviewActivity.this.result_point_view.setDatas(barcodes, bitmap);
            CustomScanPreviewActivity.this.result_point_view.setVisibility(View.VISIBLE);
            if (barcodes.size() == 1) {
                CustomScanPreviewActivity.this.finishSuccess(((Barcode)barcodes.get(0)).getDisplayValue());
            }

        });
    }

    private void startCamera() {
        this.cameraManager.startCamera();
    }

    private void initConfig() {
        this.mScanConfig = (MNScanConfig)this.getIntent().getSerializableExtra("INTENT_KEY_CONFIG_MODEL");
        if (this.mScanConfig == null) {
            this.mScanConfig = (new MNScanConfig.Builder()).builder();
        }

    }

    private void initViews() {
        this.rl_act_root = (RelativeLayout)this.findViewById(R.id.rl_act_root);
        this.mPreviewView = (PreviewView)this.findViewById(R.id.previewView);
        this.mPreviewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);
        this.fakeStatusBar = this.findViewById(R.id.fakeStatusBar);
        this.viewfinderView = (ViewfinderView)this.findViewById(R.id.viewfinderView);
        this.action_menu_view = (CustomScanActionMenuView)this.findViewById(R.id.action_menu_view);
        this.result_point_view = (CustomScanResultPointView)this.findViewById(R.id.result_point_view);
        this.action_menu_view.setOnScanActionMenuListener(new CustomScanActionMenuView.OnScanActionMenuListener() {
            public void onClose() {
                CustomScanPreviewActivity.this.finishCancle();
            }

            public void onLight() {
                if (CustomScanPreviewActivity.this.is_light_on) {
                    CustomScanPreviewActivity.this.closeLight();
                } else {
                    CustomScanPreviewActivity.this.openLight();
                }

            }

            public void onPhoto() {
                CustomScanPreviewActivity.this.getImageFromAlbum();
            }
        });
        this.result_point_view.setOnResultPointClickListener(new CustomScanResultPointView.OnResultPointClickListener() {
            public void onPointClick(String result) {
                CustomScanPreviewActivity.this.finishSuccess(result);
            }

            public void onCancle() {
                CustomScanPreviewActivity.this.cameraManager.setAnalyze(true);
                CustomScanPreviewActivity.this.result_point_view.removeAllPoints();
                CustomScanPreviewActivity.this.result_point_view.setVisibility(View.GONE);
            }
        });
        this.viewfinderView.setScanConfig(this.mScanConfig);
        this.result_point_view.setScanConfig(this.mScanConfig);
        this.action_menu_view.setScanConfig(this.mScanConfig, MNScanConfig.mCustomViewBindCallback);
    }

    private void openLight() {
        if (!this.is_light_on) {
            this.is_light_on = true;
            this.action_menu_view.openLight();
            this.cameraManager.openLight();
        }

    }

    private void closeLight() {
        if (this.is_light_on) {
            this.is_light_on = false;
            this.action_menu_view.closeLight();
            this.cameraManager.closeLight();
        }

    }

    public void getImageFromAlbum() {
        if (this.checkStoragePermission()) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.PICK");
            intent.setType("image/*");
            this.startActivityForResult(intent, 10010);
        }

    }

    private boolean checkStoragePermission() {
        if (this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 10012);
            return false;
        } else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 10011:
                if (grantResults[0] == 0) {
                    this.startCamera();
                } else {
                    Toast.makeText(this.mContext, "初始化相机失败,相机权限被拒绝", Toast.LENGTH_SHORT).show();
                    this.finishFailed("初始化相机失败,相机权限被拒绝");
                }
                break;
            case 10012:
                if (grantResults[0] == 0) {
                    this.getImageFromAlbum();
                } else {
                    Toast.makeText(this.mContext, "打开相册失败,读写权限被拒绝", Toast.LENGTH_SHORT).show();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010 && resultCode == -1) {
            if (data == null) {
                return;
            }

            final Bitmap decodeAbleBitmap = ImageUtils.getBitmap(this.mContext, data.getData());
            if (decodeAbleBitmap == null) {
                Log.e("======", "decodeAbleBitmap == null");
                return;
            }

            this.cameraManager.setAnalyze(false);
            (new Thread(() -> {
                InputImage inputImage = InputImage.fromBitmap(decodeAbleBitmap, 0);
                CustomScanPreviewActivity.this.cameraManager.getBarcodeAnalyser().getBarcodeScanner().process(inputImage).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    public void onSuccess(@NonNull final List<Barcode> barcodes) {
                        CustomScanPreviewActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Log.e("======", "barcodes.size():" + barcodes.size());
                                if (barcodes.isEmpty()) {
                                    CustomScanPreviewActivity.this.cameraManager.setAnalyze(true);
                                    Toast.makeText(CustomScanPreviewActivity.this.mContext, "未找到二维码或者条形码", Toast.LENGTH_SHORT).show();
                                } else {
                                    ArrayList<String> results = new ArrayList();

                                    for (Barcode barcode : barcodes) {
                                        String value = barcode.getDisplayValue();
                                        Log.e("======", "value:" + value);
                                        results.add(value);
                                    }

                                    CustomScanPreviewActivity.this.finishSuccess(results);
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Log.e("======", "onFailure---:" + e.toString());
                    }
                });
            })).start();
        }

    }

    public void onBackPressed() {
        if (this.result_point_view.getVisibility() == View.VISIBLE) {
            this.cameraManager.setAnalyze(true);
            this.result_point_view.removeAllPoints();
            this.result_point_view.setVisibility(View.GONE);
        } else {
            this.finishCancle();
        }
    }

    protected void onDestroy() {
        this.cameraManager.release();
        super.onDestroy();
    }

    private void finishCancle() {
        this.setResult(2, new Intent());
        this.finishFinal();
    }

    private void finishFailed(String errorMsg) {
        Intent intent = new Intent();
        intent.putExtra("INTENT_KEY_RESULT_ERROR", errorMsg);
        this.setResult(1, intent);
        this.finishFinal();
    }

    private void finishSuccess(String result) {
        ArrayList<String> results = new ArrayList();
        results.add(result);
        this.finishSuccess(results);
    }

    private void finishSuccess(ArrayList<String> results) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("INTENT_KEY_RESULT_SUCCESS", results);
        this.setResult(0, intent);
        this.finishFinal();
    }

    private void finishFinal() {
        this.closeLight();
        MNScanConfig.mCustomViewBindCallback = null;
        sActivityRef = null;
        this.viewfinderView.destroyView();
        this.cameraManager.release();
        this.rl_act_root.removeView(this.viewfinderView);
        this.rl_act_root.removeView(this.mPreviewView);
        this.rl_act_root.removeView(this.action_menu_view);
        this.finish();
        this.overridePendingTransition(0, this.mScanConfig.getActivityExitAnime() == 0 ? R.anim.mn_scan_activity_bottom_out : this.mScanConfig.getActivityExitAnime());
    }

    public static void closeScanPage() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            ((CustomScanPreviewActivity)sActivityRef.get()).finishCancle();
        }

    }

    public static void openAlbumPage() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            ((CustomScanPreviewActivity)sActivityRef.get()).getImageFromAlbum();
        }

    }

    public static void openScanLight() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            ((CustomScanPreviewActivity)sActivityRef.get()).openLight();
        }

    }

    public static void closeScanLight() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            ((CustomScanPreviewActivity)sActivityRef.get()).closeLight();
        }

    }

    public static boolean isLightOn() {
        return sActivityRef != null && sActivityRef.get() != null && ((CustomScanPreviewActivity) sActivityRef.get()).is_light_on;
    }
}