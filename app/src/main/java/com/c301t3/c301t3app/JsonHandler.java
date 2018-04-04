package com.c301t3.c301t3app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

/**
 * Created by jonah on 2018-03-16.
 * Class for JSON local storage
 */

public class JsonHandler {
    private static Gson gson = new Gson();

    private static String taskQueuePath = "/queue.json";
    private static String userDataPath = "/user.json";
    private static String userTaskPath = "/tasks.json";

    private File taskQueue;
    private File userData;
    private File userTask;

    /**
     * Outlines the structure and initalize Json Handler.
     * @param context application context
     */

    public JsonHandler(Context context) {
        if(context == null) {
            taskQueue = new File(taskQueuePath);
            userData = new File(userDataPath);
            userTask = new File(userTaskPath);
        } else {
            taskQueue = new File(context.getFilesDir(), taskQueuePath);
            userData = new File(context.getFilesDir(), userDataPath);
            userTask = new File(context.getFilesDir(), userTaskPath);
        }
        try {
            boolean newQueue = taskQueue.createNewFile();
            boolean newUser = userData.createNewFile();
            boolean newTasks = userTask.createNewFile();
            if (newQueue || newTasks) {
                ArrayList<Task> emptyTasks = new ArrayList<>();
                String repr = gson.toJson(emptyTasks);
                if (newQueue) {
                    FileWriter writer = new FileWriter(taskQueue);
                    writer.write(repr);
                    writer.close();
                }
                if (newTasks) {
                    FileWriter writer = new FileWriter(userTask);
                    writer.write(repr);
                    writer.close();
                }
            }
            if (newUser) {
                UserAccount emptyUser = new UserAccount();
                String repr = gson.toJson(emptyUser);
                FileWriter writer = new FileWriter(userData);
                writer.write(repr);
                writer.close();
            }
        } catch (java.io.IOException e) {
            Log.e("JSONError", e.getMessage());
        }
    }

    /**
     * Puts user account into gson format.
     * @param u this is a user account
     */

    public void dumpUser(UserAccount u) {
        String repr = gson.toJson(u);
        try {
            FileWriter writer = new FileWriter(userData);
            writer.write(repr);
            writer.close();
        } catch (java.io.IOException e) {
            Log.e("IOError", e.getMessage());
        }
    }

    /**
     * Retrieves user account from gson, into a UserAccount object
     * @return UserAccount object
     */

    public UserAccount loadUser() {
        FileReader file = null;
        try {
            file = new FileReader(userData);
        } catch (java.io.FileNotFoundException e) {
            Log.e("IOError", e.getMessage());
        }
        BufferedReader reader = new BufferedReader(file);
        UserAccount u = gson.fromJson(reader, UserAccount.class);
        return u;
    }

    /**
     * Creates a gson object to wirte that is tasks.
     * @param t Tasks object
     */

    public void dumpUserTasks(ArrayList<Task> t) {
        String repr = gson.toJson(t);
        try {
            FileWriter writer = new FileWriter(userTask);
            writer.write(repr);
            writer.close();
        } catch (java.io.IOException e) {
            Log.e("IOError", e.getMessage());
        }
    }

    /**
     * Loads a user task from gson to an ArrayList type.
     * @return object of type ArrayList
     */

    public ArrayList<Task> loadUserTasks() {
        FileReader file = null;
        try {
            file = new FileReader(userTask);
        } catch (java.io.FileNotFoundException e) {
            Log.e("IOError", e.getMessage());
        }
        BufferedReader reader = new BufferedReader(file);
        ArrayList<Task> t = gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
        return t;
    }

    /**
     * If full, makes a queue and dumps it to a queue.
     * @param t ArrayList type
     */

    public void dumpTaskToQueue(Task t) {
        ArrayList<Task> q = this.loadTaskQueue();
        if(q == null) {
            q = new ArrayList<>();
        }
        q.add(t);
        String repr = gson.toJson(q);
        try {
            FileWriter writer = new FileWriter(taskQueue);
            writer.write(repr);
            writer.close();
        } catch (java.io.IOException e) {
            Log.e("IOError", e.getMessage());
        }
    }

    /**
     * Load task from Queue
     * @return ArrayList of tasks.
     */

    public ArrayList<Task> loadTaskQueue() {
        FileReader file = null;
        try {
            file = new FileReader(taskQueue);
        } catch (java.io.FileNotFoundException e) {
            Log.e("IOError", e.getMessage());
        }
        BufferedReader reader = new BufferedReader(file);
        ArrayList<Task> t = gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
        return t;
    }
}