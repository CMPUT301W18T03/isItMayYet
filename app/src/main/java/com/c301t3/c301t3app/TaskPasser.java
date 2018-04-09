/*
 * TaskPasser
 *
 * March 7, 2018
 *
 * Copyright 2018 c301t3, isItMayYet
 */

package com.c301t3.c301t3app;

import java.util.ArrayList;

/**
 * Singleton for passing data between tasks
 *
 * @author Jonah
 * @version 3.02
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
    
}
