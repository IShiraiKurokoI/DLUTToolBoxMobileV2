package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.R;

import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ExpandableCardContentView.java
 * @Description TODO
 * @createTime 2022年09月06日 07:22
 */
public abstract class ExpandableCardContentView<T> extends BaseCardContentView<T> implements View.OnClickListener
{
    public boolean e;
    public Context f;
    public boolean g;
    public TextView h;
    public a i;

    public ExpandableCardContentView(@NonNull final Context f) {
        super(f);
        this.f = f;
    }

    public ExpandableCardContentView(@NonNull final Context f, @Nullable final AttributeSet set) {
        super(f, set);
        this.f = f;
    }

    public static <V> boolean v0(final List<V> list) {
        return list == null || list.size() == 0;
    }

    @Override
    public void a() {
        if (v0(super.c)) {
            this.addView(this.b());
            return;
        }
        final View c = this.c();
        final int n = (int)(this.f.getResources().getDimension(R.dimen.card_common_content_padding_left) * super.d + 0.5);
        c.setPadding(n, 0, n, 0);
        this.addView(c);
        if (!this.e) {
            return;
        }
        this.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        final int n2 = (int)(this.f.getResources().getDimension(R.dimen.card_expand_padding_bottom) * super.d + 0.5);
        final TextView h = new TextView(this.f);
        h.setPadding(0, 0, 0, n2);
        h.setId(R.id.tv_card_expand);
        h.setGravity(17);
        h.setTextColor(this.f.getResources().getColor(R.color.card_view_btn_txt));
        h.setTextSize(1, super.d * 12.0f);
        h.setLayoutParams(layoutParams);
        h.setText("+展开");
        h.setOnClickListener(this);
        this.h = h;
        this.setLayoutTransition(new LayoutTransition());
        this.h.setOnClickListener(this);
        this.addView(this.h);
    }

    @NonNull
    public abstract View c();

    public void d() {
        this.h.setText("收起");
    }

    public void e() {
        this.h.setText("+展开");
    }

    public void onClick(final View view) {
        if (view.getId() == R.id.tv_card_expand) {
            this.g ^= true;
            final a i = this.i;
            if (i != null) {
                i.SetExpand(g);
            }
        }
    }


    interface a
    {
        void SetExpand(boolean e);
    }
}
