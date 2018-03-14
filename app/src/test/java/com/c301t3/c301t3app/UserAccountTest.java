package com.c301t3.c301t3app;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class UserAccountTest {

    @Test
    public void testSetUsernameSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setUsername(user1);

        assertTrue(test.getUsername().equals(user1));
    }


    @Test
    public void testSetUsernameIllegalLength() {
        String user1 = "doyouknowdawaeonetwothreefourfivesix"; // current max is 32
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);

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
    public void testSetFirstNameSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setFirstName(first1);

        assertTrue(test.getFirstName().equals(first1));
    }

    @Test
    public void testSetLastNameSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setFirstName(last1);

        assertTrue(test.getFirstName().equals(last1));
    }

    @Test
    public void testSetEmailAddressSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setEmailAdd(eAdd1);

        assertTrue(test.getEmailAdd().equals(eAdd1));
    }

    @Test
    public void testSetAddressSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setAddress(addr1);

        assertTrue(test.getAddress().equals(addr1));
    }

    @Test
    public void testSetPhoneNumSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";
        UserAccount test = new UserAccount(user1, first1, last1, eAdd1, addr1, pNum1);
        test.setPhoneNum(pNum1);

        assertTrue(test.getPhoneNum().equals(pNum1));
    }

    @Test
    public void testUserAccountCreationSuccess() {
        String user1 = "dawae";
        String first1 = "Knuc";
        String last1 =  "Kles";
        String eAdd1 = "doyouknowdawae@gmail.com";
        String addr1 = "Show Me Da Wae";
        String pNum1 = "780-780-7800";

        UserAccount test = new UserAccount(user1,first1,last1, eAdd1,addr1,pNum1);

        assertTrue(test.getUsername().equals(user1));
        assertTrue(test.getFirstName().equals(first1));
        assertTrue(test.getLastName().equals(last1));
        assertTrue(test.getEmailAdd().equals(eAdd1));
        assertTrue(test.getAddress().equals(addr1));
        assertTrue(test.getPhoneNum().equals(pNum1));
    }

}
