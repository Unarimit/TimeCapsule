package com.unarimit.timecapsuleapp.entities;


import com.unarimit.timecapsuleapp.utils.TimeHelper;

import java.io.Serializable;
import java.util.UUID;

import static com.unarimit.timecapsuleapp.utils.TimeHelper.GetCurrentSeconds;

public class Period implements Serializable {
    String Guid;
    int Id;
    Task Task;
    long Begin;
    long End;
    long BeginCalendar;
    long EndCalendar;

    /**
     * begin a period evoke this function
     * */
    public Period(Task task){
        Guid = UUID.randomUUID().toString();
        Task = task;
        Begin = TimeHelper.GetCurrentSeconds();
        End = -1;
        BeginCalendar = Begin / 3600 / 24;
        EndCalendar = -1;
    }

    /**
     * DAO evoke
     * */
    public Period(String guid, int id, Task task, long begin, long end, long beginCalendar, long endCalendar) {
        Guid = guid;
        Id = id;
        Task = task;
        Begin = begin;
        End = end;
        BeginCalendar = beginCalendar;
        EndCalendar = endCalendar;
    }

    public void Finish(){
        End = TimeHelper.GetCurrentSeconds();
        EndCalendar = End / 3600 / 24;
    }

    public String getGuid() {
        return Guid;
    }

    public int getId() {
        return Id;
    }

    public com.unarimit.timecapsuleapp.entities.Task getTask() {
        return Task;
    }

    public long getBegin() {
        return Begin;
    }

    public long getEnd() {
        return End;
    }

    public long getBeginCalendar() {
        return BeginCalendar;
    }

    public long getEndCalendar() {
        return EndCalendar;
    }

    public void setTask(com.unarimit.timecapsuleapp.entities.Task task) {
        Task = task;
    }

    public void setBegin(long begin) {
        Begin = begin;
    }

    public void setEnd(long end) {
        End = end;
    }

    public void setBeginCalendar(long beginCalendar) {
        BeginCalendar = beginCalendar;
    }

    public void setEndCalendar(long endCalendar) {
        EndCalendar = endCalendar;
    }
}
