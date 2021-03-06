package com.unarimit.timecapsuleapp.utils.database;
import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.http.dto.TaskDto;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class TaskDAO {
    public static final String TABLE_NAME = "tasks";
    public static final String GUID = "t_guid";
    public static final String ID = "t_id";
    public static final String NAME = "t_name";
    public static final String DESC = "t_desc";
    public static final String ACHIEVE_PER_HOUR = "t_achieve_per_hour";
    public static final String IS_FINISHED = "t_isFinished";
    public static final String IS_OFTEN = "t_isOften";
    public static final String ICON = "t_icon";
    public static final String CREATE_TIME = "t_create_time";
    public static final String FINISH_TIME = "t_finish_time";
    public static final String SYNC = "t_sync";
    public static final String LAST_MODIFIED = "t_last_modified";
    public static final String TASK_CLASS_ID = "t_taskClassId";

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + GUID + " text not null,"
            + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + NAME + " text not null,"
            + DESC + " text,"
            + ACHIEVE_PER_HOUR + " real not null,"
            + IS_FINISHED + " bool not null,"
            + IS_OFTEN + " bool not null,"
            + ICON + " text not null,"
            + CREATE_TIME + " integer not null,"
            + FINISH_TIME + " integer not null,"
            + SYNC + " bool not null,"
            + LAST_MODIFIED + " integer not null,"
            + TASK_CLASS_ID + " integer not null,"
            + "foreign key("+TASK_CLASS_ID + ") references " + TaskClassDAO.TABLE_NAME + "(" +TaskClassDAO.ID + "))";

    /**
     * join task and taskclass
     * */
    public List<Task> GetTaskList(boolean allowFinished){
        Cursor cursor;
        if(!allowFinished){
            cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskClassDAO.TABLE_NAME+
                    " where "+ TASK_CLASS_ID +"="+ TaskClassDAO.TABLE_NAME+"."+TaskClassDAO.ID + " and "+IS_FINISHED+"=0", null);
        }else{
            cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskClassDAO.TABLE_NAME+
                    " where "+ TASK_CLASS_ID +"="+ TaskClassDAO.TABLE_NAME+"."+TaskClassDAO.ID, null);
        }


        if(cursor == null || !cursor.moveToFirst())
            return null;

        List<Task> result = new LinkedList<>();
        do{
            TaskClass taskClass = new TaskClass(cursor.getString(cursor.getColumnIndex(TaskClassDAO.GUID)),
                cursor.getString(cursor.getColumnIndex(TaskClassDAO.NAME)),
                cursor.getString(cursor.getColumnIndex(TaskClassDAO.COLOR)),
                cursor.getInt(cursor.getColumnIndex(TaskClassDAO.ID)));

            boolean isfinish_temp = false;
            boolean isoften_temp = false;
            if(cursor.getInt(cursor.getColumnIndex(IS_FINISHED)) == 1)
                isfinish_temp = true;
            if(cursor.getInt(cursor.getColumnIndex(IS_OFTEN)) == 1)
                isoften_temp = true;

            result.add(new Task(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(DESC)),
                    taskClass,
                    cursor.getDouble(cursor.getColumnIndex(ACHIEVE_PER_HOUR)),
                    isfinish_temp,
                    cursor.getString(cursor.getColumnIndex(ICON)),
                    isoften_temp,
                    cursor.getLong(cursor.getColumnIndex(FINISH_TIME)),
                    cursor.getLong(cursor.getColumnIndex(CREATE_TIME))));

        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }

    public void Add(@NotNull Task task){
        ContentValues values = new  ContentValues();
        values.put(GUID, task.getGuid());
        values.put(NAME, task.getName());
        values.put(DESC, task.getDesc());
        values.put(ACHIEVE_PER_HOUR, task.getAchievePerHour());
        values.put(IS_FINISHED, task.isFinished());
        values.put(IS_OFTEN, task.isOften());
        values.put(ICON, task.getIcon());
        values.put(CREATE_TIME, task.getCreateTime());
        values.put(FINISH_TIME, task.getFinishTime());

        values.put(SYNC, false);
        values.put(LAST_MODIFIED, TimeHelper.GetCurrentSeconds());
        values.put(TASK_CLASS_ID, task.getTaskClass().getId());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public void Update(@NotNull Task task){
        ContentValues values = new  ContentValues();
        values.put(GUID, task.getGuid());
        values.put(NAME, task.getName());
        values.put(DESC, task.getDesc());
        values.put(ACHIEVE_PER_HOUR, task.getAchievePerHour());
        values.put(IS_FINISHED, task.isFinished());
        values.put(IS_OFTEN, task.isOften());
        values.put(ICON, task.getIcon());
        values.put(CREATE_TIME, task.getCreateTime());
        values.put(FINISH_TIME, task.getFinishTime());

        values.put(SYNC, false);
        values.put(LAST_MODIFIED, TimeHelper.GetCurrentSeconds());
        values.put(TASK_CLASS_ID, task.getTaskClass().getId());
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+task.getId(), null);
    }

    public List<TaskDto> GetNotSyncAll(){
        Cursor cursor;
        cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskClassDAO.TABLE_NAME+
                " where "+ TASK_CLASS_ID +"="+ TaskClassDAO.TABLE_NAME+"."+TaskClassDAO.ID +
                " and " + SYNC + "= 0", null);


        if(cursor == null || !cursor.moveToFirst())
            return null;

        List<TaskDto> result = new LinkedList<>();
        do{
            boolean isfinish_temp = false;
            boolean isoften_temp = false;
            if(cursor.getInt(cursor.getColumnIndex(IS_FINISHED)) == 1)
                isfinish_temp = true;
            if(cursor.getInt(cursor.getColumnIndex(IS_OFTEN)) == 1)
                isoften_temp = true;

            result.add(new TaskDto(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(TaskClassDAO.GUID)),
                    cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(DESC)),
                    cursor.getString(cursor.getColumnIndex(ICON)),
                    cursor.getDouble(cursor.getColumnIndex(ACHIEVE_PER_HOUR)),
                    isfinish_temp,
                    isoften_temp,
                    cursor.getLong(cursor.getColumnIndex(CREATE_TIME)),
                    cursor.getLong(cursor.getColumnIndex(FINISH_TIME))));
        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }

    public void Sync(int id){
        ContentValues values = new  ContentValues();
        values.put(SYNC, 1);
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+id, null);
    }

    public void Remove(@NotNull Task task){
        DbContext._SQLiteDatabase.delete(TABLE_NAME, ID+"="+ task.getId(), null);
    }

}
