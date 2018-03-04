package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int MYTASKCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button GoToMyTasks = (Button) findViewById(R.id.button_GoToMyTasks);

        GoToMyTasks.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(view.getContext(), MyTasksActivity.class);

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
}
