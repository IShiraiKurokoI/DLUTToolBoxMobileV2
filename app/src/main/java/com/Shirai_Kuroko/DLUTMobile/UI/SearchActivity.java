package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.SearchAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.ApplicationConfig;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.SearchBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Widgets.AnanEditText;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public AnanEditText a;
    public SearchAdapter searchAdapter;
    public List<SearchBean> searchBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<ApplicationConfig> applicationConfigList = ConfigHelper.Getmlist(this);
        List<NotificationHistoryDataBaseBean> notificationHistoryDataBaseBeanList = ConfigHelper.GetNotificationHistoryList(this);
        Collections.reverse(notificationHistoryDataBaseBeanList);
        searchBeanList = new ArrayList<>();
        for (ApplicationConfig applicationConfig:applicationConfigList)
        {
            searchBeanList.add(new SearchBean(false,null,applicationConfig));
        }
        for (NotificationHistoryDataBaseBean notificationHistoryDataBaseBean:notificationHistoryDataBaseBeanList)
        {
            searchBeanList.add(new SearchBean(true,notificationHistoryDataBaseBean,null));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        this.a = this.findViewById(R.id.item_search_et);
        RecyclerView recyclerView = this.findViewById(R.id.SearchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this,new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);
        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                Log.i("搜索内容", query);
                List<SearchBean> Result = new ArrayList<>();
                if (!query.replace(" ", "").equals(""))
                {
                    for (SearchBean searchBean:searchBeanList)
                    {
                        if (searchBean.getisnotification())
                        {
                            if (URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getTitle()).contains(query))
                            {
                                Result.add(searchBean);
                                continue;
                            }
                            if (!URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getDescription()).replace(" ", "").equals(""))
                            {
                                if(URLDecoder.decode(searchBean.notificationHistoryDataBaseBean.getMsg_content().getDescription()).contains(query))
                                {
                                    Result.add(searchBean);
                                }
                            }
                        }
                        else
                        {
                            if (searchBean.applicationConfig.getAppName().contains(query)||searchBean.applicationConfig.getDescribe().contains(query))
                            {
                                Result.add(searchBean);
                            }
                        }
                    }
                }
                searchAdapter.DataRefresh(Result);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //根据判断关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }
}