package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;

import java.util.LinkedList;
import java.util.List;

public class PeriodDAO {
    public static final String TABLE_NAME = "periods";
    public static final String GUID = "p_guid";
    public static final String ID = "p_id";  // 数据库内部用于连接的ID
    public static final String TASK_ID = "p_task_id";
    public static final String BEGIN = "p_begin_date";
    public static final String END = "p_end_date";
    public static final String BEGIN_CALENDER = "p_begin_calender";
    public static final String END_CALENDER = "p_end_calender";

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + GUID + " text not null,"
            + TASK_ID + " text not null,"
            + BEGIN + " integer not null,"
            + END + " integer not null,"
            + BEGIN_CALENDER + " integer not null,"
            + END_CALENDER + " integer not null,"
            + "foreign key("+TASK_ID + ") references " + TaskDAO.TABLE_NAME + "(" +TaskDAO.ID + "))";

    /**
     * 三链接查询,正在进行的Period
     * */
    public Period GetCurrentPeriod(){
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskClassDAO.TABLE_NAME+", "+TaskDAO.TABLE_NAME+
                " where "+ TaskDAO.TASK_CLASS_ID +"="+ TaskClassDAO.ID + " and "+
                END+"= -1 and " +
                TASK_ID + "=" + TaskDAO.ID, null);
        if(cursor == null || !cursor.moveToFirst())
            return null;

        boolean isfinish_temp = false;
        boolean isoften_temp = false;
        if(cursor.getInt(cursor.getColumnIndex(TaskDAO.IS_FINISHED)) == 1)
            isfinish_temp = true;
        if(cursor.getInt(cursor.getColumnIndex(TaskDAO.IS_OFTEN)) == 1)
            isoften_temp = true;

        TaskClass taskClass = new TaskClass(cursor.getString(cursor.getColumnIndex(TaskClassDAO.GUID)),
                cursor.getString(cursor.getColumnIndex(TaskClassDAO.NAME)),
                cursor.getString(cursor.getColumnIndex(TaskClassDAO.COLOR)),
                cursor.getInt(cursor.getColumnIndex(TaskClassDAO.ID)));
        Task task = new Task(cursor.getInt(cursor.getColumnIndex(TaskDAO.ID)),
                cursor.getString(cursor.getColumnIndex(TaskDAO.GUID)),
                cursor.getString(cursor.getColumnIndex(TaskDAO.NAME)),
                cursor.getString(cursor.getColumnIndex(TaskDAO.DESC)),
                taskClass,
                cursor.getDouble(cursor.getColumnIndex(TaskDAO.ACHIEVE_PER_HOUR)),
                isfinish_temp,
                cursor.getString(cursor.getColumnIndex(TaskDAO.ICON)),
                isoften_temp);

        return new Period(cursor.getString(cursor.getColumnIndex(GUID)),
                cursor.getInt(cursor.getColumnIndex(ID)),
                task,
                cursor.getLong(cursor.getColumnIndex(BEGIN)),
                cursor.getLong(cursor.getColumnIndex(END)),
                cursor.getLong(cursor.getColumnIndex(BEGIN_CALENDER)),
                cursor.getLong(cursor.getColumnIndex(END_CALENDER)));
    }

    /**
     * 三链接查询，查询指定日期
     * */
    public List<Period> GetPeriodList(long calendar){
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskClassDAO.TABLE_NAME+", "+TaskDAO.TABLE_NAME+
                " where "+ TaskDAO.TASK_CLASS_ID +"="+ TaskClassDAO.ID
                + " and "+ TASK_ID + "=" + TaskDAO.ID
                + " and (" + BEGIN_CALENDER + "=" + calendar + " or " + END_CALENDER + "=" + calendar + ")", null);

        if(cursor == null || !cursor.moveToFirst())
            return null;
        LinkedList<Period> list = new LinkedList<Period>();
        do{
            boolean isfinish_temp = false;
            boolean isoften_temp = false;
            if(cursor.getInt(cursor.getColumnIndex(TaskDAO.IS_FINISHED)) == 1)
                isfinish_temp = true;
            if(cursor.getInt(cursor.getColumnIndex(TaskDAO.IS_OFTEN)) == 1)
                isoften_temp = true;

            TaskClass taskClass = new TaskClass(cursor.getString(cursor.getColumnIndex(TaskClassDAO.GUID)),
                    cursor.getString(cursor.getColumnIndex(TaskClassDAO.NAME)),
                    cursor.getString(cursor.getColumnIndex(TaskClassDAO.COLOR)),
                    cursor.getInt(cursor.getColumnIndex(TaskClassDAO.ID)));
            Task task = new Task(cursor.getInt(cursor.getColumnIndex(TaskDAO.ID)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.GUID)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.NAME)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.DESC)),
                    taskClass,
                    cursor.getDouble(cursor.getColumnIndex(TaskDAO.ACHIEVE_PER_HOUR)),
                    isfinish_temp,
                    cursor.getString(cursor.getColumnIndex(TaskDAO.ICON)),
                    isoften_temp);

            list.add(new Period(cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    task,
                    cursor.getLong(cursor.getColumnIndex(BEGIN)),
                    cursor.getLong(cursor.getColumnIndex(END)),
                    cursor.getLong(cursor.getColumnIndex(BEGIN_CALENDER)),
                    cursor.getLong(cursor.getColumnIndex(END_CALENDER))));

        }while(cursor.moveToNext());

        return list;
    }

    public void Add(Period period){
        ContentValues values = new  ContentValues();
        values.put(GUID, period.getGuid());
        values.put(TASK_ID, period.getTask().getId());
        values.put(BEGIN, period.getBegin());
        values.put(END, period.getEnd());
        values.put(BEGIN_CALENDER, period.getBeginCalendar());
        values.put(END_CALENDER, period.getEndCalendar());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public void UpdatePeriod(Period period){
        ContentValues values = new  ContentValues();
        values.put(GUID, period.getGuid());
        values.put(TASK_ID, period.getTask().getId());
        values.put(BEGIN, period.getBegin());
        values.put(END, period.getEnd());
        values.put(BEGIN_CALENDER, period.getBeginCalendar());
        values.put(END_CALENDER, period.getEndCalendar());
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+period.getId(), null);
    }

    public void DeletePeriod(Period period){
        DbContext._SQLiteDatabase.delete(TABLE_NAME, ID+"="+ period.getId(), null);
    }

}
