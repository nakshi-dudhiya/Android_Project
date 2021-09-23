package com.example.camohawkdudhiya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    ArrayList<String> myStringList = new ArrayList<String>();
    public static final String TAG = "==ListActivity==";
    public static String name;
    private static  int count;
    // Create a global instance of our SQL Helper class
    MyDbHelper mydbhelp = new MyDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Log.d(TAG, "onCreate");
        // Get an instance of the database using our helper class
        SQLiteDatabase db = mydbhelp.getReadableDatabase();
        // A projection defines what fields we want to retrieve.
        String[] projection = {MyDbHelper.ID, MyDbHelper.USERNAME, MyDbHelper.COMMENT};

        // db.query will retreive the data and return a Cursor to access it
        Cursor mycursor = db.query(MyDbHelper.MYTABLE, projection, null,
                null, null, null, null);
        String results = "";
        if (mycursor != null) {
            count = 0;
            // Loop through our returned results from the start
            while (mycursor.moveToNext()) {
                Log.d(TAG, "found DB item");
                String uName = mycursor.getString(
                        mycursor.getColumnIndex(MyDbHelper.USERNAME));
                String uCom = mycursor.getString(
                        mycursor.getColumnIndex(MyDbHelper.COMMENT));
                long itemID = mycursor.getLong(
                        mycursor.getColumnIndex(MyDbHelper.ID));
                // We could add our results to an array, or process them here if we want
                results = itemID + ". " + uName + " says '" + uCom + "'."; // XXX hackish
                myStringList.add(count, results);
                count = count + 1;
            }
            // Close the cursor when we're done
            mycursor.close();
        }
        if (results == "") {
            results = "!! no data !!";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringList);
        Log.d(TAG, String.valueOf(myStringList));
        Log.d(TAG, "adapter = " + adapter);
        Log.d(TAG, "adapter getCount() = " + adapter.getCount());
        ListView randList = findViewById(R.id.listView);
        randList.setAdapter(adapter);
    }
}