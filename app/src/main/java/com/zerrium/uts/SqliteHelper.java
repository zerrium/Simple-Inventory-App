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

import java.util.ArrayList;

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

    //CRUD methods (create, read, update, delete)
    protected void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_ID, item.getId());
        v.put(KEY_NAME, item.getName());
        v.put(KEY_QTY, item.getQty());
        v.put(KEY_DESC, item.getDesc());
        long todo_id = db.insert(TABLE_ITEMS, null, v);
    }

    protected ArrayList<Item> getItem(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_ITEMS, //table
                new String[]{KEY_ID, KEY_NAME, KEY_QTY, KEY_DESC}, //column
                "*", //selection
                null, //where
                null, //groupby
                null, //having
                null); //orderby

        ArrayList<Item> i = new ArrayList<>();
        while(cursor.moveToNext()){
            i.add(new Item(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
        }

        return i;
    }

    protected void updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_NAME, item.getName());
        v.put(KEY_QTY, item.getQty());
        v.put(KEY_DESC, item.getDesc());
        long todo_id = db.update(TABLE_ITEMS, v, KEY_ID + "LIKE ?", new String[]{item.getId()});
    }

    protected void deleteItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        long todo_id = db.delete(TABLE_ITEMS, KEY_ID + "LIKE ?", new String[]{item.getId()});
    }
}
