package com.c301t3.c301t3app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}

