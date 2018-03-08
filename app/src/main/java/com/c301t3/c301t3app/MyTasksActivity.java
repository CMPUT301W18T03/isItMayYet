package com.c301t3.c301t3app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Henry on 2018-03-07.
 */

public class MyTasksActivity extends AppCompatActivity {

    private final TaskPasser taskPasser = new TaskPasser();
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> adapter;
    private ListView myTasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        getSupportActionBar().setHomeButtonEnabled(true);

        myTasksList = (ListView) findViewById(R.id.myTasksList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_item, taskList);
        myTasksList.setAdapter(adapter);
        taskList = taskPasser.getTasks();
        adapter.notifyDataSetChanged();
    }

}
