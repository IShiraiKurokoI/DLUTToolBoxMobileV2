package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ContextHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.GridAppID;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private ArrayList<GridAppID> mDatas;

    public RecyclerViewAdapter(Context context, ArrayList<GridAppID> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void datarefresh()
    {
        mDatas = ConfigHelper.GetGridIDList(mContext);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drag_grid_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder)viewHolder;
            ApplicationConfig ac =ConfigHelper.getmlist(ContextHelper.getContext()).get(mDatas.get(position).getId());
            holder.name.setText(ac.getAppName());
            Glide.with(ContextHelper.getContext()).load(ac.getIcon()).into(holder.icon);
            holder.delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogCustom);
                builder.setTitle("是否移除"+ac.getAppName()+"?");
                builder.setPositiveButton("确定移除", (dialog, which) -> {
                        ConfigHelper.removesubscription(mContext, ac.getId());
                        mDatas = ConfigHelper.GetGridIDList(mContext);
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                });
                builder.setNegativeButton("取消", (dialog, which) -> {

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name_tv);
            this.icon = itemView.findViewById(R.id.icon_iv);
            this.delete = itemView.findViewById(R.id.delet_iv);
        }
    }
}
