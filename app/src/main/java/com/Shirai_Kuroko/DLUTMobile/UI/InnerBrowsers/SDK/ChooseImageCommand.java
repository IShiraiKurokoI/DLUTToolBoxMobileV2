package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.Managers.GlideEngine;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChooseImageCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public ChooseImageCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        int count;
        try {
            count = jsonObject.getInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
            sendCancelResult();
            return;
        }
        final int _count = count;
        ArrayList<String> uris = new ArrayList<>();

        OnResultCallbackListener<LocalMedia> resultCallbackListener = new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                for (LocalMedia media : result) {
                    if (media.getWidth() == 0 || media.getHeight() == 0) {
                        if (PictureMimeType.isHasImage(media.getMimeType())) {
                            MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(proxy.context, media.getPath());
                            media.setWidth(imageExtraInfo.getWidth());
                            media.setHeight(imageExtraInfo.getHeight());
                        }
                    }
                    Log.i("选择的图片", "路径：" + media.getRealPath());
                    uris.add(media.getRealPath());
                }

                if (jsonObject.toString().contains("compressed")) {
                    final JSONObject jsonObject = new JSONObject();
                    final JSONArray jsonArray = new JSONArray(uris);
                    try {
                        jsonObject.put("localIds", jsonArray);
                        jsonObject.put("compressedIds", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        sendCancelResult();
                        return;
                    }
                    sendSucceedResult(jsonObject);
                } else {
                    final JSONObject jsonObject = new JSONObject();
                    final JSONArray jsonArray = new JSONArray(uris);
                    try {
                        jsonObject.put("localIds", jsonArray);
                        jsonObject.put("uncompressIds", jsonArray);
                    } catch (JSONException e) {
                        Toast.makeText(proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        sendCancelResult();
                        return;
                    }
                    sendSucceedResult(jsonObject);
                }
            }

            @Override
            public void onCancel() {
                sendCancelResult();
            }
        };
        proxy.context.runOnUiThread(() -> showPop(resultCallbackListener, proxy.context, _count));
    }

    private PopupWindow pop;

    private void showPop(OnResultCallbackListener<LocalMedia> resultCallbackListener, Context context, int count) {
        View bottomView = View.inflate(context, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);
        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = proxy.context.getWindow().getAttributes();
        lp.alpha = 0.5f;
        proxy.context.getWindow().setAttributes(lp);
        pop.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = proxy.context.getWindow().getAttributes();
            lp1.alpha = 1f;
            proxy.context.getWindow().setAttributes(lp1);
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(proxy.context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        @SuppressLint("NonConstantResourceId") View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.tv_album:
                    //相册
                    PictureSelector.create(context)
                            .openGallery(SelectMimeType.ofImage())
                            .setImageEngine(GlideEngine.createGlideEngine())
                            .setMaxSelectNum(count)
                            .setSelectionMode(SelectModeConfig.MULTIPLE)
                            .forResult(resultCallbackListener);
                    break;
                case R.id.tv_camera:
                    //拍照
                    PictureSelector.create(context)
                            .openCamera(SelectMimeType.ofImage())
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

    public void sendCancelResult() {
        this.proxy.sendCancelResult(this.cmdId, this.cmdName);
    }

    public void sendFailedResult(String replace) {
        replace = replace.replace(":", " ").replace("'", " ").replace("\"", " ");
        this.proxy.sendFailedResult(this.cmdId, this.cmdName, replace);
    }

    public void sendSucceedResult(final JSONObject jsonObject) {
        this.proxy.sendSucceedResult(this.cmdId, this.cmdName, jsonObject);
    }
}
