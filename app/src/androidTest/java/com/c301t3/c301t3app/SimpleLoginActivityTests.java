package com.c301t3.c301t3app;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class SimpleLoginActivityTests extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public SimpleLoginActivityTests() {super(SimpleLoginActivity.class);}

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testLogin() { // just hitting login even with empty fields should bring to main menu
        //TODO: update when login is functional for accounts after ES
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
        solo.clickOnView(solo.getView(R.id.bSignIn));

        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
    }

    public void testRegister() {
        //TODO: update when registering is implemented after ES
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity",SimpleLoginActivity.class);
        solo.clickOnView(solo.getView(R.id.tvRegisterHere));

        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
    }
}
