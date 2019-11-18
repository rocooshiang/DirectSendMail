package geosat.mysys;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mydb extends SQLiteOpenHelper {

    public Mydb(Context context) {
        super(context, "mydb.db", null, 1);
        // TODO 自動產生的建構子 Stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO 自動產生的方法 Stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE SmsInfo (_id INTEGER PRIMARY KEY,FromWho Text,Body Text,Time Text); ";
        db.execSQL(strSql);
    }

    public Cursor queryBySql(String p_strSql) {
        return getReadableDatabase().rawQuery(p_strSql, null);
    }

    // 先查詢是否已有上傳資料表，再新增資料表
    public void CreateTable(Mydb manager, String TableName) {
        String ColName = "(_id INTEGER PRIMARY KEY,Phone Text,Body Text,Time Text)";

        SQLiteDatabase db = manager.getWritableDatabase();
        String strcreatesqlvale = "SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name= '"
                + TableName + "'";
        try {
            Cursor cursor1 = db.rawQuery(strcreatesqlvale, null);
            if (cursor1.moveToNext()) {
                int count = cursor1.getInt(0);
                if (count == 0) {
                    // String                    
                    String strSql = "CREATE TABLE " + TableName + " " + ColName
                            + ";";
                    db.execSQL(strSql);
                    db.close();
                }
            }
        } catch (Exception e) {

        }
    }

    // 新增資料
    public long InsertData(Mydb manager, String TableName,
                           ContentValues Sql) {
        long rowid = 0;
        SQLiteDatabase db = manager.getWritableDatabase();
        rowid = db.insert(TableName, null, Sql);
        db.close();
        return rowid;
    }

    // 修改資料 Condition=where條件
    public long UpdatetData(Mydb manager, String TableName,
                            ContentValues values, String Condition) {
        long rowid = 0;
        SQLiteDatabase db = manager.getWritableDatabase();
        rowid = db.update(TableName, values, Condition, null);
        db.close();
        return rowid;
    }

    // 刪除資料 Condition=where條件
    public long DeleteData(Mydb manager, String TableName,
                           String Condition) {
        long rowid = 0;
        SQLiteDatabase db = manager.getWritableDatabase();
        rowid = db.delete(TableName, Condition, null);
        db.close();
        return rowid;
    }

}
