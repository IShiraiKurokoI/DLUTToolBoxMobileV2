package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.DataCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.R;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName DataCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:01
 */
public class DataCardView extends ExpandableCardContentView<DataCardItemBean>
{
    public LinearLayout j;
    public View k;

    public DataCardView(@NonNull final Context context) {
        super(context);
    }

    public DataCardView(@NonNull final Context context, @Nullable final AttributeSet set) {
        super(context, set);
    }

    @NonNull
    @Override
    public View c() {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        (this.j = new LinearLayout(super.f)).setLayoutTransition(new LayoutTransition());
        this.j.setOrientation(LinearLayout.VERTICAL);
        this.j.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.j.addView(this.f(0));
        if (super.c.size() > 3) {
            super.e = true;
        }
        return (View)this.j;
    }

    @Override
    public void d() {
        super.d();
        final View f = this.f(1);
        this.k = f;
        this.j.addView(f);
    }

    @Override
    public void e() {
        super.e();
        this.j.removeView(this.k);
    }

    public final View f(final int n) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int)(super.f.getResources().getDimension(R.dimen.card_data_row_height) * super.d + 0.5));
        final LinearLayout linearLayout = new LinearLayout(super.f);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        linearLayout.setWeightSum(3.0f);
        for (int i = 0; i < 3; ++i) {
            final int n2 = n * 3 + i;
            DataCardItemBean dataCardItemBean;
            if (n2 < super.c.size()) {
                dataCardItemBean = (DataCardItemBean)super.c.get(n2);
            }
            else {
                dataCardItemBean = null;
            }
            final View inflate = LayoutInflater.from(super.f).inflate(R.layout.item_card_data, (ViewGroup)null);
            final TextView textView = (TextView)inflate.findViewById(R.id.tv_card_data_value);
            final TextView textView2 = (TextView)inflate.findViewById(R.id.tv_card_data_des);
            if (super.d != 1.0f) {
                textView.setTextSize(0, textView.getTextSize() * super.d);
                textView2.setTextSize(0, textView2.getTextSize() * super.d);
            }
            if (dataCardItemBean != null) {
                textView.setText((CharSequence)dataCardItemBean.getValue());
                try {
                    textView.setTextColor(f(dataCardItemBean.getColor()));
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                textView2.setText((CharSequence)dataCardItemBean.getTitle());
            }
            final LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -1);
            layoutParams2.weight = 1.0f;
            inflate.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
            linearLayout.addView(inflate);
        }
        return (View)linearLayout;
    }

    public static int f(final String s) {
        String string = s;
        if (s.startsWith("#")) {
            if (s.length() != 4) {
                string = s;
                if (s.length() != 5) {
                    return Color.parseColor(string);
                }
            }
            final int n = s.length() - 1;
            final char[] dst = new char[n];
            final int length = s.length();
            int i = 0;
            s.getChars(1, length, dst, 0);
            final StringBuilder sb = new StringBuilder();
            sb.append("#");
            while (i < n) {
                final char c = dst[i];
                sb.append(c);
                sb.append(c);
                ++i;
            }
            string = sb.toString();
        }
        return Color.parseColor(string);
    }
}
