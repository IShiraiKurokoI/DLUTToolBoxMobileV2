package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ADBannerBean;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ADBannerAdapter extends BannerAdapter<ADBannerBean, ADBannerAdapter.BannerViewHolder> {

    private Context mContext;

    public ADBannerAdapter(List<ADBannerBean> mDatas, Context mContext) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.mContext = mContext;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, ADBannerBean data, int position, int size) {
        Glide.with(mContext).load(data.getPicUrl()).into(holder.imageView);
        if (!data.getUrl().isEmpty()){
            holder.imageView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, PureBrowserActivity.class);
                    intent.putExtra("Name", "");
                    intent.putExtra("Url", data.getUrl());
                    mContext.startActivity(intent);
            });
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
