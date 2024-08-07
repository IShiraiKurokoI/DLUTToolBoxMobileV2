package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.TableCardRowBean;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.util.Iterator;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName TableCardView.java
 * @Description TODO
 * @createTime 2022年09月06日 08:18
 */
public class TableCardView extends ListCardContentView<TableCardRowBean>
{
    public TableCardView(@NonNull final Context context) {
        super(context);
        super.a = 14;
        super.l = false;
    }

    public TableCardView(@NonNull final Context context, @Nullable final AttributeSet set) {
        super(context, set);
        super.a = 14;
        super.l = false;
    }

    @Override
    public View f(TableCardRowBean p0, int p1) {
        return this.g(p0);
    }

    public View g(TableCardRowBean tableCardRowBean) {
        final List<TableCardRowBean.TableCellBean> data = tableCardRowBean.getData();
        View o2;
        o2 = null;
        if (data != null) {
            if (!data.isEmpty()) {
                if (data.size() > 4) {
                }
                else {
                    final Iterator<TableCardRowBean.TableCellBean> iterator = data.iterator();
                    int n = 0;
                    while (iterator.hasNext()) {
                        n += iterator.next().getSpan();
                    }
                    int n2;
                    if ((n2 = n) == 0) {
                        for (TableCardRowBean.TableCellBean datum : data) {
                            datum.setSpan(12 / data.size());
                        }
                        n2 = 12;
                    }
                    if (n2 != 12) {
                        return null;
                    }
                    final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int)(super.f.getResources().getDimension(R.dimen.card_table_row_height) * super.d + 0.5));
                    final LinearLayout linearLayout = new LinearLayout(super.f);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setWeightSum(12.0f);
                    final Iterator<TableCardRowBean.TableCellBean> iterator3 = data.iterator();
                    while (true) {
                        o2 = linearLayout;
                        if (!iterator3.hasNext()) {
                            break;
                        }
                        final TableCardRowBean.TableCellBean tableCellBean = iterator3.next();
                        final LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -1);
                        layoutParams2.weight = (float)tableCellBean.getSpan();
                        TextView tableCardRowBean1 = new TextView(super.f);
                        tableCardRowBean1.setTextSize(1, super.d * 12.0f);
                        tableCardRowBean1.setText(tableCellBean.getText());
                        tableCardRowBean1.setSingleLine();
                        tableCardRowBean1.setEllipsize(TextUtils.TruncateAt.END);
                        tableCardRowBean1.setLayoutParams(layoutParams2);
                        linearLayout.addView(tableCardRowBean1);
                    }
                }
            }
        }
        return o2;
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
