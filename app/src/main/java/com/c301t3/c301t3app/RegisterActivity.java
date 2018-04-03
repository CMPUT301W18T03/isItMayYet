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

                //TODO: I encountered an error here, same pw, threw error. Check logic.
//                else if (etPassword.getText().toString() != etConfirmPassword.getText().toString()) {
//                    Toast.makeText(RegisterActivity.this,
//                            "Password does not match",Toast.LENGTH_LONG).show();
//                }

                else {
                    account.setUsername(etUsername.getText().toString());
                    account.setFirstName(etFirstName.getText().toString());
                    account.setLastName(etLastName.getText().toString());
                    account.setEmailAdd(etEmail.getText().toString());
                    account.setPhoneNum(etPhone.getText().toString());
                    account.setPassword(etPassword.getText().toString());

                    // send user to Elasticsearch server
                    try {
                        ElasticsearchController.userToServer(account);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // check to see if the uniqueID matches. ofc it does.
                    Log.i("uniqueID", account.getID());

                    //TODO: update user in database now after having set the uniqueID

                    // send user account to jsonHandler.
                    j.dumpUser(account);

                    Intent loginIntent = new Intent(RegisterActivity.this, SimpleLoginActivity.class);
                    RegisterActivity.this.startActivity(loginIntent);
                }


            }
        });

    }

    /**
     * Check if field is empty, prevents crashes
     * @param editField
     * @return boolean
     */

    private boolean isEmpty(EditText editField) {
        if (editField.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }
    
}
