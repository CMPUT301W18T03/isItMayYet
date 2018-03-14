package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);


        final Button bRegister = (Button) findViewById(R.id.bCreate);

        // go back to login activity
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, SimpleLoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });

    }
}
