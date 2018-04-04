package com.c301t3.c301t3app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jonah on 2018-03-09.
 * Class that handles that creation of a new task, with appropriate fields and buttons
 */

public class NewTaskActivity extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;

    private TaskPasser passer;
    private Task newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);
        final TaskPasser passer = new TaskPasser();
        newTask = new Task();

        final EditText nameText = findViewById(R.id.newTaskName);
        final EditText descText = findViewById(R.id.newTaskDescription);
        final EditText priceText = findViewById(R.id.newTaskPrice);

        Button saveButton = findViewById(R.id.createButton);
        Button addImageButton = findViewById(R.id.Button_addImage);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean end = true;
                String price;
                try {
                    newTask.setName(nameText.getText().toString());
                    newTask.setDescription(descText.getText().toString());
                    price = priceText.getText().toString().replaceAll("[.]", "");
                } catch (java.lang.IllegalArgumentException e) {
                    Snackbar errorMsg = Snackbar.make(findViewById(R.id.mainConstraint),
                            R.string.name_error,
                            Snackbar.LENGTH_SHORT);
                    end = false;
                    price = "0";
                    errorMsg.show();
                }
                newTask.setPrice(Integer.parseInt(price));
                newTask.setStatus(TaskStatus.REQUESTED);
                ArrayList<Task> t = new ArrayList<>();
                t.add(newTask);
                passer.setTasks(t);
                if(end) {
                    finish();
                }
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add Image", Toast.LENGTH_SHORT).show();

                // uploading image from gallery taken from StackOverFlow website from link: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Catching image from gallery taken from StackOverFlow post: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        // Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
