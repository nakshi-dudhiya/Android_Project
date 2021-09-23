package com.example.camohawkdudhiya;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {

    private static String TAG ="Download Async";
    /**
     * Download data from the supplied URL, catch exceptions
     *
     * @param params - first parameter is the URL
     * @return a string that concatenates all of the output together, null on failure
     */
    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "Starting Background Task");
        StringBuilder results = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            String line = null;
            // Open the Connection - GET is the default setRequestMethod
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            // Read the response
            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(
                        conn.getInputStream());
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream,
                                "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    results.append(line);
                }
            }
            Log.d(TAG, "Data received = " + results.length());
            Log.d(TAG, "Response Code: " + statusCode);
        } catch (IOException ex) {
            Log.d(TAG, "Caught Exception: " + ex);
        }
        return results.toString();
    }

    /**
     * After download has completed associate, parse results
     * @param result - do nothing if results == null
     */
    protected void onPostExecute(String result) {
        FairList fairlist = null;
        if (result == null) {
            Log.d(TAG, "no results");
        } else {
            Gson gson = new Gson();
            fairlist = gson.fromJson(result, FairList.class);
        }
        // fetch the current activity so we can lookup the ListView
        Activity currentActivity = MainActivity2.getCurrentActivity();
        ListView lv = currentActivity.findViewById(R.id.listView);
        if (fairlist != null) {
            // if we populated fairlist then connect the adapter
            ArrayAdapter<Courses> adapter =
                    new ArrayAdapter<Courses>(currentActivity,
                            android.R.layout.simple_list_item_1, fairlist);
            lv.setAdapter(adapter);
        } else {
            // clear the list
            lv.setAdapter(null);
        }
    }
}
