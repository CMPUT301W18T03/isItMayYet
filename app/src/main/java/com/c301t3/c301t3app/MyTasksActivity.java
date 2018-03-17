package com.c301t3.c301t3app;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    public static final String TITLE_ASSIGNED_TASKS = "Assigned Tasks";
    public static final String TITLE_REQUESTED_TASKS = "Requested Tasks";

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

        assignedTasks = (ListView) findViewById(R.id.ListView_assignedTasks);
        requestedTasks = (ListView) findViewById(R.id.ListView_requestedTasks);

        TextView assignedTaskTextView = (TextView) findViewById(R.id.TextView_assignedTasksTitle);
        TextView requestedTaskTextView = (TextView) findViewById(R.id.TextView_requestedTasksTitle);

        assignedTaskTextView.setText(TITLE_ASSIGNED_TASKS);
        requestedTaskTextView.setText(TITLE_REQUESTED_TASKS);

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromInfoPasser();

        assignedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_assigned, assignedTaskList);
        requestedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_requested, requestedTaskList);

        assignedTasks.setAdapter(assignedAdapter);
        requestedTasks.setAdapter(requestedAdapter);

    }

    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        Bundle bundle = info.getInfo();

        try {
            TaskList adaptedAssignedList = (TaskList) bundle.getSerializable("assignedTaskList");
            TaskList adaptedRequestedList = (TaskList) bundle.getSerializable("requestedTaskList");

            assignedTaskList = adaptedAssignedList.getTaskList();
            requestedTaskList = adaptedRequestedList.getTaskList();
        } catch (NullPointerException e) {
            assignedTaskList = new ArrayList<Task>();
            requestedTaskList = new ArrayList<Task>();
        }
    }

}
