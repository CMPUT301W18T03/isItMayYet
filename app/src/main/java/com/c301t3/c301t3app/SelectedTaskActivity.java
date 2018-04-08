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
import android.widget.Toast;

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
    private Bitmap currentPicture;
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
        EditText taskBid = findViewById(R.id.editTextBid);
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
                taskDesc.setText(tokens.nextToken());
                taskStat.setText(tokens.nextToken());
                taskPrice.setText(tokens.nextToken());
//                taskBid.setVisibility(View.GONE);
//                taskBidBtn.setVisibility(View.GONE);
            }
        } else {
            extraString = (String) savedInstanceState.getSerializable("MainToSelectedTask");
            //Toast.makeText(getApplicationContext(), "savedInstanceState not NULL", Toast.LENGTH_SHORT).show();

        }

//        ArrayList<Task> t = new ArrayList<>();

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

    @Override
    protected void onStart() {
        super.onStart();

        loadFromInfoPasser();

        String name = currentTask.getName();
        String desc = currentTask.getDescription();
        TaskStatus stat = currentTask.getStatus();
        float price = currentTask.getPrice();

        if (currentPicture == null) {
            Bitmap original_picture = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.logo_big);
            int pic_height = Math.round((float) currentPicture.getHeight() / 2);
            int pic_width = Math.round((float) currentPicture.getWidth() / 2);
            currentPicture = Bitmap.createScaledBitmap(original_picture, pic_width, pic_height, true);
        }

        Toast.makeText(getApplicationContext(), currentPicture.toString(), Toast.LENGTH_SHORT).show();

        try {
            taskName.setText(name);
            taskDesc.setText(desc);
            taskStat.setText(stat.toString());
            taskPrice.setText(String.valueOf(price));
//            test.setImageBitmap(picture);
            taskImages.setImageBitmap(currentPicture);
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

    private void loadFromInfoPasser() {
        final InfoPasser info = InfoPasser.getInstance();
        Bundle bundle = info.getInfo();

        if (bundle != null) {
            if (bundle.containsKey("selectedTask")) {
                currentTask = (Task) bundle.getSerializable("selectedTask");
            }
            if (bundle.containsKey("selectedPicture")) {
                currentPicture = (Bitmap) bundle.getParcelable("selectedPicture");
            }
        }

    }


}
