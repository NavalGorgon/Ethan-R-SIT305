package com.example.task71p.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.task71p.data.Advert;
import com.example.task71p.util.Util;

//UPDATE TO WORK FOR ADVERTS


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create SQL Table
        String CREATE_ADVERT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.ADVERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.TYPE + " TEXT, " + Util.NAME + " TEXT, " + Util.PHONE + " TEXT, "
                + Util.DETAILS + " TEXT, " + Util.DATE + " TEXT, " + Util.LOCATION + " TEXT)";


        sqLiteDatabase.execSQL(CREATE_ADVERT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String DROP_ADVERT_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_ADVERT_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    //NEW ADVERT IN SQL
    public long insertAdvert(Advert advert)
    {
        //Open writable
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Add the title, description and date of the advert provided
        contentValues.put(Util.TYPE, advert.getType());
        contentValues.put(Util.NAME, advert.getName());
        contentValues.put(Util.PHONE, advert.getPhone());
        contentValues.put(Util.DETAILS, advert.getDetails());
        contentValues.put(Util.DATE, advert.getDate());
        contentValues.put(Util.LOCATION, advert.getLocation());
        //Insert into the table
        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    };

    //UPDATE ADVERT IN SQL
    public long updateAdvert(Advert advert, Integer id)
    {
        //Delete previous entry for advert
        boolean success = deleteAdvert(id);
        //Check if deleted successfully
        if(success) {
            //Insert the new advert details into table
            return insertAdvert(advert);
        }
        else {
            //If not successfully deleted, return 0
            return 0;
        }
    }

    //DELETE ADVERT FROM SQL
    public boolean deleteAdvert(Integer id)
    {
        //Check advert is in table
        if(checkAdvert(id)) {
            //Open writable
            SQLiteDatabase db = this.getWritableDatabase();
            //Delete from table
            boolean result = db.delete(Util.TABLE_NAME, Util.ADVERT_ID + "=?", new String[]{id.toString()}) > 0;
            db.close();
            //Return true or false to say if deleted successfully
            return result;
        }
        else{
            //Return false if advert is not in table
            return false;
        }
    }

    //CHECK IF ADVERT IS IN TABLE
    public boolean checkAdvert(Integer id)
    {
        //Open readable
        SQLiteDatabase db = this.getReadableDatabase();
        //Search for advert title
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ADVERT_ID}, Util.ADVERT_ID + "=?",
                new String[] {id.toString()}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();
        //Return number of rows found with that name
        return numberOfRows > 0;
    }

    //FETCH ADVERT FROM SQL
    public Cursor fetchAdvert()
    {
        //Open readable
        SQLiteDatabase db = this.getReadableDatabase();
        //Create cursor and sort SQL by ascending
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ADVERT_ID,Util.TYPE, Util.NAME, Util.PHONE,
                        Util.DETAILS, Util.DATE, Util.LOCATION}, null, null, null,
                null, null);
        //Return cursor pointing to the first advert
        return cursor;
    }

    public Advert fetchSingle(Integer id)
    {
        //Open readable
        SQLiteDatabase db = this.getReadableDatabase();
        //Create cursor
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ADVERT_ID, Util.TYPE, Util.NAME, Util.PHONE,
                Util.DETAILS, Util.DATE, Util.LOCATION}, Util.ADVERT_ID + "=?", new String[] {id.toString()},
                null, null,null);

        cursor.moveToFirst();

        return new Advert(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

    }
}
