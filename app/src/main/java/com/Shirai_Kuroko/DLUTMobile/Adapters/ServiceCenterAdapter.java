package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments.ServiceCenterFragment;
import com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement.AppDetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ServiceCenterAdapter.java
 * @Description TODO
 * @createTime 2022年09月28日 22:17
 */
public class ServiceCenterAdapter extends BaseAdapter {

    private List<ApplicationConfig> mList;
    private List<ApplicationConfig> mSearchList;
    private ServiceCenterFragment mcontext;

    public ServiceCenterAdapter(ServiceCenterFragment context) {
        mcontext = context;
        this.mList = ConfigHelper.Getmlist(context.requireContext());
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
        mSearchList = new ArrayList<>(ConfigHelper.Getmlist(mcontext.requireContext()));
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
            mList = ConfigHelper.Getmlist(mcontext.requireContext());
            for (ApplicationConfig p : mList) {
                if (p.getAppName().contains(str) || p.getDescribe().contains(str)) {
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
            mList = ConfigHelper.Getmlist(mcontext.requireContext());
            mcontext.catagoryfilter = str;
            if (str.isEmpty()) {
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
            convertView = LayoutInflater.from(mcontext.getActivity()).inflate(R.layout.items, null);
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
            Intent intent = new Intent(mcontext.getActivity(), AppDetailActivity.class);
            intent.putExtra("App_ID", App_ID);
            mcontext.startActivity(intent);
        };

        convertView.setOnClickListener(onClickListener);

        // 获取到mList中指定索引位置的资源
        String title = mSearchList.get(position).getAppName();
        String desc = mSearchList.get(position).getDescribe();
        // 将资源传递给ListItemView的两个域对象
        Glide.with(mcontext.requireActivity()).load(mSearchList.get(position).getIcon()).into(listItemView.App_Icon);
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
            if (ConfigHelper.Getmlist(mcontext.requireActivity()).get(App_ID).getIssubscription() == 0) {
                ConfigHelper.addsubscription(mcontext.requireActivity(), App_ID);
            } else {
                ConfigHelper.removesubscription(mcontext.requireActivity(), App_ID);
            }
            mcontext.Refresh();
        });
        return convertView;
    }

    static class ListItemView {
        TextView Position_Num;
        ImageView App_Icon;
        TextView App_Name;
        TextView App_Describe;
        ImageButton Btn_Add_Remove;
    }
}
