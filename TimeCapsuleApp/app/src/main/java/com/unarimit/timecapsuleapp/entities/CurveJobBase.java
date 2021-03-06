package com.unarimit.timecapsuleapp.entities;

public class CurveJobBase {
    int Id;
    Task Task;
    int BaseCost;
    long BeginCalendar;
    long LastCheckCalendar;
    boolean IsOver;

    /**
     * create call
     * */
    public CurveJobBase(com.unarimit.timecapsuleapp.entities.Task task, long beginCalendar, int baseCost) {
        Task = task;
        BeginCalendar = beginCalendar;
        LastCheckCalendar = -1;
        BaseCost = baseCost;
        IsOver = false;
    }

    /**
     * DAO query call
     * */
    public CurveJobBase(int id, com.unarimit.timecapsuleapp.entities.Task task, int baseCost, long beginCalendar, long lastCheckCalendar, boolean isOver) {
        Id = id;
        Task = task;
        BaseCost = baseCost;
        BeginCalendar = beginCalendar;
        LastCheckCalendar = lastCheckCalendar;
        IsOver = isOver;
    }

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
}
