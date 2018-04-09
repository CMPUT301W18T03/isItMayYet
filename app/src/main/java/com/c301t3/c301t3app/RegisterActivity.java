package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by kiefer on 2018-03-08.
 * Class for Registering a new user.
 */

/** The Register Activity for when creating a profile
 *  You need a username, first name, last name, email, phone, password, etc.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final UserAccount account = new UserAccount();

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etFirstName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
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

                else if (!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
                    Toast.makeText(RegisterActivity.this,
                            "Password does not match",Toast.LENGTH_LONG).show();
                }

                else if (!ApplicationController.isOnline(getApplicationContext())){
                    Toast.makeText(RegisterActivity.this,
                            "Internet Connection Unavailable: Registration unsuccessful.",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    UserAccount checkAcc = new UserAccount();
                    account.setUsername(etUsername.getText().toString());
                    account.setFirstName(etFirstName.getText().toString());
                    account.setLastName(etLastName.getText().toString());
                    account.setEmailAdd(etEmail.getText().toString());
                    account.setPhoneNum(etPhone.getText().toString());
                    account.setPassword(etPassword.getText().toString());

                    ElasticsearchController.GetUserByUsername getUserByName =
                            new ElasticsearchController.GetUserByUsername();
                    ElasticsearchController.AddUser addUser =
                            new ElasticsearchController.AddUser();
                    ElasticsearchController.UpdateUser updateUser =
                            new ElasticsearchController.UpdateUser();

                    getUserByName.execute(account.getUsername());
                    try {checkAcc = getUserByName.get();}
                    catch (Exception e) {
                        Log.i("Good","User not found");
                    }

                    if (checkAcc!=null) {
                        Toast.makeText(RegisterActivity.this,
                                "Username is taken. Please choose a different username.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        addUser.execute(account);
                        updateUser.execute(account);
                        // check to see if the uniqueID matches. ofc it does.
                        Log.i("uniqueID", account.getID());

                        // send user account to jsonHandler.
                        j.dumpUser(account);

                        Intent loginIntent = new Intent(RegisterActivity.this, SimpleLoginActivity.class);
                        RegisterActivity.this.startActivity(loginIntent);
                    }
                }
            }
        });
    }

    /**Check if field is empty, prevents crashes
     *
     * @param editField; the field to check if it is empty or not
     * @return boolean; false if it is not empty, true if it is
     */
    private boolean isEmpty(EditText editField) {
        if (editField.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }
    
}
