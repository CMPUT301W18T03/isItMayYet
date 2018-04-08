package com.c301t3.c301t3app;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static junit.framework.Assert.assertTrue;

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

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button bSignIn = findViewById(R.id.bSignIn);
        final TextView registerLink = findViewById(R.id.tvRegisterHere);
        final JsonHandler j = new JsonHandler(this);

        // go to main activity
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginName = etUsername.getText().toString().trim();
                String inputPassword = etPassword.getText().toString().trim();
                UserAccount user = new UserAccount();

                if (!ApplicationController.isOnline(getApplicationContext())){
                    Toast.makeText(SimpleLoginActivity.this,
                            "Internet Connection Unavailable: Login unsuccessful.",
                            Toast.LENGTH_LONG).show();
                }
                else if (loginName.isEmpty()) {
                    Toast.makeText(SimpleLoginActivity.this,
                            "No username input. Please fill in a username.",
                            Toast.LENGTH_LONG).show();
                }
                else if (inputPassword.isEmpty()) {
                    Toast.makeText(SimpleLoginActivity.this,
                            "Please provide a password.",
                            Toast.LENGTH_LONG).show();
                }
                else {

                    ElasticsearchController.GetUserByUsername getUserByName =
                            new ElasticsearchController.GetUserByUsername();
                    getUserByName.execute(loginName);
                    try{
                    user = getUserByName.get();}
                    catch (Exception e) {
                        Log.i("E","User not found");
                    }

                    if(user==null) {
                        Toast.makeText(SimpleLoginActivity.this,
                                "User not found. Perhaps you would like to register?",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(!inputPassword.equals(user.getPassword())) {
                        Toast.makeText(SimpleLoginActivity.this,
                                "Login failed. Password is incorrect.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        ApplicationController.setUser(user);
                        Intent mainIntent = new Intent(SimpleLoginActivity.this,
                                MainMenuActivity.class);
                        SimpleLoginActivity.this.startActivity(mainIntent);
                    }
                }
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
