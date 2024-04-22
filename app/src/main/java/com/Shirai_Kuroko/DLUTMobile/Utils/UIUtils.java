package com.Shirai_Kuroko.DLUTMobile.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName UIUtils.java
 * @Description TODO
 * @createTime 2024年04月22日 15:24
 */
public class UIUtils {
    /**
     * 计算经过符合设备dpi的宽高大小
     * @param context 上下文
     * @param width 默认dpi下的宽度
     * @param height  默认dpi下的高度
     * @return 缩放计算后的宽高
     */
    public static int[] calculateDpiSize(Context context,int width,int height) {
        final int[] size = new int[2];
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int density = displayMetrics.densityDpi;
        float scale = (float) density/(float) 480;
        size[0] = (int) (width * scale);
        size[1] = (int) (height * scale);
        return size;
    }
}
