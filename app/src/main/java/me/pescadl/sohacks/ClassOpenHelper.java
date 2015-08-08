package me.pescadl.sohacks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chufan on 8/7/2015.
 */
public class ClassOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "classes";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "days TEXT, " +
            "startTime TEXT, " +
            "endTime TEXT, " +
            "location TEXT, " +
            "color TEXT);";
    ClassOpenHelper(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // nop
    }
}
