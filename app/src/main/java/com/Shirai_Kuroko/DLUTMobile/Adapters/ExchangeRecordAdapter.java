package com.Shirai_Kuroko.DLUTMobile.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.ExchangeRecordActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
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
        Glide.with(mContext).load(listDTO.getImage().get(0)).into(holder.icon_gift);
        int is_exchange = listDTO.getIs_exchange();
        int id = Integer.parseInt(listDTO.getId());
        Log.d("TAG", "onBindViewHolder: "+listDTO);
        switch (is_exchange){
            case 0:{
                holder.cancel_text.setVisibility(View.VISIBLE);
                holder.iv_out_date.setVisibility(View.GONE);
                holder.record_time.setText(new Date(listDTO.getCreate_time()*1000).toLocaleString());
                holder.cancel_text.setOnClickListener(view -> {
                    Dialog Dialog = new Dialog(mContext, R.style.CustomDialog);
                    @SuppressLint("InflateParams") View view1 = LayoutInflater.from(mContext).inflate(
                            R.layout.dialog_confirm_center, null);
                    final TextView title = view1.findViewById(R.id.title);
                    title.setText("请确认");
                    final TextView msg = view1.findViewById(R.id.msg);
                    msg.setText("是否撤销" + listDTO.getName() + "的兑换?");
                    final Button ok = view1.findViewById(R.id.ok);
                    ok.setOnClickListener(view2 -> {
                        BackendUtils.cancelGiftExchange(mContext,id);
                        Dialog.dismiss();
                    });
                    final Button cancel = view1.findViewById(R.id.cancel);
                    cancel.setOnClickListener(view12 -> Dialog.dismiss());
                    Window window = Dialog.getWindow();
                    window.setContentView(view1);
                    window.setGravity(Gravity.CENTER);
                    window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                            android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                    Dialog.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lp = ((ExchangeRecordActivity)mContext).getWindow().getAttributes();
                    lp.alpha = 0.5f;
                    ((ExchangeRecordActivity)mContext).getWindow().setAttributes(lp);
                    Dialog.setOnDismissListener(dialogInterface -> {
                        WindowManager.LayoutParams lp1 = ((ExchangeRecordActivity)mContext).getWindow().getAttributes();
                        lp1.alpha = 1f;
                        ((ExchangeRecordActivity)mContext).getWindow().setAttributes(lp1);
                    });
                    Dialog.show();
                });
                break;
            }
            case 2:{
                holder.cancel_text.setVisibility(View.GONE);
                holder.iv_out_date.setVisibility(View.VISIBLE);
                holder.iv_out_date.setImageResource(R.drawable.icon_out_date);
                holder.cancel_text.setOnClickListener(null);
                holder.record_time.setText(new Date(listDTO.getEnd_time()*1000).toLocaleString());
                break;
            }
            case 3:{
                holder.cancel_text.setVisibility(View.GONE);
                holder.iv_out_date.setVisibility(View.VISIBLE);
                holder.iv_out_date.setImageResource(R.drawable.mark_gift_exchange_receive);
                holder.cancel_text.setOnClickListener(null);
                holder.record_time.setText(new Date(listDTO.getExchange_time()*1000).toLocaleString());
                break;
            }
            case 4:{
                holder.cancel_text.setVisibility(View.GONE);
                holder.iv_out_date.setVisibility(View.VISIBLE);
                holder.iv_out_date.setImageResource(R.drawable.icon_gift_exchange_undo);
                holder.cancel_text.setOnClickListener(null);
                holder.record_time.setText(new Date(listDTO.getExchange_time()*1000).toLocaleString());
                break;
            }
            default:{
                //俺也不知道is_exchange为1时这该显示啥
                break;
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
        TextView cancel_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.icon_gift = itemView.findViewById(R.id.icon_gift);
            this.gift_name = itemView.findViewById(R.id.gift_name);
            this.record_time = itemView.findViewById(R.id.record_time);
            this.iv_out_date = itemView.findViewById(R.id.iv_out_date);
            this.cancel_text = itemView.findViewById(R.id.cancel_text);
        }
    }
}
