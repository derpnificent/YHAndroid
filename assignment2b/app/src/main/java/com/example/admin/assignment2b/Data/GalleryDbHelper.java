package com.example.admin.assignment2b.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.assignment2b.Data.Contracts.ContractGallery;
import com.example.admin.assignment2b.Models.GalleryFrame;

/**
 * Created by Admin on 2014-09-23.
 */


public class GalleryDbHelper extends SQLiteOpenHelper {

    // Constants
    private final static String DATABASE_NAME = "GalleryTable.db";
    private final static int DATABASE_VERSION = 6;
    private final static String COMMA_SEP = ",";
    private final static String TEXT_TYPE = " TEXT";

    // Projection
    String[] projection = {
            ContractGallery.Gallery._ID,
            ContractGallery.Gallery.COLUMN_NAME_URL,
            ContractGallery.Gallery.COLUMN_NAME_NAME,
            ContractGallery.Gallery.COLUMN_NAME_AGE,
            ContractGallery.Gallery.COLUMN_NAME_DESCRIPTION
     };

    // Variables
    SQLiteDatabase db;


    // Database creation SQL statement
    private static final String SQL_CREATE_GALLERY =
            "CREATE TABLE " + ContractGallery.Gallery.TABLE_NAME + " (" +
                    ContractGallery.Gallery._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    ContractGallery.Gallery.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ContractGallery.Gallery.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    ContractGallery.Gallery.COLUMN_NAME_AGE + TEXT_TYPE + COMMA_SEP +
                    ContractGallery.Gallery.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + " )";

    // Database delete SQL statement
    private static final String SQL_DELETE_GALLERY =
            "DROP TABLE IF EXISTS " + ContractGallery.Gallery.TABLE_NAME;

    public GalleryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Create database method
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(SQL_CREATE_GALLERY);
            Log.d("GalleryDbHelper", "Table created version" + DATABASE_VERSION);
        }
        catch (Exception ex){
            Log.d("GalleryDbHelper", ex.getMessage());
        }
    }
    // Update database method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion != oldVersion){
            try{
                db.execSQL(SQL_DELETE_GALLERY);
                onCreate(db);
            }
            catch (Exception ex){
                Log.d("GalleryDbHelper", ex.getMessage());
            }

        }
    }
    // Get database data
    public Cursor get(){

        db = getReadableDatabase();

        Cursor cursor = db.query(
                ContractGallery.Gallery.TABLE_NAME,  // The table to query
                projection,              // The columns to return
                null,                    // The columns for the WHERE clause
                null,                    // The values for the WHERE clause
                null,                    // don't group the rows
                null,                    // don't filter by row groups
                null                     // The sort order
        );

        return cursor;
    }

    // Get database id
    public GalleryFrame getId(int id){

        db = getReadableDatabase();

        Cursor cursor = db.query(
                ContractGallery.Gallery.TABLE_NAME,  // The table to query
                projection,              // The columns to return
                ContractGallery.Gallery._ID+"=" + id,  // The columns for the WHERE clause
                null,                    // The values for the WHERE clause
                null,                    // don't group the rows
                null,                    // don't filter by row groups
                null                     // The sort order
        );

        /*The method will move to first row, return false if the cursor is empty*/
        cursor.moveToFirst();

        /*Creates a new object and set its value from your database.
        Then returns the object instead of a cursor*/
        GalleryFrame contact = new GalleryFrame(cursor.getString(
        cursor.getColumnIndex(ContractGallery.Gallery.COLUMN_NAME_URL)),
        cursor.getString(cursor.getColumnIndex(ContractGallery.Gallery.COLUMN_NAME_NAME)),
        cursor.getString(cursor.getColumnIndex(ContractGallery.Gallery.COLUMN_NAME_AGE)),
        cursor.getString(cursor.getColumnIndex(ContractGallery.Gallery.COLUMN_NAME_DESCRIPTION)));

        return contact;
    }

    // Insert database data
    public long insert(GalleryFrame galleryFrame){

        db = getWritableDatabase();

        //Get inserts and the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractGallery.Gallery.COLUMN_NAME_NAME, galleryFrame.getName());
        contentValues.put(ContractGallery.Gallery.COLUMN_NAME_AGE, galleryFrame.getAge());
        contentValues.put(ContractGallery.Gallery.COLUMN_NAME_DESCRIPTION, galleryFrame.getDescription());
        contentValues.put(ContractGallery.Gallery.COLUMN_NAME_URL, galleryFrame.getUrl());


        long id = db.insert(ContractGallery.Gallery.TABLE_NAME, null, contentValues);

        return id;
    }
}
