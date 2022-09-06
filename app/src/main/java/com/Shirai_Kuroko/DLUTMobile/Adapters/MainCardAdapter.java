package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.AppBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Entities.MainCardBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments.HomeFragment;
import com.Shirai_Kuroko.DLUTMobile.Widgets.CardView.CardView;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class MainCardAdapter extends RecyclerView.Adapter<MainCardAdapter.ViewHolder> {
    private final Context mContext;
    private List<ID> mDatas;
    private HomeFragment fragment;

    public MainCardAdapter(Context context, ArrayList<ID> datas, HomeFragment fragment) {
        mContext = context;
        mDatas = datas;
        this.fragment = fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<ID> arrayList) {
        mDatas = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ID listDTO = mDatas.get(position);
            MainCardBean.MainCardDataBean mainCardDataBean = ConfigHelper.GetCardList(mContext).get(listDTO.getId());
            holder.card.homeFragment = fragment;
            holder.card.id = listDTO.getId();
            holder.card.position = holder.getAdapterPosition();
            holder.card.g(JSON.parseObject(JSON.toJSONString(mainCardDataBean), AppBean.class));
    }

    public List<ID> RemovePostion(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.card = itemView.findViewById(R.id.card);
        }
    }
}
