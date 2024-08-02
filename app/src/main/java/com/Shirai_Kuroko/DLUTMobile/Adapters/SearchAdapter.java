package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.SearchBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.bumptech.glide.Glide;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final Context mContext;
    private List<SearchBean> mDatas;

    public SearchAdapter(Context context, List<SearchBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void DataRefresh(List<SearchBean> datas) {
        mDatas.clear();
        notifyDataSetChanged();
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_common, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchBean searchBean = mDatas.get(position);
        if (!searchBean.getisnotification()) {
            holder.ServiceItem.setVisibility(View.VISIBLE);
            holder.NoticeItem.setVisibility(View.GONE);
            Glide.with(mContext).load(searchBean.applicationConfig.getIcon()).into(holder.App_Icon);
            holder.App_Name.setText(searchBean.applicationConfig.getAppName());
            holder.App_Describe.setText(searchBean.applicationConfig.getDescribe());
            holder.ServiceItem.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra("App_ID", String.valueOf(searchBean.applicationConfig.getId()));
                mContext.startActivity(intent);
            });
        } else {
            holder.ServiceItem.setVisibility(View.GONE);
            holder.NoticeItem.setVisibility(View.VISIBLE);
            holder.tv_notice_title.setText(URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getTitle()));
            if (!URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getDescription()).replace(" ", "").isEmpty()) {
                holder.tv_notice_content.setText(URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getDescription()));
            } else {
                holder.tv_notice_content.setVisibility(View.GONE);
            }
            holder.tv_datetime.setText(new Date(Long.parseLong(searchBean.notificationHistoryDataBaseBean.getCreate_time()) * 1000).toLocaleString());
            holder.NoticeItem.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, PureBrowserActivity.class);
                intent.putExtra("Name", "");
                intent.putExtra("Url", searchBean.notificationHistoryDataBaseBean.getMsg_content().getUrl());
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ServiceItem;
        ImageView App_Icon;
        TextView App_Name;
        TextView App_Describe;
        LinearLayout NoticeItem;
        TextView tv_notice_title;
        TextView tv_notice_content;
        TextView tv_datetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ServiceItem = itemView.findViewById(R.id.ServiceItem);
            this.App_Icon = itemView.findViewById(R.id.App_Icon);
            this.App_Name = itemView.findViewById(R.id.App_Name);
            this.App_Describe = itemView.findViewById(R.id.App_Describe);
            this.NoticeItem = itemView.findViewById(R.id.NoticeItem);
            this.tv_notice_title = itemView.findViewById(R.id.tv_notice_title);
            this.tv_notice_content = itemView.findViewById(R.id.tv_notice_content);
            this.tv_datetime = itemView.findViewById(R.id.tv_datetime);
        }
    }
}
