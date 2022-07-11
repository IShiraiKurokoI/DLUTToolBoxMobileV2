package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.Shirai_Kuroko.DLUTMobile.Managers.GlideEngine;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PersonalInfoActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        MobileUtils.InitializePersonalInfo(this, requireViewById(R.id.InfoScrollView));
        RelativeLayout head_panel = findViewById(R.id.head_panel);
        ImageView user_head = findViewById(R.id.user_head);
        head_panel.setOnClickListener(view -> showPop(user_head, this));
    }

    private PopupWindow pop;

    private void showPop(ImageView head, Context context) {
        View bottomView = View.inflate(context, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);
        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        OnResultCallbackListener<LocalMedia> resultCallbackListener = new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                for (LocalMedia media : result) {
                    if (media.getWidth() == 0 || media.getHeight() == 0) {
                        if (PictureMimeType.isHasImage(media.getMimeType())) {
                            MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(context, media.getPath());
                            media.setWidth(imageExtraInfo.getWidth());
                            media.setHeight(imageExtraInfo.getHeight());
                        } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                            MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(context, media.getPath());
                            media.setWidth(videoExtraInfo.getWidth());
                            media.setHeight(videoExtraInfo.getHeight());
                        }
                    }
                    String TAG = "";
                    Log.i(TAG, "沙盒路径:" + media.getSandboxPath());
                    Log.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
                    Log.i(TAG, "文件大小: " + media.getSize());
                    BackendUtils.UploadNewHead(context, head, media.getSandboxPath());
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
            }
        };

        @SuppressLint("NonConstantResourceId") View.OnClickListener clickListener = view -> {
            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/Crop";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dirFile.mkdirs();
            }
            String Date = new Date().toLocaleString().replace(" ", "");
            String FilePath = path + "/" + Date + ".jpg";
            Uri contentUri = FileProvider.getUriForFile(context, "com.Shirai_Kuroko.DLUTMobile.fileProvider", new File(FilePath));
            UCrop.Options options = new UCrop.Options();
            //设置压缩质量
            options.setCompressionQuality(100);
            //设置位图最大大小
            options.setMaxBitmapSize(1000);
            //宽高比
            options.withAspectRatio(1, 1);
            options.setToolbarTitle("裁剪头像图片");
            options.setFreeStyleCropEnabled(false);
            options.setCropOutputPathDir(path);
            options.isForbidCropGifWebp(true);
            options.isDarkStatusBarBlack(true);
            options.setToolbarColor(getColor(R.color.main_theme_color));
            options.setRootViewBackgroundColor(getColor(R.color.main_theme_color));
            options.setStatusBarColor(getColor(R.color.main_theme_color));
            options.setCropOutputPathDir(path);
            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.NONE);
            options.setShowCropGrid(false);
            options.setShowCropFrame(false);
            switch (view.getId()) {
                case R.id.tv_album:
                    //相册
                    PictureSelector.create(context)
                            .openGallery(SelectMimeType.ofImage())
                            .setImageEngine(GlideEngine.createGlideEngine())
                            .setMaxSelectNum(1)
                            .setMinSelectNum(1)
                            .setSelectionMode(SelectModeConfig.SINGLE)
                            .setCropEngine((fragment, currentLocalMedia, dataSource, requestCode) -> {
                                // 注意* 如果你实现自己的裁剪库，需要在Activity的.setResult();
                                // Intent中需要给MediaStore.EXTRA_OUTPUT，塞入裁剪后的路径；如果有额外数据也可以通过CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA字段存入；

                                UCrop uCrop = UCrop.of(Uri.parse(dataSource.get(0).getPath()), contentUri);
                                uCrop.setImageEngine(new UCropImageEngine() {
                                    @Override
                                    public void loadImage(Context context, String url, ImageView imageView) {
                                        Glide.with(context).load(url).into(imageView);
                                    }

                                    @Override
                                    public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {

                                    }
                                });
                                uCrop.withOptions(options);
                                uCrop.start(context, fragment, requestCode);
                            })
                            .forResult(resultCallbackListener);
                    break;
                case R.id.tv_camera:
                    //拍照
                    PictureSelector.create(context)
                            .openCamera(SelectMimeType.ofImage())
                            .setCropEngine((fragment, currentLocalMedia, dataSource, requestCode) -> {
                                // 注意* 如果你实现自己的裁剪库，需要在Activity的.setResult();
                                // Intent中需要给MediaStore.EXTRA_OUTPUT，塞入裁剪后的路径；如果有额外数据也可以通过CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA字段存入；
                                UCrop uCrop = UCrop.of(Uri.parse(dataSource.get(0).getPath()), contentUri);
                                uCrop.setImageEngine(new UCropImageEngine() {
                                    @Override
                                    public void loadImage(Context context, String url, ImageView imageView) {
                                        Glide.with(context).load(url).into(imageView);
                                    }

                                    @Override
                                    public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {

                                    }
                                });
                                uCrop.withOptions(options);
                                uCrop.start(context, fragment, requestCode);
                            })
                            .forResult(resultCallbackListener);
                    break;
                case R.id.tv_cancel:
                    break;
            }
            closePopupWindow();
        };
        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
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
}