package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Shirai_Kuroko.DLUTMobile.Adapters.MainCardAdapter;
import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.Configurations.QRCodeScanConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.CustomMNScanManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.ApiQRLoginActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.CardManageActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.BrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginConfirmActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.OpenVirtualCardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.SearchActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement.AppGridManageActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.Shirai_Kuroko.DLUTMobile.Utils.UIUtils;
import com.bumptech.glide.Glide;
import com.maning.mlkitscanner.scan.MNScanManager;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    MainGridAdapter adapter;
    public MainCardAdapter mainCardAdapter;
    ArrayList<ID> prevlist = new ArrayList<>();

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
        mgv.setNestedScrollingEnabled(false);
        Banner banner = requireView().findViewById(R.id.banner);
        if (banner != null) {
            //设置宽高
            int ratioWidth = 752;
            int ratioHeight = 376;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int calculatedHeight = (screenWidth * ratioHeight) / ratioWidth;
            ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = calculatedHeight;
            banner.setLayoutParams(layoutParams);
            //加载轮播
            MobileUtils.GetGalllery(requireActivity(), banner);
        }

        ImageView imageView = requireView().findViewById(R.id.iv_search);
        imageView.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            startActivity(intent);
        });
        ImageView iv_more = requireView().findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this::showPopupWindow);
        RecyclerView CollectionCard = requireView().findViewById(R.id.CollectionCard);
        CollectionCard.setLayoutManager(new LinearLayoutManager(requireActivity()));
        prevlist = ConfigHelper.GetCardIDList(getContext());
        mainCardAdapter = new MainCardAdapter(getContext(), prevlist, this);
        mainCardAdapter.setHasStableIds(true);
        CollectionCard.setAdapter(mainCardAdapter);
        CollectionCard.setNestedScrollingEnabled(false);
        LinearLayout ll_main_manager_card = requireView().findViewById(R.id.ll_main_manager_card);
        ll_main_manager_card.setOnClickListener(view12 -> {
            Intent intent = new Intent(requireContext(), CardManageActivity.class);
            startActivity(intent);
        });
        SwipeRefreshLayout swipeRefreshLayout = requireView().findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CallReload();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void showPopupWindow(View view) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.popup_main_right_more, null);

        int[] size = UIUtils.calculateDpiSize(getContext(),350,450);
        PopupWindow window = new PopupWindow(v, size[0], size[1], true);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setAnimationStyle(R.style.main_more_anim);
        window.showAsDropDown(view, 0, 0);
        v.findViewById(R.id.btn_start_qr).setOnClickListener(v1 -> {
            CustomMNScanManager.startScan(requireActivity(), QRCodeScanConfig.scanConfig, (resultCode, data) -> {
                switch (resultCode) {
                    case MNScanManager.RESULT_SUCCESS:
                        ArrayList<String> results = data.getStringArrayListExtra(MNScanManager.INTENT_KEY_RESULT_SUCCESS);
                        String resultTxt = results.get(0);
                        Log.i("扫码结果", resultTxt);
                        if (resultTxt.contains("whistle_info")) {
                            Intent intent1 = new Intent(requireContext(), ApiQRLoginActivity.class);
                            intent1.putExtra("whistle_info", resultTxt);
                            startActivity(intent1);
                        } else if (resultTxt.contains("qrLogin")) {
                            Intent intent1 = new Intent(requireContext(), LoginConfirmActivity.class);
                            intent1.putExtra("UUID", resultTxt);
                            startActivity(intent1);
                        } else if (resultTxt.startsWith("http") || resultTxt.startsWith("www")) {
                            Intent intent1 = new Intent(requireContext(), PureBrowserActivity.class);
                            intent1.putExtra("Name", "");
                            intent1.putExtra("Url", resultTxt);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(requireContext(), "无法解析的二维码", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                        }
                        break;
                    case MNScanManager.RESULT_FAIL:
                        String resultError = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_ERROR);
                        showToast(resultError);
                        break;
                    case MNScanManager.RESULT_CANCLE:
                        break;
                }
            });
            window.dismiss(); //控制弹窗消失
        });
        v.findViewById(R.id.btn_start_virtual_card).setOnClickListener(v12 -> {
            Intent intent = new Intent(requireContext(), OpenVirtualCardActivity.class);
            startActivity(intent);
            window.dismiss();
        });
        v.findViewById(R.id.btn_main_card_manage_ban).setOnClickListener(v12 -> {
            Intent intent = new Intent(requireContext(), CardManageActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void CallRemoveAndUpdate(int Position) {
        if (Position != 0) {
            prevlist = (ArrayList<ID>) mainCardAdapter.RemovePostion(Position);
        } else {
            prevlist = null;
            CallRefresh();
        }
    }

    public void CallRefresh() {
        if (mainCardAdapter != null) {
            if (!ConfigHelper.GetCardIDList(getContext()).equals(prevlist)) {
                prevlist = ConfigHelper.GetCardIDList(getContext());
                mainCardAdapter.datarefresh(prevlist);
            }
        }
    }

    public void CallReload() {
        RecyclerView CollectionCard = requireView().findViewById(R.id.CollectionCard);
        prevlist = ConfigHelper.GetCardIDList(getContext());
        mainCardAdapter = new MainCardAdapter(getContext(), prevlist, this);
        mainCardAdapter.setHasStableIds(true);
        CollectionCard.setAdapter(mainCardAdapter);
        CenterToast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
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
        CallRefresh();
    }
}