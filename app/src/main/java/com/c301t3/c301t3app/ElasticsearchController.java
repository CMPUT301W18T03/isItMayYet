package com.c301t3.c301t3app;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by Kvongaza and Jquist on 2018-03-23.
 */

public class ElasticsearchController {
    private static JestDroidClient client;

    //############################################################################################
    //############################################################################################

    /**
     * Task methods for Elasticsearch
     */

    public static class AddTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task t : tasks) {
                Index index = new Index.Builder(t).index("cmput301w18t03").type("task").build();

                try {
                    // where is the client?
                    DocumentResult documentresult = client.execute(index);
                    if (documentresult.isSucceeded()) {
                        t.setId(documentresult.getId());
                    } else {
                        Log.e("Error", "Failed to get a result");
                    }

                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tasks");
                }
            }
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... ids) {
            verifySettings();
            Boolean result = true;

            for (String s : ids) {
                String query = "{\"query\": {\"match\": {\"_id\":\"" + s + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query).addIndex("cmput301w18t03").addType("task").build();

                try {
                    JestResult j = client.execute(delete);
                    result = result && j.isSucceeded();
                } catch (Exception e) {
                    Log.i("Error", e.getMessage().toString());
                }

            }
            return result;
        }
    }

    public static Boolean deleteTaskByID(String... ids) {
        if (!checkOnline()) return false;

        ElasticsearchController.DeleteTask deleteTask = new ElasticsearchController.DeleteTask();
        deleteTask.execute(ids);
        Boolean success;
        try {
            success = deleteTask.get();
        } catch (InterruptedException e) {
            Log.e("Error", e.getMessage().toString());
            success = false;
        } catch (ExecutionException e) {
            Log.e("Error", e.getMessage().toString());
            success = false;
        }
        return success;
    }

    public static class GetTask extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Task> tasks = new ArrayList<Task>();

//          TODO: Make an actual query parser.
            String query = "{\"query\": {\"bool\": {\"must\": [";
            for (String s : search_parameters) {
                query += "{ \"match\": { \"name\": \"";
                query += s;
                query += "\" } }, ";
            }
            query = query.substring(0, query.length() - 2);
            query += " ] } } }";
            // String query = "{\"query\": {\"match\": " + terms + "}}";
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t03")
                    .addType("task")
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> returnTask = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(returnTask);
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tasks;
        }
    }

    public static boolean taskToServer(Task t) {
        if (!checkOnline()) return false; //check if connected to network

        ElasticsearchController.AddTask addTask = new ElasticsearchController.AddTask();
        addTask.execute(t);
        return true;
    }

    public static ArrayList<Task> serverTaskQuery(String... params) {
        if (!checkOnline()) return null; //check if connected to network

        ElasticsearchController.GetTask getTask = new ElasticsearchController.GetTask();
        try {
            getTask.execute(params);
            return getTask.get();
        } catch (InterruptedException e) {
            Log.e("E", "Server access interrupted");
        } catch (ExecutionException e) {
            Log.e("E", e.getMessage().toString());
        }
        return null;
    }

    //############################################################################################
    //############################################################################################

    /**
     * User methods for Elasticsearch
     */

    public static class AddUser extends AsyncTask<UserAccount, Void, String> {

        @Override
        protected String doInBackground(UserAccount... user) {
            verifySettings();
            String userID = new String();

            for (UserAccount u : user) {
                Index index = new Index.Builder(u).index("cmput301w18t03").type("user").build();

                try {
                    // where is the client?
                    DocumentResult documentresult = client.execute(index);
                    if (documentresult.isSucceeded()) {
                        u.setID(documentresult.getId());
                        Log.i("UserID", u.getID());
                        userID = documentresult.getId();
                    } else {
                        Log.e("Error", "Failed to get a result");
                    }
                } catch (Exception e) {
//                    Log.i("Error", "The application failed to build and send the user");
                    Log.i("Error", e.getMessage());
                }
            }
            return userID;
        }
    }

    public static boolean userToServer(UserAccount u) throws ExecutionException, InterruptedException {
        if (!checkOnline()) return false;

        ElasticsearchController.AddUser addUser = new ElasticsearchController.AddUser();
        addUser.execute(u);
        u.setID(addUser.get());
        return true;
    }

    //TODO: MODIFY THIS FOR GetUser
    public static class GetUserByUsername extends AsyncTask<String, Void, UserAccount> {
        @Override
        protected UserAccount doInBackground(String... search_parameters) {
            verifySettings();

            UserAccount user = new UserAccount();
            // TODO Build the query
            String query = "{\"query\": {\"match\": {\"username\": " + search_parameters[0] + "}}}";
//            String result = String.format(query, search_parameters);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t03")
                    .addType("task")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Task> returnTask = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(returnTask);
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tasks;
        }
    }

    // TODO: edit for query on users
//    public static ArrayList<Task> serverUserQuery(String... params) {

//        ElasticsearchController.GetUser getTask = new ElasticsearchController.GetUser();
//        try {
//            getUser.execute(params);
//            return getUser.get();
//        } catch (InterruptedException e) {
//            Log.e("E", "Server access interrupted");
//        } catch (ExecutionException e) {
//            Log.e("E", e.getMessage().toString());
//        }
//        return null;
//    }



    /**
     * Sync a user to elasticsearch server
     */
    public static class UpdateUser extends AsyncTask<UserAccount, Void, Boolean> {

        /**
         * Sync a user to Elasticsearch
         * @param user the user being synced
         * @return true if user synced
         */
        @Override
        protected Boolean doInBackground(UserAccount... user) {
            verifySettings();
            Boolean userSynced = Boolean.FALSE;
            for (UserAccount u : user) {
                try {
                    String userID = u.getID();
                    Index index = new Index.Builder(u).index("cmput301w18t03").type("user").id(userID).build();
                    DocumentResult documentresult = client.execute(index);
                    userSynced = documentresult.isSucceeded();
                    if (!userSynced) {
                        Log.i("Error", "Failed to sync user to Elasticsearch");
                    }
                    return userSynced;
                }
                catch (Exception e) {
                    Log.i("Error", "Application failed to sync the user");
                    return false;
                }
            }
            return userSynced;
        }
    }

    /**
     * Update an existing user in Elasticsearch
     * @param u the user being updated.
     */
    public static boolean userUpdateServer(UserAccount u) {
        if (!checkOnline()) return false;
        ElasticsearchController.UpdateUser user = new ElasticsearchController.UpdateUser();
        Boolean userUpdated;
        user.execute(u);
        try {
            userUpdated = user.get();
            //Do Gson stuff here.
            Gson gson = new Gson();
            Log.i("User Synced", gson.toJson(u));
            return userUpdated;
        } catch (Exception e) {
            Log.i("Error", "User failed to sync");
            return false;
        }
    }


    /**
     * Other methods for Elasticsearch
     */

    /**
     * method checks if online connection exists.
     * @return
     */
    public static boolean checkOnline() {
        return (ApplicationController.isOnline(ApplicationController.c));
    }

    //TODO: catch offline flag here?
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
