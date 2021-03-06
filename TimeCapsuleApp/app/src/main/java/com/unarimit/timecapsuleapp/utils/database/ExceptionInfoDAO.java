package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.ExceptionInfo;
import com.unarimit.timecapsuleapp.entities.TaskClass;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ExceptionInfoDAO {
    public static final String TABLE_NAME = "exceptions";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String INFO = "info";

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + DATE + " integer not null,"
            + INFO + " text not null)";

    public List<ExceptionInfo> GetExceptionInfoList(){
        Cursor cursor = DbContext._SQLiteDatabase.query(TABLE_NAME, new String[]{ID, DATE, INFO}, null, null, null, null, null);
        if(cursor == null || !cursor.moveToFirst())
            return null;
        List<ExceptionInfo> result = new LinkedList<>();
        do{
            result.add(new ExceptionInfo(cursor.getLong(cursor.getColumnIndex(DATE)),
                    cursor.getString(cursor.getColumnIndex(INFO))));
        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }
    public void Add(@NotNull ExceptionInfo exceptionInfo){
        ContentValues values = new  ContentValues();
        values.put(DATE, exceptionInfo.getDate());
        values.put(INFO, exceptionInfo.getInfo());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

}
