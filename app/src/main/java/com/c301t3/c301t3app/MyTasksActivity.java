package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This file handles the Activity that displays all the Tasks the user has
 * at the given moment, which displays the Task info (name and status).
 * Expects: Intent with TaskList object
 * Returns: Nothing
 *
 * @author hingyue
 * @version 1.5
 * @see AppCompatActivity
 * @see MainActivity //TODO This is a place holder!
 */
public class MyTasksActivity extends AppCompatActivity {

    private TaskList tasklist;
    private ArrayAdapter<Task> adapter;
    private ListView listview_tasklist;

    /**
     * MyTasksActivity sets up its layout (i.e the back button on top of the title bar).
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        getSupportActionBar().setTitle("My Tasks");   // This sets the title up on the title bar.
        /* The statement below displays the button for the title bar up top, and how it is able
         * to go backwards on click was by modifying android:parentActivityName=".Activity"
         * within <activity android:name=".MyTasksActivity"> in the manifests.xml file.
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializes the list view my MyTasksActivity
        listview_tasklist = (ListView) findViewById(R.id.myTasksList);

        Intent intent = getIntent();
        /* This if/if else/else statement combs over all the possible keys coming from the
        * possible Activities and extracts the TaskList object once a key is found to have an
        * ExtraStored within it. However, note that it assumes all Extras are Serializable-Extras.
        */
        if (intent.hasExtra("SentTaskList")) {
            tasklist = (TaskList) intent.getSerializableExtra("SentTaskList");
        } else {
            /* Warning message: this will not lead to a catastrophic failure, but will create
            * peculiar logic errors, if anything is not showing up correctly, please go on to
            * AndroidStudio, click the button "4: Run" at the bottom of the window, and look
            * for the message below to see if this is a component to the error.
            */
            String message = "CUSTOM WARNING: MyTasksActivity.java at method onCreate(...) " +
                            "encountered no retrievable TaskLists from intent!";
            System.out.println(message);
        }
//        final TaskPasser taskpasser = new TaskPasser();
//        tasklist = taskpasser.getTasks();
//        adapter.notifyDataSetChanged();

    }

    /**
     * Sets up all data on the start of the Activity and the object tasklist is retrieved by
     * getting the intents and attempting to extract information from it.
     */
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        /* This if/if else/else statement combs over all the possible keys coming from the
        * possible Activities and extracts the TaskList object once a key is found to have an
        * ExtraStored within it. However, note that it assumes all Extras are Serializable-Extras.
        */
        if (intent.hasExtra("SentTaskList")) {
            tasklist = (TaskList) intent.getSerializableExtra("SentTaskList");
        } else {
            /* Warning message: this will not lead to a catastrophic failure, but will create
            * peculiar logic errors, if anything is not showing up correctly, please go on to
            * AndroidStudio, click the button "4: Run" at the bottom of the window, and look
            * for the message below to see if this is a component to the error.
            * NOTE: TaskList will be initialize as a new TaskList to avoid NullPointerExceptions.
            */
            String message = "CUSTOM WARNING: MyTasksActivity.java at method onStart(...) " +
                    "encountered no retrievable TaskLists from intent!";
            System.out.println(message);
            tasklist = new TaskList();
        }
        adapter.notifyDataSetChanged();
//        final TaskPasser taskpasser = new TaskPasser();
//        tasklist = taskpasser.getTasks();
//        adapter.notifyDataSetChanged();


        // Sets up the array adapter with task_item, and binds it with tasklist's ArrayList<~>
        adapter = new ArrayAdapter<Task>(this,
                R.layout.task_item, tasklist.getTaskList());
        listview_tasklist.setAdapter(adapter);
    }


}
