package com.Shirai_Kuroko.DLUTMobile.Widgets;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.Shirai_Kuroko.DLUTMobile.R;

@SuppressLint({"AppCompatCustomView"})
public class AnanEditText extends EditText {
    public int a;
    public boolean b;
    public Context c;
    public Handler d;
    public d e;
    public long f;
    public Runnable g;

    public AnanEditText(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = -1;
        this.b = false;
        this.f = 1000L;
        c=context;
        AnanEditText ananEditText = this;
        this.g = () -> {
            final d e = ananEditText.e;
            if (e != null) {
                e.a(ananEditText.getText().toString());
            }
        };
        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(set, R.styleable.AnanEditText, 0, 0);
        this.a = obtainStyledAttributes.getInt(R.styleable.AnanEditText_maxTextCount, -1);
        this.b = obtainStyledAttributes.getBoolean(R.styleable.AnanEditText_isInputLimited, false);
        this.c(this.a);
        this.d = new Handler(Looper.getMainLooper());
        this.addTextChangedListener(new a(this));
        this.setTextColor(getResources().getColor(R.color.black));
    }

    public AnanEditText(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = -1;
        this.b = false;
        this.f = 1000L;
        c=context;
        AnanEditText ananEditText = this;
        this.g = () -> {
            final d e = ananEditText.e;
            if (e != null) {
                e.a(ananEditText.getText().toString());
            }
        };
        final TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(set, R.styleable.AnanEditText, n, 0);
        this.a = obtainStyledAttributes.getInt(R.styleable.AnanEditText_maxTextCount, -1);
        this.b = obtainStyledAttributes.getBoolean(R.styleable.AnanEditText_isInputLimited, false);
        this.c(this.a);
        this.d = new Handler(Looper.getMainLooper());
        this.addTextChangedListener(new a(this));
        this.setTextColor(getResources().getColor(R.color.black));
    }
//
//    public static void a(final AnanEditText ananEditText, final String s) {
//        if (ananEditText.c != null) {
//            final int i = ananEditText.a - s.length();
//            final c c = ananEditText.c;
//            if (i < 0) {
//                final ContentInputActivity a = c.a;
//                a.b.setTextColor(a.getResources().getColor(R.color.time_count_red));
//            } else {
//                final ContentInputActivity a2 = c.a;
//                a2.b.setTextColor(a2.getResources().getColor(R.color.comment_edit_hint));
//            }
//            c.a.b.setText((CharSequence) String.valueOf(i));
//        }
//    }

    public int b() {
        return this.a - this.getText().toString().length();
    }

    public final void c(final int n) {
        if (n >= 0) {
            this.setFilters(new InputFilter[]{new b(this,n)});
        } else {
            this.setFilters(new InputFilter[]{new b(this,-1)});
        }
    }

    public boolean onTextContextMenuItem(final int n) {
        if (n == 16908322) {
            try {
                final ClipboardManager clipboardManager = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("", clipboardManager.getPrimaryClip().getItemAt(0).getText()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return super.onTextContextMenuItem(n);
    }

    public class b implements InputFilter {
        public int a;
        public final AnanEditText b;

        public b(final AnanEditText b, final int a) {
            super();
            this.b = b;
            this.a = a;
        }

        public CharSequence filter(CharSequence obj, final int n, int n2, final Spanned spanned, int length, final int beginIndex) {
            final String string = spanned.toString();
            final int n3 = 0;
            final String substring = string.substring(0, length);
            final String substring2 = spanned.toString().substring(beginIndex);
            final CharSequence subSequence = obj.subSequence(n, n2);
            final String string2 = substring +
                    subSequence +
                    substring2;
            if (string2.length() > this.a && this.b.b) {
                n2 = n3;
                CharSequence charSequence;
                while (true) {
                    length = subSequence.length();
                    obj = (charSequence = "");
                    if (n2 >= length) {
                        break;
                    }
                    final CharSequence subSequence2 = subSequence.subSequence(n, n + n2 + 1);
                    final String string3 = substring +
                            subSequence2 +
                            substring2;
                    if (string3.length() >= this.a) {
                        if (string3.length() == this.a) {
                            obj = subSequence2;
                        }
                        //                        AnanEditText.a(b, sb3);
                        charSequence = obj;
                        break;
                    }
                    ++n2;
                }
                return charSequence;
            }
//            AnanEditText.a(this.b, string2);
            return null;
        }
    }

    public interface c {
    }

    public interface d {
        void a(final String p0);

        void b(final String p0);
    }

    public class a implements TextWatcher
    {
        public final AnanEditText a;

        public a(final AnanEditText a) {
            super();
            this.a = a;
        }

        public void afterTextChanged(final Editable editable) {
        }

        public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        }

        public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            final AnanEditText a = this.a;
            a.d.removeCallbacks(a.g);
            final AnanEditText a2 = this.a;
            a2.d.postDelayed(a2.g, a2.f);
            final AnanEditText a3 = this.a;
            final AnanEditText.d e = a3.e;
            if (e != null) {
                e.b(a3.getText().toString());
            }
        }
    }
}
