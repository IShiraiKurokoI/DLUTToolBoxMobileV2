package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.widget.*;
import android.graphics.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;

public class PullDownScrollView extends ScrollView
{
    public View a;
    public float b;
    public Rect c;

    public PullDownScrollView(final Context context, final AttributeSet set) {
        super(context, set);
        this.c = new Rect();
    }

    public void onFinishInflate() {
        if (this.getChildCount() > 0) {
            this.a = this.getChildAt(0);
        }
        super.onFinishInflate();
    }

    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (this.a == null) {
            return super.onTouchEvent(motionEvent);
        }
        final int action = motionEvent.getAction();
        if (action != 0) {
            final boolean b = true;
            if (action != 1) {
                if (action == 2) {
                    final float b2 = this.b;
                    final float y = motionEvent.getY();
                    if (y >= this.b) {
                        final int n = (int)(b2 - y) / 3;
                        this.b = y;
                        final int measuredHeight = this.a.getMeasuredHeight();
                        final int height = this.getHeight();
                        final int scrollY = this.getScrollY();
                        int n2 = b ? 1 : 0;
                        if (scrollY != 0) {
                            if (scrollY == measuredHeight - height) {
                                n2 = (b ? 1 : 0);
                            }
                            else {
                                n2 = 0;
                            }
                        }
                        if (n2 != 0) {
                            if (this.c.isEmpty()) {
                                this.c.set(this.a.getLeft(), this.a.getTop(), this.a.getRight(), this.a.getBottom());
                            }
                            else {
                                final View a = this.a;
                                a.layout(a.getLeft(), this.a.getTop() - n, this.a.getRight(), this.a.getBottom() - n);
                            }
                        }
                    }
                }
            }
            else if (this.c.isEmpty() ^ true) {
                final TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float)(this.a.getTop() - this.c.top), 0.0f);
                translateAnimation.setDuration(200L);
                this.a.startAnimation(translateAnimation);
                final View a2 = this.a;
                final Rect c = this.c;
                a2.layout(c.left, c.top, c.right, c.bottom);
                this.c.setEmpty();
            }
        }
        else {
            this.b = motionEvent.getY();
        }
        return super.onTouchEvent(motionEvent);
    }
}

