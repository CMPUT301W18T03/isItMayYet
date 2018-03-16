package com.c301t3.c301t3app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    // private final TaskPasser taskPasser = new TaskPasser();
    private TaskPasser taskPasser;
    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/

    /// Menu Start Here-----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This is the place to handle all the menu items.
        int id = item.getItemId();

        switch (id) {

            case R.id.Profile:
                Toast.makeText(getApplicationContext(), "Profile selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.SignIN:
                Toast.makeText(getApplicationContext(), "SignIn selected", Toast.LENGTH_SHORT).show();
                // go to login activity
                Intent loginIntent = new Intent(activity, SimpleLoginActivity.class);
                activity.startActivity(loginIntent);

                break;

            case R.id.Logout:
                Toast.makeText(getApplicationContext(), "Logout selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.myTasks:
                Toast.makeText(getApplicationContext(), "MyTasks selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.MyBids:
                Toast.makeText(getApplicationContext(), "MyBids selected", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //-----------Menu  Stuff ends here-----------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskPasser = new TaskPasser();


        Button mainButton = findViewById(R.id.button_GoToMyTasks);

        mainButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent intent = new Intent(activity, MainMenuActivity.class);

                ArrayList<Task> dummytasklist = new ArrayList<>();
                Task task0 = new Task("task0","desc0", TaskStatus.COMPLETED);
                Task task1 = new Task("task1","desc1", TaskStatus.ASSIGNED);
                Task task2 = new Task("task2","desc2", TaskStatus.REQUESTED);
                dummytasklist.add(task0);
                dummytasklist.add(task1);
                dummytasklist.add(task2);
                final TaskPasser taskPasser = new TaskPasser();
                taskPasser.setTasks(dummytasklist);

                String foo = taskPasser.getTasks().toString();
                Toast.makeText(getApplicationContext(), foo, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

        });

    }

}

