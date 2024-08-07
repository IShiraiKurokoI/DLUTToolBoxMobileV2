package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ScoreDetailResult;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreDetailAdapter extends RecyclerView.Adapter<ScoreDetailAdapter.ViewHolder> {
    private final Context mContext;
    private List<ScoreDetailResult.DataDTO.ListDTO> mDatas;
    private String prefix = "+";
    private RecyclerView recyclerView;
    private LinearLayout emptyview;

    public ScoreDetailAdapter(Context context, ArrayList<ScoreDetailResult.DataDTO.ListDTO> datas, RecyclerView recyclerView, LinearLayout emptyview) {
        mContext = context;
        mDatas = datas;
        this.recyclerView =recyclerView;
        this.emptyview = emptyview;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<ScoreDetailResult.DataDTO.ListDTO> arrayList) {
        mDatas = arrayList;
        if(mDatas.isEmpty())
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

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_score,viewGroup,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task_name.setText(mDatas.get(position).getReason());
        holder.task_time.setText(new Date(Long.parseLong(mDatas.get(position).getTime())*1000).toLocaleString());
        holder.task_in_out.setText(prefix+mDatas.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView task_name;
        TextView task_time;
        TextView task_in_out;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.task_name = itemView.findViewById(R.id.task_name);
            this.task_time = itemView.findViewById(R.id.task_time);
            this.task_in_out = itemView.findViewById(R.id.task_in_out);
        }
    }
}
