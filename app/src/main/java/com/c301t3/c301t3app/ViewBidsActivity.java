package com.c301t3.c301t3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
