package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Shirai_Kuroko.DLUTMobile.R;

public class PreferenceRightDetailView extends RelativeLayout
{
    public TextView a;
    public TextView b;
    public ImageView c;
    public RelativeLayout d;
    public Context e;

    public PreferenceRightDetailView(final Context e, final AttributeSet set) {
        super(e, set);
        this.e = e;
        this.c(set);
    }

    public PreferenceRightDetailView(final Context e, final AttributeSet set, final int n) {
        super(e, set, n);
        this.e = e;
        this.c(set);
    }

    public CharSequence GetContentText() {
        return this.b.getText();
    }

    public CharSequence GetTitleText() {
        return this.a.getText();
    }

    public void SetContentText(String text)
    {
        this.b.setText(text);
    }

    public final void c(final AttributeSet set) {
        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.e).inflate(R.layout.preference_detail_item, this);
        this.d = this.findViewById(R.id.container);
        this.a = this.findViewById(R.id.title);
        this.b = this.findViewById(R.id.content);
        this.c = this.findViewById(R.id.arrow);
        final TypedArray obtainStyledAttributes = this.e.getTheme().obtainStyledAttributes(set, R.styleable.PreferenceRightDetailView, 0, 0);
        this.a.setText(obtainStyledAttributes.getString(R.styleable.PreferenceRightDetailView_titleName));
        this.b.setText(obtainStyledAttributes.getString(R.styleable.PreferenceRightDetailView_titleContent));
        final int preferenceRightDetailView_titleColor = R.styleable.PreferenceRightDetailView_titleColor;
        if (obtainStyledAttributes.getColorStateList(preferenceRightDetailView_titleColor) != null) {
            this.a.setTextColor(obtainStyledAttributes.getColorStateList(preferenceRightDetailView_titleColor));
        }
        final int preferenceRightDetailView_contentColor = R.styleable.PreferenceRightDetailView_contentColor;
        if (obtainStyledAttributes.getColorStateList(preferenceRightDetailView_contentColor) != null) {
            this.b.setTextColor(obtainStyledAttributes.getColorStateList(preferenceRightDetailView_contentColor));
        }
        this.a.setTextSize(0, (float)obtainStyledAttributes.getDimensionPixelSize(R.styleable.PreferenceRightDetailView_titleSize, (int)(this.e.getResources().getDisplayMetrics().density * 14.0f + 0.5f)));
        this.b.setTextSize(0, (float)obtainStyledAttributes.getDimensionPixelSize(R.styleable.PreferenceRightDetailView_contentSize, (int)(this.e.getResources().getDisplayMetrics().density * 14.0f + 0.5f)));
        final int integer = obtainStyledAttributes.getInteger(R.styleable.PreferenceRightDetailView_accessStyle, 1);
        this.c.setImageResource(obtainStyledAttributes.getResourceId(R.styleable.PreferenceRightDetailView_accessImage, R.drawable.setting_right_arrow));
        if (integer != 1) {
            this.c.setVisibility(INVISIBLE);
        }
        else {
            this.c.setVisibility(VISIBLE);
        }
        final int integer2 = obtainStyledAttributes.getInteger(R.styleable.PreferenceRightDetailView_divider_location, 2);
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 1);
        if (integer2 != 0) {
            if (integer2 == 1) {
                final View inflate = LayoutInflater.from(this.e).inflate(R.layout.divider_hor, relativeLayout);
                layoutParams.addRule(10);
                inflate.setLayoutParams(layoutParams);
            }
            else if (integer2 == 2) {
                final View inflate2 = LayoutInflater.from(this.e).inflate(R.layout.divider_hor, relativeLayout);
                layoutParams.addRule(12);
                inflate2.setLayoutParams(layoutParams);
            }
            else {
                final LayoutInflater from = LayoutInflater.from(this.e);
                final int divider_hor = R.layout.divider_hor;
                final View inflate3 = from.inflate(divider_hor, relativeLayout);
                layoutParams.addRule(10);
                inflate3.setLayoutParams(layoutParams);
                final View inflate4 = LayoutInflater.from(this.e).inflate(divider_hor, relativeLayout);
                final RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, 1);
                layoutParams2.addRule(12);
                inflate4.setLayoutParams(layoutParams2);
            }
        }
        final RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams3.addRule(13);
        this.d.setLayoutParams(layoutParams3);
    }

    public void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        super.onLayout(b, n, n2, n3, n4);
    }
}
