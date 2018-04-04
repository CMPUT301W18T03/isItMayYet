package com.c301t3.c301t3app;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class WelcomeActivityTests extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public WelcomeActivityTests() {super(WelcomeActivity.class);}

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testBrowse() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", WelcomeActivity.class);
        solo.clickOnView(solo.getView(R.id.button_GoToMainMenu));
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
    }

    public void testLogin() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", WelcomeActivity.class);
        solo.clickOnView(solo.getView(R.id.loginBtn));
        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
    }


}
