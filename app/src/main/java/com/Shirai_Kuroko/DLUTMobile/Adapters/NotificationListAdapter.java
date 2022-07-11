package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationPayload;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NotificationListAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<NotificationPayload> mDatas;

    public NotificationListAdapter(Context context, List<NotificationPayload> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh() {
        mDatas = ConfigHelper.GetNotificationHistoryList(mContext);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notice_layout, null);
        return new NotificationListAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            NotificationPayload payload = mDatas.get(position);
            Date date = new Date(Long.parseLong(payload.getTimestamp()) * 1000);
            holder.tv_notice_time.setText(date.toLocaleString());
            String payloadcustom = payload.getPayload().getBody().getCustom().getContent();
            DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseObject(payloadcustom, DLUTNoticeContentBean.class);
            holder.tv_notice_title.setText(dlutNoticeContentBean.getTitle());
            if (!Objects.equals(dlutNoticeContentBean.getDescription(), "")) {
                holder.tv_notice_desc.setText(dlutNoticeContentBean.getPicurl());
                Log.i("", "onBindViewHolder: " + dlutNoticeContentBean.getPicurl());
            } else {
                holder.tv_notice_desc.setVisibility(View.GONE);
            }
            if (dlutNoticeContentBean.getPicurl() != null) {
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
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
