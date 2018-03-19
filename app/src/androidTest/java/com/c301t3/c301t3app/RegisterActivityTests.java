package com.c301t3.c301t3app;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class RegisterActivityTests extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public RegisterActivityTests() {super(RegisterActivity.class);}

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void TestEmptyRegister() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
        solo.clickOnView(solo.getView(R.id.bCreate));

        solo.waitForText("Please fill in all the fields"); // message from toast on empty register
    }

    public void TestPasswordMismatch() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
        solo.enterText(0,"username");
        solo.enterText(1,"firstname");
        solo.enterText(2,"lastname");
        solo.enterText(3,"email");
        solo.enterText(4,"7807807000");
        solo.enterText(5,"password1");
        solo.enterText(6,"password2");
        solo.clickOnView(solo.getView(R.id.bCreate));

        solo.waitForText("Password does not match");
    }
}
