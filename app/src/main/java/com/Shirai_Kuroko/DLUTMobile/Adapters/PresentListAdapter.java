package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.PresentListResult;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.GiftDetailActivity;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PresentListAdapter extends RecyclerView.Adapter<PresentListAdapter.ViewHolder> {
    private final Context mContext;
    private List<PresentListResult.DataDTO.ListDTO> mDatas;

    public PresentListAdapter(Context context, ArrayList<PresentListResult.DataDTO.ListDTO> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<PresentListResult.DataDTO.ListDTO> arrayList) {
        mDatas = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gift_store, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(mDatas.get(position).getImage().get(0)).into(holder.iv_gift_pic);
        if(!mDatas.get(position).getStoreCount().equals("0"))
        {
            holder.mask_out_of_stock.setVisibility(View.GONE);
        }
        holder.tv_gift_name.setText(mDatas.get(position).getName());
        holder.tv_gift_price.setText(mDatas.get(position).getNeedPoints());
        holder.GiftContainer.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, GiftDetailActivity.class);
            intent.putExtra("GiftObject", JSON.toJSONString(mDatas.get(holder.getAdapterPosition())));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout GiftContainer;
        ImageView iv_gift_pic;
        TextView mask_out_of_stock;
        TextView tv_gift_name;
        TextView tv_gift_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.GiftContainer = itemView.findViewById(R.id.GiftContainer);
            this.iv_gift_pic = itemView.findViewById(R.id.iv_gift_pic);
            this.mask_out_of_stock = itemView.findViewById(R.id.mask_out_of_stock);
            this.tv_gift_name = itemView.findViewById(R.id.tv_gift_name);
            this.tv_gift_price = itemView.findViewById(R.id.tv_gift_price);
        }
    }
}
