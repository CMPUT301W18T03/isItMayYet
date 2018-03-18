package com.c301t3.c301t3app;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import android.util.Log;

/**
 * Created by jonah on 2018-03-16.
 * Class for JSON local storage
 */

public class JsonHandler {
    private static Gson gson = new Gson();

    private static String taskQueuePath = "queue.json";
    private static String userDataPath = "user.json";
    private static String userTaskPath = "tasks.json";

    private FileWriter taskQueue;
    private FileWriter userData;
    private FileWriter userTask;

    private FileReader taskQueueR;
    private FileReader userDataR;
    private FileReader userTaskR;

    public JsonHandler() {
        try {
            taskQueue = new FileWriter(taskQueuePath, false);
            userData = new FileWriter(userDataPath, false);
            userTask = new FileWriter(userTaskPath, false);

            taskQueueR = new FileReader(taskQueuePath);
            userDataR = new FileReader(userDataPath);
            userTaskR = new FileReader(userTaskPath);
        } catch (java.io.IOException e) {
            Log.e("IOError", "Unable to open local stored objects");
        }
    }

    public void dumpUser(UserAccount u) {
        String repr = gson.toJson(u);
        try {
            userData.write(repr);
            userData.flush();
        } catch (java.io.IOException e) {
            Log.e("IOError", "Unable to open local stored objects");
        }
    }

    public UserAccount loadUser() {
            BufferedReader reader = new BufferedReader(userDataR);
            UserAccount u = gson.fromJson(reader, UserAccount.class);
            return u;
    }
     /*

    public void dumpUserTasks(ArrayList<Task> t) {

    }

    public ArrayList<Task> loadUserTasks() {

    }

    public void dumpTaskToQueue(Task t) {

    }

    public ArrayList<Task> loadTaskQueue() {

    }
    */
}
