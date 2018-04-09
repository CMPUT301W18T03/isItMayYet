package com.c301t3.c301t3app;

/**
 * Created by nynic on 2018-03-03.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This  class is to handle the event a user wants more details and to bid on the tasks.
 * Also handles for if a user wants to see only the details of their assigned task.
 *
 */
public class SelectedTaskActivity extends AppCompatActivity {

    private TextView taskName;
    private TextView taskDesc;
    private TextView taskStat;
    private TextView taskPrice;
    private ImageView taskImages;

    private TaskPasser passer;
    private Task currentTask;
    private String extraString;
    //final InfoPasser infoInstance = InfoPasser.getInstance();
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_task);
        extraString = null;
//        final TaskPasser passer = new TaskPasser();
        //get current selected task.

        taskName = findViewById(R.id.textViewTaskName);
        taskDesc = findViewById(R.id.textViewTaskDes);
        taskStat = findViewById(R.id.textViewStatus);
        taskPrice = findViewById(R.id.textViewPrice);
        taskImages = findViewById(R.id.imageView);
        final EditText taskBid = findViewById(R.id.editTextBid);
        Button taskBidBtn = findViewById(R.id.bidBtn);

        /**
         * This Grabs any extras from other activities and applies the changes we want. Which is to show only task detials but not to bid on it.
         */
//        Task task = new Task();--- garbage
//        Bundle bundle1 = infoInstance.getInfo();
//        task = (Task) bundle1.getSerilizable("assignedTask");
        //Log.i("the passed String is:", extraString);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                extraString = null;
                //Toast.makeText(getApplicationContext(), "extra string is NULL", Toast.LENGTH_SHORT).show();
            } else {
                extraString = extras.getString("SelectedTask");

                Log.i("the passed String is:", extraString);
                StringTokenizer tokens = new StringTokenizer(extraString, "/");
                taskName.setText(tokens.nextToken());
                taskDesc.setText("Description: " + tokens.nextToken());
                taskStat.setText(tokens.nextToken());
                taskPrice.setText("$" + tokens.nextToken());
//                taskBid.setVisibility(View.GONE);
//                taskBidBtn.setVisibility(View.GONE);
            }
        } else {
            extraString = (String) savedInstanceState.getSerializable("MainToSelectedTask");
            //Toast.makeText(getApplicationContext(), "savedInstanceState not NULL", Toast.LENGTH_SHORT).show();

        }

        taskBidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAccount usr = ApplicationController.getCurrUser();

                if (usr != null) {
                    TaskStatus status = currentTask.getStatus();

                    if (usr.getUsername().equals(currentTask.getOwnerName())) {
                        Toast.makeText(getApplicationContext(),
                                "You can't bid on your own Task.",
                                Toast.LENGTH_SHORT).show();
                    } else if (status == TaskStatus.COMPLETED || status == TaskStatus.ASSIGNED) {
                        Toast.makeText(getApplicationContext(),
                                "It seems the Task is already completed or assigned.",
                                Toast.LENGTH_SHORT).show();
                    } else  {
                        String text = taskBid.getText().toString();
                        float value = Float.valueOf(text);

                        Bid bid = new Bid(value, usr.getID());
                        currentTask.addBid(bid);
                        currentTask.setStatus(TaskStatus.BIDDED);

                        String id = currentTask.getId();
                        ElasticsearchController.deleteTaskByID(id);

                        ElasticsearchController.taskToServer(currentTask);

                        Toast.makeText(getApplicationContext(),
                                "Bid successful.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please log in to be able to bid on a Task.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromInfoPasser();

        String name = currentTask.getName();
        String desc = currentTask.getDescription();
        TaskStatus stat = currentTask.getStatus();
        float price = currentTask.getPrice();
        Bitmap picture = currentTask.getPicture();

        if (picture == null) {
            Bitmap original_picture = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.logo_big);
            currentTask.setPicture(original_picture);
            picture = currentTask.getPicture();
        }

        try {
            taskName.setText(name);
            taskDesc.setText("Description: " + desc);
            taskStat.setText(stat.toString());
            taskPrice.setText("$" + String.valueOf(price));
//            taskImages.setImageBitmap(jk);
            taskImages.setImageBitmap(picture);
        } catch (Exception e) {}

    }

    /**
     *
     * @param view The bid button functionality.
     */
    public void taskBidded(View view) {
        Log.i("button is pressed", "bid button is pressed.");
        // If the status of the bid is Green, btn is active.
        Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenuIntent);

    }


    public void goToMap(View view) {
        Intent mapIntent = new Intent(this, FindTaskonMapActivity.class);
        String coords = "33.8994864" + "/" + "-118.2861378"; //33.8994864,-118.2861378
        Log.i("going to map", "this means button pressed and coords are " + coords);
        mapIntent.putExtra("taskCoords", coords);
        startActivity(mapIntent);
    }

    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        Bundle bundle = info.getInfo();

        if (bundle != null) {
            if (bundle.containsKey("selectedTask")) {
                currentTask = (Task) bundle.getSerializable("selectedTask");
            }
        }

    }


}
