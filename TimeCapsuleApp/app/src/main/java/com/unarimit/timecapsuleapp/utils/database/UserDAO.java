package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.TimeHelper;

public class UserDAO {
    public static final String TABLE_NAME = "user_infos";
    public static final String FIELD_NAME = "ui_field_name";
    public static final String FIELD_VALUE = "ui_field_value";
    public static final String LAST_MODIFY = "ui_last_modify";

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + FIELD_NAME + " text PRIMARY KEY,"
            + FIELD_VALUE + " text not null,"
            + LAST_MODIFY + " long not null)";

    public String GetValue(UserConfig key){
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+
                " where "+ FIELD_NAME +"= '"+ String.valueOf(key) + "' ", null);
        if(cursor == null || !cursor.moveToFirst())
            return null;
        String result = cursor.getString(cursor.getColumnIndex(FIELD_VALUE));

        cursor.close();
        return result;
    }

    public void AddValue(UserConfig key, String value){
        ContentValues values = new  ContentValues();
        values.put(FIELD_NAME, String.valueOf(key));
        values.put(FIELD_VALUE, value);
        values.put(LAST_MODIFY, TimeHelper.GetCurrentSeconds());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, FIELD_NAME, values);
    }

    public void UpdateValue(UserConfig key, String value){
        ContentValues values = new  ContentValues();
        values.put(FIELD_VALUE, value);
        values.put(LAST_MODIFY, TimeHelper.GetCurrentSeconds());
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, FIELD_NAME+"="+String.valueOf(key), null);
    }
}
