package com.example.andriodproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Steps_number"; // Name of the created databased
    private static final String idForListDataFunction = "ID"; //required for databasedlister to list out all id's.
    private static final String Steps = "Number"; //store data and number of steps

    SQLiteDatabase db = this.getWritableDatabase();

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    // create table fucntiom
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + Steps +" TEXT)";
        db.execSQL(createDatabaseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // function to insert data
    public boolean insertData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Steps, item);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //check if data is inserted into databased is success, if -1 means fail  1 means sucess
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

// get and return all data from the Databased
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}
























