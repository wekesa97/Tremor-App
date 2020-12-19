package com.simplisticwriting.tremordetectorapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME="Users.db";
        public static final String TABLE_NAME="User_table";
        public static final String COL_1="ID";
        public static final String COL_2="TREMOREVENT";
        public DataBaseHelper(Context context){
            super(context,DATABASE_NAME,null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TREMOREVENT TEXT)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        }
        public boolean insertData(String event_data) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, event_data);

            long result = db.insert(TABLE_NAME, null, contentValues);
            db.close();
            //To check whether data is inserted in database
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        public Cursor getAllData(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME ,null);
            return res;
        }
}