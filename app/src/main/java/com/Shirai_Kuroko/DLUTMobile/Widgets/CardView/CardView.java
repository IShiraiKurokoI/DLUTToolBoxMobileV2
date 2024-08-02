package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Shirai_Kuroko.DLUTMobile.Entities.AppBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.CardInfoBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.CardListResultBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.CardTabsBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.DataCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.HorScrollCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.ImgTextCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.TableCardRowBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.TextCardItemBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.CardManageActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments.HomeFragment;
import com.Shirai_Kuroko.DLUTMobile.Widgets.MaxHeightView;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CardView.java
 * @Description TODO
 * @createTime 2022年09月06日 07:16
 */
public class CardView extends MaxHeightView implements View.OnClickListener {
    public static final String w;
    public int[] d;
    public Context context;
    public AppBean appBean;
    public CardInfoBean cardInfoBean;
    public ImageView imageView;
    public TextView textView1;
    public TextView textView2;
    public FrameLayout frameLayout;
    public FrameLayout frameLayout1;
    public View m;
    public TextView n;
    public float o;
    public float p;
    public LinearLayout linearLayout;
    public boolean r;
    public e t;
    public TextView textView;
    public ExpandableCardContentView.a v;
    public int id;
    public int position;
    public HomeFragment homeFragment;

    static {
        w = CardView.class.getSimpleName();
    }

    public CardView(@NonNull final Context e, @Nullable final AttributeSet set) {
        super(e, set);
        final int[] array;
        final int[] d = array = new int[2];
        array[0] = 16842996;
        array[1] = 16842997;
        this.d = d;
        this.o = -1.0f;
        this.p = 1.0f;
        this.r = true;
        this.context = e;
        this.o = (float) e.obtainStyledAttributes(set, d).getLayoutDimension(0, -1);
        this.t = new e();
        LayoutInflater.from(this.context).inflate(R.layout.card_base, this);
        this.linearLayout = this.findViewById(R.id.ll_card_title);
        this.imageView = this.findViewById(R.id.iv_card_icon);
        this.textView1 = this.findViewById(R.id.tv_card_name);
        this.textView2 = this.findViewById(R.id.tv_unread_count);
        this.frameLayout = this.findViewById(R.id.tabs_container);
        this.frameLayout1 = this.findViewById(R.id.fl_card_content);
        this.m = this.findViewById(R.id.tabs_container_base);
        this.findViewById(R.id.iv_card_menu).setOnClickListener(this);
        this.textView = this.findViewById(R.id.tv_action_btn);
        if (this.o != -1.0f) {
            this.setScaleX(this.p = V(this.context, 270.0f) / (float) (u0(e)[0] - V(this.context, 16.0f) * 2));
            this.setScaleY(this.p);
        }
    }

    public static int V(final Context context, final float n) {
        return (int) (n * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int[] u0(Context context) {
        return new int[]{context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels};
    }

    public static HashMap A(final String key, final String value) {
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(key, value);
        return hashMap;
    }

    public final Map<String, String> d() {
        final HashMap a = A("v", "1");
        a.put("verify", ConfigHelper.GetUserBean(context).getData().getVerify());
        String sb = this.appBean.getSelectTab() +
                "";
        a.put("tab", sb);
        return (Map<String, String>) a;
    }

    public final int e(final float n) {
        return (int) (n * this.p + 0.5);
    }

    public class c implements View.OnClickListener {
        public final CardInfoBean.ActionBean a;
        public final CardView b;

        public c(final CardView b, final CardInfoBean.ActionBean a) {
            super();
            this.b = b;
            this.a = a;
        }

        public void onClick(final View view) {
            Intent intent = new Intent(context, PureBrowserActivity.class);
            intent.putExtra("Name", "");
            intent.putExtra("Url", this.a.getUrl());
            context.startActivity(intent);
        }
    }

    public final void f() {
        this.frameLayout1.removeView(this.n);
        this.frameLayout.removeAllViews();
        if (this.cardInfoBean == null) {
            new Handler(context.getMainLooper()).post(() -> h(2));
            return;
        }
        final e t = this.t;
        String sb = this.appBean.getCard_url() +
                this.d();
        t.b.put(sb, this.cardInfoBean);
        final CardInfoBean.ConfigBean global = this.cardInfoBean.getGlobal();
        if (global != null) {
            final int tips = global.getTips();
            if (tips > 0) {
                this.textView2.setVisibility(View.VISIBLE);
            } else {
                this.textView2.setVisibility(View.GONE);
            }
            String value;
            if (tips > 99) {
                value = "99+";
            } else {
                value = String.valueOf(tips);
            }
            this.textView2.setText(value);
        } else {
            this.textView2.setVisibility(View.GONE);
        }
        if (global != null && global.getAction() != null) {
            final CardInfoBean.ActionBean action = global.getAction();
            if (TextUtils.isEmpty(action.getName())) {
                this.textView.setVisibility(View.GONE);
            } else {
                this.textView.setVisibility(View.VISIBLE);
                this.textView.setText(action.getName());
                if (!TextUtils.isEmpty(action.getUrl())) {
                    this.textView.setOnClickListener(new c(this, action));
                }
            }
        } else {
            this.textView.setVisibility(View.GONE);
        }
        this.m.setVisibility(View.GONE);
        final CardInfoBean g = this.cardInfoBean;
        if (g != null && g.getMeta() != null) {
            final int template = this.cardInfoBean.getMeta().getTemplate();
            if (template >= 1 && template <= 5) {
                BaseCardContentView baseCardContentView = new BaseCardContentView(this.context);
                Object c;
                if (template != 1) {
                    if (template != 2) {
                        if (template != 3) {
                            if (template != 4) {
                                baseCardContentView = new DataCardView(this.context);
                                c = this.cardInfoBean.getData(DataCardItemBean.class);
                            } else {
                                baseCardContentView = new TableCardView(this.context);
                                c = this.cardInfoBean.getData(TableCardRowBean.class, true);
                            }
                        } else {
                            baseCardContentView = new HorScrollCardView(this.context);
                            c = this.cardInfoBean.getData(HorScrollCardItemBean.class);
                        }
                    } else {
                        baseCardContentView = new ImgTextCardView(this.context);
                        c = this.cardInfoBean.getData(ImgTextCardItemBean.class);
                    }
                } else {
                    baseCardContentView = new TextCardView(this.context);
                    c = this.cardInfoBean.getData(TextCardItemBean.class);
                }
                this.frameLayout.removeAllViews();
                final CardInfoBean g2 = this.cardInfoBean;
                if (g2 != null && g2.getTabs() != null && this.cardInfoBean.getTabs().getData() != null) {
                    final List<CardTabsBean.TabBean> data = this.cardInfoBean.getTabs().getData();
                    final int size = data.size();
                    if (size < 1) {
                        this.m.setVisibility(View.GONE);
                    } else {
                        this.m.setVisibility(View.VISIBLE);
                        final boolean b = size > 4;
                        final LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(-1, -2);
                        final LinearLayout linearLayout = new LinearLayout(this.context);
                        linearLayout.setLayoutParams(new LayoutParams(-1, -2));
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        final int n = u0(context)[0] - V(this.context, 16.0f) * 2;
                        int n2;
                        if (size >= 4) {
                            n2 = n / 4;
                        } else {
                            n2 = n / size;
                        }
                        int e;
                        if (b) {
                            e = this.e((float) n2);
                        } else {
                            e = 0;
                        }
                        for (int i = 0; i < size; ++i) {
                            final CardTabsBean.TabBean tabBean = data.get(i);
                            final LinearLayout linearLayout2 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.item_card_tab, null);
                            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(e, -2);
                            if ((layoutParams.width = e) == 0) {
                                layoutParams.weight = 1.0f;
                            }
                            linearLayout2.setLayoutParams(layoutParams);
                            String name;
                            if (TextUtils.isEmpty(tabBean.getName())) {
                                name = "";
                            } else {
                                name = tabBean.getName();
                            }
                            String substring = name;
                            if (name.length() > 4) {
                                substring = name.substring(0, 4);
                            }
                            final TextView textView = linearLayout2.findViewById(R.id.tv_tab_name);
                            textView.setHeight(this.e((float) textView.getMeasuredHeight()));
                            textView.setTextSize(0, (float) this.e(textView.getTextSize()));
                            textView.setText(substring);
                            textView.setOnClickListener(new b(this, 2000, linearLayout2, i));
                            linearLayout2.findViewById(R.id.card_ind).setSelected(i == this.appBean.getSelectTab());
                            linearLayout.addView(linearLayout2);
                        }
                        if (b) {
                            final HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.context);
                            horizontalScrollView.setHorizontalScrollBarEnabled(false);
                            horizontalScrollView.setLayoutParams(linearLayoutLayoutParams);
                            horizontalScrollView.addView(linearLayout);
                            this.frameLayout.addView(horizontalScrollView);
                        } else {
                            linearLayout.setId(R.id.main_card_tabs_container);
                            linearLayout.setLayoutParams(linearLayoutLayoutParams);
                            this.frameLayout.addView(linearLayout);
                        }
                    }
                } else {
                    this.m.setVisibility(View.GONE);
                }
                baseCardContentView.c = (List) c;
                baseCardContentView.a();
                baseCardContentView.b = this.cardInfoBean;
                this.v = e -> {
                    appBean.setExpand(e);
                    f();
                };
                final ExpandableCardContentView.a v = this.v;
                if (v != null && baseCardContentView instanceof ExpandableCardContentView) {
                    final ExpandableCardContentView expandableCardContentView = (ExpandableCardContentView) baseCardContentView;
                    expandableCardContentView.i = v;
                    final boolean expand = this.appBean.isExpand();
                    expandableCardContentView.g = expand;
                    if (expandableCardContentView.e) {
                        if (expand) {
                            expandableCardContentView.d();
                        } else {
                            expandableCardContentView.e();
                        }
                    }
                }
                this.frameLayout1.removeAllViews();
                this.frameLayout1.addView(baseCardContentView);
            } else {
                this.h(4);
            }
        } else {
            this.h(2);
        }
    }

    public void g(@NonNull final AppBean f) {
        this.appBean = f;
        this.textView1.setText(f.getApp_name());
        Glide.with(this.context).load(this.appBean.getIcon()).into(this.imageView);
        final e t = this.t;
        final String string = this.appBean.getCard_url() +
                this.d();
        t.b.size();
        final CardInfoBean g = t.b.get(string);
        if (g == null) {
            if (!d(this.context)) {
                this.h(3);
            } else if (TextUtils.isEmpty(this.appBean.getCard_url())) {
                this.h(2);
            } else {
                this.h(1);
                Handler handler = new Handler(this.context.getMainLooper());
                handler.post(new com.Shirai_Kuroko.DLUTMobile.Widgets.CardView.a(this));
            }
        } else {
            this.cardInfoBean = g;
            this.f();
        }
        this.linearLayout.setOnClickListener(
                new d(context, f.getUrl())
        );
    }

    public static boolean d(final Context context) {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void h(final int n) {
        this.frameLayout1.removeAllViews();
        if (this.n == null) {
            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, (int) (this.context.getResources().getDimension(R.dimen.card_loading_main_view_height) * this.p + 0.5));
            final TextView n2 = new TextView(this.context);
            n2.setLayoutParams(layoutParams);
            n2.setGravity(17);
            n2.setTextSize(1, this.p * 12.0f);
            n2.setTextColor(this.context.getResources().getColor(R.color.textColorPrimary));
            n2.setText("Loading ... ...");
            this.n = n2;
        }
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        this.n.setText(R.string.card_wording_un_support);
                    }
                } else {
                    this.n.setText(R.string.card_wording_empty);
                }
            } else {
                this.n.setText(R.string.card_wording_error);
            }
        } else {
            this.n.setText(R.string.card_wording_loading);
        }
        this.frameLayout1.addView(this.n);
    }

    public void onClick(final View view) {
        if (view.getId() == R.id.iv_card_menu) {
            final Context e = this.context;
            final View inflate = LayoutInflater.from(e).inflate(R.layout.popup_card_menu, null);
            final PopupWindow popupWindow = new PopupWindow(inflate, -1, -1, true);
            popupWindow.setTouchable(true);
            popupWindow.setAnimationStyle(R.style.popwin_anim_style);
            inflate.findViewById(R.id.ll_card_menu_root).setOnClickListener(new r(popupWindow));
            final LinearLayout linearLayout = inflate.findViewById(R.id.ll_card_menu_content);
            final int c = c(view.getContext());
            final int n = u0(e)[1] / 2;
            final int[] array = new int[2];
            view.getLocationOnScreen(array);
            final float n2 = (float) array[1];
            if (n2 > n) {
                linearLayout.measure(View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                final int n3 = linearLayout.getMeasuredHeight();
                linearLayout.setBackgroundResource(R.drawable.bg_card_menu_up);
                linearLayout.setY(n2 - c - n3);
            } else {
                final int measuredHeight = view.getMeasuredHeight();
                linearLayout.setBackgroundResource(R.drawable.bg_card_menu_down);
                linearLayout.setY(n2 - c + measuredHeight);
            }
            linearLayout.requestLayout();

            WindowManager.LayoutParams lp = homeFragment.requireActivity().getWindow().getAttributes();
            lp.alpha = 0.5f;
            homeFragment.requireActivity().getWindow().setAttributes(lp);
            popupWindow.setOnDismissListener(() -> {
                WindowManager.LayoutParams lp1 = homeFragment.requireActivity().getWindow().getAttributes();
                lp1.alpha = 1f;
                homeFragment.requireActivity().getWindow().setAttributes(lp1);
            });
            inflate.findViewById(R.id.tv_card_menu_hide).setOnClickListener(view1 -> {
                ConfigHelper.DeleteFromCard(getContext(), id);
                homeFragment.CallRemoveAndUpdate(position);
                popupWindow.dismiss();
            });
            inflate.findViewById(R.id.tv_card_menu_manager).setOnClickListener(view12 -> {
                Intent intent = new Intent(e, CardManageActivity.class);
                e.startActivity(intent);
                popupWindow.dismiss();
            });
            inflate.findViewById(R.id.tv_card_menu_refresh).setOnClickListener(view12 -> {
                h(1);
                Handler handler = new Handler(this.context.getMainLooper());
                handler.post(new com.Shirai_Kuroko.DLUTMobile.Widgets.CardView.a(this));
                popupWindow.dismiss();
            });
            popupWindow.showAsDropDown(new View(e));
        }
    }

    public static int c(final Context context) {
        final int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize;
        if (identifier > 0) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
        } else {
            dimensionPixelSize = 0;
        }
        return dimensionPixelSize;
    }

    public final class r implements View.OnClickListener {
        public final PopupWindow a;

        public r(final PopupWindow a) {
            super();
            this.a = a;
        }

        public void onClick(final View view) {
            this.a.dismiss();
        }
    }

    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        return !this.r || super.onInterceptTouchEvent(motionEvent);
    }


    public class e {
        public static final String c = "e";
        public CardListResultBean a;
        public Map<String, CardInfoBean> b;

        public e() {
            super();
            this.b = new HashMap<>();
        }
    }

    public class d implements View.OnClickListener {
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
