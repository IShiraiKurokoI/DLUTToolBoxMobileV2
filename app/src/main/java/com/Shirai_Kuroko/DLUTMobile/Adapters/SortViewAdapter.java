package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Entities.MainCardBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SortViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private ArrayList<ID> mDatas;

    public SortViewAdapter(Context context, ArrayList<ID> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh() {
        mDatas = ConfigHelper.GetCardIDList(mContext);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_sort_drag_view, null);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            MainCardBean.MainCardDataBean mainCardDataBean = ConfigHelper.GetCardList(mContext).get(mDatas.get(position).getId());
            holder.card_name.setText(mainCardDataBean.getApp_name());
            Glide.with(mContext).load(mainCardDataBean.getIcon()).into(holder.card_icon);
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_name;
        ImageView card_icon;
        View v_item_divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.card_name = itemView.findViewById(R.id.card_name);
            this.card_icon = itemView.findViewById(R.id.card_icon);
            this.v_item_divider = itemView.findViewById(R.id.v_item_divider);
        }
    }
}
