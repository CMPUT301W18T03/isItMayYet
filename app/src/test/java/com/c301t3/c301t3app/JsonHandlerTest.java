package com.c301t3.c301t3app;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

public class JsonHandlerTest {

    @Test
    public void userWriteTest() {
        UserAccount u = new UserAccount("dva",
                "hana",
                "song",
                "hana@gg.kr",
                "101 EZ Street",
                "420-696-1337",
                "noh4x",
                20);
        JsonHandler j = new JsonHandler(null);
        j.dumpUser(u);
        UserAccount v = j.loadUser();
        assertTrue(v.getFirstName().equals(u.getFirstName()));
    }

    @Test
    public void taskWriteTest() {
        ArrayList<Task> a = new ArrayList<>();
        Task t1 = new Task();
        t1.setName("Carry me to diamond");
        Task t2 = new Task();
        t2.setName("Make me a halloween costume");
        a.add(t1);
        a.add(t2);
        JsonHandler j = new JsonHandler(null);
        j.dumpUserTasks(a);
        ArrayList<Task> l = j.loadUserTasks();
        for(int i = 0; i < a.size(); i++) {
            assertTrue(a.get(i).getName().equals(l.get(i).getName()));
        }
    }

    @Test
    public void taskQueueTest() {
        Task t = new Task();
        t.setName("Finish my degree for me");
        JsonHandler j = new JsonHandler(null);
        j.dumpTaskToQueue(t);
        ArrayList<Task> a = j.loadTaskQueue();
        assertTrue(a.get(0).getName().equals(t.getName()));
    }
}
