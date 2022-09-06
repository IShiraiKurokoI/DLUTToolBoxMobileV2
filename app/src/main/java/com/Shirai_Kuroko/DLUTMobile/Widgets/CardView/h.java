package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.view.View;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName h.java
 * @Description TODO
 * @createTime 2022年09月06日 09:06
 */

public abstract class h implements View.OnClickListener
{
    public long a;
    public int b;

    public h() {
        super();
        this.b = 2000;
    }

    public h(final int b) {
        super();
        this.b = 2000;
        this.b = b;
    }

    public abstract void a(final View p0);

    public void onClick(final View view) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.a;
        boolean b;
        if (0L < n && n < this.b) {
            b = true;
        }
        else {
            this.a = currentTimeMillis;
            b = false;
        }
        if (b) {
            return;
        }
        this.a(view);
    }
}
