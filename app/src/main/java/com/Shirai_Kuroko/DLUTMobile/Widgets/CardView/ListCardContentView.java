package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ListCardContentView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:19
 */
public abstract class ListCardContentView<T> extends ExpandableCardContentView<T> {
    public LinearLayout j;
    public List<View> k;
    public boolean l;

    public ListCardContentView(@NonNull final Context context) {
        super(context);
        this.k = new ArrayList<>();
        this.l = true;
    }

    public ListCardContentView(@NonNull final Context context, @Nullable final AttributeSet set) {
        super(context, set);
        this.k = new ArrayList<>();
        this.l = true;
    }

    @NonNull
    @Override
    public View c() {
        (this.j = new LinearLayout(super.f)).setLayoutParams(new LayoutParams(-1, -2));
        this.j.setOrientation(LinearLayout.VERTICAL);
        if (this.l) {
            this.j.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            this.j.setDividerDrawable(super.f.getResources().getDrawable(R.drawable.divider_hor));
        }
        final List<T> c = super.c;
        if (c != null && c.size() > 0) {
            for (int i = 0; i < super.c.size(); ++i) {
                final T value = super.c.get(i);
                if (value != null) {
                    if (this.j.getChildCount() == super.a / 2) {
                        super.e = true;
                        this.j.setLayoutTransition(new LayoutTransition());
                        break;
                    }
                    final View f = this.f(value, i);
                    if (f != null) {
                        this.j.addView(f);
                    }
                }
            }
        }
        return this.j;
    }

    @Override
    public void d() {
        super.d();
        for (int i = this.j.getChildCount(); i < super.c.size(); ++i) {
            final View f = this.f(super.c.get(i), i);
            if (f != null) {
                this.j.addView(f);
                this.k.add(f);
                if (this.j.getChildCount() >= super.a) {
                    break;
                }
            }
        }
    }

    @Override
    public void e() {
        super.e();
        for (View view : this.k) {
            this.j.removeView(view);
        }
        this.k.clear();
    }
    public abstract View f(final T p0, final int p1);
}


