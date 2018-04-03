package com.c301t3.c301t3app;

/**
 * Application global variables and user control
 */

public class ApplicationController {
    static UserAccount currUser;


    static public UserAccount getCurrUser(){
        return currUser;
    }

    static public void setUser(UserAccount user){
        currUser = user;
    }

    static public void clearUser(){
        currUser = null;
    }

}
