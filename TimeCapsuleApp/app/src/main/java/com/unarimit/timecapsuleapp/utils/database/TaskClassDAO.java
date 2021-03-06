package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.TaskClass;

import java.util.LinkedList;
import java.util.List;

public class TaskClassDAO {
    public static final String TABLE_NAME = "task_classes";
    public static final String GUID = "tc_guid";
    public static final String NAME = "tc_name";
    public static final String COLOR = "tc_color";
    public static final String ID = "tc_id";  // 数据库内部用于连接的ID

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + GUID + " text not null,"
            + NAME + " text not null,"
            + COLOR + " text not null)";

    public List<TaskClass> GetTaskClassList(){
        Cursor cursor = DbContext._SQLiteDatabase.query(TABLE_NAME, new String[]{ID, NAME, COLOR, GUID}, null, null, null, null, null);
        if(cursor == null || !cursor.moveToFirst())
            return null;
        List<TaskClass> result = new LinkedList<>();
        do{
            result.add(new TaskClass(cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(COLOR)),
                    cursor.getInt(cursor.getColumnIndex(ID))));
        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }

    public void Add(TaskClass taskClass){
        ContentValues values = new  ContentValues();
        values.put(GUID, taskClass.getGuid());
        values.put(NAME, taskClass.getName());
        values.put(COLOR, taskClass.getColor());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public void Update(TaskClass taskClass){
        ContentValues values = new  ContentValues();
        values.put(NAME, taskClass.getName());
        values.put(COLOR, taskClass.getColor());
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+taskClass.getId(), null);
    }

    public void Remove(TaskClass taskClass){
        DbContext._SQLiteDatabase.delete(TABLE_NAME, ID+"="+ taskClass.getId(), null);
    }

}
