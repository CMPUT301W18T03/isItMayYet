package com.c301t3.c301t3app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Henry on 2018-03-03.
 * Adapter to user for list views
 */

/** TaskList is a list of tasks
 *
 */
public class TaskList implements Serializable {

    private ArrayList<Task> tasklist;

    /** Constructor, creates a new empty ArrayList
     */
    TaskList() {
        this.tasklist = new ArrayList<Task>();
    }

    /** Constructor, creates a new ArrayList with the data in tasklist
     *
     * @param tasklist; the current list of tasks
     */
    TaskList(ArrayList<Task> tasklist) {
        this();
        this.tasklist = tasklist;
    }

    /** Adds a task to the taskList
     *
     * @param task; task to add to the taskList
     */
    public void addTask(Task task) {
        this.tasklist.add(task);
    }

    /** Returns the task in the list at index i
     *
     * @param i; the index of task you are looking for
     * @return task; the task at index i
     */
    public Task getTask(int i) {
        return this.tasklist.get(i);
    }

    /** Removes a task at index i
     *
     * @param i; the index of a task to delete
     */
    public void delTask(int i) {
        this.tasklist.remove(i);
    }

    /** Set the task at index i
     *
     * @param i; the index of task you are looking to set
     * @param task; the task to be set at index i
     */
    public void setTask(int i, Task task) {
        this.tasklist.set(i, task);
    }

    /** Return the entire arrayList
     *
     * @return arrayList; the entire arrayList of tasks
     */
    public ArrayList<Task> getTaskList() {
        return this.tasklist;
    }


}
