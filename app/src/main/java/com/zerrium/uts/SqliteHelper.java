package com.zerrium.uts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "item.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QTY = "qty";
    private static final String KEY_DESC = "descr";
    private static final String SQL_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS +
            "( "+ KEY_ID +" INTEGER PRIMARY KEY NOT NULL, " +
            KEY_NAME +" TEXT, " +
            KEY_QTY + " INTEGER, " +
            KEY_DESC + " TEXT)";

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ITEMS);
    }

    //CRUD methods (create, read, update, delete)
    protected void addItem(Item item) throws SQLiteException{
        if (item == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_ID, item.getId());
        v.put(KEY_NAME, item.getName());
        v.put(KEY_QTY, item.getQty());
        v.put(KEY_DESC, item.getDesc());
        db.insert(TABLE_ITEMS, null, v);
    }

    protected ArrayList<Item> getItem() throws SQLiteException{
        SQLiteDatabase db = SqliteHelper.this.getReadableDatabase();

        Cursor cursor = db.query(
                    TABLE_ITEMS, //table
                    new String[]{KEY_ID, KEY_NAME, KEY_QTY, KEY_DESC}, //column
                    null, //selection
                    null, //where
                    null, //groupby
                    null, //having
                    null); //orderby

        ArrayList<Item> i = new ArrayList<>();

        while(cursor.moveToNext()){
            i.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
        }

        cursor.close();

        return i;
    }

    protected void updateItem(Item item) throws SQLiteException {
        if (item == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_NAME, item.getName());
        v.put(KEY_QTY, item.getQty());
        v.put(KEY_DESC, item.getDesc());
        db.update(TABLE_ITEMS, v, KEY_ID + "LIKE ?", new String[]{String.valueOf(item.getId())});
    }

    protected void deleteItem(Item item) throws SQLiteException{
        if (item == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + "LIKE ?", new String[]{String.valueOf(item.getId())});
    }
}
