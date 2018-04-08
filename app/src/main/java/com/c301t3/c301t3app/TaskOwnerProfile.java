package com.c301t3.c301t3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TaskOwnerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_owner_profile);

        final TextView tvUsername = findViewById(R.id.username);
        final TextView tvFirstName = findViewById(R.id.firstName);
        final TextView tvEmail = findViewById(R.id.email);
        final TextView tvPhoneNum = findViewById(R.id.phoneNum);

        Intent intent = getIntent();
        String name = intent.getStringExtra("ownerName");

        ElasticsearchController.GetUserByUsername getUserByName =
                new ElasticsearchController.GetUserByUsername();
        getUserByName.execute(name);

        try{
            UserAccount ownerAccount = getUserByName.get();
            tvUsername.setText(ownerAccount.getUsername());
            tvFirstName.setText(ownerAccount.getFirstName());
            tvEmail.setText(ownerAccount.getEmailAdd());
            tvPhoneNum.setText(ownerAccount.getPhoneNum());
        }
        catch (Exception e){
            Log.i("E","User not found");
        }

    }
}
