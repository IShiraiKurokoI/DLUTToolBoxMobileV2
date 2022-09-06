package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.HorScrollCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName HorScrollCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:28
 */
public class HorScrollCardView extends BaseCardContentView<HorScrollCardItemBean>
{
    public Context e;

    public HorScrollCardView(@NonNull final Context e) {
        super(e);
        this.e = e;
        super.a = 10;
    }

    public HorScrollCardView(@NonNull final Context e, @Nullable final AttributeSet set) {
        super(e, set);
        this.e = e;
        super.a = 10;
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
        final View inflate = LayoutInflater.from(this.e).inflate(R.layout.card_hor_scroll_content, null);
        final int n = (int)(this.e.getResources().getDimension(R.dimen.card_common_content_padding_left) * super.d + 0.5);
        int n2 = 0;
        inflate.setPadding(n, 0, n, 0);
        final LinearLayout linearLayout = inflate.findViewById(R.id.ll_card_scroll_content);
        for (int i = 0; i < super.c.size(); ++i) {
            final HorScrollCardItemBean horScrollCardItemBean = super.c.get(i);
            final View inflate2 = LayoutInflater.from(this.e).inflate(R.layout.item_card_hor_scroll, null);
            final ImageView imageView = inflate2.findViewById(R.id.iv_card_scroll_img);
            final TextView textView = inflate2.findViewById(R.id.tv_card_scroll_tittle);
            final TextView textView2 = inflate2.findViewById(R.id.tv_card_scroll_des);
            if (i != 0) {
                final int n3 = (int)(this.e.getResources().getDimension(R.dimen.card_hor_scroll_item_margin) * super.d + 0.5);
                final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.setMargins(n3, n2, n2, n2);
                inflate2.setLayoutParams(layoutParams);
            }
            if (super.d != 1.0f) {
                final LinearLayout.LayoutParams linearLayout$LayoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
                final float n4 = (float)linearLayout$LayoutParams.width;
                final float d = super.d;
                linearLayout$LayoutParams.width = (int)(n4 * d + 0.5);
                linearLayout$LayoutParams.height = (int)(linearLayout$LayoutParams.height * d + 0.5);
                linearLayout$LayoutParams.topMargin = (int)(linearLayout$LayoutParams.topMargin * d + 0.5);
                imageView.requestLayout();
                final LinearLayout.LayoutParams linearLayout$LayoutParams2 = (LinearLayout.LayoutParams)textView.getLayoutParams();
                final float n5 = (float)linearLayout$LayoutParams2.width;
                final float d2 = super.d;
                linearLayout$LayoutParams2.width = (int)(n5 * d2 + 0.5);
                linearLayout$LayoutParams2.height = (int)(linearLayout$LayoutParams2.height * d2 + 0.5);
                linearLayout$LayoutParams2.topMargin = (int)(linearLayout$LayoutParams2.topMargin * d2 + 0.5);
                textView.requestLayout();
                textView.setTextSize(0, textView.getTextSize() * super.d);
                final LinearLayout.LayoutParams linearLayout$LayoutParams3 = (LinearLayout.LayoutParams)textView2.getLayoutParams();
                final float n6 = (float)linearLayout$LayoutParams3.width;
                final float d3 = super.d;
                linearLayout$LayoutParams3.width = (int)(n6 * d3 + 0.5);
                linearLayout$LayoutParams3.topMargin = (int)(linearLayout$LayoutParams3.topMargin * d3 + 0.5);
                linearLayout$LayoutParams3.bottomMargin = (int)(linearLayout$LayoutParams3.bottomMargin * d3 + 0.5);
                textView2.requestLayout();
                final float textSize = textView2.getTextSize();
                final float d4 = super.d;
                textView2.setTextSize(0, textSize * d4);
            }
            textView.setText(horScrollCardItemBean.getTitle());
            textView2.setText(horScrollCardItemBean.getSubtitle());
            Glide.with(e).load(horScrollCardItemBean.getImage()).into(imageView);
            if (!TextUtils.isEmpty(horScrollCardItemBean.getUrl())) {
                inflate2.setOnClickListener(new d(this.getContext(), horScrollCardItemBean.getUrl()));
            }
            linearLayout.addView(inflate2);
            if (linearLayout.getChildCount() >= super.a) {
                break;
            }
        }
        this.addView(inflate);
    }

    public class d implements View.OnClickListener
    {
        public Context a;
        public String b;

        public d(final Context a, final String b) {
            super();
            this.a = a;
            this.b = b;
        }

        public void onClick(final View view) {
            Intent intent = new Intent(this.a, PureBrowserActivity.class);
            intent.putExtra("Name", "");
            intent.putExtra("Url", this.b);
            this.a.startActivity(intent);
        }
    }

}
