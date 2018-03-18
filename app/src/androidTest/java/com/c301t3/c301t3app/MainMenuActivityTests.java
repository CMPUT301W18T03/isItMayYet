package com.c301t3.c301t3app;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class MainMenuActivityTests extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public MainMenuActivityTests() {super(MainMenuActivity.class);}

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSearch() { //tests on hardcoded sample tasks
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0, "Description for");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Task0");

        solo.clearEditText(0);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0,"desc");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Task0");
        solo.waitForText("Task1");

        solo.clearEditText(0);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0,"fluff");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Task3");
        solo.waitForText("Task4");
    }

    public void testSelectedTask() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0, "Description for");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Task0");
        solo.clickOnText("Task0");

        solo.assertCurrentActivity("Wrong activity", SelectedTaskActivity.class);

    }

    public void testAddTask() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.addTaskButton));

        solo.assertCurrentActivity("Wrong activity", NewTaskActivity.class);
    }
}
