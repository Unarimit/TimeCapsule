package com.unarimit.timecapsuleapp.entities;

import com.unarimit.timecapsuleapp.ui.common.ConstField;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;
import java.util.Locale;

public class CurveJobBase {
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
        LastCheckCalendar = -1;
        BaseCost = baseCost;
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

        for(int i = 0; i < ConstField.EpochCount; i++){
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
            sum += job.GetCostTimeForNowLoop();
        }
        return sum;
    }

    public String GetListCostString(){
        int cost = GetListCost();
        return cost / 60 + String.format(Locale.getDefault(), "%02d", cost % 60);
    }

    public void Fail(){
        Fails += 1;
    }



    public void Over(){
        IsOver = true;
    }

    /**
     * check date, call this function if need create CurveJob for this day, do not forget update in dbcontext
     * */
    public void Create(long calendar){
        List<CurveJob> allJobs = DbContext.CurveJobs.GetByBaseId(Id, true);
        // for before days
        for(long i = LastCheckCalendar; i < calendar; i++){
            int during = (int)(i - calendar + 1);
            CurveJob temp = new CurveJob(this, 0, "", BaseCost);
            temp.setActive(false);
            temp.Fail();
            DbContext.CurveJobs.Add(temp);
            Fail();
            for(int epoch : ConstField.CurveEpoch){
                if(during > epoch){
                    temp = allJobs.get(during - epoch);
                    temp.Fail();
                    this.Fail();
                    DbContext.CurveJobs.Update(temp);
                }
            }
        }
        // for calendar day(normally today)
        int during = (int)(LastCheckCalendar - calendar + 1);
        CurveJob temp;
        for(int epoch : ConstField.CurveEpoch){
            if(during > epoch){
                temp = allJobs.get(during - epoch);
                temp.setActive(true);
                DbContext.CurveJobs.Update(temp);
            }
        }
        LastCheckCalendar = calendar;
    }
    /**
     * do not forget update in dbcontext
     * */
    public void AuditActiveJob(){
        List<CurveJob> activeJobs = DbContext.CurveJobs.GetByBaseId(Id, false);
        for (CurveJob job: activeJobs
             ) {
            job.Fail();
            this.Fail();
            job.setActive(false);
            DbContext.CurveJobs.Update(job);
        }
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
