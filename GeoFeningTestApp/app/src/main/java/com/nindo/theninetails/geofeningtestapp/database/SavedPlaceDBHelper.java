package com.nindo.theninetails.geofeningtestapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by theninetails on 20/1/18.
 */

/**
 * this is helper class which gives access to database to read and write through its Object
 *
 * eg : SavedPlaceDBHelper helper = new SavedPlaceDBHelper();
 *
 * this "helper" object will be used to read and write database
 * like helper.getReadableDatabase() gives readable database
 *
 */


public class SavedPlaceDBHelper extends SQLiteOpenHelper{

    // SQL Command to create entries of database ; executes as String
    private final static String CREATE_ENTRIES = "CREATE TABLE " + SavedPlacesReaderContract.PlaceEntry.TABLE_NAME + " (" +
            SavedPlacesReaderContract.PlaceEntry._ID + " INTEGER PRIMARY KEY," +
            SavedPlacesReaderContract.PlaceEntry.COLUMN_PLACE_NAME + " TEXT," +
            SavedPlacesReaderContract.PlaceEntry.COLUMN_ADDRESS + " TEXT)" ;



    // SQL command to delete entries ; executed as String
    private static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SavedPlacesReaderContract.PlaceEntry.TABLE_NAME;

    private static final String DATABASE_NAME="SavedPlaces.db";
    private static final int DATABASE_VER=1;

    public SavedPlaceDBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES); // this function executes SQL commands and requires command as String
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }
}
