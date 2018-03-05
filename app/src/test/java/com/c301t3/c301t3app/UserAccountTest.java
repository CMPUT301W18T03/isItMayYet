package com.c301t3.c301t3app;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class UserAccountTest {

    @Test
    public void testSetUsernameSuccess() {
        String user1 = "dawae";
        UserAccount test = new UserAccount();
        test.setUsername(user1);

        assertTrue(test.getUsername()==user1);
    }

    @Test
    public void testSetUsernameIllegalLength() {
        String user1 = "doyouknowdawaeonetwothreefourfivesix"; // current max is 8
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

    @Test
    public void testSetFullNameSuccess() {
        String full1 = "Ash Ketchum";
        UserAccount test = new UserAccount();
        test.setFullName(full1);

        assertTrue(test.getFullName()==full1);
    }

    @Test
    public void testSetEmailAddressSuccess() {
        String eAdd1 = "memesneverdie@gmail.com";
        UserAccount test = new UserAccount();
        test.setEmailAdd(eAdd1);

        assertTrue(test.getEmailAdd()==eAdd1);
    }

    @Test
    public void testSetAddressSuccess() {
        String addr1 = "Do You Know Da Wae";
        UserAccount test = new UserAccount();
        test.setAddress(addr1);

        assertTrue(test.getAddress()==addr1);
    }

    @Test
    public void testSetPhoneNumSuccess() {
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount();
        test.setPhoneNum(pNum1);

        assertTrue(test.getPhoneNum()==pNum1);
    }

    @Test
    public void testUserAccountCreationSuccess() {
        String user1 = "dawae";
        String full1 = "Knuc Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";

        UserAccount test = new UserAccount(user1,full1,eAdd1,addr1,pNum1);

        assertTrue(test.getUsername()==user1);
        assertTrue(test.getFullName()==full1);
        assertTrue(test.getEmailAdd()==eAdd1);
        assertTrue(test.getAddress()==addr1);
        assertTrue(test.getPhoneNum()==pNum1);
    }

}
