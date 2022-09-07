package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.Entities.ImgTextCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.bumptech.glide.Glide;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ImgTextCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:50
 */
public class ImgTextCardView extends ListCardContentView<ImgTextCardItemBean> {
    public ImgTextCardView(@NonNull final Context context) {
        super(context);
        super.a = 4;
    }

    public ImgTextCardView(@NonNull final Context context, @Nullable final AttributeSet set) {
        super(context, set);
        super.a = 4;
    }

    @Override
    public View f(ImgTextCardItemBean p0, int p1) {
        return this.g((ImgTextCardItemBean) p0);
    }

    public View g(final ImgTextCardItemBean imgTextCardItemBean) {
        final View inflate = LayoutInflater.from(super.f).inflate(R.layout.item_card_img_text, (ViewGroup) null);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_card_img_icon);
        final TextView textView = (TextView) inflate.findViewById(R.id.tv_card_img_tittle);
        final TextView textView2 = (TextView) inflate.findViewById(R.id.tv_card_img_content);
        if (super.d != 1.0f) {
            final RelativeLayout.LayoutParams relativeLayout$LayoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            final float n = (float) relativeLayout$LayoutParams.width;
            final float d = super.d;
            relativeLayout$LayoutParams.width = (int) (n * d + 0.5);
            relativeLayout$LayoutParams.height = (int) (relativeLayout$LayoutParams.height * d + 0.5);
            relativeLayout$LayoutParams.topMargin = (int) (relativeLayout$LayoutParams.topMargin * d + 0.5);
            relativeLayout$LayoutParams.bottomMargin = (int) (relativeLayout$LayoutParams.bottomMargin * d + 0.5);
            imageView.requestLayout();
            final RelativeLayout.LayoutParams relativeLayout$LayoutParams2 = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            final float n2 = (float) relativeLayout$LayoutParams2.leftMargin;
            final float d2 = super.d;
            relativeLayout$LayoutParams2.leftMargin = (int) (n2 * d2 + 0.5);
            relativeLayout$LayoutParams2.topMargin = (int) (relativeLayout$LayoutParams2.topMargin * d2 + 0.5);
            textView.requestLayout();
            textView.setTextSize(0, textView.getTextSize() * super.d);
            final RelativeLayout.LayoutParams relativeLayout$LayoutParams3 = (RelativeLayout.LayoutParams) textView2.getLayoutParams();
            final float n3 = (float) relativeLayout$LayoutParams3.leftMargin;
            final float d3 = super.d;
            relativeLayout$LayoutParams3.leftMargin = (int) (n3 * d3 + 0.5);
            relativeLayout$LayoutParams3.topMargin = (int) (relativeLayout$LayoutParams3.topMargin * d3 + 0.5);
            textView2.requestLayout();
            textView2.setTextSize(0, textView2.getTextSize() * super.d);
        }
        textView.setText((CharSequence) imgTextCardItemBean.getTitle());
        textView2.setText((CharSequence) imgTextCardItemBean.getContent());
        if (imgTextCardItemBean.getImage().equals("http://www.dlut.edu.cn"))
        {
            imageView.setImageResource(R.drawable.icon);
        }
        else
        {
            Glide.with(this.f).load(imgTextCardItemBean.getImage()).into(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) imgTextCardItemBean.getUrl())) {
            inflate.setOnClickListener((View.OnClickListener) new d(this.getContext(), imgTextCardItemBean.getUrl()));
        }else
        {
            inflate.setOnClickListener(view -> CenterToast.makeText(getContext(),"无可打开内容", Toast.LENGTH_SHORT).show());
        }
        return inflate;
    }

    public static class d implements View.OnClickListener {
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
