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

/**
 * This is the Welcome screen of the App, the User only sees it when starting the app.
 * Allows for quick login or to start searching as a free user.
 */
public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    // private final TaskPasser taskPasser = new TaskPasser();
    private TaskPasser taskPasser;
    private Intent loginIntent;
    private Intent mainMenuIntent;
    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/

    /// Menu Start Here-----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * @param item The menu being selected
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

                //TODO: actually log the user out.

                // go to login activity
                Intent logoutIntent = new Intent(activity, SimpleLoginActivity.class);
                activity.startActivity(logoutIntent);

                break;

            case R.id.myTasks:
                Toast.makeText(getApplicationContext(), "MyTasks selected", Toast.LENGTH_SHORT).show();
                // go to myTasks activity
                Intent myTasksIntent = new Intent(activity, MyTasksActivity.class);


                /* Henry's code, seems to assign test shit. */

                /*
                ArrayList<Task> assignedTaskList = new ArrayList<Task>();
                ArrayList<Task> requestedTaskList = new ArrayList<Task>();

                Task assignedTask0 = new Task("assignedTask0",
                        "assignedTask description0",
                        TaskStatus.ASSIGNED, 10);

                Task assignedTask1 = new Task("assignedTask1",
                        "assignedTask description1",
                        TaskStatus.COMPLETED, 15);

                assignedTaskList.add(assignedTask0);
                assignedTaskList.add(assignedTask1);

                Bid bid0 = new Bid(1920, 12345);
                Bid bid1 = new Bid(1254, 54321);
                Bid bidx = new Bid(420, 99999);
                Bid bidy = new Bid(720, 33333);

                ArrayList<Bid> bids0 = new ArrayList<Bid>();
                bids0.add(bid0);
                bids0.add(bid1);

                ArrayList<Bid> bids1 = new ArrayList<Bid>();
                bids1.add(bidx);
                bids1.add(bidy);

                Task requestedTask0 = new Task("requestedTask0",
                        "requestedTask description0",
                        TaskStatus.REQUESTED, 11, bids0);

                Task requestedTask1 = new Task("requestedTask1",
                        "requestedTask description1",
                        TaskStatus.BIDDED, 19, bids1);

                requestedTaskList.add(requestedTask0);
                requestedTaskList.add(requestedTask1);

                final InfoPasser info = InfoPasser.getInstance();
                Bundle bundle = new Bundle();

                TaskList adaptedAssignedList = new TaskList(assignedTaskList);
                TaskList adaptedRequestedList = new TaskList(requestedTaskList);

                bundle.putSerializable("assignedTaskList", adaptedAssignedList);
                bundle.putSerializable("requestedTaskList", adaptedRequestedList);

                info.setInfo(bundle);
                */
                activity.startActivity(myTasksIntent);

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

        Button mainButton = findViewById(R.id.button_GoToMainMenu);
        Button loginButton = findViewById(R.id.loginBtn);

        mainButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v the button that is being pressed, the Start Searching button.
             *          This will bring the user to the main menu.
             */
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_SHORT).show();
                mainMenuIntent = new Intent(activity, MainMenuActivity.class);
                // commented  this out cuz usless, have diff dummy lists.
//                ArrayList<Task> dummytasklist = new ArrayList<>();
//                Task task0 = new Task("task0","desc0", TaskStatus.COMPLETED);
//                Task task1 = new Task("task1","desc1", TaskStatus.ASSIGNED);
//                Task task2 = new Task("task2","desc2", TaskStatus.REQUESTED);
//                dummytasklist.add(task0);
//                dummytasklist.add(task1);
//                dummytasklist.add(task2);
//                final TaskPasser taskPasser = new TaskPasser();
//                taskPasser.setTasks(dummytasklist);

//                String foo = taskPasser.getTasks().toString();
//                Toast.makeText(getApplicationContext(), foo, Toast.LENGTH_SHORT).show();
                startActivity(mainMenuIntent);

            }

        });

    }

    /**
     * @param view The Login button that brings the user right to the login page.
     */
    public void loginClick(View view) {
        //Toast.makeText(getApplicationContext(), "Login clicked",Toast.LENGTH_SHORT).show();
        loginIntent = new Intent(activity, SimpleLoginActivity.class);
        startActivity(loginIntent);

    }

}

