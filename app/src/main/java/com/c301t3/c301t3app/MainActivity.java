package com.c301t3.c301t3app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    public static final int MYTASKCODE = 1;
    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* This is all garbage test code below

         */
        Button GoToMyTasks = findViewById(R.id.button_GoToMyTasks);

        GoToMyTasks.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(view.getContext(), Task.class);

                Task task0 = new Task("task0", "description for task0", "okay");
                Task task1 = new Task("task1", "description for task1", "meh");
                Task task2 = new Task("task2", "description for task2", "cool");
                TaskList tasklist = new TaskList();
                tasklist.addTask(task0);
                tasklist.addTask(task1);
                tasklist.addTask(task2);

                intent.putExtra("SentTaskList", tasklist);
                startActivityForResult(intent, MYTASKCODE);
            }

        });

    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, selected_task_activity.class);
        startActivity(intent);
    }
}