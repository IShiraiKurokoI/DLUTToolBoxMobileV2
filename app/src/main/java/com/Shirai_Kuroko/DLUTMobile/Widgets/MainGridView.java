package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName MainGridView.java
 * @Description TODO
 * @createTime 2022年09月05日 12:44
 */
public class MainGridView extends GridView {
    public MainGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MainGridView(Context context) {
        super(context);
    }
    public MainGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
