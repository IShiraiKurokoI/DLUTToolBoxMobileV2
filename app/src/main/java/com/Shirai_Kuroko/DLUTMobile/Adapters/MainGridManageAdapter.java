package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ContextHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainGridManageAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private ArrayList<ID> mDatas;

    public MainGridManageAdapter(Context context, ArrayList<ID> datas) {
        mContext = context;
        mDatas = datas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void datarefresh() {
        mDatas = ConfigHelper.GetGridIDList(mContext);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drag_grid_item, null);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            ApplicationConfig ac = ConfigHelper.Getmlist(ContextHelper.getContext()).get(mDatas.get(position).getId());
            Activity activity = (Activity) mContext;
            holder.name.setText(ac.getAppName());
            Glide.with(ContextHelper.getContext()).load(ac.getIcon()).into(holder.icon);
            holder.delete.setOnClickListener(v -> {
                Dialog Dialog = new Dialog(activity, R.style.CustomDialog);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(
                        R.layout.dialog_confirm_center, null);
                final TextView title = view.findViewById(R.id.title);
                title.setText("请确认");
                final TextView msg = view.findViewById(R.id.msg);
                msg.setText("是否移除" + ac.getAppName() + "?");
                final Button ok = view.findViewById(R.id.ok);
                ok.setOnClickListener(view1 -> {
                    ConfigHelper.removesubscription(mContext, ac.getId());
                    mDatas = ConfigHelper.GetGridIDList(mContext);
                    notifyItemRemoved(viewHolder.getAdapterPosition());
                    Dialog.dismiss();
                });
                final Button cancel = view.findViewById(R.id.cancel);
                cancel.setOnClickListener(view12 -> Dialog.dismiss());
                Window window = Dialog.getWindow();
                window.setContentView(view);
                window.setGravity(Gravity.CENTER);
                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                Dialog.setCanceledOnTouchOutside(false);
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 0.5f;
                activity.getWindow().setAttributes(lp);
                Dialog.setOnDismissListener(dialogInterface -> {
                    WindowManager.LayoutParams lp1 = activity.getWindow().getAttributes();
                    lp1.alpha = 1f;
                    activity.getWindow().setAttributes(lp1);
                });
                Dialog.show();
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
