package com.unarimit.timecapsuleapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.unarimit.timecapsuleapp.utils.TimeHelper;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {
    TaskClass TaskClass;
    int Id; // id for sql connection query
    String Guid;
    String Name;
    String Desc;
    String Icon;
    double AchievePerHour;
    boolean IsFinished;
    boolean IsOften;
    long CreateTime;
    long FinishTime;

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
        CreateTime = TimeHelper.GetCurrentSeconds();
        FinishTime = -1;
    }

    /**
     * DAO evoke
     * */
    public Task(int id, String guid, String name, String desc, TaskClass taskClass, double achievePerHour, boolean isFinished, String icon, boolean isOften, long createTime, long finishTime) {
        Id = id;
        Guid = guid;
        Name = name;
        Desc = desc;
        TaskClass = taskClass;
        AchievePerHour = achievePerHour;
        IsFinished = isFinished;
        Icon = icon;
        IsOften = isOften;
        CreateTime = createTime;
        FinishTime = finishTime;
    }


    public void Finish(){
        IsFinished = true;
        FinishTime = TimeHelper.GetCurrentSeconds();
    }

    public void ReverseFinish(){
        IsFinished = false;
        FinishTime = -1;
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

    public long getCreateTime() {
        return CreateTime;
    }

    public long getFinishTime() {
        return FinishTime;
    }
}
