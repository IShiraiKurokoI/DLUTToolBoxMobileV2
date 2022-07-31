package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.animation.ValueAnimator;

public class PinViewAnimHelper implements ValueAnimator.AnimatorUpdateListener
{
    public final PinView a;

    public PinViewAnimHelper(final PinView a) {
        super();
        this.a = a;
    }

    public void onAnimationUpdate(final ValueAnimator valueAnimator) {
        final float floatValue = (float)valueAnimator.getAnimatedValue();
        final int alpha = (int)(255.0f * floatValue);
        final PinView a = this.a;
        a.h.setTextSize(a.getTextSize() * floatValue);
        this.a.h.setAlpha(alpha);
        this.a.postInvalidate();
    }
}
