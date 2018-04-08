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

    public void testLogin() {

        ElasticsearchController.AddUser addUser = new ElasticsearchController.AddUser();

        UserAccount user1 = new UserAccount();
        user1.setUsername("testme");
        user1.setPassword("tested");

        addUser.execute(user1);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
        solo.enterText(0,"testme");
        solo.enterText(1,"tested");
        solo.clickOnView(solo.getView(R.id.bSignIn));

        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
        delUser.execute(user1.getUsername());
    }

    public void testSendToRegister() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity",SimpleLoginActivity.class);
        solo.clickOnView(solo.getView(R.id.tvRegisterHere));

        solo.assertCurrentActivity("Wrong activity", RegisterActivity.class);
    }

    public void testLoginNonExisting() {
        ElasticsearchController.AddUser addUser = new ElasticsearchController.AddUser();

        UserAccount user1 = new UserAccount();
        user1.setUsername("toasty");
        user1.setPassword("tested");

        addUser.execute(user1);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
        solo.enterText(0,"testme");
        solo.enterText(1,"tested");
        solo.clickOnView(solo.getView(R.id.bSignIn));
        solo.waitForText("not found");

        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
        delUser.execute(user1.getUsername());
    }

    public void testLoginWrongPassword() {
        ElasticsearchController.AddUser addUser = new ElasticsearchController.AddUser();

        UserAccount user1 = new UserAccount();
        user1.setUsername("testme");
        user1.setPassword("tested");

        addUser.execute(user1);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", SimpleLoginActivity.class);
        solo.enterText(0,"testme");
        solo.enterText(1,"testedit");
        solo.clickOnView(solo.getView(R.id.bSignIn));
        solo.waitForText("incorrect");

        ElasticsearchController.DeleteUser delUser = new ElasticsearchController.DeleteUser();
        delUser.execute(user1.getUsername());
    }
}
