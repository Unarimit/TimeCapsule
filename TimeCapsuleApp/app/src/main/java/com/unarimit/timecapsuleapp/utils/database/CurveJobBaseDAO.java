package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Task;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class CurveJobBaseDAO {
    public static final String TABLE_NAME = "curve_job_bases";

    public static final String ID = "cjb_id";  // 数据库内部用于连接的ID
    public static final String BASE_COST = "cjb_base_cost";
    public static final String BEGIN_CALENDER = "cjb_begin_calendar";
    public static final String LAST_CHECK_CALENDER = "cjb_last_check_calendar";
    public static final String IS_OVER = "cjb_is_over";
    public static final String FAILS = "cjb_fails";

    public static final String TASK_ID = "cjb_task_id";


    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + BASE_COST + " integer not null,"
            + TASK_ID + " integer not null,"
            + IS_OVER + " bool not null,"
            + BEGIN_CALENDER + " integer not null,"
            + LAST_CHECK_CALENDER + " integer not null,"
            + FAILS + " integer not null,"
            + TASK_ID + " integer not null,"
            + "foreign key("+TASK_ID + ") references " + TaskDAO.TABLE_NAME + "(" +TaskDAO.ID + "))";


    public List<CurveJobBase> GetList(){
        List<CurveJobBase> result = new LinkedList<>();
        List<Task> taskList = DbContext.Tasks.GetTaskList(false);
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+
                " where " + IS_OVER + "=0", null);
        if(cursor == null || !cursor.moveToFirst())
            return null;
        int id_index = cursor.getColumnIndex(ID);
        int task_id_index = cursor.getColumnIndex(TASK_ID);
        int base_cost_index = cursor.getColumnIndex(BASE_COST);
        int begin_calendar_index = cursor.getColumnIndex(BEGIN_CALENDER);
        int last_check_calendar_index = cursor.getColumnIndex(LAST_CHECK_CALENDER);
        int is_over_index = cursor.getColumnIndex(IS_OVER);
        int fails_index = cursor.getColumnIndex(IS_OVER);

        do{
            boolean is_over = false;
            if(cursor.getInt(is_over_index) == 1)
                is_over = true;
            Task temp = taskList.stream().filter(x -> x.getId() == cursor.getInt(task_id_index)).findFirst().orElse(null);

            if(temp == null) // 如果任务被完成，相应的curvejob也作废（不应该被查到
                continue;

            result.add(new CurveJobBase(cursor.getInt(id_index),
                    temp,
                    cursor.getInt(base_cost_index),
                    cursor.getLong(begin_calendar_index),
                    cursor.getLong(last_check_calendar_index),
                    is_over,
                    cursor.getInt(fails_index)));

        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }

    public void Add(@NotNull CurveJobBase curveJobBase){
        ContentValues values = new  ContentValues();
        values.put(ID, curveJobBase.getId());
        values.put(BASE_COST, curveJobBase.getBaseCost());
        values.put(BEGIN_CALENDER, curveJobBase.getBeginCalendar());
        values.put(LAST_CHECK_CALENDER, curveJobBase.getLastCheckCalendar());
        values.put(IS_OVER, curveJobBase.isOver());
        values.put(FAILS, curveJobBase.getFails());
        values.put(TASK_ID, curveJobBase.getTask().getId());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public void Update(@NotNull CurveJobBase curveJobBase){
        ContentValues values = new  ContentValues();
        values.put(ID, curveJobBase.getId());
        values.put(BASE_COST, curveJobBase.getBaseCost());
        values.put(BEGIN_CALENDER, curveJobBase.getBeginCalendar());
        values.put(LAST_CHECK_CALENDER, curveJobBase.getLastCheckCalendar());
        values.put(IS_OVER, curveJobBase.isOver());
        values.put(FAILS, curveJobBase.getFails());
        // values.put(TASK_ID, curveJobBase.getTask().getId()); need not change id
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+curveJobBase.getId(), null);
    }

}
