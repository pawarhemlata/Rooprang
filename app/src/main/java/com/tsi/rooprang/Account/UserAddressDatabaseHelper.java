package com.tsi.rooprang.Account;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class UserAddressDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserHegistration.db";

    public static class UserDatabase implements BaseColumns {
        public static final String TABLE_NAME = "user_details";
        public static final String COLUMN_NAME_COL1 = "name";
        public static final String COLUMN_NAME_COL2 = "pincode";
        public static final String COLUMN_NAME_COL3 = "address";
        public static final String COLUMN_NAME_COL4 = "landmark";
        public static final String COLUMN_NAME_COL5 = "city";
        public static final String COLUMN_NAME_COL6 = "state";
        public static final String COLUMN_NAME_COL7 = "mobile";

    }

    public UserAddressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersio, int newVersion) {

        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserDatabase.TABLE_NAME +
            "( " + UserDatabase._ID + " INTEGER PRIMARY KEY," +
            UserDatabase.COLUMN_NAME_COL1 + " text," +
            UserDatabase.COLUMN_NAME_COL2 + " text," +
            UserDatabase.COLUMN_NAME_COL3 + " text," +
            UserDatabase.COLUMN_NAME_COL4 + " text," +
            UserDatabase.COLUMN_NAME_COL5 + " text," +
            UserDatabase.COLUMN_NAME_COL6 + " text," +
            UserDatabase.COLUMN_NAME_COL7 + " text)";
    private static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + UserDatabase.TABLE_NAME;

}

