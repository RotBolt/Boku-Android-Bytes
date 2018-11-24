package com.nindo.theninetails.geofeningtestapp.database;

import android.provider.BaseColumns;

/**
 * Created by theninetails on 20/1/18.
 */


/*
* this is contract class  which tells the name of table and columns of table in a database
*
* */
public class SavedPlacesReaderContract {

    public class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME="savedplaces";
        public static final String _ID="_id";
        public static final String COLUMN_PLACE_NAME="placename";
        public static final String COLUMN_ADDRESS="address";

    }
}
