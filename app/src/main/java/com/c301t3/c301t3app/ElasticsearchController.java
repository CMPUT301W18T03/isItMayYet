package com.c301t3.c301t3app;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by Kvongaza on 2018-03-23.
 */

public class ElasticsearchController {
    private static JestDroidClient client;

    //
    public static class AddTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task t : tasks) {
                Index index = new Index.Builder(tasks).index("cmput301w18t03").type("task").build();

                try {
                    // where is the client?
                    DocumentResult documentresult = client.execute(index);
                    if (documentresult.isSucceeded()){
                        t.setId(documentresult.getId());
                    }else{
                        Log.e("Error", "Failed to get a result");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tasks");
                }

            }
            return null;
        }
    }

    public static class GetTask extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {
            //verifySettings();

            ArrayList<Task> tasks = new ArrayList<Task>();

            // TODO Build the query
//            Search search = new Search.Builder(search_parameters[0]).addIndex("cmput301w18t03").addType("task").build(); // default

            String query = "{\"query\": {\"term\": {\"name\": %s}}}";
//            String result = String.format(query, search_parameters);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t03")
                    .addType("task")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Task> returnTweet = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(returnTweet);
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tasks;
        }
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
