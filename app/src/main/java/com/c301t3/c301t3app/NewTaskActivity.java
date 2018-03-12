package com.c301t3.c301t3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Jonah on 2018-03-09.
 */

public class NewTaskActivity extends AppCompatActivity {
    private TaskPasser passer;
    private Task newTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);
        final TaskPasser passer = new TaskPasser();
        newTask = new Task();

        final EditText nameText = findViewById(R.id.newTaskName);
        final EditText dateText = findViewById(R.id.newTaskDate);
        final EditText descText = findViewById(R.id.newTaskDescription);
        final EditText priceText = findViewById(R.id.newTaskPrice);

        Button saveButton = findViewById(R.id.createButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean end = true;
                newTask.setName(nameText.getText().toString());
                newTask.setDescription(descText.getText().toString());
                // the other fields go here

                ArrayList<Task> t = new ArrayList<>();
                t.add(newTask);
                passer.setTasks(t);

                if(end) {
                    finish();
                }
            }
        });
    }
}
