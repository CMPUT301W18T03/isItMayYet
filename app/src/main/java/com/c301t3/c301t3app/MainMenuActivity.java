package com.c301t3.c301t3app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


/**
 * This is the main Screen of the app, all activities go back to this one.
 * Allows for the searching of tasks and selecting a task to bid on, as well as
 * to see more  details about a task.
 */
public class MainMenuActivity extends AppCompatActivity{
    private MainMenuActivity activity = this;
    private final TaskPasser taskPasser = new TaskPasser();
    public TaskList taskList = new TaskList(); //sample list TODO: remove, maybe
    private final Context context = MainMenuActivity.this;

    private RecyclerView taskListView;
    private TasksRequestedAdapter adapter;
    private FloatingActionButton addTaskButton;
    private EditText searchInput;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    private int position;


    /// Menu Start Here-----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * @param item The menu  item selected.
     * @return item selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This is the place to handle all the menu items.
        int id = item.getItemId();

        switch (id) {

            case R.id.Profile:
                Toast.makeText(getApplicationContext(), "Profile selected", Toast.LENGTH_SHORT).show();

                //TODO: set up Profile activity.

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
                Intent myTaskIntent = new Intent(activity, MyTasksActivity.class);

                //---------------------------------------------------------------------------------///
                /* Henry's code, seems to assign test shit. */
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
                //---------------------------------------------------------------------------------///


                activity.startActivity(myTaskIntent);


                break;

            case R.id.MyBids:
                Toast.makeText(getApplicationContext(), "MyBids selected", Toast.LENGTH_SHORT).show();
                Intent myBidsIntent = new Intent(activity, BiddingScreen.class);
                activity.startActivity(myBidsIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //-----------Menu  Stuff ends here-----------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.tasksView);

        //debug
        /**
         * Test Cases
         */

        Task task0 = new Task("Task0","Description for task0",TaskStatus.REQUESTED,15);
        Task task1 = new Task("Task1","There isn't really any reason to describe this",TaskStatus.BIDDED,20);
        Task task2 = new Task("Task2","Desc2",TaskStatus.ASSIGNED,20);
        Task task3 = new Task("Task3","Some fluff text here for task 3", TaskStatus.BIDDED,22);
        Task task4 = new Task("Task4","Some fluff text here for task 4",TaskStatus.REQUESTED,12);

        taskList.addTask(task0);
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        taskList.addTask(task4);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                //start activity for adding new task
                Intent intent = new Intent(activity, NewTaskActivity.class);

                startActivity(intent);
            }
        });
    }

    /**
     * Creates the task list to show the user all the tasks they can search for.
     */
    @Override
    protected void onStart() {
        super.onStart();
        taskListView = findViewById(R.id.tasksView);
        searchInput = findViewById(R.id.searchBar);
        searchInput.setOnKeyListener(searchTasks);

        adapter = new TasksRequestedAdapter(this,tasks);
        RecyclerView.ItemDecoration itemDeco = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        taskListView.addItemDecoration(itemDeco);
        RecyclerView.LayoutManager layoutMan = new LinearLayoutManager(this);
        layoutMan.setAutoMeasureEnabled(true);

        taskListView.setAdapter(adapter);
        taskListView.setLayoutManager(layoutMan);

        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new TasksRequestedAdapter.OnItemClickListener() {
            /**
             * @param singleTask the task selected by the user
             * @param pos        The position of the selected task in the listview.
             */
            @Override
            public void onItemClick(View singleTask, int pos) {
                position = pos;
                Intent selectedIntent = new Intent(context, SelectedTaskActivity.class);
                Task selTask = adapter.getItem(pos);
//                Log.i("Info", selTask.getName());
//                Log.i("desc", selTask.getDescription());
//                Log.i("price", String.valueOf(selTask.getPrice()));
                String strname = selTask.getName() + "/" + selTask.getDescription() + "/" + selTask.getStatus().toString() + "/" + String.valueOf(selTask.getPrice());
                selectedIntent.putExtra("SelectedTask", strname);
                startActivity(selectedIntent);
            }
        });
    }

    /**
     * This is where the search is handled for the tasks. By description and if left empty and hit enter, returns all results.
     */
    EditText.OnKeyListener searchTasks = new EditText.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if ((keyEvent.getAction()==KeyEvent.ACTION_UP) && (i==KeyEvent.KEYCODE_ENTER)){
                //InputMethodManager methodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                //methodManager.toggleSoftInput(0,0);

                ArrayList<Task> searchMatch = new ArrayList<Task>();
                String searchWord = searchInput.getText().toString().toLowerCase(Locale.getDefault());
                if (searchWord.isEmpty()) {
                    for (i=0;i<taskList.getTaskList().size();i++) {
                        if ((taskList.getTask(i).getStatus()==TaskStatus.REQUESTED)
                                || (taskList.getTask(i).getStatus()==TaskStatus.BIDDED)) {
                            searchMatch.add(taskList.getTask(i));
                        }
                    }

                    tasks.clear();
                    tasks.addAll(searchMatch);
                    adapter.notifyDataSetChanged();
                }

                else {
                    for (i=0;i<taskList.getTaskList().size();i++) { // works for hardcoded short list of tasks.. takes long for more content
                        if ((taskList.getTask(i).getDescription().toLowerCase().contains(searchWord))
                                && ((taskList.getTask(i).getStatus()==TaskStatus.REQUESTED)
                                || (taskList.getTask(i).getStatus()==TaskStatus.BIDDED))) {
                            searchMatch.add(taskList.getTask(i));
                        }
                    }
                    tasks.clear();
                    tasks.addAll(searchMatch);
                    adapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    };
}
