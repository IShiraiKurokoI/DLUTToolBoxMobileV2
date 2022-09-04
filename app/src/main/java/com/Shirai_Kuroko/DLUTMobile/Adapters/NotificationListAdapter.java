package com.Shirai_Kuroko.DLUTMobile.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.bumptech.glide.Glide;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    private final Context mContext;
    private List<NotificationHistoryDataBaseBean> mDatas;

    public NotificationListAdapter(Context context, List<NotificationHistoryDataBaseBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notice_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationHistoryDataBaseBean payload = mDatas.get(position);
        Date date = new Date(Long.parseLong(payload.getCreate_time()) * 1000);
        holder.tv_notice_time.setText(date.toLocaleString());
        DLUTNoticeContentBean dlutNoticeContentBean = payload.getMsg_content();
        holder.tv_notice_title.setText(dlutNoticeContentBean.getTitle());
        if (URLDecoder.decode(dlutNoticeContentBean.getDescription()).length()!=0) {
            holder.tv_notice_desc.setVisibility(View.VISIBLE);
            holder.tv_notice_desc.setText(URLDecoder.decode(dlutNoticeContentBean.getDescription()));
        } else {
            holder.tv_notice_desc.setVisibility(View.GONE);
        }
        if (dlutNoticeContentBean.getPicurl() != null) {
            holder.iv_notice_big.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(dlutNoticeContentBean.getPicurl()).into(holder.iv_notice_big);
        } else {
            holder.iv_notice_big.setVisibility(View.GONE);
        }
        holder.popup_base_view.setOnClickListener(view -> {
            if (dlutNoticeContentBean.getUrl() != null) {
                Intent intent = new Intent(mContext, PureBrowserActivity.class);
                intent.putExtra("Name", "");
                intent.putExtra("Url", dlutNoticeContentBean.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout popup_base_view;
        TextView tv_notice_time;
        CheckBox card_view_check_box;
        TextView tv_notice_title;
        ImageView iv_notice_big;
        TextView tv_notice_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.popup_base_view = itemView.findViewById(R.id.popup_base_view);
            this.tv_notice_time = itemView.findViewById(R.id.tv_notice_time);
            this.card_view_check_box = itemView.findViewById(R.id.card_view_check_box);
            this.tv_notice_title = itemView.findViewById(R.id.tv_notice_title);
            this.iv_notice_big = itemView.findViewById(R.id.iv_notice_big);
            this.tv_notice_desc = itemView.findViewById(R.id.tv_notice_desc);
        }
    }
}
