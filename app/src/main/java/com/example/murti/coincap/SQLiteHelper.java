package com.example.murti.coincap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void queryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(String name,double price,int rank){
        SQLiteDatabase database=getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (?, ?, ?)";

        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindDouble(2,price);
        statement.bindDouble(3, (double)rank);

        statement.executeInsert();

    }
    public void updateData(String name,double price,int rank) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOOD SET name = ?,price = ? WHERE rank = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindDouble(3, (double)rank);

        statement.execute();
        database.close();
    }

    public  void deleteData(int rank) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM FOOD WHERE rank = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) rank);
        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}