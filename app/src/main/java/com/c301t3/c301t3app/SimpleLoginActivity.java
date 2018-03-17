package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kiefer on 2018-03-08.
 * Class for Login
 */

public class SimpleLoginActivity extends AppCompatActivity {


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bSignIn = (Button) findViewById(R.id.bSignIn);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        // go to main activity
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SimpleLoginActivity.this, MainActivity.class);
                SimpleLoginActivity.this.startActivity(mainIntent);
            }
        });

        // go to register activity
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(SimpleLoginActivity.this, RegisterActivity.class);
                SimpleLoginActivity.this.startActivity(registerIntent);

            }
        });

    }
}
