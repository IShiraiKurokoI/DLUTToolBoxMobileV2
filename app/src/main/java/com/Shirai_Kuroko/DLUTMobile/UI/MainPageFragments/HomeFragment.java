package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.CardManageActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.OpenVirtualCardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ScanQRCodeActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.SearchActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement.AppGridManageActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.util.List;

public class HomeFragment extends Fragment {

    MainGridAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressWarnings("ALL")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView mgv = requireView().findViewById(R.id.main_grid);
        List<ID> IDS = ConfigHelper.GetGridIDList(requireContext());
        IDS.add(new ID(-1));
        adapter = new MainGridAdapter(IDS);
        if (mgv != null) {
            mgv.setAdapter(adapter);
        } else {
            Toast.makeText(requireActivity(), "错误：未检测到主界面GridView", Toast.LENGTH_SHORT).show();
        }

        Banner banner = requireView().findViewById(R.id.banner);
        if (banner != null) {
            MobileUtils.GetGalllery(requireActivity(), banner);
        }

        ImageView imageView = requireView().findViewById(R.id.iv_search);
        imageView.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            startActivity(intent);
        });
        ImageView iv_more = requireView().findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this::showPopupWindow);
        //TODO:主页卡片实现
        RecyclerView CollectionCard = requireView().findViewById(R.id.CollectionCard);
        CollectionCard.setLayoutManager(new LinearLayoutManager(requireActivity()));

        LinearLayout ll_main_manager_card = requireView().findViewById(R.id.ll_main_manager_card);
        ll_main_manager_card.setOnClickListener(view12 -> {
            Intent intent = new Intent(requireContext(), CardManageActivity.class);
            startActivity(intent);
        });
    }

    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.popup_main_right_more, null);
        PopupWindow window = new PopupWindow(v, 360, 300, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setAnimationStyle(R.style.main_more_anim);
        window.showAsDropDown(view, 0, 0);
        v.findViewById(R.id.btn_start_qr).setOnClickListener(v1 -> {
            Intent intent = new Intent(requireContext(), ScanQRCodeActivity.class);
            startActivity(intent);
            window.dismiss(); //控制弹窗消失
        });
        v.findViewById(R.id.btn_start_virtual_card).setOnClickListener(v12 -> {
            Intent intent = new Intent(requireContext(), OpenVirtualCardActivity.class);
            startActivity(intent);
            window.dismiss();
        });
    }



    public class MainGridAdapter extends BaseAdapter {
        private List<ID> mList;

        public MainGridAdapter(List<ID> List) {
            this.mList = List;
        }

        public void showallgriditems(List<ID> List) {
            mList = List;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridItemView gridItemView;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.main_grid_item, null);
                // 实例化一个封装类GridItemView，并实例化它的域
                gridItemView = new GridItemView();
                gridItemView.Icon = convertView.findViewById(R.id.main_grid_icon);
                gridItemView.Name = convertView.findViewById(R.id.main_grid_name);
                // 将GridItemView对象传递给convertView
                convertView.setTag(gridItemView);
            } else {
                // 从convertView中获取ListItemView对象
                gridItemView = (GridItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            int id = mList.get(position).getId();
            if (id != -1) {
                convertView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), BrowserActivity.class);
                    intent.putExtra("App_ID", String.valueOf(id));
                    startActivity(intent);
                });
                // 将资源传递给GridItemView的两个域对象
                Glide.with(requireActivity()).load(ConfigHelper.Getmlist(requireContext()).get(id).getIcon()).into(gridItemView.Icon);
                gridItemView.Name.setText(ConfigHelper.Getmlist(requireContext()).get(id).getAppName());
            } else {
                convertView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), AppGridManageActivity.class);
                    startActivity(intent);
                });
                Glide.with(requireActivity()).load(R.drawable.app_center_pre).into(gridItemView.Icon);
                gridItemView.Name.setText("管理服务");
            }
            return convertView;
        }
    }

    static class GridItemView {
        ImageView Icon;
        TextView Name;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            List<ID> IDS = ConfigHelper.GetGridIDList(requireContext());
            IDS.add(new ID(-1));
            adapter.showallgriditems(IDS);
        }
    }
}