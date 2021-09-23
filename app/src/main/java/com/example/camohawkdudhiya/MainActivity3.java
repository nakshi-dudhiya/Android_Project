package com.example.camohawkdudhiya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private static String tag ="==MAINACTIVITY3==";
    MyDbHelper mydbhelp = new MyDbHelper(this);
    public String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d(tag,"OnCreate");

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        Log.d(tag,"onCreate() - received User '" + user);
        TextView tv= findViewById(R.id.textView);
        tv.setText("Commentbox for "+ user);
    }
    public void addData(View view) {
        // Get an instance of the database using our helper class
        SQLiteDatabase db = mydbhelp.getWritableDatabase();
        EditText comment = (EditText) findViewById(R.id.commentEditTextView);
        //String pName= productName.getText().toString();
        // A ContentValues class provides an easy helper function to add our values
        ContentValues values = new ContentValues();
        // Similar to using a bundle - put values using column name and value
        values.put(MyDbHelper.USERNAME, user);
        values.put(MyDbHelper.COMMENT, comment.getText().toString());
        if (!(user.equals(""))) {
            // The db.insert command will do a SQL insert on our table, return the new row ID
            long newrowID = db.insert(MyDbHelper.MYTABLE, null, values);
            Log.d(tag, "New ID " + newrowID);
        }
        // Clear out fields for next entry
        comment.setText("");
    }
    /**
     * onClick handler, launches our List Activity
     * @param view - button view (unused)
     */
    public void callList(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
        Log.d(tag, "callList");
    }
}