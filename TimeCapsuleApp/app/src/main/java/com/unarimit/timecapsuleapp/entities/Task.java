package com.unarimit.timecapsuleapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {
    int Id; // id for sql connection query
    String Guid;
    String Name;
    String Desc;
    TaskClass TaskClass;
    double AchievePerHour;
    boolean IsFinished;
    String Icon;
    boolean IsOften;

    /**
     * create evoke
     * */
    public Task(String name, String desc, TaskClass taskClass, double achievePerHour, String icon, boolean isOften) {
        Name = name;
        Desc = desc;
        TaskClass = taskClass;
        AchievePerHour = achievePerHour;
        Icon = icon;
        Guid = UUID.randomUUID().toString();
        IsFinished = false;
        IsOften = isOften;
    }

    /**
     * DAO evoke
     * */
    public Task(int id, String guid, String name, String desc, TaskClass taskClass, double achievePerHour, boolean isFinished, String icon, boolean isOften) {
        Id = id;
        Guid = guid;
        Name = name;
        Desc = desc;
        TaskClass = taskClass;
        AchievePerHour = achievePerHour;
        IsFinished = isFinished;
        Icon = icon;
        IsOften = isOften;
    }


    public void Finish(){
        IsFinished = true;
    }

    public void ReverseFinish(){
        IsFinished = false;
    }

    public String getGuid() {
        return Guid;
    }

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public TaskClass getTaskClass() {
        return TaskClass;
    }

    public double getAchievePerHour() {
        return AchievePerHour;
    }

    public String getIcon() {
        return Icon;
    }

    public int getId() {
        return Id;
    }

    public boolean isFinished() {
        return IsFinished;
    }

    public boolean isOften() {
        return IsOften;
    }

    public void setOften(boolean often) {
        IsOften = often;
    }
}
