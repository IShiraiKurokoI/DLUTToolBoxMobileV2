package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.NotificationListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.NotificationManageActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private static boolean scroll = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        RecyclerView recyclerView = requireView().requireViewById(R.id.rv_notice_list);
        if (recyclerView == null) {
            return;
        }
        TextView iv_manage = requireView().requireViewById(R.id.iv_manage);
        iv_manage.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), NotificationManageActivity.class);
            startActivity(intent);
        });
        TextView tv_ReadAll = requireView().requireViewById(R.id.tv_ReadAll);
        tv_ReadAll.setOnClickListener(view2 -> {
            Dialog Dialog = new Dialog(getActivity(), R.style.CustomDialog);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.dialog_confirm_center, null);
            final TextView title = view.findViewById(R.id.title);
            title.setText("请确认");
            final TextView msg = view.findViewById(R.id.msg);
            msg.setText("是否全部已读?");
            final Button ok = view.findViewById(R.id.ok);
            ok.setOnClickListener(view1 -> {
                new MsgHistoryManager(getContext()).SetReadAll();
                Resumeinit();
                Dialog.dismiss();
            });
            final Button cancel = view.findViewById(R.id.cancel);
            cancel.setOnClickListener(view12 -> Dialog.dismiss());
            Window window = Dialog.getWindow();
            window.setContentView(view);
            window.setGravity(Gravity.CENTER);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            Dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.5f;
            getActivity().getWindow().setAttributes(lp);
            Dialog.setOnDismissListener(dialogInterface -> {
                WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
                lp1.alpha = 1f;
                getActivity().getWindow().setAttributes(lp1);
            });
            Dialog.show();
        });
        LinearLayout NoticeEmptyView = requireView().requireViewById(R.id.NoticeEmptyView);
        List<NotificationHistoryDataBaseBean> notificationPayloadhistoryList;
        try {
            notificationPayloadhistoryList = ConfigHelper.GetNotificationHistoryList(getContext());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerView.addItemDecoration(new SimplePaddingDecoration(requireContext()));

        if (notificationPayloadhistoryList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            NoticeEmptyView.setVisibility(View.VISIBLE);
        } else {
            //清除红点
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
            if(bottomNavigationView!=null)
            {
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottomNavigationView.getMenu().getItem(1).getItemId());
                badgeDrawable.setVisible(false);
                badgeDrawable.clearNumber();
            }
            List<Integer> list = ConfigHelper.GetUnreadCount(getContext());
            int unreadcount = list.get(1);

            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(requireContext()) {
                @Override
                protected int getHorizontalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }

                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            if (unreadcount > 0) {
                TextView unreadbutton = requireView().requireViewById(R.id.tv_tips_unread_new_msg);
                unreadbutton.setText(unreadcount + "条未读消息");
                unreadbutton.setVisibility(View.VISIBLE);
                unreadbutton.setOnClickListener(view1 -> {
                    smoothScroller.setTargetPosition(list.get(0));
                    Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
                    unreadbutton.setVisibility(View.GONE);
                });
            }
            recyclerView.setVisibility(View.VISIBLE);
            NoticeEmptyView.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            NotificationListAdapter notificationListAdapter = new NotificationListAdapter(getContext(), notificationPayloadhistoryList);
            recyclerView.setAdapter(notificationListAdapter);
        }
    }

    @SuppressLint("SetTextI18n")
    public void Resumeinit() {
        RecyclerView recyclerView = requireView().requireViewById(R.id.rv_notice_list);
        if (recyclerView == null) {
            return;
        }
        LinearLayout NoticeEmptyView = requireView().requireViewById(R.id.NoticeEmptyView);
        List<NotificationHistoryDataBaseBean> notificationPayloadhistoryList;
        try {
            notificationPayloadhistoryList = ConfigHelper.GetNotificationHistoryList(getContext());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (notificationPayloadhistoryList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            NoticeEmptyView.setVisibility(View.VISIBLE);
        } else {
            //清除红点
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
            if(bottomNavigationView!=null)
            {
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottomNavigationView.getMenu().getItem(1).getItemId());
                badgeDrawable.setVisible(false);
                badgeDrawable.clearNumber();
            }
            List<Integer> list = ConfigHelper.GetUnreadCount(getContext());
            int unreadcount = list.get(1);

            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(requireContext()) {
                @Override
                protected int getHorizontalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }

                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            if (unreadcount > 0) {
                TextView unreadbutton = requireView().requireViewById(R.id.tv_tips_unread_new_msg);
                unreadbutton.setText(unreadcount + "条未读消息");
                unreadbutton.setVisibility(View.VISIBLE);
                unreadbutton.setOnClickListener(view1 -> {
                    smoothScroller.setTargetPosition(list.get(0));
                    Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
                    unreadbutton.setVisibility(View.GONE);
                });
            }

            recyclerView.setVisibility(View.VISIBLE);
            NoticeEmptyView.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            NotificationListAdapter notificationListAdapter = new NotificationListAdapter(getContext(), notificationPayloadhistoryList);
            recyclerView.setAdapter(notificationListAdapter);
        }
    }

    @SuppressWarnings("ALL")
    public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

        public SimplePaddingDecoration(Context context) {
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = 60;
            outRect.right = 60;
            outRect.bottom = 60;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        if (prefs.getBoolean("unread", false)) {
           Resumeinit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}