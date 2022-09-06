package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.Shirai_Kuroko.DLUTMobile.R;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName MaxHeightView.java
 * @Description TODO
 * @createTime 2022年09月06日 07:20
 */
public class MaxHeightView extends FrameLayout {
    public float a;
    public float b;
    public float c;

    public MaxHeightView(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = 0.0f;
        this.b = 0.0f;
        this.c = 0.0f;
        this.c(context, set);
        this.b();
    }

    public MaxHeightView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = 0.0f;
        this.b = 0.0f;
        this.c = 0.0f;
        this.c(context, set);
        this.b();
    }

    public final int a(final Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    public final void b() {
        final float b = this.b;
        if (b <= 0.0f && this.a <= 0.0f) {
            this.c = this.a(this.getContext()) * 0.6f;
        } else {
            if (b <= 0.0f) {
                final float a = this.a;
                if (a > 0.0f) {
                    this.c = a * this.a(this.getContext());
                    return;
                }
            }
            if (b > 0.0f && this.a <= 0.0f) {
                this.c = b;
            } else {
                this.c = Math.min(b, this.a * this.a(this.getContext()));
            }
        }
    }

    public final void c(final Context context, final AttributeSet set) {
        final TypedArray obtainStyledAttributes = context.obtainStyledAttributes(set, R.styleable.MaxHeightView);
        for (int indexCount = obtainStyledAttributes.getIndexCount(), i = 0; i < indexCount; ++i) {
            final int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.MaxHeightView_mhv_HeightRatio) {
                this.a = obtainStyledAttributes.getFloat(index, 0.0f);
            } else if (index == R.styleable.MaxHeightView_mhv_HeightDimen) {
                this.b = obtainStyledAttributes.getDimension(index, 0.0f);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public void onMeasure(final int n, int size) {
        final int mode = View.MeasureSpec.getMode(size);
        final int n2 = size = View.MeasureSpec.getSize(size);
        if (mode == 1073741824) {
            final float n3 = (float) n2;
            final float c = this.c;
            if (n3 <= c) {
                size = n2;
            } else {
                size = (int) c;
            }
        }
        int n4 = size;
        if (mode == 0) {
            final float n5 = (float) size;
            final float c2 = this.c;
            if (n5 <= c2) {
                n4 = size;
            } else {
                n4 = (int) c2;
            }
        }
        size = n4;
        if (mode == Integer.MIN_VALUE) {
            final float n6 = (float) n4;
            final float c3 = this.c;
            if (n6 <= c3) {
                size = n4;
            } else {
                size = (int) c3;
            }
        }
        super.onMeasure(n, View.MeasureSpec.makeMeasureSpec(size, mode));
    }
}
