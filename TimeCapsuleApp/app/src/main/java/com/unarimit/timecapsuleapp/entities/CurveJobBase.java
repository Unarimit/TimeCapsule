package com.unarimit.timecapsuleapp.entities;

import android.util.Log;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.ConstField;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class CurveJobBase implements Serializable {
    int Id;
    Task Task;
    int BaseCost;
    long BeginCalendar;
    long LastCheckCalendar;
    boolean IsOver;
    int Fails;
    List<CurveJob> Jobs;

    /**
     * create call
     * */
    public CurveJobBase(com.unarimit.timecapsuleapp.entities.Task task, long beginCalendar, int baseCost) {
        Task = task;
        BeginCalendar = beginCalendar;
        LastCheckCalendar = beginCalendar - 1;
        BaseCost = baseCost; // all minutes express a time
        Fails = 0;
        IsOver = false;
    }

    /**
     * DAO query call
     * */
    public CurveJobBase(int id, com.unarimit.timecapsuleapp.entities.Task task, int baseCost, long beginCalendar, long lastCheckCalendar, boolean isOver, int fails) {
        Id = id;
        Task = task;
        BaseCost = baseCost;
        BeginCalendar = beginCalendar;
        LastCheckCalendar = lastCheckCalendar;
        IsOver = isOver;
        Fails = fails;
    }

    public int GetTotalJobs(long calendar){
        int days = (int)(calendar - BeginCalendar) + 1;
        int jobs = days;

        for(int i = 1; i < ConstField.EpochCount; i++){
            if(days > ConstField.CurveEpoch[i]){
                jobs += days - ConstField.CurveEpoch[i];
            }
        }

        return jobs;
    }
    public int GetListCost(){
        int sum = 0;
        for (CurveJob job:
             Jobs) {
            if(job.isActive())
                sum += job.GetCostTimeForNowLoop();
        }
        return sum;
    }

    public String GetListCostString(){
        int cost = GetListCost();
        if(cost == 0)
            return DbContext.Context.getString(R.string.curvejob_finish);
        else
            return cost / 60 + ":" + String.format(Locale.getDefault(), "%02d", cost % 60);
    }

    public void Fail(){
        Fails += 1;
    }



    public void Over(){
        IsOver = true;
    }

    /**
     * [DB related] check date, call this function if need create CurveJob for this day, do not forget update in dbcontext
     * */
    public void Create(long calendar){
        if(calendar <= LastCheckCalendar)
            return;
        // for before days create
        for(long i = LastCheckCalendar; i < calendar - 1; i++){ // lastCheckCalendar always smaller than calendar
            int during = (int)(i - BeginCalendar + 2);
            CurveJob temp = new CurveJob(this, "", BaseCost, during);
            DbContext.CurveJobs.Add(temp);
        }
        // for before days fails
        List<CurveJob> allJobs = DbContext.CurveJobs.GetByBaseId(Id, true);
        if(allJobs != null){
            for(long i = LastCheckCalendar; i < calendar - 1; i++){
                int during = (int)(i - BeginCalendar + 1);
                for(int epoch : ConstField.CurveEpoch){
                    if(during >= epoch){
                        CurveJob temp = allJobs.get(during - epoch); // index begin with 0
                        temp.Fail();
                        this.Fail();
                        DbContext.CurveJobs.Update(temp);
                    }
                }
            }
        }
        // for calendar day(normally today) create
        CurveJob temp = new CurveJob(this, "", BaseCost, (int)calendar - (int)BeginCalendar + 1);
        temp.Begin();
        DbContext.CurveJobs.Add(temp);

        // for calendar day(normally today) review
        int during = (int)(calendar - BeginCalendar);
        for(int i = 1; i < ConstField.EpochCount;  i ++){
            if(during >= ConstField.CurveEpoch[i]){
                temp = allJobs.get(during - ConstField.CurveEpoch[i]); // index begin with 0
                temp.Begin();
                DbContext.CurveJobs.Update(temp);
            }
        }
        LastCheckCalendar = calendar;
    }
    /**
     * [DB related] do not forget update in dbcontext
     * */
    public void AuditActiveJob(){
        List<CurveJob> activeJobs = DbContext.CurveJobs.GetByBaseId(Id, false);
        if(activeJobs == null){
            return;
        }
        for (CurveJob job: activeJobs
             ) {
            job.Fail();
            this.Fail();
            DbContext.CurveJobs.Update(job);
        }
    }
    /**
     *
     * */
    public List<Integer> GetTodayJobsId(long calendar){
        List<Integer> result = new LinkedList<>();

        for(int i = 0; i < ConstField.EpochCount; i++){
            int during = (int)(calendar - BeginCalendar); // 从第一天时，第一个任务应当执行开始推
            if(during >= ConstField.CurveEpoch[i]){
                result.add(during - ConstField.CurveEpoch[i] + 1);
            }
        }
        return result;
    }

    public int getId() {
        return Id;
    }

    public com.unarimit.timecapsuleapp.entities.Task getTask() {
        return Task;
    }

    public long getBeginCalendar() {
        return BeginCalendar;
    }

    public int getBaseCost() {
        return BaseCost;
    }

    public boolean isOver() {
        return IsOver;
    }

    public long getLastCheckCalendar() {
        return LastCheckCalendar;
    }

    public void setLastCheckCalendar(long lastCheckCalendar) {
        LastCheckCalendar = lastCheckCalendar;
    }

    public int getFails() {
        return Fails;
    }

    public List<CurveJob> getJobs() {
        return Jobs;
    }

    public void setJobs(List<CurveJob> jobs) {
        Jobs = jobs;
    }
}
