package com.example.task41p.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.task41p.Task;
import com.example.task41p.util.Util;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create SQL Table
        String CREATE_TASK_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.TITLE + " TEXT, " + Util.DETAILS + " TEXT, " + Util.DATE + " Text)";

        String CREATE_UNIT_TABLE = "CREATE TABLE tasks (task_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, DESC TEXT, DATE TEXT)";


        sqLiteDatabase.execSQL(CREATE_TASK_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String DROP_TASK_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_TASK_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    //NEW TASK IN SQL
    public long insertTask(Task task)
    {
        //Open writable
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Add the title, description and date of the task provided
        contentValues.put(Util.TITLE, task.getTitle());
        contentValues.put(Util.DETAILS, task.getDetails());
        contentValues.put(Util.DATE, task.getDate());
        //Insert into the table
        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    };

    //UPDATE TASK IN SQL
    public long updateTask(Task task, String name)
    {
        //Delete previous entry for task
        boolean success = deleteTask(name);
        //Check if deleted succesfully
        if(success) {
            //Insert the new task details into table
            return insertTask(task);
        }
        else {
            //If not succesfully deleted, return 0
            return 0;
        }
    }

    //DELETE TASK FROM SQL
    public boolean deleteTask(String name)
    {
        //Check task is in table
        if(checkTask(name)) {
            //Open writable
            SQLiteDatabase db = this.getWritableDatabase();
            //Delete from table
            boolean result = db.delete(Util.TABLE_NAME, Util.TITLE + "=?", new String[] {name}) > 0;
            db.close();
            //Return true or false to say if deleted successfully
            return result;
        }
        else{
            //Return false if task is not in table
            return false;
        }
    }

    //CHECK IF TASK IS IN TABLE
    public boolean checkTask(String title)
    {
        //Open readable
        SQLiteDatabase db = this.getReadableDatabase();
        //Search for task title
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.TASK_ID}, Util.TITLE + "=?",
                new String[] {title}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();
        //Return number of rows found with that name
        return numberOfRows > 0;
    }

    //FETCH TASK FROM SQL
    public Cursor fetchTask()
    {
        //Open readable
        SQLiteDatabase db = this.getReadableDatabase();
        //Create cursor and sort SQL by ascending
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.TASK_ID, Util.TITLE, Util.DETAILS, Util.DATE}, null,
                null, null, null, Util.DATE + " ASC");
        //Return cursor pointing to the first task
        return cursor;
    }
}
