package com.c301t3.c301t3app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kvongaza on 2018-04-07.
 */

/** The activity for userProfile; creates the view
 *  This is the screen for the user to edit their profile
 *  They can add their address, and change any previous information entered by them
 *
 */
public class UserProfile extends AppCompatActivity {
    private UserAccount user = new UserAccount();

    /**  Called when the view is first created
     *   Sets everything in motion
     *
     *
     * @param savedInstanceState; the previous information needed for the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView tvUsername = findViewById(R.id.tvUsername);
        final EditText etFirstName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etAddress = findViewById(R.id.etAddress);
        final Button bSave = findViewById(R.id.bSave);
        final Button bDelete = findViewById(R.id.bDelete);
        final JsonHandler j = new JsonHandler(this);

        ElasticsearchController.GetUserByUsername getUserByName =
                new ElasticsearchController.GetUserByUsername();
        getUserByName.execute(ApplicationController.getCurrUser().getUsername());

        try {
            UserAccount user = getUserByName.get();

            tvUsername.setText(user.getUsername());
            etFirstName.setText(user.getFirstName(), TextView.BufferType.EDITABLE);
            etLastName.setText(user.getLastName(), TextView.BufferType.EDITABLE);
            etEmail.setText(user.getEmailAdd(), TextView.BufferType.EDITABLE);
            etPhone.setText(user.getPhoneNum(), TextView.BufferType.EDITABLE);
            etAddress.setText(user.getAddress(), TextView.BufferType.EDITABLE);

            saveNewData(etFirstName, etLastName, etEmail, etPhone, etAddress, bSave, j);

            deleteProfile(bDelete);
        }
        catch(Exception e) {Log.i("E","Failed to find user");}
    }

    /** Listener to see if the button for deleting the profile was clicked
     *  If the button was clicked, a confirmation button appears, and then
     *  actually delete the account
     *
     * @param bDelete; button for deleting the app
     */
    private void deleteProfile(Button bDelete) {
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //source: https://stackoverflow.com/questions/36747369/how-to-show-a-pop-up-in-android-studio-to-confirm-an-order;
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                //builder.setCancelable(true);
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure you want to permanently delete user?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ElasticsearchController.DeleteUser delUser =
                                        new ElasticsearchController.DeleteUser();
                                ElasticsearchController.GetTaskByOwner getTaskByOwner =
                                        new ElasticsearchController.GetTaskByOwner();
                                ElasticsearchController.DeleteTask delTask =
                                        new ElasticsearchController.DeleteTask();

                                getTaskByOwner.execute(ApplicationController.getCurrUser().getID());
                                try {
                                    ArrayList<Task> currUserTasks = getTaskByOwner.get();
                                    for (Task t : currUserTasks) {
                                        delTask.execute(t.getId());
                                        delTask = new ElasticsearchController.DeleteTask();
                                    }
                                    delUser.execute(ApplicationController.getCurrUser().getUsername());
                                    dialog.dismiss();
                                    ApplicationController.clearUser();
                                    Intent welcomeIntent =
                                            new Intent(UserProfile.this,
                                                    WelcomeActivity.class);
                                    UserProfile.this.startActivity(welcomeIntent);
                                }
                                catch (Exception e) {
                                    Log.i("E","No tasks found to delete for user");
                                }
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AppCompatDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /** Listner to see if the button was clicked for saving the data
     *  If the button was clicked, save all the data to elastic search
     *
     * @param etFirstName; editText FirstName field
     * @param etLastName; editText LastName field
     * @param etEmail; editText email field
     * @param etPhone; editText phone field
     * @param etAddress; editText address field
     * @param bSave; button for saving the account
     * @param j; JsonHandler that has the account
     */
    private void saveNewData(final EditText etFirstName, final EditText etLastName,
                             final EditText etEmail, final EditText etPhone,
                             final EditText etAddress, Button bSave, final JsonHandler j) {
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserAccount account = ApplicationController.getCurrUser();
                ElasticsearchController.UpdateUser updateUser =
                        new ElasticsearchController.UpdateUser();

                account.setFirstName(etFirstName.getText().toString());
                account.setLastName(etLastName.getText().toString());
                account.setEmailAdd(etEmail.getText().toString());
                account.setPhoneNum(etPhone.getText().toString());
                account.setAddress(etAddress.getText().toString());

                updateUser.execute(account);
                j.dumpUser(account);

                Intent mainIntent = new Intent(UserProfile.this, MainMenuActivity.class);
                UserProfile.this.startActivity(mainIntent);
            }
        });
    }
}
