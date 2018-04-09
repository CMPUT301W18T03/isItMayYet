package com.c301t3.c301t3app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    public static final int MY_BID_CODE = 1;

    private ArrayList<Task> assignedTaskList = new ArrayList<Task>();
    private ArrayList<Task> requestedTaskList = new ArrayList<Task>();
    private ArrayAdapter<Task> assignedAdapter;
    private ArrayAdapter<Task> requestedAdapter;
    private ListView assignedTasks;
    private ListView requestedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        getSupportActionBar().setHomeButtonEnabled(true); //TODO: Henry, what is this?

        assignedTasks = (ListView) findViewById(R.id.ListView_assignedTasks);
        requestedTasks = (ListView) findViewById(R.id.ListView_requestedTasks);

        TextView assignedTaskTextView = (TextView) findViewById(R.id.TextView_assignedTasksTitle);
        TextView requestedTaskTextView = (TextView) findViewById(R.id.TextView_requestedTasksTitle);

        assignedTaskTextView.setText(TITLE_ASSIGNED_TASKS);
        requestedTaskTextView.setText(TITLE_REQUESTED_TASKS);

        assignedTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // click's action is left empty for now.
                Object listItem = assignedTasks.getItemAtPosition(position);
                Task task = (Task) listItem;

                final InfoPasser info = InfoPasser.getInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("assignedTask", task);
                bundle.putInt("assignedIndex", position);
                info.setInfo(bundle);

                Intent intent = new Intent(view.getContext(), SelectedTaskActivity.class);
                startActivity(intent);

            }

        });

        requestedTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // click's action is left empty for now.
                Object listItem = requestedTasks.getItemAtPosition(position);
                Task task = (Task) listItem;

                final InfoPasser info = InfoPasser.getInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("requestedTask", task);
                bundle.putInt("requestedIndex", position);
                info.setInfo(bundle);

                Intent intent = new Intent(view.getContext(), ViewBidsActivity.class);
                startActivityForResult(intent, MY_BID_CODE);
            }

        });

        requestedTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = requestedTasks.getItemAtPosition(position);
                final Task taskItem = (Task) listItem;

                //source: https://stackoverflow.com/questions/36747369/how-to-show-a-pop-up-in-android-studio-to-confirm-an-order;
                AlertDialog.Builder builder = new AlertDialog.Builder(MyTasksActivity.this);
                //builder.setCancelable(true);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure you want to permanently delete this task?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ElasticsearchController.deleteTaskByID(taskItem.getId());
                                requestedTaskList = ElasticsearchController.serverTasksByOwner(ApplicationController.getCurrUser().getID());
                                requestedAdapter.remove(taskItem);
                                requestedAdapter.notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AppCompatDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadInfo();

        assignedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_assigned, assignedTaskList);
        requestedAdapter = new ArrayAdapter<Task>(this, R.layout.my_tasks_requested, requestedTaskList);

        assignedTasks.setAdapter(assignedAdapter);
        requestedTasks.setAdapter(requestedAdapter);
        assignedAdapter.notifyDataSetChanged();
        requestedAdapter.notifyDataSetChanged();

    }

    /**
     * Function that loads the task from infoPasser constructor.
     */
    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        final JsonHandler j = new JsonHandler(this);
        Bundle bundle = info.getInfo();

        // This is to attempt to check if the infoPasser passed assignedList/requestedList
        TaskList adaptedAssignedList = (TaskList) bundle.getSerializable("assignedTaskList");
        if (adaptedAssignedList != null) {
            assignedTaskList = adaptedAssignedList.getTaskList();
        } else {}

        TaskList adaptedRequestedList = (TaskList) bundle.getSerializable("requestedTaskList");
        if (adaptedRequestedList != null) {
            requestedTaskList = adaptedRequestedList.getTaskList();
        } else {
            requestedTaskList = j.loadUserTasks();
        }

        // This is to attempt to check if there is any modifiers that came from ViewBidsActvity.java
        Task task = (Task) bundle.getSerializable("ViewBidsTask");
        int taskIndex = bundle.getInt("ViewBidsTaskIndex");
        if (task != null) {
            requestedTaskList.set(taskIndex, task);
            j.dumpUserTasks(requestedTaskList);
        }
    }

    private void loadInfo() {
        UserAccount usr = ApplicationController.getCurrUser();

        if (usr != null) {
            loadFromInfoPasser();
            requestedTaskList = ElasticsearchController.serverTasksByOwner(usr.getID());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // loadInfo();
    }

}
