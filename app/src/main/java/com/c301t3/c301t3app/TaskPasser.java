package com.c301t3.c301t3app;

import java.util.ArrayList;

/**
 * Created by jonah on 07/03/18.
 * Singleton for passing data between tasks
 */

public class TaskPasser {
    private static ArrayList<Task> tasks;

    /**
     * Create a new ArrayList when creating a TaskPasser
     */
    public TaskPasser() {
        tasks = new ArrayList<>();
    }
    /**
     * Get the ArrayList passed, ALL the items
     *
     * @return  tasks; the ArrayList of information to pass
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Set tasks to the ArrayList passed, t
     *
     * @param t; Set the tasks to t, disregarding all previous tasks
     */
    public void setTasks(ArrayList<Task> t) {
        tasks = t;
    }

    /**
    public Task getTask() {
        return tasks.get(0);
    }

    public void setTask(Task t) {
        tasks = new ArrayList<>();
        tasks.add(t);
    }
    */
}
