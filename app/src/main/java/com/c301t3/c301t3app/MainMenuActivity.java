package com.c301t3.c301t3app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainMenuActivity extends AppCompatActivity{
    private MainMenuActivity activity = this;
    private final TaskPasser taskPasser = new TaskPasser();
    public TaskList taskList = new TaskList(); //sample list

    private ListView taskListView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        Button addTaskButton = (Button) findViewById(R.id.floatingActionButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                //start activity for adding new task
                Intent intent = new Intent(activity, NewTaskActivity.class);
                taskPasser.setTasks(taskList.getTaskList());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        taskListView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter(this, R.layout.task_item);
        taskListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
