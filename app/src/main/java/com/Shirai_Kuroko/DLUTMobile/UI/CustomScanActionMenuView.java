package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.maning.mlkitscanner.scan.callback.MNCustomViewBindCallback;
import com.maning.mlkitscanner.scan.model.MNScanConfig;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CustomScanActionMenuView.java
 * @Description TODO
 * @createTime 2023年01月01日 08:56
 */
public class CustomScanActionMenuView extends FrameLayout {
    private LinearLayout btn_scan_light;
    private ImageView iv_scan_light;
    private TextView tv_scan_light;
    private LinearLayout btn_close;
    private LinearLayout btn_photo;
    private RelativeLayout rl_default_menu;
    private LinearLayout ll_custom_view;
    private CustomScanActionMenuView.OnScanActionMenuListener onScanActionMenuListener;
    private MNScanConfig scanConfig;

    public void setOnScanActionMenuListener(CustomScanActionMenuView.OnScanActionMenuListener onScanActionMenuListener) {
        this.onScanActionMenuListener = onScanActionMenuListener;
    }

    public CustomScanActionMenuView(Context context) {
        this(context, (AttributeSet)null);
    }

    public CustomScanActionMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScanActionMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.mn_scan_action_menu_custom, this);
        this.btn_scan_light = (LinearLayout)this.findViewById(R.id.btn_scan_light);
        this.iv_scan_light = (ImageView)this.findViewById(R.id.iv_scan_light);
        this.tv_scan_light = (TextView)this.findViewById(R.id.tv_scan_light);
        this.btn_close = (LinearLayout)this.findViewById(R.id.btn_close);
        this.btn_photo = (LinearLayout)this.findViewById(R.id.btn_photo);
        this.rl_default_menu = (RelativeLayout)this.findViewById(R.id.rl_default_menu);
        this.ll_custom_view = (LinearLayout)this.findViewById(R.id.ll_custom_view);
        this.rl_default_menu.setVisibility(View.GONE);
        this.ll_custom_view.setVisibility(View.GONE);
        this.btn_scan_light.setOnClickListener(v -> {
            if (CustomScanActionMenuView.this.onScanActionMenuListener != null) {
                CustomScanActionMenuView.this.onScanActionMenuListener.onLight();
            }

        });
        this.btn_close.setOnClickListener(v -> {
            if (CustomScanActionMenuView.this.onScanActionMenuListener != null) {
                CustomScanActionMenuView.this.onScanActionMenuListener.onClose();
            }

        });
        this.btn_photo.setOnClickListener(v -> {
            if (CustomScanActionMenuView.this.onScanActionMenuListener != null) {
                CustomScanActionMenuView.this.onScanActionMenuListener.onPhoto();
            }

        });
    }

    public void setScanConfig(MNScanConfig config, MNCustomViewBindCallback customViewBindCallback) {
        this.scanConfig = config;
        int customShadeViewLayoutID = this.scanConfig.getCustomShadeViewLayoutID();
        if (customShadeViewLayoutID > 0 && customViewBindCallback != null) {
            this.ll_custom_view.setVisibility(View.VISIBLE);
            View customView = LayoutInflater.from(this.getContext()).inflate(customShadeViewLayoutID, (ViewGroup)null);
            this.ll_custom_view.addView(customView);
            customViewBindCallback.onBindView(customView);
        } else {
            this.rl_default_menu.setVisibility(View.VISIBLE);
        }

        boolean showLightController = this.scanConfig.isShowLightController();
        if (showLightController) {
            this.btn_scan_light.setVisibility(View.VISIBLE);
        } else {
            this.btn_scan_light.setVisibility(View.GONE);
        }

        if (!this.scanConfig.isShowPhotoAlbum()) {
            this.btn_photo.setVisibility(View.GONE);
        }

    }

    public void openLight() {
        this.iv_scan_light.setImageResource(R.drawable.icon_custom_light_open);
        this.tv_scan_light.setText("关闭手电筒");
    }

    public void closeLight() {
        this.iv_scan_light.setImageResource(R.drawable.icon_custom_light_close);
        this.tv_scan_light.setText("打开手电筒");
    }

    public interface OnScanActionMenuListener {
        void onClose();

        void onLight();

        void onPhoto();
    }
}
