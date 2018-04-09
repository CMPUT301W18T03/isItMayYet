package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/** Splash Activity, the load screen for when the app is loading
 *
 */
public class SplashActivity extends AppCompatActivity {

    /** Called when the view is first created,
     *  Simply displays the app logo and waits to load
     *
     * @param savedInstanceState; previous data, nothing probably
     */
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
