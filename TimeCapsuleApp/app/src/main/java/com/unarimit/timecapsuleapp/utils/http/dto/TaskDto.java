package com.unarimit.timecapsuleapp.utils.http.dto;

import com.unarimit.timecapsuleapp.entities.TaskClass;

public class TaskDto {
    public int _id;
    String ClassId;
    String Id;
    String Name;
    String Desc;
    String Icon;
    double AchievePerHour;
    boolean IsFinished;
    boolean IsOften;
    long CreateTime;
    long FinishTime;

    private TaskDto(){

    }

    public TaskDto(int _id, String classId, String id, String name, String desc, String icon, double achievePerHour, boolean isFinished, boolean isOften, long createTime, long finishTime) {
        this._id = _id;
        ClassId = classId;
        Id = id;
        Name = name;
        Desc = desc;
        Icon = icon;
        AchievePerHour = achievePerHour;
        IsFinished = isFinished;
        IsOften = isOften;
        CreateTime = createTime;
        FinishTime = finishTime;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public double getAchievePerHour() {
        return AchievePerHour;
    }

    public void setAchievePerHour(double achievePerHour) {
        AchievePerHour = achievePerHour;
    }

    public boolean isFinished() {
        return IsFinished;
    }

    public void setFinished(boolean finished) {
        IsFinished = finished;
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

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public long getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(long finishTime) {
        FinishTime = finishTime;
    }
}
