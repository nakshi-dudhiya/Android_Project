package com.example.camohawkdudhiya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
    public static final String TAG = "==MyDbHelper==";
    /** collection of DataBase field names **/
    public static final String DATABASE_FILE_NAME = "MyDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String MYTABLE = "mytable";
    public static final String ID = "_id";
    public static final String USERNAME = "userName";
    public static final String COMMENT = "comment";
    // This is the SQL Statement that will be executed to create the table and columns
    // "_id" is recommended to be a standard first column and primary key
    private static final String SQL_CREATE =
            "CREATE TABLE " + MYTABLE + " ( " + ID + " INTEGER PRIMARY KEY, " +
                    USERNAME + " TEXT, " + COMMENT + " TEXT) ";
    public MyDbHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "constructor");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate " + SQL_CREATE);
        db.execSQL(SQL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is only called if the DATABASE_VERSION changes
        // Possible actions - delete table (ie DROP TABLE IF EXISTS mytable), then call onCreate
    }

}
