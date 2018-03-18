package com.c301t3.c301t3app;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainMenuActivity extends AppCompatActivity{

    private final Context context = MainMenuActivity.this;
    private MainMenuActivity activity = this;
    private final TaskPasser taskPasser = new TaskPasser();
    public TaskList taskList = new TaskList(); //sample list TODO: remove, maybe

    private int position;

    private RecyclerView taskListView;
    private TasksRequestedAdapter adapter;
    private FloatingActionButton addTaskButton;
    private EditText searchInput;
    private ArrayList<Task> tasks = new ArrayList<Task>();


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
        taskListView = findViewById(R.id.listView);

        //debug
        Task task0 = new Task("Task0","Description for task0",TaskStatus.REQUESTED,15);
        Task task1 = new Task("Task1","There isn't really any reason to describe this",TaskStatus.REQUESTED,20);
        Task task2 = new Task("Task2","Desc2",TaskStatus.ASSIGNED,20);
        taskList.addTask(task0);
        taskList.addTask(task1);
        taskList.addTask(task2);

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

    @Override
    protected void onStart() {
        super.onStart();
        taskListView = findViewById(R.id.listView);
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
            @Override
            public void onItemClick(View singleTask, int pos) {
                position = pos;
                Intent selectedIntent = new Intent(context, SelectedTaskActivity.class);
                Task selTask = adapter.getItem(pos);
                selectedIntent.putExtra("MainSelectedTask",selTask);
                startActivity(selectedIntent);
            }
        });
    }

    EditText.OnKeyListener searchTasks = new EditText.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if ((keyEvent.getAction()==KeyEvent.ACTION_UP) && (i==KeyEvent.KEYCODE_ENTER)){
                //InputMethodManager methodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                //methodManager.toggleSoftInput(0,0);

                ArrayList<Task> searchMatch = new ArrayList<Task>();
                String searchWord = searchInput.getText().toString().toLowerCase(Locale.getDefault());
                if (searchWord.isEmpty()) {
                    Log.i("debug","empty search");
                    Toast.makeText(getApplicationContext(),"Error: Please enter a search query",Toast.LENGTH_SHORT).show();
                    return false;
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
