package com.c301t3.c301t3app;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Henry on 2018-02-23.
 */

public class TaskTest {

    public void failTest(String msg) {
        Assert.fail(msg);
    }

    @Test
    // Tests if creating a task using "new Task()" is possible.
    public void testTaskCreation() {
        String assert_message = "task object task() cannot be created";
        try {   // Expectation, try {} attempt will succeed with no exceptions caught
            Task task = new Task();
        } catch (Exception e) {
            failTest(assert_message);
        }
    }

    @Test
    // Tests if setting a name in task using "task.setName" is possible (under 30 chars).
    public void testSetName() {
        String name = "task name";  // max for the name is 30 chars
        Task task = new Task();
        task.setName(name);
        // Ensures that setName worked if the fetched name set in task and name set matches
        Assert.assertEquals(task.getName(), name);
    }

    @Test
    // Tests if setting a name with an empty string into a
    // task object will throw an IllegalArgumentException.
    public void testSetEmptyName() {
        Task task = new Task();
        String EmptyString = "";
        try {   // try {} statement excepted to fail and catch an IllegalArgumentException
            task.setName(EmptyString);
            Assert.fail("Setting name with an empty string of more than 300 characters " +
                    "failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    // Tests if setting a name in task using a String with exactly 30 chars will succeed.
    public void testSetNameAt30Chars() {
        String name = "0123456789";
        name = name + name + name;  // This is to get to 30 characters exactly
        Task task = new Task();
        try {   // try {} statement is expected to succeed without any exceptions caught
            task.setName(name);
        } catch (Exception e) {
            Assert.fail("Setting name with a string of exactly 30 characters failed");
        }
    }

    @Test
    // Tests if setting a name in task with a name over 30 chars will throw IllegalArgumentException
    public void testSetNameTooLongException() {
        String name = "0123456789";
        name = name + name + name + "A";  // This is to get over 30 characters.
        Task task = new Task();
        try {   // try {} statement is expected to fail with an IllegalArgumentException caught
            task.setName(name);
            Assert.fail("Setting name with a string of more than 30 characters " +
                    "failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    // Tests if setting a description (String under/equal 300 chars) in a task object is possible.
    public void testSetDescription() {
        String description = "task description";    // Max 300 chars
        Task task = new Task();
        task.setDescription(description);
        // Expects the description fetched from task is equal to description used in setDescription
        Assert.assertEquals(task.getDescription(), description);
    }

    @Test
    // Tests if setting a description with an empty string into a
    // task object will throw an IllegalArgumentException.
    public void testSetEmptyDescription() {
        Task task = new Task();
        String EmptyString = "";
        try {   // try {} statement excepted to fail and catch an IllegalArgumentException
            task.setDescription(EmptyString);
            Assert.fail("Setting description with an empty string of more than 300 characters " +
                    "failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    // Tests if setting a description (String with exactly 300 chars) to a task object will succeed
    public void testSetDescriptionAt300Chars() {
        String description10 = "0123456789";
        String description300 = "";
        for (int i = 0; i < 30; i++) {
            // for loop is to generate a description with a char length of 300 chars
            description300 += description10;
        }
        Task task = new Task();
        try {   // try {} statement is expected to succeed with no exceptions caught
            task.setDescription(description300);
        } catch (Exception e) {
            Assert.fail("Setting a description with a String of 300 characters failed");
        }
    }

    @Test
    // Tests if setting a description over 300 chars into a
    // task object will throw an IllegalArgumentException.
    public void testSetDescriptionTooLongException() {
        String description10 = "0123456789";
        String description301 = "";
        for (int i = 0; i < 30; i++) {
            // For loop is meant to construct description301 to have a char length of 300 chars
            description301 += description10;
        }
        description301 += "A";  // Adding "A" is to bring description301 just over 300 chars
        Task task = new Task();
        try {   // try {} statement expected to fail and catch an IllegalArgumentException
            task.setDescription(description301);
            Assert.fail("Setting description with a string of more than 300 characters " +
                    "failed to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    // Tests if setting a status to task using "setStatus" is possible.
    public void testSetStatus() {
        String status = "task status";
        Task task = new Task();
        task.setStatus(status);
        // Expects that task's fetched status is equal to the status used to set task's status
        Assert.assertEquals(task.getStatus(), status);
    }

}
