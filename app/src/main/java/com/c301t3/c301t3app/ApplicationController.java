package com.c301t3.c301t3app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Application global variables and user control
 */

public class ApplicationController {
    public final static int MAX_USERNAME_LENGTH = 32; //TODO: update as necessary
    public final static int MAX_TASK_DESC_LENGTH = 300; //TODO: update as necessary
    public final static int MAX_TASK_NAME_LENGTH = 30; //TODO: update as necessary
    public final static int MAX_PHOTO_BYTESIZE = 65536; //TODO: update as necessary
    public static Context c;


    static UserAccount currUser;

    /**
     * default constructor
     * @param context creates context for the application.
     */
    public ApplicationController(Context context) {
        c = context;
    }

    /**
     * Gets the current user account logged in
     * @return UserAccount
     */
    static public UserAccount getCurrUser(){
        return currUser;
    }

    /**
     * Sets the current user account on log in (for SimpleLoginActivity)
     * @param user (UserAccount being logged in)
     */
    static public void setUser(UserAccount user){
        currUser = user;
    }

    /**
     * Clears the current user account (log out)
     */
    static public void clearUser(){
        currUser = null;
    }


    /**
     * For checking online connection
     * Taken off of https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     * 2018-04-03
     */
    static public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
