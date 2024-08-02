package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.TextCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;

import java.text.SimpleDateFormat;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName TextCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:58
 */
public class TextCardView extends ListCardContentView<TextCardItemBean>
{
    public static final SimpleDateFormat m;

    static {
        m = new SimpleDateFormat("MM-dd");
    }

    public TextCardView(@NonNull final Context context) {
        super(context);
        super.a = 8;
        super.l = false;
    }

    public TextCardView(@NonNull final Context context, @Nullable final AttributeSet set) {
        super(context, set);
        super.a = 8;
        super.l = false;
    }

    @Override
    public View f(TextCardItemBean o, int n) {
        final View inflate = LayoutInflater.from(super.f).inflate(R.layout.item_card_text, null);
        final TextView textView = inflate.findViewById(R.id.tv_card_text_tittle);
        final TextView textView2 = inflate.findViewById(R.id.tv_content);
        final TextView textView3 = inflate.findViewById(R.id.tv_card_text_date);
        final View viewById = inflate.findViewById(R.id.divider);
        if (n == 0) {
            viewById.setVisibility(View.GONE);
        }
        else {
            viewById.setVisibility(View.VISIBLE);
        }
        final View viewById2 = inflate.findViewById(R.id.iv_unread_tip);
        if (o.isRead()) {
            viewById2.setVisibility(View.INVISIBLE);
        }
        else {
            viewById2.setVisibility(View.VISIBLE);
        }
        viewById2.setVisibility(n);
        textView.setText(o.getTitle());
        final String text = o.getText();
        if (TextUtils.isEmpty(text)) {
            textView2.setText("暂无内容");
//            textView2.setTextColor(-6710887);
        }
        else {
            textView2.setText(text);
//            textView2.setTextColor(-13421773);
        }
        textView3.setText(TextCardView.m.format(o.getTime()));
        if (super.d != 1.0f) {
            viewById2.setVisibility(View.INVISIBLE);
            final LinearLayout.LayoutParams linearLayout$LayoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            linearLayout$LayoutParams.height = (int)(linearLayout$LayoutParams.height * super.d + 0.5);
            textView.setTextSize(0, textView.getTextSize() * super.d);
            textView.requestLayout();
            final LinearLayout.LayoutParams linearLayout$LayoutParams2 = (LinearLayout.LayoutParams)textView2.getLayoutParams();
            linearLayout$LayoutParams2.height = (int)(linearLayout$LayoutParams2.height * super.d + 0.5);
            textView2.setTextSize(0, textView2.getTextSize() * super.d);
            textView2.requestLayout();
            final LinearLayout.LayoutParams linearLayout$LayoutParams3 = (LinearLayout.LayoutParams)textView3.getLayoutParams();
            linearLayout$LayoutParams3.height = (int)(linearLayout$LayoutParams3.height * super.d + 0.5);
            textView3.setTextSize(0, textView3.getTextSize() * super.d);
            textView3.requestLayout();
        }
        if (!TextUtils.isEmpty(o.getUrl())) {
            inflate.setOnClickListener(new d(this.getContext(), o.getUrl()));
        }
        return inflate;
    }

    public static class d implements View.OnClickListener
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
