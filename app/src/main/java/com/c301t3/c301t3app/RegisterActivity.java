package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kiefer on 2018-03-08.
 * Class for Register
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final UserAccount account = new UserAccount(); // move below, to spot after you get details from user

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etFirstName = (EditText) findViewById(R.id.etLastName);
        final EditText etLastName = (EditText) findViewById(R.id.etFirstName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final JsonHandler j = new JsonHandler(this);

        final Button bRegister = (Button) findViewById(R.id.bCreate);
        
        // go back to login activity after creating new user
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(etUsername)||isEmpty(etFirstName)||isEmpty(etLastName)||isEmpty(etEmail)
                        ||isEmpty(etPhone)||isEmpty(etPassword)||isEmpty(etConfirmPassword)){
                    Toast.makeText(RegisterActivity.this,
                            "Please fill in all the fields",Toast.LENGTH_LONG).show();
                }

                else if (etPassword.getText().toString() != etConfirmPassword.getText().toString()) {
                    Toast.makeText(RegisterActivity.this,
                            "Password does not match",Toast.LENGTH_LONG).show();
                }

                else {
                    account.setUsername(etUsername.getText().toString());
                    account.setFirstName(etFirstName.getText().toString());
                    account.setLastName(etLastName.getText().toString());
                    account.setEmailAdd(etEmail.getText().toString());
                    account.setPhoneNum(etPhone.getText().toString());
                    account.setPassword(etPassword.getText().toString());
                    account.setID(UserAccount.userCount++);


                    //handle user.id here. needs to increment based on existing count of users.


                    // send user account to jsonHandler.
                    j.dumpUser(account);

                    Intent loginIntent = new Intent(RegisterActivity.this, SimpleLoginActivity.class);
                    RegisterActivity.this.startActivity(loginIntent);
                }


            }
        });

    }

    private boolean isEmpty(EditText editField) {
        if (editField.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }

    // Refer to select_task_activity for
    // taskStat.setText(currentTask.getStatus());
    // taskPrice.setText(currentTask.getPrice());
    // except for editing user profile, via register view.
}
