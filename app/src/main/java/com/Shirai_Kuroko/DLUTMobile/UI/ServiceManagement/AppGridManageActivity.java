package com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Entities.GridAppID;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ContextHelper;
import com.Shirai_Kuroko.DLUTMobile.Adapters.RecyclerViewAdapter;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;

public class AppGridManageActivity extends AppCompatActivity {

    private RecyclerViewAdapter mAdapter;
    private ArrayList<GridAppID> mDatas = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_app_grid_manage);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("管理我的服务");
        }
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Button btn = requireViewById(R.id.add_more);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AppcenterActivity.class);
            startActivity(intent);
        });
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.ManageGrid);
        mDatas = ConfigHelper.GetGridIDList(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new RecyclerViewAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags;
                int swipeFlags;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                mDatas = ConfigHelper.GetGridIDList(mContext);
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                String json = JSON.toJSONString(mDatas);
                ConfigHelper.SaveGridPrefJson(ContextHelper.getContext(), json);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.datarefresh();
    }
}
