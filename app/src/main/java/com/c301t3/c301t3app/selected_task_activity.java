package com.c301t3.c301t3app;

/**
 * Created by nynic on 2018-03-03.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class selected_task_activity extends AppCompatActivity {

    private TaskPasser passer;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_task);
        final TaskPasser passer = new TaskPasser();
        //get current selected task.

        TextView taskName = findViewById(R.id.textViewTaskName);
        TextView taskDesc = findViewById(R.id.textViewTaskDes);
        ImageView taskImages = findViewById(R.id.imageView);
        TextView taskStat = findViewById(R.id.textViewStatus);
        TextView taskPrice = findViewById(R.id.textViewPrice);
        EditText taskBid = findViewById(R.id.editTextBid);
        Button taskBidBtn = findViewById(R.id.bidBtn);
        ArrayList<Task> t = new ArrayList<>();

//        taskBidBtn.setEnabled(false);
//        taskName.setText(currentTask.getTaskName());
//        taskDesc.setText(currentTask.getTaskDescription());
//        //taskImages.setImageResource(Drawablesres);
//        taskStat.setText(currentTask.getStatus());
//        taskPrice.setText(currentTask.getPrice());
//        try {
//            taskBid.setText(currentTask.getCurrentBid);
//        } catch (Exception e) {
//            Log.i("Bid error", "getting current bid error.");
//            e.printStackTrace();
//        }

    }

    public void taskBidded(View view) {
        Log.i("button is pressed", "bid button is pressed.");
        // If the status of the bid is Green, btn is active.

    }
}
