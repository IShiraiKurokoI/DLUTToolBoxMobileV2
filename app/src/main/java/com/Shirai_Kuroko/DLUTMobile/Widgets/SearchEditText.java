package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.Shirai_Kuroko.DLUTMobile.R;

public class SearchEditText extends RelativeLayout implements View.OnClickListener
{
    public Context a;
    public Button b;
    public View c;
    public View d;
    public View e;
    public View f;
    public AnanEditText g;
    public View h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public float n;
    public float o;
    public boolean p;
    public int q;
    public d r;
    public c s;

    public SearchEditText(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }

    public SearchEditText(final Context a, final AttributeSet set, final int n) {
        super(a, set, n);
        this.n = -1.0f;
        this.o = -1.0f;
        this.p = false;
        this.q = 300;
        this.a = a;
        LayoutInflater.from(a).inflate(R.layout.item_search, this, true);
        this.b = this.findViewById(R.id.btn_search_cancel);
        this.c = this.findViewById(R.id.item_search_et_ll);
        this.d = this.findViewById(R.id.item_search_icon);
        this.e = this.findViewById(R.id.item_search_txt);
        this.f = this.findViewById(R.id.item_serach_panel);
        this.g = this.findViewById(R.id.item_search_et);
        this.h = this.findViewById(R.id.item_search_clean_btn);
        this.c.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.g.addTextChangedListener(new j1(this));
        this.g.getViewTreeObserver().addOnGlobalLayoutListener(new k1(this));
        this.b.getViewTreeObserver().addOnGlobalLayoutListener(new l1(this));
        this.d.getViewTreeObserver().addOnGlobalLayoutListener(new m1(this));
        this.e.getViewTreeObserver().addOnGlobalLayoutListener(new n1(this));
    }

    public static void E(final Context context) {
        final InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context instanceof Activity && inputMethodManager != null) {
            final View currentFocus = ((Activity)context).getCurrentFocus();
            if (currentFocus != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }

    public void a(final boolean b) {
        if (this.p) {
            return;
        }
        Label_0049: {
            if (b) {
                final d r = this.r;
                if (r != null) {
                    r.a();
                    break Label_0049;
                }
            }
            if (!b) {
                final c s = this.s;
                if (s != null) {
                    s.a();
                }
            }
        }
        if (!b) {
            this.g.setText("");
            this.f.setVisibility(View.INVISIBLE);
            this.c.setVisibility(View.VISIBLE);
            E(this.a);
        }
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(this.q);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        final SearchEditText aa = this;
        ofFloat.addUpdateListener(valueAnimator -> {
            final Float n = (Float)valueAnimator.getAnimatedValue();
            float x;
            if (b) {
                final float n2 = aa.n;
                x = n2 - n * (n2 - aa.k);
            }
            else {
                final int k = aa.k;
                x = n * (aa.n - k) + k;
            }
            aa.d.setX(x);
            float x2;
            if (b) {
                final float o = aa.o;
                x2 = o - n * (o - aa.k - aa.m - aa.l);
            }
            else {
                final int l = aa.l;
                final int i = aa.k;
                final int m = aa.m;
                x2 = n * (aa.o - i - m - l) + (l + i + m);
            }
            aa.e.setX(x2);
            float n3;
            if (b) {
                final int j = aa.i;
                final int j2 = aa.j;
                n3 = n * (j + j2) - (j + j2);
            }
            else {
                n3 = n * -(aa.i + aa.j);
            }
            ((LayoutParams)aa.b.getLayoutParams()).rightMargin = (int)n3;
            aa.b.requestLayout();
        });
        ofFloat.addListener(new Animator.AnimatorListener() {

            public void onAnimationCancel(final Animator animator) {
            }

            public void onAnimationEnd(final Animator animator) {
                aa.p = false;
                if (b) {
                    aa.f.setVisibility(View.VISIBLE);
                    aa.c.setVisibility(View.GONE);
                    aa.g.requestFocus();
                    T(aa.a, aa.g);
                }
            }

            public void onAnimationRepeat(final Animator animator) {
            }

            public void onAnimationStart(final Animator animator) {
            }
        });
        ofFloat.start();
        this.p = true;
    }

    public static void T(final Context context, final View view) {
        final InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public void onClick(final View view) {
        final int id = view.getId();
        if (id == R.id.item_search_et_ll) {
            this.a(true);
        }
        else if (id == R.id.btn_search_cancel) {
            ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            this.a(false);
        }
        else if (id == R.id.item_search_clean_btn) {
            this.g.setText("");
        }
    }

    public interface c
    {
        void a();
    }

    public interface d
    {
        void a();
    }

    public static class j1 implements TextWatcher
    {
        public final SearchEditText a;

        public j1(final SearchEditText a) {
            super();
            this.a = a;
        }

        public void afterTextChanged(final Editable editable) {
        }

        public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        }

        @SuppressLint("WrongConstant")
        public void onTextChanged(final CharSequence charSequence, int visibility, final int n, final int n2) {
            final View h = this.a.h;
            if (charSequence.toString().isEmpty()) {
                visibility = 4;
            }
            else {
                visibility = 0;
            }
            h.setVisibility(visibility);
        }
    }
    public static class k1 implements ViewTreeObserver.OnGlobalLayoutListener
    {
        public final SearchEditText a;

        public k1(final SearchEditText a) {
            super();
            this.a = a;
        }

        public void onGlobalLayout() {
            this.a.g.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            final SearchEditText a = this.a;
            a.k = a.g.getCompoundDrawablePadding() + (int)(this.a.a.getResources().getDisplayMetrics().density * 2.0f + 0.5f);
        }
    }

    public static class l1 implements ViewTreeObserver.OnGlobalLayoutListener
    {
        public final SearchEditText a;

        public l1(final SearchEditText a) {
            super();
            this.a = a;
        }

        public void onGlobalLayout() {
            this.a.b.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            final SearchEditText a = this.a;
            a.i = a.b.getWidth();
            final SearchEditText a2 = this.a;
            a2.j = ((RelativeLayout.LayoutParams)a2.b.getLayoutParams()).leftMargin;
        }
    }

    public static class m1 implements ViewTreeObserver.OnGlobalLayoutListener
    {
        public final SearchEditText a;

        public m1(final SearchEditText a) {
            super();
            this.a = a;
        }

        public void onGlobalLayout() {
            this.a.d.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            final SearchEditText a = this.a;
            a.n = a.d.getX();
            final SearchEditText a2 = this.a;
            a2.m = a2.d.getMeasuredWidth();
        }
    }

    public static class n1 implements ViewTreeObserver.OnGlobalLayoutListener
    {
        public final SearchEditText a;

        public n1(final SearchEditText a) {
            super();
            this.a = a;
        }

        public void onGlobalLayout() {
            this.a.e.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            final SearchEditText a = this.a;
            a.o = a.e.getX();
            final SearchEditText a2 = this.a;
            a2.l = ((LinearLayout.LayoutParams)a2.e.getLayoutParams()).leftMargin;
        }
    }
}
