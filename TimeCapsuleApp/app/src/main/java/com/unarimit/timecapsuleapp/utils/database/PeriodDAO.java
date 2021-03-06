package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.http.dto.PeriodDto;

import org.jetbrains.annotations.NotNull;

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
    public static final String SYNC = "p_sync";
    public static final String LAST_MODIFIED = "p_last_modified";

    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + GUID + " text not null,"
            + TASK_ID + " integer not null,"
            + BEGIN + " integer not null,"
            + END + " integer not null,"
            + BEGIN_CALENDER + " integer not null,"
            + END_CALENDER + " integer not null,"
            + SYNC + " bool not null,"
            + LAST_MODIFIED + " integer not null,"
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
                isoften_temp,0,0); // 不需要展示task的时间数据

        return new Period(cursor.getString(cursor.getColumnIndex(GUID)),
                cursor.getInt(cursor.getColumnIndex(ID)),
                task,
                cursor.getLong(cursor.getColumnIndex(BEGIN)),
                cursor.getLong(cursor.getColumnIndex(END)),
                cursor.getLong(cursor.getColumnIndex(BEGIN_CALENDER)),
                cursor.getLong(cursor.getColumnIndex(END_CALENDER)),
                cursor.getLong(cursor.getColumnIndex(LAST_MODIFIED)));
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
            // TODO: 性能优化，getIndex在循环外进行，不要进行联表查询
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
                    isoften_temp, 0, 0); // 不需要展示task的时间数据

            list.add(new Period(cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    task,
                    cursor.getLong(cursor.getColumnIndex(BEGIN)),
                    cursor.getLong(cursor.getColumnIndex(END)),
                    cursor.getLong(cursor.getColumnIndex(BEGIN_CALENDER)),
                    cursor.getLong(cursor.getColumnIndex(END_CALENDER)),
                    cursor.getLong(cursor.getColumnIndex(LAST_MODIFIED))));

        }while(cursor.moveToNext());

        return list;
    }

    public void Add(Period period, boolean sync){
        ContentValues values = new  ContentValues();
        values.put(GUID, period.getGuid());
        values.put(TASK_ID, period.getTask().getId());
        values.put(BEGIN, period.getBegin());
        values.put(END, period.getEnd());
        values.put(BEGIN_CALENDER, period.getBeginCalendar());
        values.put(END_CALENDER, period.getEndCalendar());
        values.put(SYNC, sync);
        if(period.getLastModified() != 0){
            values.put(LAST_MODIFIED, period.getLastModified());
        }else{
            values.put(LAST_MODIFIED, TimeHelper.GetCurrentSeconds());
        }
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);

        if(period.getEnd() != -1){
            long total = (period.getEnd() - period.getBegin()) / 3600; // to min
            double achieve = (double)total  * period.getTask().getAchievePerHour();
            DbContext.CurrentUser.SetAchievePoint(DbContext.CurrentUser.getAchievePoint() + achieve);
        }
    }

    public void UpdatePeriod(Period period, boolean sync){
        ContentValues values = new  ContentValues();
        values.put(TASK_ID, period.getTask().getId());
        values.put(BEGIN, period.getBegin());
        values.put(END, period.getEnd());
        values.put(BEGIN_CALENDER, period.getBeginCalendar());
        values.put(END_CALENDER, period.getEndCalendar());
        values.put(SYNC, sync);
        if(period.getLastModified() != 0){
            values.put(LAST_MODIFIED, period.getLastModified());
        }else{
            values.put(LAST_MODIFIED, TimeHelper.GetCurrentSeconds());
        }
        if(period.getId() == 0){
            DbContext._SQLiteDatabase.update(TABLE_NAME, values, GUID+"= '"+period.getGuid()+"'", null);
        }else{
            DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+period.getId(), null);
        }


        if(period.getEnd() != -1){  // 处理之前修改过的时间段
            double last_achievement = 0;
            if(period.getLogEnd() != -1){
                long total = (period.getLogEnd() - period.getBegin()) / 3600; // to min
                last_achievement = (double)total  * period.getLogTask().getAchievePerHour();
            }
            long total = (period.getEnd() - period.getBegin()) / 3600; // to min
            double achieve = (double)total  * period.getTask().getAchievePerHour();
            DbContext.CurrentUser.SetAchievePoint(DbContext.CurrentUser.getAchievePoint() + (achieve - last_achievement));
        }
    }
    /**
     * invoke by sync function, task.id(int) need
     * */
    public void UpdateOrAddPeriod(Period period){
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME +
                " where " + GUID + " = '" + period.getGuid()+"'", null);
        if(cursor == null || !cursor.moveToFirst())
            Add(period, true);
        else
            UpdatePeriod(period, true);
    }

    public List<PeriodDto> GetNotSyncPeriodAll(){
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+", "+TaskDAO.TABLE_NAME+
                " where "+ TASK_ID + "=" + TaskDAO.ID
                + " and "+ SYNC + "= 0"
                + " limit 100", null);

        if(cursor == null || !cursor.moveToFirst())
            return null;
        LinkedList<PeriodDto> list = new LinkedList<>();
        do{
            // TODO: 性能优化，getIndex在循环外进行，不要进行联表查询
            list.add(new PeriodDto(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(GUID)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.GUID)),
                    cursor.getLong(cursor.getColumnIndex(BEGIN)),
                    cursor.getLong(cursor.getColumnIndex(END)),
                    cursor.getLong(cursor.getColumnIndex(LAST_MODIFIED))));
        }while(cursor.moveToNext());

        return list;
    }
    public void Sync(int id){
        ContentValues values = new  ContentValues();
        values.put(SYNC, true);
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+id, null);
    }

    public void DeletePeriod(Period period){
        DbContext._SQLiteDatabase.delete(TABLE_NAME, ID+"="+ period.getId(), null);
    }

}
