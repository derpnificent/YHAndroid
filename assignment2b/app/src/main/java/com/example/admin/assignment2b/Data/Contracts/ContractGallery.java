package com.example.admin.assignment2b.Data.Contracts;

import android.provider.BaseColumns;

/**
 * Created by Admin on 2014-09-23.
 */

public class ContractGallery {

    public ContractGallery(){
    }

    //Set the column names for the database.
    public static abstract class Gallery implements BaseColumns{

        public final static String TABLE_NAME = "Gallery";
        public final static String COLUMN_NAME_URL = "url";
        public final static String COLUMN_NAME_NAME = "name";
        public final static String COLUMN_NAME_AGE = "age";
        public final static String COLUMN_NAME_DESCRIPTION = "description";

    }

}
