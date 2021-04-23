package com.unarimit.timecapsuleapp.utils.http.dto;


import java.io.Serializable;

public class PeriodDto implements Serializable {
    public int _id;
    private String id;
    private String taskId;
    private long begin;
    private long end;
    private long lastModified;

    public PeriodDto(int _id, String i, String taskId, long begin, long end, long lastModified) {
        this._id = _id;
        this.id = i;
        this.taskId = taskId;
        this.begin = begin;
        this.end = end;
        this.lastModified = lastModified;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
