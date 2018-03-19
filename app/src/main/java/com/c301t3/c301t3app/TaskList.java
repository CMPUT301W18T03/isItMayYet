package com.c301t3.c301t3app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Henry on 2018-03-03.
 */

public class TaskList implements Serializable {

    private ArrayList<Task> tasklist;

    TaskList() {
        this.tasklist = new ArrayList<Task>();
    }

    TaskList(ArrayList<Task> tasklist) {
        this();
        this.tasklist = tasklist;
    }

    public void addTask(Task task) {
        this.tasklist.add(task);
    }

    public Task getTask(int i) {
        return this.tasklist.get(i);
    }

    public void delTask(int i) {
        this.tasklist.remove(i);
    }

    public void setTask(int i, Task task) {
        this.tasklist.set(i, task);
    }

    public ArrayList<Task> getTaskList() {
        return this.tasklist;
    }


}
