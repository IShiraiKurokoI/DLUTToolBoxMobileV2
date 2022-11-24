package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.CardInfoBean;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName BaseCardContentView.java
 * @Description TODO
 * @createTime 2022年09月06日 07:23
 */
public class BaseCardContentView<T> extends LinearLayout {
    public int a;
    public CardInfoBean b;
    public List<T> c;
    public float d;

    public BaseCardContentView(final Context context) {
        super(context);
        this.a = 1;
        this.d = 1.0f;
    }

    public BaseCardContentView(final Context context, @Nullable final AttributeSet set) {
        super(context, set);
        this.a = 1;
        this.d = 1.0f;
    }

    public void a() {

    }

    public TextView b() {
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, (int) (getResources().getDimension(R.dimen.card_loading_view_height) * this.d + 0.5));
        final TextView textView = new TextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setGravity(17);
        textView.setTextSize(1, this.d * 12.0f);
        textView.setTextColor(Color.parseColor("#999999"));
        final String string = this.getContext().getResources().getString(R.string.card_wording_empty);
        Serializable string2;
        Label_0325:
        {
            Label_0313:
            {
                try {
                    final CardInfoBean b = this.b;
                    string2 = string;
                    if (b == null) {
                        break Label_0325;
                    }
                    if (b.getGlobal() == null) {
                        break Label_0325;
                    }
                    if (this.b.getGlobal().getTheme() == null) {
                        break Label_0325;
                    }
                    if (this.b.getGlobal().getTheme().getNoDataText() == null) {
                        break Label_0325;
                    }
                    if (this.b.getGlobal().getTheme().getNoDataText().size() == 0) {
                        break Label_0325;
                    }
                    final List<String> noDataText = this.b.getGlobal().getTheme().getNoDataText();
                    string2 = noDataText.get(0);
                    try {
                        if (noDataText.size() <= 1) {
                            break Label_0313;
                        }
                        if (!TextUtils.isEmpty(noDataText.get(1))) {
                            string2 = noDataText.get(0) +
                                    "\r\n" +
                                    noDataText.get(1);
                        }
                    } catch (Exception exx) {
                        string2 = exx.getLocalizedMessage();
                    }
                } catch (Exception charSequence) {
                    string2 = charSequence.getLocalizedMessage();
                    charSequence.printStackTrace();
                }
            }
            if (TextUtils.isEmpty((CharSequence) string2)) {
                string2 = string;
            }
        }
        textView.setText((CharSequence) string2);
        return textView;
    }
}
