package com.c301t3.c301t3app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Henry on 2018-03-07.
 */

public class MyTasksActivity extends AppCompatActivity {

    private TaskPasser taskPasser;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> adapter;
    private ListView myTasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        getSupportActionBar().setHomeButtonEnabled(true);

        myTasksList = (ListView) findViewById(R.id.myTasksList);

        if (taskList == null) {
            String thing = "Thing";
            Toast.makeText(getApplicationContext(), thing, Toast.LENGTH_SHORT).show();
        }


//        ArrayList<Task> tl = new ArrayList<>();
//        Task task0 = new Task("Task0");
//        Task task1 = new Task("Task1");
//        tl.add(task1);
//        tl.add(task0);
//        tl.add(task1);
//        taskList = tl;
//        adapter.notifyDataSetChanged();
        //final TaskPasser taskPasser = new TaskPasser();
        //taskList = taskPasser.getTasks();
        //adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        taskList = new ArrayList<>();
//        Task task0 = new Task("Task0");
//        Task task1 = new Task("Task1");
//        taskList.add(task0);
//        taskList.add(task1);
        adapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_item, taskList);
        myTasksList.setAdapter(adapter);
//        taskList.add(task0);
//        adapter.notifyDataSetChanged();

        final TaskPasser taskPasser = new TaskPasser();
        taskList = taskPasser.getTasks();
        String foo = taskPasser.getTasks().toString();
        adapter.notifyDataSetChanged();

        String thing = "Bop";
        Toast.makeText(getApplicationContext(), foo, Toast.LENGTH_SHORT).show();

//        taskList = taskPasser.getTasks();
//        adapter.notifyDataSetChanged();
    }

}
