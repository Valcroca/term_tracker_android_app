package com.example.valeriarocac196;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =  "MYDB.db";
    private static final int DATABASE_VERSION = 2;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(String tableName) {
        this.getWritableDatabase().execSQL(
                "CREATE TABLE IF NOT EXISTS " + tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, start DATE, end DATE, current BOOLEAN);"
        );
    }

    //create
    public long addRecord(String titleKey, String titleValue, String startKey, String startValue, String endKey, String endValue, String currentKey, boolean currentValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(titleKey, titleValue);
        values.put(startKey, startValue);
        values.put(endKey, endValue);
        values.put(String.valueOf(currentKey), currentValue);

        return db.insert("term_table", null, values);
    }

    //delete
    public void deleteRecord(String sqlStatement) {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    //update
    public int changesRecord(String tableName, boolean value, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("current", value);

        return db.update(tableName, cv, whereClause, whereArgs);
    }

    //return a list of term objects
    public ArrayList<Term> readRecords(String sqlStatement) {
        ArrayList<Term> allTerms = new ArrayList<Term>();
        long termId;
        String title;
        String startDate;
        String endDate;
        int current;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement, null);

        while(cursor.moveToNext()) {
            termId = cursor.getLong(cursor.getColumnIndex("id"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            startDate = cursor.getString(cursor.getColumnIndex("start"));
            endDate = cursor.getString(cursor.getColumnIndex("end"));
            current = cursor.getInt(cursor.getColumnIndex("current"));

            allTerms.add(new Term(termId, title, startDate, endDate, current));
        }
        return allTerms;
    }

}
