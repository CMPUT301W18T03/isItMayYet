package com.c301t3.c301t3app;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

public class UserProfileTests extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public UserProfileTests() {super(UserProfile.class);}

    @Override
    public void setUp() throws Exception {
        UserAccount user1 = new UserAccount();
        user1.setUsername("userHere");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmailAdd("email@add.com");
        user1.setPhoneNum("1234567890");
        user1.setPassword("myPassword");

        ElasticsearchController.AddUser addUser = new ElasticsearchController.AddUser();
        addUser.execute(user1);
        ApplicationController.setUser(user1);

        Thread.sleep(1000);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testUserProfile() {

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", UserProfile.class);
        assertTrue(solo.waitForText("userHere"));
        assertTrue(solo.waitForText("firstName"));
        assertTrue(solo.waitForText("lastName"));
        assertTrue(solo.waitForText("email@add.com"));
        assertTrue(solo.waitForText("1234567890"));

        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
        delUser.execute("userHere");

        ApplicationController.clearUser();

    }

    public void testUserProfileUpdate() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", UserProfile.class);
        assertTrue(solo.waitForText("userHere"));
        assertTrue(solo.waitForText("firstName"));
        assertTrue(solo.waitForText("lastName"));
        assertTrue(solo.waitForText("email@add.com"));
        assertTrue(solo.waitForText("1234567890"));

        solo.enterText(4,"Hill");
        solo.clickOnText("Save");

        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.sendKey(Solo.MENU);
        solo.sleep(10);
        solo.clickOnText("Account");
        solo.sleep(10);
        solo.clickOnText("Profile");

        solo.assertCurrentActivity("Wrong activity", UserProfile.class);
        solo.waitForText("Hill");

        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
        delUser.execute("userHere");

        ApplicationController.clearUser();
    }

    public void testUserProfileDelete() {

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", UserProfile.class);
        assertTrue(solo.waitForText("userHere"));
        assertTrue(solo.waitForText("firstName"));
        assertTrue(solo.waitForText("lastName"));
        assertTrue(solo.waitForText("email@add.com"));
        assertTrue(solo.waitForText("1234567890"));

        solo.clickOnText("Delete");
        solo.sleep(2);
        solo.clickOnText("Confirm");

        solo.assertCurrentActivity("Wrong activity", WelcomeActivity.class);

    }
}
