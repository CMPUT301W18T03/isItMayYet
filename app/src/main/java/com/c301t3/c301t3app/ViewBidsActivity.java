package com.c301t3.c301t3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewBidsActivity extends AppCompatActivity {
    private ArrayList<Bid> bids;
    private ArrayAdapter<Bid> bidAdapter;
    private ListView bidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        getSupportActionBar().setHomeButtonEnabled(true);

        bidList = (ListView) findViewById(R.id.ListView_BidList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromInfoPasser();

        bidAdapter = new ArrayAdapter<Bid>(this, R.layout.view_bids_item, bids);

        bidList.setAdapter(bidAdapter);

    }

    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        Bundle bundle = info.getInfo();

        try {
            Task task = (Task) bundle.getSerializable("assignedTask");
            bids = task.getBids();
        } catch (NullPointerException e) {
            bids = new ArrayList<Bid>();
        }

    }


}
