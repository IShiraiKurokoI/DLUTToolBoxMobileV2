package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Shirai_Kuroko.DLUTMobile.Adapters.ServiceCenterAdapter;
import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Widgets.AnanEditText;

public class ServiceCenterFragment extends Fragment {


    private ServiceCenterAdapter adapter;
    private boolean nobar = false;
    public String catagoryfilter = "";

    public ServiceCenterFragment() {
    }

    public ServiceCenterFragment(int id) {
        if (id == 1) {
            nobar = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_servicecenter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIInitialize();
    }

    public int CatagorySelected = Color.argb(140, 228, 245, 255);

    @SuppressLint("ResourceAsColor")
    public void UIInitialize() {
        ListView mListView = requireView().findViewById(R.id.applistview);
        final LayoutInflater inflater = LayoutInflater.from(requireActivity());
        @SuppressLint("InflateParams")
        View footview = inflater.inflate(R.layout.view_listfooter, null, false);
        mListView.addFooterView(footview);
        adapter = new ServiceCenterAdapter(this);
        View emptyView = requireView().findViewById(R.id.emptyview);
        mListView.setEmptyView(emptyView);
        TextView Return = requireView().findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> requireActivity().finish());
        if (nobar) {
            mListView.setPadding(0, 0, 0, 0);
            Return.setVisibility(View.VISIBLE);
        }
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ((InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(view.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        AnanEditText ananEditText = this.requireActivity().findViewById(R.id.item_search_et);
        if (ananEditText != null) {
            ananEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        adapter.showAllProduct();
                    } else {
                        adapter.getFilter().filter(editable.toString());
                    }
                    cleanbg();
                    LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
                    l1.setBackgroundColor(CatagorySelected);
                }
            });
        } else {
            return;
        }
        LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
        LinearLayout l2 = requireActivity().findViewById(R.id.CatagoryLinear2);
        LinearLayout l3 = requireActivity().findViewById(R.id.CatagoryLinear3);
        LinearLayout l4 = requireActivity().findViewById(R.id.CatagoryLinear4);
        LinearLayout l5 = requireActivity().findViewById(R.id.CatagoryLinear5);
        LinearLayout l6 = requireActivity().findViewById(R.id.CatagoryLinear6);
        l1.setBackgroundColor(CatagorySelected);
        l1.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("");
            cleanbg();
            l1.setBackgroundColor(CatagorySelected);
        });
        l2.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("study");
            cleanbg();
            l2.setBackgroundColor(CatagorySelected);
        });
        l3.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("office");
            cleanbg();
            l3.setBackgroundColor(CatagorySelected);
        });
        l4.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("life");
            cleanbg();
            l4.setBackgroundColor(CatagorySelected);
        });
        l5.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("social");
            cleanbg();
            l5.setBackgroundColor(CatagorySelected);
        });
        l6.setOnClickListener(l -> {
            adapter.getCatagoryFilter().filter("game");
            cleanbg();
            l6.setBackgroundColor(CatagorySelected);
        });
    }

    private void cleanbg() {
        LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
        LinearLayout l2 = requireActivity().findViewById(R.id.CatagoryLinear2);
        LinearLayout l3 = requireActivity().findViewById(R.id.CatagoryLinear3);
        LinearLayout l4 = requireActivity().findViewById(R.id.CatagoryLinear4);
        LinearLayout l5 = requireActivity().findViewById(R.id.CatagoryLinear5);
        LinearLayout l6 = requireActivity().findViewById(R.id.CatagoryLinear6);
        l1.setBackgroundColor(Color.TRANSPARENT);
        l2.setBackgroundColor(Color.TRANSPARENT);
        l3.setBackgroundColor(Color.TRANSPARENT);
        l4.setBackgroundColor(Color.TRANSPARENT);
        l5.setBackgroundColor(Color.TRANSPARENT);
        l6.setBackgroundColor(Color.TRANSPARENT);
    }


    public void Refresh() {
        AnanEditText ananEditText = requireActivity().findViewById(R.id.item_search_et);
        String query = ananEditText.getText().toString();
        if (!TextUtils.isEmpty(query)) {
            cleanbg();
            LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
            l1.setBackgroundColor(CatagorySelected);
            ananEditText.setText("");
        } else {
            if (catagoryfilter.isEmpty()) {
                adapter.showAllProduct();
            } else {
                adapter.getCatagoryFilter().filter(catagoryfilter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            AnanEditText ananEditText = requireActivity().findViewById(R.id.item_search_et);
            if (!ananEditText.hasFocus())
            {
                Refresh();
            }
            else
            {
                Log.i("TAG", "onResume: 修bug不刷新");
            }
        }
        catch (Exception e)
        {
            Log.e("TAG", "onResume: "+e.getLocalizedMessage() );
            LogToFile.e("ServiceCenter",e.toString());
        }
    }
}