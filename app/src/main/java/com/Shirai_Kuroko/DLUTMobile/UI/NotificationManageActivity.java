package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.NotificationManageListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments.NotificationsFragment;
import com.Shirai_Kuroko.DLUTMobile.Utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationManageActivity extends AppCompatActivity {
    NotificationManageListAdapter notificationListAdapter;
    RecyclerView recyclerView;
    List<NotificationHistoryDataBaseBean> notificationPayloadhistoryList;
    List<NotificationHistoryDataBaseBean> Temp;
    List<NotificationHistoryDataBaseBean> SelectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Temp = new ArrayList<>();
        SelectedList = new ArrayList<>();
        setContentView(R.layout.activity_notification_manage);
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_more = requireViewById(R.id.tv_more);
        tv_more.setOnClickListener(this::showPopupWindow);
        recyclerView = requireViewById(R.id.rv_notice_list);
        LinearLayout NoticeEmptyView = requireViewById(R.id.NoticeEmptyView);
        try {
            notificationPayloadhistoryList = ConfigHelper.GetNotificationHistoryList(this);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerView.addItemDecoration(new NotificationsFragment.SimplePaddingDecoration(this));
        if (notificationPayloadhistoryList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            NoticeEmptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            NoticeEmptyView.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            notificationListAdapter = new NotificationManageListAdapter(this, notificationPayloadhistoryList, SelectedList);
            recyclerView.setAdapter(notificationListAdapter);
        }
    }

    public void AddSelected(NotificationHistoryDataBaseBean bean) {
        if (!SelectedList.contains(bean)) {
            SelectedList.add(bean);
        }
    }

    public void RemoveSelected(NotificationHistoryDataBaseBean bean) {
        SelectedList.remove(bean);
    }


    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(this).inflate(R.layout.popup_notification_manage_right_more, null);
        int[] size = UIUtils.calculateDpiSize(this,360,750);
        PopupWindow window = new PopupWindow(v, size[0], size[1], true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setAnimationStyle(R.style.main_more_anim);
        window.showAsDropDown(view, 0, 0);
        v.findViewById(R.id.btn_delete).setOnClickListener(view1 -> {
            Dialog Dialog = new Dialog(this, R.style.CustomDialog);
            @SuppressLint("InflateParams") View view3 = LayoutInflater.from(this).inflate(
                    R.layout.dialog_confirm_center, null);
            final TextView title = view3.findViewById(R.id.title);
            title.setText("请确认");
            final TextView msg = view3.findViewById(R.id.msg);
            msg.setText("是否删除选中的消息?");
            final Button ok = view3.findViewById(R.id.ok);
            ok.setOnClickListener(view2 -> {
                for (NotificationHistoryDataBaseBean notificationHistoryDataBaseBean : SelectedList) {
                    Log.i("消息管理", "删除消息: " + notificationHistoryDataBaseBean.getTitle());
                    new MsgHistoryManager(this).Remove(notificationHistoryDataBaseBean.get_id());
                }
                SelectedList = new ArrayList<>();
                try {
                    notificationPayloadhistoryList = ConfigHelper.GetNotificationHistoryList(this);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                notificationListAdapter = new NotificationManageListAdapter(this, notificationPayloadhistoryList, SelectedList);
                recyclerView.setAdapter(notificationListAdapter);
                Toast.makeText(this, "删除成功!", Toast.LENGTH_LONG).show();
                Dialog.dismiss();
            });
            final Button cancel = view3.findViewById(R.id.cancel);
            cancel.setOnClickListener(view12 -> Dialog.dismiss());
            Window window2 = Dialog.getWindow();
            window2.setContentView(view3);
            window2.setGravity(Gravity.CENTER);
            window2.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            Dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.alpha = 0.5f;
            this.getWindow().setAttributes(lp);
            Dialog.setOnDismissListener(dialogInterface -> {
                WindowManager.LayoutParams lp1 = this.getWindow().getAttributes();
                lp1.alpha = 1f;
                this.getWindow().setAttributes(lp1);
            });
            Dialog.show();
            window.dismiss();
        });
        v.findViewById(R.id.btn_select_all).setOnClickListener(view12 -> {
            SelectedList = new ArrayList<>();
            SelectedList.addAll(notificationPayloadhistoryList);
            notificationListAdapter.UpdateSelected(SelectedList);
            window.dismiss();
        });
        v.findViewById(R.id.btn_reverse).setOnClickListener(view13 -> {
            Temp = new ArrayList<>();
            Temp.addAll(notificationPayloadhistoryList);
            for (NotificationHistoryDataBaseBean notificationHistoryDataBaseBean : SelectedList) {
                Temp.remove(notificationHistoryDataBaseBean);
            }
            SelectedList = new ArrayList<>();
            SelectedList.addAll(Temp);
            notificationListAdapter.UpdateSelected(SelectedList);
            window.dismiss();
        });
        v.findViewById(R.id.btn_cancel).setOnClickListener(view14 -> {
            SelectedList = new ArrayList<>();
            notificationListAdapter.UpdateSelected(SelectedList);
            window.dismiss();
        });
    }
}