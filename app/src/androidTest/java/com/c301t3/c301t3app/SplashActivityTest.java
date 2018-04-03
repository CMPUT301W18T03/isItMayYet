package com.c301t3.c301t3app;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class SplashActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public SplashActivityTest() {super(SplashActivity.class);}

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testUserExisting() {
        UserAccount user = new UserAccount();
        user.setUsername("lambda");
        ApplicationController.setUser(user);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.waitForActivity(MainMenuActivity.class);
    }

    public void testUserNew() {
        UserAccount user = new UserAccount();
        ApplicationController.setUser(user);
        ApplicationController.clearUser();

        solo = new Solo(getInstrumentation(),getActivity());
        solo.waitForActivity(WelcomeActivity.class);
    }

}
