package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.CardManageListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Entities.ID;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.R;

import java.util.ArrayList;

public class CardManageActivity extends AppCompatActivity {

    private CardManageListAdapter cardManageListAdapter;
    private CardManageListAdapter cardManageListAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        TextView Return = findViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setOnClickListener(view -> {
            Intent intent = new Intent(this, CardSortActivity.class);
            startActivity(intent);
        });
        ArrayList<ID> IDS = ConfigHelper.GetCardIDList(this);
        LinearLayout emptyView = findViewById(R.id.head_empty_view);
        RecyclerView Added_Card = findViewById(R.id.Added_Card);
        Added_Card.setLayoutManager(new LinearLayoutManager(this));
        cardManageListAdapter = new CardManageListAdapter(this, IDS, Added_Card, emptyView, 0, this);
        Added_Card.setAdapter(cardManageListAdapter);
        ArrayList<ID> IDS1 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (!IDS.contains(new ID(i))) {
                IDS1.add(new ID(i));
            }
        }
        RecyclerView Notadded_Card = findViewById(R.id.Notadded_Card);
        Notadded_Card.setLayoutManager(new LinearLayoutManager(this));
        cardManageListAdapter1 = new CardManageListAdapter(this, IDS1, Notadded_Card, null, 1, this);
        Notadded_Card.setAdapter(cardManageListAdapter1);
    }

    public void CallUpdate() {
        ArrayList<ID> IDS = ConfigHelper.GetCardIDList(this);
        ArrayList<ID> IDS1 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (!IDS.contains(new ID(i))) {
                IDS1.add(new ID(i));
            }
        }
        cardManageListAdapter.datarefresh(IDS);
        cardManageListAdapter1.datarefresh(IDS1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallUpdate();
    }
}