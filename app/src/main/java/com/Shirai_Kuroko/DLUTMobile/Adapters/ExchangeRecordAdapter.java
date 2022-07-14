package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExchangeRecordAdapter extends RecyclerView.Adapter<ExchangeRecordAdapter.ViewHolder> {
    private final Context mContext;
    private List<ExchangeRecordResult.DataDTO.ListDTO> mDatas;
    private RecyclerView recyclerView;
    private LinearLayout emptyview;

    public ExchangeRecordAdapter(Context context, ArrayList<ExchangeRecordResult.DataDTO.ListDTO> datas, RecyclerView recyclerView, LinearLayout emptyview) {
        mContext = context;
        mDatas = datas;
        this.recyclerView =recyclerView;
        this.emptyview = emptyview;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<ExchangeRecordResult.DataDTO.ListDTO> arrayList) {
        mDatas = arrayList;
        if(mDatas.size()==0)
        {
            recyclerView.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_exchange_gifts_record,viewGroup,false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExchangeRecordResult.DataDTO.ListDTO listDTO = mDatas.get(position);
        holder.gift_name.setText(listDTO.getName());
        if(listDTO.getType()==1)
        {
            holder.gift_name.setCompoundDrawables(null,null,mContext.getDrawable(R.drawable.icon_lucky),null);
        }
        holder.record_time.setText(new Date(Long.parseLong(listDTO.getExchange_time())*1000).toLocaleString());
        Glide.with(mContext).load(listDTO.getImage().get(0)).into(holder.icon_gift);
        int is_exchange = listDTO.getIs_exchange();
        if (is_exchange != 0) {
            if (is_exchange != 1) {
                if (is_exchange == 2) {
                    holder.iv_out_date.setImageResource(R.drawable.icon_out_date);
                    holder.record_time.setText("失效时间: "+new Date(Long.parseLong(listDTO.getExchange_time())*1000).toLocaleString());
                }
            }
            else {
                holder.iv_out_date.setImageResource(R.drawable.mark_gift_exchange_receive);
                holder.record_time.setText("兑换日期: "+new Date(Long.parseLong(listDTO.getExchange_time())*1000).toLocaleString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_gift;
        TextView gift_name;
        TextView record_time;
        ImageView iv_out_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.icon_gift = itemView.findViewById(R.id.icon_gift);
            this.gift_name = itemView.findViewById(R.id.gift_name);
            this.record_time = itemView.findViewById(R.id.record_time);
            this.iv_out_date = itemView.findViewById(R.id.iv_out_date);
        }
    }
}
