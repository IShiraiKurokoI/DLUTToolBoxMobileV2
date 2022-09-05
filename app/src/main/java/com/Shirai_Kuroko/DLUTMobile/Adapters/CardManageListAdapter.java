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

import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Entities.MainCardBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.CardManageActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardManageListAdapter extends RecyclerView.Adapter<CardManageListAdapter.ViewHolder> {
    private final Context mContext;
    private List<ID> mDatas;
    private RecyclerView recyclerView;
    private LinearLayout emptyview;
    private int type = 0;
    private CardManageActivity activity;

    public CardManageListAdapter(Context context, ArrayList<ID> datas, RecyclerView recyclerView, LinearLayout emptyview, int type, CardManageActivity activity) {
        mContext = context;
        mDatas = datas;
        this.recyclerView = recyclerView;
        this.emptyview = emptyview;
        this.type = type;
        this.activity = activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<ID> arrayList) {
        mDatas = arrayList;
        if (emptyview != null) {
            if (mDatas.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                emptyview.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyview.setVisibility(View.GONE);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_manage, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ID listDTO = mDatas.get(position);
        MainCardBean.MainCardDataBean mainCardDataBean = ConfigHelper.GetCardList(mContext).get(listDTO.getId());
        holder.card_name.setText(mainCardDataBean.getApp_name());
        Glide.with(mContext).load(mainCardDataBean.getIcon()).into(holder.card_icon);
        if (type == 0) {
            holder.btn_card_add.setSelected(true);
            holder.btn_card_add.setOnClickListener(view -> {
                ConfigHelper.removeCardsubscription(mContext, listDTO.getId());
                activity.CallUpdate();
            });
        } else {
            holder.btn_card_add.setSelected(false);
            holder.btn_card_add.setOnClickListener(view -> {
                ConfigHelper.addCardsubscription(mContext, listDTO.getId());
                activity.CallUpdate();
            });
        }
        if (position==mDatas.size()-1)
        {
            holder.v_item_divider.setVisibility(View.GONE);
        }
        else
        {
            holder.v_item_divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView card_icon;
        TextView card_name;
        ImageView btn_card_add;
        View v_item_divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.card_icon = itemView.findViewById(R.id.card_icon);
            this.card_name = itemView.findViewById(R.id.card_name);
            this.btn_card_add = itemView.findViewById(R.id.btn_card_add);
            this.v_item_divider = itemView.findViewById(R.id.v_item_divider);
        }
    }
}
