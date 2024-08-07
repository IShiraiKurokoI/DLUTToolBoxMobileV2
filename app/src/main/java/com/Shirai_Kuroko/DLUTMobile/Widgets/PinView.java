package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.R;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
@SuppressLint({"AppCompatCustomView"})
public class PinView extends EditText {
    public static final InputFilter[] q;
    public int a;
    public float b;
    public float c;
    public int d;
    public int e;
    public final Paint f;
    public final TextPaint g;
    public final Paint h;
    public ColorStateList i;
    public int j;
    public int k;
    public final Rect l;
    public final RectF m;
    public final Path n;
    public final PointF o;
    public ValueAnimator p;

    static {
        q = new InputFilter[0];
    }

    public PinView(final Context context, @Nullable final AttributeSet set) {
        this(context, set, R.attr.pinViewStyle);
    }

    public PinView(final Context context, @Nullable final AttributeSet set, int n) {
        super(context, set, n);
        this.j = -2565928;
        this.l = new Rect();
        this.m = new RectF();
        this.n = new Path();
        this.o = new PointF();
        final Resources resources = this.getResources();
        final Paint f = new Paint(1);
        (this.f = f).setStyle(Paint.Style.STROKE);
        final TextPaint g = new TextPaint(1);
        this.g = g;
        g.density = resources.getDisplayMetrics().density;
        g.setStyle(Paint.Style.FILL);
        g.setTextSize(this.getTextSize());
        this.h = new TextPaint(g);
        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(set, R.styleable.PinView, n, 0);
        this.a = obtainStyledAttributes.getInt(R.styleable.PinView_boxCount, 4);
        final int pinView_boxWidth = R.styleable.PinView_boxWidth;
        n = R.dimen.pv_pin_view_box_height;
        this.b = (float) obtainStyledAttributes.getDimensionPixelSize(pinView_boxWidth, resources.getDimensionPixelOffset(n));
        this.c = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.PinView_boxHeight, resources.getDimensionPixelOffset(n));
        this.e = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.PinView_boxMargin, resources.getDimensionPixelOffset(R.dimen.pv_pin_view_box_margin));
        this.k = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.PinView_mborderWidth, resources.getDimensionPixelOffset(R.dimen.pv_pin_view_box_border_width));
        this.d = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.PinView_boxRadius, resources.getDimensionPixelOffset(R.dimen.pv_pin_view_box_radius));
        final ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.PinView_mborderColor);
        this.i = colorStateList;
        this.j = colorStateList.getDefaultColor();
        obtainStyledAttributes.recycle();
        n = this.a;
        if (n >= 0) {
            this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n)});
        } else {
            this.setFilters(PinView.q);
        }
        f.setStrokeWidth((float) this.k);
        (this.p = ValueAnimator.ofFloat(0.5f, 1.0f)).setDuration(150L);
        this.p.setInterpolator(new DecelerateInterpolator());
        this.p.addUpdateListener(new PinViewAnimHelper(this));
        this.setCursorVisible(false);
        this.setTextIsSelectable(false);
    }

    public final void a(final Canvas canvas, final Paint paint, final CharSequence charSequence, final int n) {
        final String string = charSequence.toString();
        final int n2 = n + 1;
        paint.getTextBounds(string, n, n2, this.l);
        final PointF o = this.o;
        final float x = o.x;
        final float y = o.y;
        final float n3 = (float) (Math.abs(this.l.width()) / 2);
        final Rect l = this.l;
        canvas.drawText(charSequence, n, n2, x - n3 - l.left, y + Math.abs(l.height()) / 2 - this.l.bottom, paint);
    }



    public final void b() {
        this.setSelection(this.getText().length());
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        final ColorStateList i = this.i;
        if (i == null || i.isStateful()) {
            final ColorStateList j = this.i;
            boolean b = false;
            int k;
            if (j != null) {
                k = j.getColorForState(this.getDrawableState(), 0);
            } else {
                k = this.j;
            }
            if (k != this.j) {
                this.j = k;
                b = true;
            }
            if (b) {
                this.invalidate();
            }
        }
    }

    public MovementMethod getDefaultMovementMethod() {
        return null;
    }

    public void onDraw(final Canvas canvas) {
        canvas.save();
        this.f.setColor(this.j);
        this.f.setStrokeWidth((float) this.k);
        this.g.setColor(this.getCurrentTextColor());
        for (int i = 0; i < this.a; ++i) {
            final int width = this.getWidth();
            final int a = this.a;
            final int e = this.e;
            final float n = (float) (width - (a - 1) * e);
            final float n2 = (float) a;
            final float b = this.b;
            final float n3 = (n - n2 * b) / 2.0f;
            final float n4 = (float) i;
            final float n5 = (float) (e * i);
            final int k = this.k;
            final float n6 = n4 * b + n3 + n5 + k;
            final float n7 = (float) (k * 2);
            final float n8 = (float) (this.getPaddingTop() + k);
            this.m.set(n6, n8, b + n6 - n7, this.c + n8 - this.k * 2);
            final RectF m = this.m;
            final float left = m.left;
            final float n9 = Math.abs(m.width()) / 2.0f;
            final RectF j = this.m;
            this.o.set(n9 + left, Math.abs(j.height()) / 2.0f + j.top);
            final int e2 = this.e;
            boolean b3;
            boolean b4;
            Label_0324:
            {
                Label_0322:
                {
                    if (e2 <= 0) {
                        final int a2 = this.a;
                        if (a2 > 1) {
                            if (i == 0) {
                                b3 = true;
                            } else {
                                if (i == a2 - 1) {
                                    b3 = false;
                                    break Label_0322;
                                }
                                b3 = false;
                            }
                            b4 = false;
                            break Label_0324;
                        }
                    }
                    b3 = true;
                }
                b4 = true;
            }
            final RectF l = this.m;
            final float n10 = (float) this.d;
            this.n.reset();
            final float left2 = l.left;
            final float top = l.top;
            final float right = l.right;
            final float bottom = l.bottom;
            final float n11 = n10 * 2.0f;
            final float n12 = right - left2 - n11;
            final float n13 = bottom - top - n11;
            this.n.moveTo(left2, top + n10);
            if (b3) {
                final Path n14 = this.n;
                final float n15 = -n10;
                n14.rQuadTo(0.0f, n15, n10, n15);
            } else {
                if (this.e > 0) {
                    this.n.rLineTo(0.0f, -n10);
                } else {
                    this.n.rMoveTo(0.0f, -n10);
                }
                this.n.rLineTo(n10, 0.0f);
            }
            this.n.rLineTo(n12, 0.0f);
            if (b4) {
                this.n.rQuadTo(n10, 0.0f, n10, n10);
            } else {
                this.n.rLineTo(n10, 0.0f);
                this.n.rLineTo(0.0f, n10);
            }
            this.n.rLineTo(0.0f, n13);
            if (b4) {
                this.n.rQuadTo(0.0f, n10, -n10, n10);
            } else {
                this.n.rLineTo(0.0f, n10);
                this.n.rLineTo(-n10, 0.0f);
            }
            this.n.rLineTo(-n12, 0.0f);
            if (b3) {
                final Path n16 = this.n;
                final float n17 = -n10;
                n16.rQuadTo(n17, 0.0f, n17, n17);
            } else {
                final Path n18 = this.n;
                final float n19 = -n10;
                n18.rLineTo(n19, 0.0f);
                if (this.e > 0) {
                    this.n.rLineTo(0.0f, n19);
                } else {
                    this.n.rMoveTo(n19, 0.0f);
                }
            }
            if (i != 0 && this.e <= 0) {
                this.n.rMoveTo(0.0f, -n13);
            } else {
                this.n.rLineTo(0.0f, -n13);
            }
            this.n.close();
            canvas.drawPath(this.n, this.f);
            if (this.getText().length() > i) {
                final int n20 = this.getInputType() & 0xFFF;
                int n21 = 1;
                if (n20 != 129) {
                    if (n20 != 225) {
                        if (n20 != 18) {
                            n21 = 0;
                        }
                    }
                }
                if (n21 != 0) {
                    final TextPaint g = this.g;
                    final PointF o = this.o;
                    canvas.drawCircle(o.x, o.y, g.getTextSize() / 2.0f, g);
                } else {
                    this.a(canvas, this.g, this.getText(), i);
                }
            } else if (!TextUtils.isEmpty(this.getHint()) && this.getHint().length() == this.a) {
                final TextPaint g2 = this.g;
                g2.setColor(this.getCurrentHintTextColor());
                this.a(canvas, g2, this.getHint(), i);
            }
        }
        canvas.restore();
    }

    public void onFocusChanged(final boolean b, final int n, final Rect rect) {
        super.onFocusChanged(b, n, rect);
        if (b) {
            this.b();
        }
    }

    public void onMeasure(int n, int n2) {
        final int mode = View.MeasureSpec.getMode(n);
        final int mode2 = View.MeasureSpec.getMode(n2);
        n = View.MeasureSpec.getSize(n);
        n2 = View.MeasureSpec.getSize(n2);
        final float b = this.b;
        final float c = this.c;
        if (mode != 1073741824) {
            n = this.a;
            n = Math.round(n * b + (n - 1) * this.e + this.getPaddingRight() + this.getPaddingLeft());
        }
        if (mode2 != 1073741824) {
            n2 = Math.round(c + this.getPaddingTop() + this.getPaddingBottom());
        }
        this.setMeasuredDimension(n, n2);
    }

    public void onSelectionChanged(final int n, final int n2) {
        super.onSelectionChanged(n, n2);
        if (n2 != this.getText().length()) {
            this.b();
        }
    }

    public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        super.onTextChanged(charSequence, n, n2, n3);
        if (n != charSequence.length()) {
            this.b();
        }
    }
}

