package com.c301t3.c301t3app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    private final TaskPasser taskPasser = new TaskPasser();
    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mainButton = (Button) findViewById(R.id.button_GoToMyTasks);

        mainButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent intent = new Intent(activity, MyTasksActivity.class);

                ArrayList<Task> dummytasklist = new ArrayList<>();
                Task task0 = new Task("task0","desc0", TaskStatus.COMPLETED);
                Task task1 = new Task("task1","desc1", TaskStatus.ASSIGNED);
                Task task2 = new Task("task2","desc2", TaskStatus.REQUESTED);
                dummytasklist.add(task0);
                dummytasklist.add(task1);
                dummytasklist.add(task2);
                taskPasser.setTasks(dummytasklist);

                startActivity(intent);

            }

        });

    }

}

