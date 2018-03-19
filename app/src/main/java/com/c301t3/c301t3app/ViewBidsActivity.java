package com.c301t3.c301t3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        bidAdapter = new ArrayAdapter<Bid>(this, R.layout.my_tasks_requested, bids);

        bidList.setAdapter(bidAdapter);

    }

    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        Bundle bundle = info.getInfo();

        Task task = (Task) bundle.getSerializable("requestedTask");

        try {
            // Toast.makeText(getApplicationContext(), task.toString(), Toast.LENGTH_SHORT).show();
            //ArrayList<Bid> b = task.getBids();
            bids = task.getBids();

        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            bids = new ArrayList<Bid>();
        }

    }


}
