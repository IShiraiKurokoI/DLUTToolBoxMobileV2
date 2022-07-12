package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.RankResult;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private final Context mContext;
    private List<RankResult.DataDTO.ListDTO> mDatas;
    private boolean b =true;

    public RankAdapter(Context context, ArrayList<RankResult.DataDTO.ListDTO> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<RankResult.DataDTO.ListDTO> arrayList) {
        mDatas = arrayList;
        notifyDataSetChanged();
    }

    public void setcoin(Boolean b)
    {
        this.b=b;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.ranking_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder) holder).user_name.setText(mDatas.get(position).getName());
        ((ViewHolder) holder).user_org.setText(mDatas.get(position).getOrgName());
        ((ViewHolder) holder).tv_count.setText(mDatas.get(position).getPoints());
        if(b)
        {
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.gold_coins);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv_count.setCompoundDrawables(null,null,drawable,null);
        }
        else
        {
            holder.tv_count.setCompoundDrawables(null,null,null,null);
        }
        if(mDatas.get(position).getHead().length()>4)
        {
            Glide.with(mContext).load(mDatas.get(position).getHead()).into(((ViewHolder) holder).user_head);
        }
        ((ViewHolder) holder).tv_top.setText(String.valueOf(position+1));
    }
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_top;
        ImageView user_head;
        TextView user_name;
        TextView user_org;
        TextView tv_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_top = itemView.findViewById(R.id.tv_top);
            this.user_head = itemView.findViewById(R.id.user_head);
            this.user_name = itemView.findViewById(R.id.user_name);
            this.user_org = itemView.findViewById(R.id.user_org);
            this.tv_count = itemView.findViewById(R.id.tv_count);
        }
    }
}