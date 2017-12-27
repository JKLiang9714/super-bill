package com.example.liangjiakun.superbill.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Liang Jiakun on 2017/5/15.
 */

public class BillSqliteOpenHelper extends SQLiteOpenHelper {

    public BillSqliteOpenHelper(Context context) {
        super(context, "bill.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table bill (id integer primary key autoincrement, out_in varchar(20) money varchar(20), kind varchar(20), date varchar(20), memo varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
