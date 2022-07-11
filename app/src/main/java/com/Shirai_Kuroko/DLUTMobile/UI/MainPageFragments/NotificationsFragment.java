package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.NotificationListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationPayload;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

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
        LinearLayout NoticeEmptyView = requireView().requireViewById(R.id.NoticeEmptyView);
        List<NotificationPayload> notificationPayloadhistoryList = ConfigHelper.GetNotificationHistoryList(getContext());
        recyclerView.addItemDecoration(new SimplePaddingDecoration(requireContext()));

        if (notificationPayloadhistoryList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            NoticeEmptyView.setVisibility(View.VISIBLE);
        } else {
            //清除红点
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottomNavigationView.getMenu().getItem(1).getItemId());
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();
            int unreadcount = prefs.getInt("unreadcount", 0);
            prefs.edit().putBoolean("unread", false).apply();
            prefs.edit().putInt("unreadcount", 0).apply();

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
                    smoothScroller.setTargetPosition(notificationPayloadhistoryList.size() - unreadcount);
                    Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
                    unreadbutton.setVisibility(View.GONE);
                });
            }

            recyclerView.setVisibility(View.VISIBLE);
            NoticeEmptyView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            NotificationListAdapter notificationListAdapter = new NotificationListAdapter(getContext(), notificationPayloadhistoryList);
            recyclerView.setAdapter(notificationListAdapter);
            recyclerView.scrollToPosition(notificationListAdapter.getItemCount() - 1);
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
            outRect.bottom = 70;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        if (prefs.getBoolean("unread", false)) {
            init();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}