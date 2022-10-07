package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.CommentDetailResultBean;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.net.URLDecoder;
import java.util.List;

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.ViewHolder> {
    private final Context mContext;
    private List<CommentDetailResultBean.DataDTO.ListDataDTO> mDatas;

    public CommentDetailAdapter(Context context, List<CommentDetailResultBean.DataDTO.ListDataDTO> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh(List<CommentDetailResultBean.DataDTO.ListDataDTO> arrayList) {
        mDatas = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_user_comment_lv_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentDetailResultBean.DataDTO.ListDataDTO listDataDTO = mDatas.get(position);
        holder.tv_app_comment_name.setText(listDataDTO.getUser_info().getName());
        holder.app_comment_time.setText(listDataDTO.getAdd_time_format());
        holder.app_comment_content.setText(URLDecoder.decode(listDataDTO.getComment()));
        holder.app_rating.setRating(listDataDTO.getScore().floatValue());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_app_comment_name;
        TextView app_comment_time;
        RatingBar app_rating;
        TextView app_comment_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_app_comment_name = itemView.findViewById(R.id.tv_app_comment_name);
            this.app_comment_time = itemView.findViewById(R.id.app_comment_time);
            this.app_rating = itemView.findViewById(R.id.app_rating);
            this.app_comment_content = itemView.findViewById(R.id.app_comment_content);
        }
    }
}
