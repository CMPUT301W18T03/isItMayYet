package com.c301t3.c301t3app;

import org.junit.Test;

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
        JsonHandler j = new JsonHandler();
        j.dumpUser(u);
        UserAccount v = j.loadUser();
        assertTrue(v.getFirstName().equals(u.getFirstName()));
    }
}
