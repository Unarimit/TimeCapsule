package com.unarimit.timecapsuleapp.utils.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.unarimit.timecapsuleapp.entities.CurveJob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CurveJobDAO {

    public static final String TABLE_NAME = "curve_jobs";

    public static final String ID = "cj_id";
    public static final String EPOCH_LOG = "cj_epoch_log";
    public static final String DO_WHAT = "cj_do_what";
    public static final String COST_TIME = "cj_cost_time";
    public static final String IS_ACTIVE = "cj_is_active";

    public static final String CURVE_JOB_BASE_ID = "cj_curve_job_base_id";


    public static final String CreateSql = "CREATE TABLE "
            + TABLE_NAME
            + " (" + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + DO_WHAT + " text not null,"
            + EPOCH_LOG + " text not null,"
            + COST_TIME + " integer not null,"
            + IS_ACTIVE + " bool not null,"
            + CURVE_JOB_BASE_ID + " integer not null,"
            + "foreign key("+ CURVE_JOB_BASE_ID + ") references " + CurveJobBaseDAO.TABLE_NAME + "(" +CurveJobBaseDAO.ID + "))";

    /**
     * this method do not return base class instance
     * */
    public List<CurveJob> GetByBaseId(int baseId, boolean allowUnActive){
        List<CurveJob> result = new ArrayList<>();
        Cursor cursor;
        if(!allowUnActive){
            cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+
                    " where " + CURVE_JOB_BASE_ID + "=" + baseId +
                    " and " + IS_ACTIVE +"= 1", null);
        }else{
            cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+
                    " where " + CURVE_JOB_BASE_ID + "=" + baseId
                    +" order by " + ID, null);
        }
        if(cursor == null || !cursor.moveToFirst())
            return null;

        int id_i = cursor.getColumnIndex(ID);
        int epoch_log_i = cursor.getColumnIndex(EPOCH_LOG);
        int do_what_i = cursor.getColumnIndex(DO_WHAT);
        int cost_time_i = cursor.getColumnIndex(COST_TIME);
        int is_active_i = cursor.getColumnIndex(IS_ACTIVE);
        do{
            boolean is_active = false;
            if(cursor.getInt(is_active_i) == 1){
                is_active = true;
            }
            result.add(new CurveJob(cursor.getInt(id_i),
                    null,
                    cursor.getString(epoch_log_i),
                    cursor.getString(do_what_i),
                    cursor.getInt(cost_time_i),
                    is_active));
        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }


    public List<CurveJob> GetByBaseIdAndIndex(int baseId, List<Integer> allowIndex){
        boolean begin = true;
        StringBuilder index_express = new StringBuilder("");
        for (Integer index:
             allowIndex) {
            if(begin){
                begin = false;
            }else{
                index_express.append(" or ");
            }
            index_express.append(index);
        }
        Cursor cursor = DbContext._SQLiteDatabase.rawQuery("select * from "+TABLE_NAME+
                " where " + CURVE_JOB_BASE_ID + "=" + baseId +
                " and " + ID +"=" + index_express.toString(), null);
        if(cursor == null || !cursor.moveToFirst())
            return null;
        List<CurveJob> result = new LinkedList<>();
        int id_i = cursor.getColumnIndex(ID);
        int epoch_log_i = cursor.getColumnIndex(EPOCH_LOG);
        int do_what_i = cursor.getColumnIndex(DO_WHAT);
        int cost_time_i = cursor.getColumnIndex(COST_TIME);
        int is_active_i = cursor.getColumnIndex(IS_ACTIVE);
        do{
            boolean is_active = false;
            if(cursor.getInt(is_active_i) == 1){
                is_active = true;
            }
            result.add(new CurveJob(cursor.getInt(id_i),
                    null,
                    cursor.getString(epoch_log_i),
                    cursor.getString(do_what_i),
                    cursor.getInt(cost_time_i),
                    is_active));
        }while (cursor.moveToNext());

        cursor.close();
        return result;
    }

    public void Add(CurveJob curveJob){
        ContentValues values = new  ContentValues();
        values.put(DO_WHAT, curveJob.getDoWhat());
        values.put(EPOCH_LOG, curveJob.getEpochLog());
        values.put(COST_TIME, curveJob.getCostTime());
        values.put(IS_ACTIVE, curveJob.isActive());
        values.put(CURVE_JOB_BASE_ID, curveJob.getCurveJobBase().getId());
        DbContext._SQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public void Update(CurveJob curveJob){
        ContentValues values = new  ContentValues();
        values.put(DO_WHAT, curveJob.getDoWhat());
        values.put(EPOCH_LOG, curveJob.getEpochLog());
        values.put(COST_TIME, curveJob.getCostTime());
        values.put(IS_ACTIVE, curveJob.isActive());
        DbContext._SQLiteDatabase.update(TABLE_NAME, values, ID+"="+curveJob.getId(), null);
    }

}
