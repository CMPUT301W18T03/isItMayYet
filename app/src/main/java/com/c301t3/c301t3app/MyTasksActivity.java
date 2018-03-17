package com.c301t3.c301t3app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Henry on 07/03/18.
 * Class that displays all the tasks that the user has, which
 * primarily displays a summary of the information of all tasks
 * and gives the user an opportunity to view more details of each
 * of the tasks.
 *
 * NOTE:
 * ASSUMES THAT AN ARRAYLIST WILL BE PROVIDED VIA TASKPASSER FOR IT
 * TO FUNCTION CORRECTLY, NULL ENTRIES CANNOT BE ALLOWED.
 *
 * @author Henry
 * @version 3.0
 */
public class MyTasksActivity extends AppCompatActivity {
    // All the variables that hold data
    // private TaskPasser taskPasser;
//    private ArrayList<Task> taskList;
//    private ArrayAdapter<Task> adapter;
//    private ListView myTasksList;

    private ArrayList<Task> assignedTaskList;
    private ArrayList<Task> requestedTaskList;
    private ArrayAdapter<Task> assignedAdapter;
    private ArrayAdapter<Task> requestedAdapter;
    private ListView assignedTasks;
    private ListView requestedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        getSupportActionBar().setHomeButtonEnabled(true);

//        myTasksList = (ListView) findViewById(R.id.myTasksList);

        assignedTasks = (ListView) findViewById(R.id.ListView_assignedTasks);
        requestedTasks = (ListView) findViewById(R.id.ListView_requestedTasks);

//        if (taskList == null) {
//            String thing = "Thing";
//            Toast.makeText(getApplicationContext(), thing, Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        assignedTaskList = new ArrayList<Task>();
        requestedTaskList = new ArrayList<Task>();

        Task task0 = new Task("task0");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        assignedTaskList.add(task0);
        assignedTaskList.add(task1);
        assignedTaskList.add(task2);

        Task taskx = new Task("taskx");
        Task tasky = new Task("tasky");
        Task taskz = new Task("taskz");
        requestedTaskList.add(taskx);
        requestedTaskList.add(tasky);
        requestedTaskList.add(taskz);

        assignedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_assigned, assignedTaskList);
        requestedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_requested, requestedTaskList);

        assignedTasks.setAdapter(assignedAdapter);
        requestedTasks.setAdapter(requestedAdapter);

//        final TaskPasser taskPasser = new TaskPasser();
//        ArrayList<Task> taskList = taskPasser.getTasks();
//        if (taskList != null) {
//            this.taskList = taskList;
//        } else {
//            this.taskList = new ArrayList<Task>();
//        }
//        Task task0 = new Task("task0");
//        Task task1 = new Task("task1");
//        Task task2 = new Task("task2");
//        taskList.add(task0);
//        taskList.add(task1);
//        taskList.add(task2);
//
//        adapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_item, taskList);
//        myTasksList.setAdapter(adapter);

//        final TaskPasser taskPasser = new TaskPasser();
//        taskList = taskPasser.getTasks();
//        adapter.notifyDataSetChanged();
//        final TaskPasser taskPasser = new TaskPasser();
////        String thing = taskPasser.getTasks().toString();
//        ArrayList<Task> mytasks = taskPasser.getTasks();
//        taskList = mytasks;
//        adapter.notifyDataSetChanged();
//        String thing = "Boop";
//        Toast.makeText(getApplicationContext(), thing, Toast.LENGTH_SHORT).show();

    }

}
