package com.c301t3.c301t3app;

import java.util.ArrayList;

/**
 * Created by jonah on 07/03/18.
 * Singleton for passing data between tasks
 */

public class TaskPasser {
    private ArrayList<Task> tasks;

    public TaskPasser() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> t) {
        tasks = t;
    }
}
