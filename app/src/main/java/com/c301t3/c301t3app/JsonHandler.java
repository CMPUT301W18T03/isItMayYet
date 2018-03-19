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

    private static String taskQueuePath = "queue.json";
    private static String userDataPath = "user.json";
    private static String userTaskPath = "tasks.json";

    private File taskQueue;
    private File userData;
    private File userTask;

    public JsonHandler(Context context) {
        if(context == null) {
            taskQueue = new File(taskQueuePath);
            userData = new File(userDataPath);
            userTask = new File(userDataPath);
        } else {
            taskQueue = new File(context.getFilesDir(), taskQueuePath);
            userData = new File(context.getFilesDir(), userDataPath);
            userTask = new File(context.getFilesDir(), userDataPath);
        }
    }

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