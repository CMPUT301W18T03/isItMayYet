package com.c301t3.c301t3app;

import android.app.Application;
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
import java.util.EventListener;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * This is the main Screen of the app, all activities go back to this one.
 * Allows for the searching of tasks and selecting a task to bid on, as well as
 * to see more  details about a task.
 */
public class MainMenuActivity extends AppCompatActivity{
    private MainMenuActivity activity = this;
    private final TaskPasser taskPasser = new TaskPasser();
    public ArrayList<Task> taskList = new ArrayList<Task>();
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

                ApplicationController.clearUser();

                // go to login activity
                Intent logoutIntent = new Intent(activity, SimpleLoginActivity.class);
                activity.startActivity(logoutIntent);

                break;

            case R.id.myTasks:
                Toast.makeText(getApplicationContext(), "MyTasks selected", Toast.LENGTH_SHORT).show();
                Intent myTaskIntent = new Intent(activity, MyTasksActivity.class);
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
                String strname = selTask.getName() + "/" + selTask.getDescription() + "/" + selTask.getStatus().toString() + "/" + String.valueOf(selTask.getPrice()) + "/" + String.valueOf(selTask.getLongitude()) + "/" + String.valueOf(selTask.getLatitude());
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
                if (!ApplicationController.isOnline(getApplicationContext())){
                    Toast.makeText(MainMenuActivity.this,
                            "Search failed: No connection to server",
                            Toast.LENGTH_LONG).show();
                }

                else if (searchWord.isEmpty()) {
                    ElasticsearchController.GetAllTask getAllTask =
                            new ElasticsearchController.GetAllTask();
                    getAllTask.execute();
                    try {
                    taskList = getAllTask.get();
                    for (i=0;i<taskList.size();i++) {
                        if ((taskList.get(i).getStatus()==TaskStatus.REQUESTED)
                                || (taskList.get(i).getStatus()==TaskStatus.BIDDED)) {
                            searchMatch.add(taskList.get(i));
                        }
                    }
                    tasks.clear();
                    tasks.addAll(searchMatch);
                    }
                    catch (Exception e) {
                        Log.i("E","No tasks");
                    }
                    if(searchMatch.isEmpty()){Toast.makeText(MainMenuActivity.this,
                            "No task fits the description searched.",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }

                else {
                    ElasticsearchController.GetTask getTask =
                            new ElasticsearchController.GetTask();
                    getTask.execute(searchWord);
                    try {
                    taskList = getTask.get();
                    for (i=0;i<taskList.size();i++) {
                        if (((taskList.get(i).getStatus()==TaskStatus.REQUESTED)
                                || (taskList.get(i).getStatus()==TaskStatus.BIDDED))) {
                            searchMatch.add(taskList.get(i));
                        }
                    }
                    tasks.clear();
                    tasks.addAll(searchMatch);}
                    catch (Exception e) {
                        Log.i("E","No tasks");
                    }
                    if(searchMatch.isEmpty()){Toast.makeText(MainMenuActivity.this,
                            "No task fits the description searched.",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    };
}
