package com.zerrium.uts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "itemdb";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QTY = "qty";
    private static final String KEY_DESC = "descr";
    private static final String SQL_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS +
            "( "+ KEY_ID +" INTEGER PRIMARY KEY, " +
            KEY_NAME +" TEXT, " +
            KEY_QTY + " INTEGER, " +
            KEY_DESC + " TEXT)";

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SqliteHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ITEMS);
    }

    /*
    protected void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_USER_NAME, user.userName);
        v.put(KEY_EMAIL, user.email);
        v.put(KEY_PASSWORD, user.password);
        long todo_id = db.insert(TABLE_USERS, null, v);
    }
    */
}
