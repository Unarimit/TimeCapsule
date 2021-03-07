package com.unarimit.timecapsuleapp.entities;

import java.util.List;

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

    public CurveJobBase(int id, com.unarimit.timecapsuleapp.entities.Task task, int baseCost, long beginCalendar, long lastCheckCalendar, boolean isOver, int fails) {
        Id = id;
        Task = task;
        BaseCost = baseCost;
        BeginCalendar = beginCalendar;
        LastCheckCalendar = lastCheckCalendar;
        IsOver = isOver;
        Fails = fails;
    }

    /**
     * DAO query call
     * */


    public void Over(){
        IsOver = true;
    }

    /**
     * check date, call this function if need create CurveJob for this day
     * */
    public void Create(int calendar){

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
