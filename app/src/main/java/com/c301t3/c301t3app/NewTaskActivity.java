package com.c301t3.c301t3app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jonah on 2018-03-09.
 * Class that handles that creation of a new task, with appropriate fields and buttons
 */

public class NewTaskActivity extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;

    private ImageView userPicture;

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
        userPicture = findViewById(R.id.ImageView_userPicture);

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

            if (bitmap != null) {


//                int bytes_original = bitmap.getByteCount();

//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
//                Bitmap compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
//
//                int bytes_compressed = compressed.getByteCount();
//
//                Toast.makeText(getApplicationContext(), "Original: " + String.valueOf(bytes_original), Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Compressed: " + String.valueOf(bytes_compressed), Toast.LENGTH_LONG).show();

                String message = "";

                int bitmap_width = bitmap.getWidth();
                int bitmap_height = bitmap.getHeight();

                message = String.format("Width: %d\nHeight: %d", bitmap_width, bitmap_height);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                int scaled_width = bitmap.getScaledWidth(bitmap_width);
                int scaled_height = bitmap.getScaledHeight(bitmap_height);

                message = String.format("ScaledW: %d\nScaledH: %d", scaled_width, scaled_height);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                int byte_count = bitmap.getByteCount();

                message = String.format("byte_count unsized: %d", byte_count);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

//                Bitmap new_bitmap = cleanBitmap(bitmap);
//
//                int byte_count_new = new_bitmap.getByteCount();
//
//                message = String.format("byte_count_new sized: %d", byte_count_new);
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                // --- //

//                Bitmap background = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);

//                float originalWidth = originalImage.getWidth();
//                float originalHeight = originalImage.getHeight();

//                Canvas canvas = new Canvas(background);

//                float scale = width / originalWidth;

//                float xTranslation = 0.0f;
//                float yTranslation = (height - originalHeight * scale) / 2.0f;

//                Matrix transformation = new Matrix();
//                transformation.postTranslate(xTranslation, yTranslation);
//                transformation.preScale(scale, scale);

//                Paint paint = new Paint();
//                paint.setFilterBitmap(true);

//                canvas.drawBitmap(originalImage, transformation, paint);

//                return background;

                // --- //


                userPicture.setImageBitmap(bitmap);
            }

        }

    }

    private Bitmap cleanBitmap(Bitmap picture) {
        int num_bytes = picture.getByteCount();
        int width = picture.getWidth();
        int height = picture.getHeight();
        if (width > 4096) {
            picture.setWidth(4096);
        }
        if (height > 4096) {
            picture.setHeight(4096);
        }
        if (num_bytes >= 65536 ) {
            picture.setWidth(2048);
            picture.setHeight(2048);
        }

        return picture;
    }

}
