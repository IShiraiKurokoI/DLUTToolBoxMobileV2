package com.Shirai_Kuroko.DLUTMobile.Common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.R;

public class CenterToast extends Toast
{
    public CenterToast c;
    public Context a;
    public TextView b;

    public CenterToast(final Context a) {
        super(a);
        c=this;
        this.b = null;
        this.a = a;
        final View inflate = LayoutInflater.from(a).inflate(R.layout.center_toast, null);
        this.b = inflate.findViewById(R.id.text);
        this.setView(inflate);
        this.setGravity(17, 0, 0);
    }

    public void a(final String text) {
        this.b.setText(text);
        this.setDuration(Toast.LENGTH_SHORT);
        this.show();
    }

    public void b(final String text, final String s2) {
        String s3 = s2;
        if (TextUtils.isEmpty(s2)) {
            s3 = "#ffffff";
        }
        this.b.setTextColor(Color.parseColor(s3));
        this.b.setTextSize(1, (float)16);
        this.b.setText(text);
        this.setDuration(Toast.LENGTH_SHORT);
        int tvToastId = Resources.getSystem().getIdentifier("message", "id", "android");
        TextView tvToast = this.getView().findViewById(tvToastId);
        if(tvToast != null){
            tvToast.setGravity(Gravity.CENTER);
        }
        this.show();
    }

    public void setText(final int text) {
        this.b.setText(text);
    }

    public void setText(final CharSequence text) {
        this.b.setText(text);
    }
}
