package com.c301t3.c301t3app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Henry
 * Displays all bids, and userIDs for the given task selected.
 * Handles accepting or denying a bid.
 */

public class ViewBidsActivity extends AppCompatActivity {
    public static final String FACADE_TASK = "com.c301t3.c301t3app.FACADE_TASK";

    private Task task;
    private int taskIndex;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_yes_no, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.yes:
                Toast.makeText(getApplicationContext(), "Confirm bid selected", Toast.LENGTH_SHORT).show();
                //
                bidList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                        // alert = new AlertDialog.Builder(getApplicationContext());
                        new AlertDialog.Builder(ViewBidsActivity.this)
                                //.setIcon(android.R.drawable.ic_dialog_alert)
                                .setIcon(R.drawable.circle)
                                .setTitle("Confirmation of Bid")
                                .setMessage("Are you sure you wish to confirm this bid for your task?")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();

//                                        final InfoPasser info = InfoPasser.getInstance();
//                                        Bundle bundle = new Bundle();

                                        ArrayList<Bid> newBids = new ArrayList<Bid>();
                                        Bid selectedBid = bids.get(position);
                                        newBids.add(selectedBid);
                                        task.setBids(newBids);
                                        task.setStatus(TaskStatus.ASSIGNED);

                                        String taskId = task.getId();
                                        ElasticsearchController.deleteTaskByID(taskId);
                                        ElasticsearchController.taskToServer(task);

//                                        bundle.putSerializable("ViewBidsTask", task);
//                                        bundle.putInt("ViewBidsTaskIndex", taskIndex);
//                                        info.setInfo(bundle);

                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                        return false;
                    }
                });

                break;

            case R.id.no:
                Toast.makeText(getApplicationContext(), "Remove bid selected", Toast.LENGTH_SHORT).show();
                //
                bidList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                        final View v = view;

                        // alert = new AlertDialog.Builder(getApplicationContext());
                        new AlertDialog.Builder(ViewBidsActivity.this)
                                //.setIcon(android.R.drawable.ic_dialog_alert)
                                .setIcon(R.drawable.cross)
                                .setTitle("Removal of Bid")
                                .setMessage("Are you sure you wish to remove this bid for your task?")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

                                        task.remBid(position);
                                        ArrayList<Bid> bidList = task.getBids();
                                        if (bidList.size() == 0) {
                                            task.setStatus(TaskStatus.REQUESTED);
                                        }

                                        String taskId = task.getId();
                                        ElasticsearchController.deleteTaskByID(taskId);
                                        ElasticsearchController.taskToServer(task);

                                        Intent intent = new Intent(v.getContext(), MyTasksActivity.class);
                                        intent.putExtra(FACADE_TASK, task);

                                        setResult(Activity.RESULT_OK, intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                        return false;
                    }
                });


                break;
        }

        return super.onOptionsItemSelected(item);
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

        task = (Task) bundle.getSerializable("requestedTask");
        taskIndex = bundle.getInt("requestedIndex");

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
