package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class myTasksActivity extends AppCompatActivity {

    private TaskList tasklist = new TaskList();
    private ArrayList<Task> taskarraylist = tasklist.getTaskList();
    private ArrayAdapter<Task> adapter;
    private ListView listview_tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listview_tasklist = (ListView) findViewById(R.id.myTasksList);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Task t0 = new Task("ting", "descr", "yo");
        Task t1 = new Task("what", "esle", "wsq");
        Task t2 = new Task("qewa", "pew", "pwo");


//        adapter = new ArrayAdapter<Task>(this,
//                R.layout.my_tasks_item, R.id.TextView_TaskItem, taskarraylist);
        adapter = new ArrayAdapter<Task>(this,
                R.layout.task_item, taskarraylist);
        listview_tasklist.setAdapter(adapter);

        taskarraylist.add(t0);
        taskarraylist.add(t1);
        taskarraylist.add(t2);
        adapter.notifyDataSetChanged();

    }

}
