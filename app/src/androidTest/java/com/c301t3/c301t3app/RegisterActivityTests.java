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

    public void testEmptyRegister() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
        solo.clickOnView(solo.getView(R.id.bCreate));

        solo.waitForText("Please fill in all the fields"); // message from toast on empty register
    }

    public void testPasswordMismatch() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
        solo.enterText(0,"username");
        solo.enterText(1,"email");
        solo.enterText(2,"7807800000");
        solo.enterText(3,"password1");
        solo.enterText(4,"password2");
        solo.enterText(5,"firstname");
        solo.enterText(6,"lastname");
        solo.clickOnView(solo.getView(R.id.bCreate));

        solo.waitForText("Password does not match"); // message from toast on non-matching pw
    }

    public void testAccountRegisterSuccess() { //TODO: implement ES
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
        solo.enterText(0,"username");
        solo.enterText(1,"email");
        solo.enterText(2,"7807800000");
        solo.enterText(3,"password1");
        solo.enterText(4,"password1");
        solo.enterText(5,"firstname");
        solo.enterText(6,"lastname");
        solo.clickOnView(solo.getView(R.id.bCreate));

        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
//        ElasticsearchController.deleteUser("username");
//        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
//        delUser.execute("username");
    }

    //TODO: test on registering
}
