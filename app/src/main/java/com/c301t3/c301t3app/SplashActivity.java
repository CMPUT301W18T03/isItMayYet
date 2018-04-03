package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent;

        if (ApplicationController.getCurrUser()==null){
            intent = new Intent(this,WelcomeActivity.class);
        }
        else {
            intent = new Intent(this, MainMenuActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
