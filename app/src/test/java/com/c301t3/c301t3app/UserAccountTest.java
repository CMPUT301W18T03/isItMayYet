package com.c301t3.c301t3app;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class UserAccountTest {

    @Test
    public void testSetUsernameSuccess() {
        String user1 = "one";
        UserAccount test = new UserAccount();
        test.setUsername(user1);

        assertTrue(test.getUsername()==user1);
    }

    @Test
    public void testSetUsernameIllegalLength() {
        String user1 = "onetwothreefourfivesix"; // current max is 8
        UserAccount test = new UserAccount();

        try { // exception expected
            test.setUsername(user1);
        }
        catch (IllegalArgumentException e) {
            assertTrue(Boolean.TRUE);
            return;
        }

        assertTrue(Boolean.FALSE); // failure to create exception
    }

    

}
