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

    public void testSearch() {

        ElasticsearchController.AddTask addTask =
                new ElasticsearchController.AddTask();

        Task task1 = new Task();
        task1.setName("Test this");
        task1.setDescription("Help me pass this project");
        task1.setId("test");
        task1.setOwnerName("ownerHere");

        addTask.execute(task1);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0, "help");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Test");

        ElasticsearchController.DeleteTask delTask =
                new ElasticsearchController.DeleteTask();
        delTask.execute("test");
    }

    public void testSelectedTask() {
        ElasticsearchController.AddTask addTask =
                new ElasticsearchController.AddTask();

        Task task1 = new Task();
        task1.setName("Test this");
        task1.setDescription("Help me pass this project");
        task1.setId("test");
        task1.setOwnerName("ownerHere");

        addTask.execute(task1);

        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.searchBar));
        solo.enterText(0, "help");
        solo.pressSoftKeyboardDoneButton();
        solo.waitForText("Test");
        solo.clickOnText("Test");

        solo.assertCurrentActivity("Wrong activity", SelectedTaskActivity.class);
        solo.waitForText("help");

        ElasticsearchController.DeleteTask delTask =
                new ElasticsearchController.DeleteTask();
        delTask.execute("test");
    }

    public void testAddTask() {
        solo = new Solo(getInstrumentation(),getActivity());
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.addTaskButton));

        solo.assertCurrentActivity("Wrong activity", NewTaskActivity.class);
    }
}
