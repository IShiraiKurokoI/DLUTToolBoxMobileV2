package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement.AppDetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ServiceCenterFragment extends Fragment {

    private boolean nobar = false;

    public ServiceCenterFragment() {
    }

    public ServiceCenterFragment(int id) {
        if (id == 1) {
            nobar = true;
        }
    }

    MainListViewAdapter adapter;

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

    private String catagoryfilter = "";
    public int CatagorySelected = Color.argb(140, 228, 245, 255);

    @SuppressLint("ResourceAsColor")
    public void UIInitialize() {
        ListView mListView = requireView().findViewById(R.id.applistview);
        final LayoutInflater inflater = LayoutInflater.from(requireActivity());
        @SuppressLint("InflateParams")
        View footview = inflater.inflate(R.layout.view_listfooter, null, false);
        mListView.addFooterView(footview);
        adapter = new MainListViewAdapter();
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
                androidx.appcompat.widget.SearchView search;
                search = requireActivity().findViewById(R.id.search);
                if (search != null) {
                    if (search.hasFocus()) {
                        search.clearFocus();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        androidx.appcompat.widget.SearchView search;
        search = requireActivity().findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("查找");
        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    adapter.showAllProduct();
                } else {
                    adapter.getFilter().filter(query);
                }
                cleanbg();
                LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
                l1.setBackgroundColor(CatagorySelected);
//                search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.showAllProduct();
                } else {
                    adapter.getFilter().filter(newText);
                }
                cleanbg();
                LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
                l1.setBackgroundColor(CatagorySelected);
                return true;
            }
        });
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

    class MainListViewAdapter extends BaseAdapter {

        private List<ApplicationConfig> mList;
        private List<ApplicationConfig> mSearchList;

        public MainListViewAdapter() {
            this.mList = ConfigHelper.Getmlist(getActivity());
            this.mSearchList = new ArrayList<>(mList);
        }

        @Override
        public int getCount() {
            return mSearchList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSearchList.get(position);
        }

        public void showAllProduct() {
            mSearchList = new ArrayList<>(ConfigHelper.Getmlist(requireActivity()));
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public Filter getFilter() {
            return mFilter;
        }

        public Filter getCatagoryFilter() {
            return mCatagoryFilter;
        }

        private final Filter mFilter = new Filter() {
            @SuppressLint("DefaultLocale")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<ApplicationConfig> list = new ArrayList<>();
                mList = ConfigHelper.Getmlist(getActivity());
                for (ApplicationConfig p : mList) {
                    if (p.getAppName().contains(str)) {
                        list.add(p);
                    }
                }
                results.values = list;
                results.count = list.size();
                Log.i("Debug", str + " 搜索结果数量" + results.count);
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mSearchList.clear();
                mSearchList.addAll((ArrayList<ApplicationConfig>) results.values);
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        private final Filter mCatagoryFilter = new Filter() {
            @SuppressLint("DefaultLocale")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<ApplicationConfig> list = new ArrayList<>();
                mList = ConfigHelper.Getmlist(getActivity());
                catagoryfilter = str;
                if (str.equals("")) {
                    list = mList;
                } else {
                    for (ApplicationConfig p : mList) {
                        if (p.getCategory().equals(str)) {
                            list.add(p);
                        }
                    }
                }
                results.values = list;
                results.count = list.size();
                Log.i("Debug", str + " 分类结果数量" + results.count);
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mSearchList.clear();
                mSearchList.addAll((ArrayList<ApplicationConfig>) results.values);
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        /**
         * 返回item的视图
         */
        @SuppressLint({"InflateParams", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.items, null);
                // 实例化一个封装类ListItemView，并实例化它的域
                listItemView = new ListItemView();
                listItemView.Position_Num = convertView.findViewById(R.id.Position_Num);
                listItemView.App_Icon = convertView.findViewById(R.id.App_Icon);
                listItemView.App_Name = convertView.findViewById(R.id.App_Name);
                listItemView.App_Describe = convertView.findViewById(R.id.App_Describe);
                listItemView.Btn_Add_Remove = convertView.findViewById(R.id.Btn_Add_Remove);
                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从convertView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }


            View.OnClickListener onClickListener = view -> {
                int App_ID = mSearchList.get(position).getId();
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                intent.putExtra("App_ID", App_ID);
                startActivity(intent);
            };

            convertView.setOnClickListener(onClickListener);

            // 获取到mList中指定索引位置的资源
            String title = mSearchList.get(position).getAppName();
            String desc = mSearchList.get(position).getDescribe();
            // 将资源传递给ListItemView的两个域对象
            Glide.with(requireActivity()).load(mSearchList.get(position).getIcon()).into(listItemView.App_Icon);
            listItemView.App_Name.setText(title);
            listItemView.Position_Num.setText(String.valueOf(position + 1));
            listItemView.App_Describe.setText(desc);
            if (mSearchList.get(position).getIssubscription() == 1) {
                listItemView.Btn_Add_Remove.setBackgroundResource(R.drawable.btn_cancel_app);
            } else {
                listItemView.Btn_Add_Remove.setBackgroundResource(R.drawable.btn_add_app);
            }
            listItemView.Btn_Add_Remove.setOnClickListener(v -> {
                int App_ID = mSearchList.get(position).getId();
                if (ConfigHelper.Getmlist(requireActivity()).get(App_ID).getIssubscription() == 0) {
                    ConfigHelper.addsubscription(requireActivity(), App_ID);
                } else {
                    ConfigHelper.removesubscription(requireActivity(), App_ID);
                }
                Refresh();
            });
            return convertView;
        }
    }

    public void Refresh() {
        androidx.appcompat.widget.SearchView search;
        search = requireActivity().findViewById(R.id.search);
        String query = search.getQuery().toString();
        if (!TextUtils.isEmpty(query)) {
            cleanbg();
            LinearLayout l1 = requireActivity().findViewById(R.id.CatagoryLinear1);
            l1.setBackgroundColor(CatagorySelected);
            adapter.getFilter().filter(query);
        } else {
            if (catagoryfilter.equals("")) {
                adapter.showAllProduct();
            } else {
                adapter.getCatagoryFilter().filter(catagoryfilter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Refresh();
    }

    static class ListItemView {
        TextView Position_Num;
        ImageView App_Icon;
        TextView App_Name;
        TextView App_Describe;
        ImageButton Btn_Add_Remove;
    }
}