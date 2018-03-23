package com.c301t3.c301t3app;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Kvongaza on 2018-03-23.
 */

public class ElasticsearchControllerTest {

    @Test
    public void taskDumpTest() {
        Task t1 = new Task();
        t1.setName("Carry me to diamond");


        ElasticsearchController.AddTask addTask = new ElasticsearchController.AddTask();
        addTask.execute(t1);

        ElasticsearchController.GetTask getTask = new ElasticsearchController.GetTask();

        ArrayList<Task> r1 = getTask.doInBackground("diamond");

        Task t2 = r1.get(0);

        assertTrue(t1.getName().equals(t2.getName()));

    }
}
