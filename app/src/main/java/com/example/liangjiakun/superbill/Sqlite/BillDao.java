package com.example.liangjiakun.superbill.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.liangjiakun.superbill.Utility.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang Jiakun on 2017/5/15.
 */

public class BillDao {
    /*private BillSqliteOpenHelper helper;

    public BillDao(Context context) {
        helper = new BillSqliteOpenHelper(context);
    }

    public void add(String out_in,String money,String kind,String date,String memo){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into person (out_in, money, kind, date, memo) values (?,?,?,?,?)", new Object[]{out_in, money, kind, date, memo});
        db.close();
    }

    public boolean find(String memo){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bill where memo=?", new String[]{memo});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    public void update(String memo, String newnumber){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update person set number=? where memo=?", new Object[]{newnumber, memo});
        db.close();
    }

    public void delete(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from person where name=?",new String[]{name});
        db.close();
    }

    public List<Bill> findAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Bill> persons = new ArrayList<Bill>();
        Cursor cursor = db.rawQuery("select name,id,number from person ",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            Bill p = new Bill(id,name,number);
            persons.add(p);
        }
        cursor.close();
        db.close();
        return persons;
    }*/
}
