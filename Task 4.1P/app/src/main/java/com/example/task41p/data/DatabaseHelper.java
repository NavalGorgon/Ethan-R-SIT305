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

    public long insertTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TITLE, task.getTitle());
        contentValues.put(Util.DETAILS, task.getDetails());
        contentValues.put(Util.DATE, task.getDate());
        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    };

    public long updateTask(Task task, String name)
    {
        boolean success = deleteTask(name);
        if(success) {
            Log.wtf("Delete", "Done");
            return insertTask(task);
        }
        else {
            return 0;
        }
    }

    public boolean deleteTask(String name)
    {
        if(checkTask(name)) {
            Log.wtf("Deleting", "Got to here");
            SQLiteDatabase db = this.getWritableDatabase();
            boolean result = db.delete(Util.TABLE_NAME, Util.TITLE + "=?", new String[] {name}) > 0;
            db.close();
            return result;
        }
        else{
            return false;
        }
    }

    public boolean checkTask(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.TASK_ID}, Util.TITLE + "=?",
                new String[] {title}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();
        return numberOfRows > 0;
    }
    public Cursor fetchTask()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.TASK_ID, Util.TITLE, Util.DETAILS, Util.DATE}, null,
                null, null, null, Util.DATE + " ASC");
        return cursor;
    }
}
