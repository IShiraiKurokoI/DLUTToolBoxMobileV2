package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.maning.mlkitscanner.scan.model.MNScanConfig;
import com.maning.mlkitscanner.scan.utils.CommonUtils;
import com.maning.mlkitscanner.scan.utils.StatusBarUtil;

import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CustomScanResultPointView.java
 * @Description TODO
 * @createTime 2023年01月01日 08:13
 */
public class CustomScanResultPointView extends FrameLayout {
    private MNScanConfig scanConfig;
    private List<Barcode> resultPoint;
    private CustomScanResultPointView.OnResultPointClickListener onResultPointClickListener;
    private int resultPointColor;
    private int resultPointStrokeColor;
    private int resultPointWithdHeight;
    private int resultPointRadiusCorners;
    private int resultPointStrokeWidth;
    private TextView tv_cancle;
    private FrameLayout fl_result_point_root;
    private View fakeStatusBar;
    private int statusBarHeight;
    private ImageView iv_show_result;
    private Bitmap barcodeBitmap;

    public void setOnResultPointClickListener(CustomScanResultPointView.OnResultPointClickListener onResultPointClickListener) {
        this.onResultPointClickListener = onResultPointClickListener;
    }

    public CustomScanResultPointView(Context context) {
        this(context, (AttributeSet)null);
    }

    public CustomScanResultPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScanResultPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.mn_scan_result_point_view_custom, this);
        this.fakeStatusBar = view.findViewById(R.id.fakeStatusBar2);
        this.iv_show_result = (ImageView)view.findViewById(R.id.iv_show_result);
        this.tv_cancle = (TextView)view.findViewById(R.id.tv_cancle);
        this.fl_result_point_root = (FrameLayout)view.findViewById(R.id.fl_result_point_root);
        this.statusBarHeight = StatusBarUtil.getStatusBarHeight(this.getContext());
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams fakeStatusBarLayoutParams =  this.fakeStatusBar.getLayoutParams();
            fakeStatusBarLayoutParams.height = this.statusBarHeight;
            this.fakeStatusBar.setLayoutParams(fakeStatusBarLayoutParams);
        }

        this.tv_cancle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CustomScanResultPointView.this.onResultPointClickListener != null) {
                    CustomScanResultPointView.this.onResultPointClickListener.onCancle();
                }

                CustomScanResultPointView.this.removeAllPoints();
            }
        });
        this.iv_show_result.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
    }

    public void setScanConfig(MNScanConfig config) {
        this.scanConfig = config;
        this.initResultPointConfigs();
    }

    private void initResultPointConfigs() {
        if (this.scanConfig != null) {
            this.resultPointRadiusCorners = CommonUtils.dip2px(this.getContext(), (float)this.scanConfig.getResultPointCorners());
            this.resultPointWithdHeight = CommonUtils.dip2px(this.getContext(), (float)this.scanConfig.getResultPointWithdHeight());
            this.resultPointStrokeWidth = CommonUtils.dip2px(this.getContext(), (float)this.scanConfig.getResultPointStrokeWidth());
            String resultPointColorStr = this.scanConfig.getResultPointColor();
            String resultPointStrokeColorStr = this.scanConfig.getResultPointStrokeColor();
            if (this.resultPointWithdHeight == 0) {
                this.resultPointWithdHeight = CommonUtils.dip2px(this.getContext(), 36.0F);
            }

            if (this.resultPointRadiusCorners == 0) {
                this.resultPointRadiusCorners = CommonUtils.dip2px(this.getContext(), 36.0F);
            }

            if (this.resultPointStrokeWidth == 0) {
                this.resultPointStrokeWidth = CommonUtils.dip2px(this.getContext(), 3.0F);
            }

            if (!TextUtils.isEmpty(resultPointColorStr)) {
                this.resultPointColor = Color.parseColor(resultPointColorStr);
            } else {
                this.resultPointColor = this.getContext().getResources().getColor(R.color.mn_scan_viewfinder_laser_result_point);
            }

            if (!TextUtils.isEmpty(resultPointStrokeColorStr)) {
                this.resultPointStrokeColor = Color.parseColor(resultPointStrokeColorStr);
            } else {
                this.resultPointStrokeColor = this.getContext().getResources().getColor(R.color.mn_scan_viewfinder_laser_result_point_border);
            }

        }
    }

    public void setDatas(List<Barcode> results, Bitmap barcode) {
        this.resultPoint = results;
        this.barcodeBitmap = barcode;
        this.drawableResultPoint();
    }

    public void removeAllPoints() {
        this.fl_result_point_root.removeAllViews();
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height,matrix, true);
    }

    private void drawableResultPoint() {
        Log.e(">>>>>>", "drawableResultPoint---start");
        this.iv_show_result.setBackground(new BitmapDrawable(this.barcodeBitmap));
        this.removeAllPoints();
        if (this.resultPoint != null && this.resultPoint.size() != 0) {
            if (this.scanConfig == null) {
                this.scanConfig = (new MNScanConfig.Builder()).builder();
            }

            if (this.resultPoint.size() == 1) {
                this.tv_cancle.setVisibility(View.INVISIBLE);
            } else {
                this.tv_cancle.setVisibility(View.VISIBLE);
            }

            int childCount;
            for(childCount = 0; childCount < this.resultPoint.size(); ++childCount) {
                final Barcode barcode = (Barcode)this.resultPoint.get(childCount);
                Rect boundingBox = barcode.getBoundingBox();
                int centerX = boundingBox.centerX();
                int centerY = boundingBox.centerY();
                View inflate = LayoutInflater.from(this.getContext()).inflate(R.layout.mn_scan_result_point_item_view_cusom, (ViewGroup)null);
                RelativeLayout rl_root = (RelativeLayout)inflate.findViewById(R.id.rl_root);
                ImageView iv_point_bg = (ImageView)inflate.findViewById(R.id.iv_point_bg);
                ImageView iv_point_arrow = (ImageView)inflate.findViewById(R.id.iv_point_arrow);
                android.widget.RelativeLayout.LayoutParams lpRoot = new android.widget.RelativeLayout.LayoutParams(this.resultPointWithdHeight, this.resultPointWithdHeight);
                rl_root.setLayoutParams(lpRoot);
                rl_root.setX((float)centerX - (float)this.resultPointWithdHeight / 2.0F);
                rl_root.setY((float)centerY - (float)this.resultPointWithdHeight / 2.0F);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius((float)this.resultPointRadiusCorners);
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setStroke(this.resultPointStrokeWidth, this.resultPointStrokeColor);
                gradientDrawable.setColor(this.resultPointColor);
                iv_point_bg.setImageDrawable(gradientDrawable);
                ViewGroup.LayoutParams lpPoint = iv_point_bg.getLayoutParams();
                lpPoint.width = this.resultPointWithdHeight;
                lpPoint.height = this.resultPointWithdHeight;
                iv_point_bg.setLayoutParams(lpPoint);
                if (this.resultPoint.size() > 1) {
                    ViewGroup.LayoutParams lpArrow = iv_point_arrow.getLayoutParams();
                    lpArrow.width = this.resultPointWithdHeight / 2;
                    lpArrow.height = this.resultPointWithdHeight / 2;
                    iv_point_arrow.setLayoutParams(lpArrow);
                    iv_point_arrow.setVisibility(View.VISIBLE);
                } else {
                    iv_point_arrow.setVisibility(View.GONE);
                }

                iv_point_bg.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (CustomScanResultPointView.this.onResultPointClickListener != null) {
                            CustomScanResultPointView.this.onResultPointClickListener.onPointClick(barcode.getDisplayValue());
                        }

                    }
                });
                this.fl_result_point_root.addView(inflate);
            }

            childCount = this.fl_result_point_root.getChildCount();
            Log.e(">>>>>>", "fl_result_point_root---childCount：" + childCount);
            if (childCount <= 0 && this.onResultPointClickListener != null) {
                this.onResultPointClickListener.onCancle();
            }

            Log.e(">>>>>>", "drawableResultPoint---end");
        } else {
            if (this.onResultPointClickListener != null) {
                this.onResultPointClickListener.onCancle();
            }

        }
    }

    public interface OnResultPointClickListener {
        void onPointClick(String var1);

        void onCancle();
    }
}