package com.undev.donkuy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "donasi.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

    public ModelUser queryUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ModelUser user = null;
        Cursor cursor = db.query("user", new String[]{"id",
                        "username", "password"}, "username=? and password=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new ModelUser(cursor.getString(1), cursor.getString(2));
        }
        return user;
    }
}
