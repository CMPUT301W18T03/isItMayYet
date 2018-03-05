package com.c301t3.c301t3app;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Henry on 2018-03-04.
 */

public class TaskListTest {

    public void failTest(String msg) { Assert.fail(msg); }

    @Test
    // Tests if creating a task using "new TaskList()" is possible.
    public void testTaskListCreation() {
        String assert_message = "TaskList object TaskList() cannot be created";
        try {   // Expectation, try() attempt will succeed with no exceptions thrown
            TaskList tasklist = new TaskList();
        } catch (Exception e) {
            failTest(assert_message);
        }
    }

    @Test
    // Tests if creating a task with an arraylist as an argument is possible.
    public void testTaskListCreationWithArrayList() {
        String assert_message = "TaskList object TaskList() cannot be created with ArrayList";
        Task task = new Task();
        ArrayList<Task> arraylist = new ArrayList<Task>();
        arraylist.add(task);
        try {   // Expectation, try() attempt will succeed with no exceptions thrown
            TaskList tasklist = new TaskList(arraylist);
        } catch (Exception e) {
            failTest(assert_message);
        }
    }

    @Test
    // Tests if adding a task in tasklist is possible and orders them like an ArrayList<> would.
    public void testAddTask() {
        TaskList tasklist = new TaskList();
        Task task0 = new Task();
        Task task1 = new Task();
        tasklist.addTask(task0);
        Assert.assertEquals(tasklist.getTask(0), task0);
        tasklist.addTask(task1);
        Assert.assertEquals(tasklist.getTask(1), task1);
        Assert.assertEquals(tasklist.getTask(0), task0);
    }

    @Test
    // Tests if deleting a task in tasklist is possible while still preserving order.
    public void testDelTask() {
        TaskList tasklist = new TaskList();
        Task task0 = new Task();
        Task task1 = new Task();
        Task task2 = new Task();
        tasklist.addTask(task0);
        tasklist.addTask(task1);
        tasklist.addTask(task2);
        tasklist.delTask(0);
        Assert.assertEquals(tasklist.getTask(0), task1);
        tasklist.delTask(1);
        Assert.assertEquals(tasklist.getTask(0), task1);
    }

    @Test
    // Tests if setting a task in tasklist is possible while preserving order and allows duplicates.
    public void testSetTask() {
        TaskList tasklist = new TaskList();
        Task task0 = new Task();
        Task task1 = new Task();
        Task task2 = new Task();
        tasklist.addTask(task0);
        tasklist.addTask(task1);
        tasklist.addTask(task2);
        tasklist.setTask(0, task2);
        Assert.assertEquals(tasklist.getTask(0), task2);
        tasklist.setTask(1, task1);
        Assert.assertEquals(tasklist.getTask(1), task1);
        tasklist.setTask(1, task2);
        Assert.assertEquals(tasklist.getTask(1), task2);
    }

    @Test
    // Tests if .getArrayList() returns an ArrayList<> and reflects the same way to how its
    // initialized, constructed, and modified
    public void testGetTaskList() {
        TaskList tasklist = new TaskList();
        ArrayList<Task> arraylist = new ArrayList<Task>();
        Assert.assertEquals(tasklist.getTaskList(), arraylist);
        Task task0 = new Task();
        Task task1 = new Task();
        Task task2 = new Task();
        tasklist.addTask(task0);
        tasklist.addTask(task1);
        tasklist.addTask(task2);
        arraylist.add(task0);
        arraylist.add(task1);
        arraylist.add(task2);
        Assert.assertEquals(tasklist.getTaskList(), arraylist);
        tasklist.delTask(1);
        arraylist.remove(1);
        Assert.assertEquals(tasklist.getTaskList(), arraylist);
        tasklist = new TaskList(arraylist);
        Assert.assertEquals(tasklist.getTaskList(), arraylist);
    }

}
