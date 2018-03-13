package com.c301t3.c301t3app;

/**
 * Created by nynic on 2018-03-03.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class selected_task_activity extends AppCompatActivity {

    private TaskPasser passer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_task);
        final TaskPasser passer = new TaskPasser();

        TextView taskName = findViewById(R.id.textViewTaskName);
        TextView taskDesc = findViewById(R.id.textViewTaskDes);
        ImageView taskImages = findViewById(R.id.imageView);
        TextView taskStat = findViewById(R.id.textViewStatus);
        TextView taskPrice = findViewById(R.id.textViewPrice);
        EditText taskBid = findViewById(R.id.editTextBid);
        Button taskBidBtn = findViewById(R.id.bidBtn);

    }
}
