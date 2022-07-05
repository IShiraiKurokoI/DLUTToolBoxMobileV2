package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.assist.*;
import com.nostra13.universalimageloader.core.listener.*;

import com.Shirai_Kuroko.DLUTMobile.R;

public class SchoolCardView extends RelativeLayout
{
    public Context a;
    public ImageView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public TextView g;
    public ImageView h;
    public ImageLoadingListener i;

    public SchoolCardView(final Context a, final AttributeSet set) {
        super(a, set);
        this.i = new ImageLoadingListener() {
            public SchoolCardView a;

            public void SchoolCardView(final SchoolCardView a) {
                this.a = a;
            }

            @Override
            public void onLoadingCancelled(final String s, final View view) {
            }

            @Override
            public void onLoadingComplete(final String s, final View view, final Bitmap bitmap) {
                final int color = this.a.a.getResources().getColor(R.color.text_color_school_card);
                this.a.c.setTextColor(color);
                this.a.d.setTextColor(color);
                this.a.f.setTextColor(color);
                final TextView e = this.a.e;
                if (e != null) {
                    e.setTextColor(color);
                }
                final TextView g = this.a.g;
                if (g != null) {
                    g.setTextColor(color);
                }
            }

            @Override
            public void onLoadingFailed(final String s, final View view, final FailReason failReason) {
            }

            @Override
            public void onLoadingStarted(final String s, final View view) {
            }
        };
        this.a = a;
    }
}
