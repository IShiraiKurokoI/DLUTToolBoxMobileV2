package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName MainCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 10:05
 */
public class MainCardView extends RecyclerView {

    public MainCardView(@NonNull Context context) {
        super(context);
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
