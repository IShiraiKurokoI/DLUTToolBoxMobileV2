package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.Shirai_Kuroko.DLUTMobile.R;

public class RoundedBackgroundSpan extends ReplacementSpan {

    private static int CORNER_RADIUS = 8;

    private int backgroundColor = 0;

    private int textColor = 0;

    public RoundedBackgroundSpan(Context context) {

        super();

        backgroundColor = context.getResources().getColor(R.color.gray);

        textColor = context.getResources().getColor(R.color.white);

    }

    @Override

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);

        paint.setColor(backgroundColor);

        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);

        paint.setColor(textColor);

        canvas.drawText(text, start, end, x, y, paint);

    }

    @Override

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {

        return Math.round(paint.measureText(text, start, end));

    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {

        return paint.measureText(text, start, end);

    }

}
