package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.view.View;
import android.widget.LinearLayout;

import com.Shirai_Kuroko.DLUTMobile.R;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName b.java
 * @Description TODO
 * @createTime 2022年09月06日 09:06
 */
public class b extends h
{
    public final LinearLayout c;
    public final int d;
    public final CardView e;

    public b(final CardView e, final int n, final LinearLayout c, final int d) {
        super(n);
        this.e = e;
        this.c = c;
        this.d = d;
    }

    @Override
    public void a(final View view) {
        if (this.c.findViewById(R.id.card_ind).isSelected()) {
            return;
        }
        this.e.appBean.setSelectTab(this.d);
        this.e.h(1);
        this.e.f();
    }
}
