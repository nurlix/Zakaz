package com.ulutsoft.nurlan.dsclient;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NURLAN on 02.07.2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "uerp_appdata.db";
    private static final int DATABASE_VERSION = 1;

    private static final String db = "CREATE TABLE Items (ID INTEGER PRIMARY KEY, Item STRING (100), Category INTEGER, Parent INTEGER, Price REAL);";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS Items");
        database.execSQL("DROP TABLE IF EXISTS Categories");
        onCreate(database);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

}
