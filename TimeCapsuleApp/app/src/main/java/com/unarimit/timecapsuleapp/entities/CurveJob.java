package com.unarimit.timecapsuleapp.entities;

import java.util.UUID;

public class CurveJob {
    int Id;
    CurveJobBase CurveJobBase;
    int EpochLog;
    String DoWhat;
    int CostTime;
    boolean IsActive;

    /**
     * create call
     * */
    public CurveJob(CurveJobBase curveJobBase, int epochLog, String doWhat, int costTime) {
        CurveJobBase = curveJobBase;
        EpochLog = epochLog;
        DoWhat = doWhat;
        CostTime = costTime;
        IsActive = true;
    }

    /**
     * DAO call
     * */
    public CurveJob(int id, com.unarimit.timecapsuleapp.entities.CurveJobBase curveJobBase, int epochLog, String doWhat, int costTime, boolean isActive) {
        Id = id;
        CurveJobBase = curveJobBase;
        EpochLog = epochLog;
        DoWhat = doWhat;
        CostTime = costTime;
        IsActive = isActive;
    }

    public int getId() {
        return Id;
    }

    public com.unarimit.timecapsuleapp.entities.CurveJobBase getCurveJobBase() {
        return CurveJobBase;
    }

    public int getEpochLog() {
        return EpochLog;
    }

    public String getDoWhat() {
        return DoWhat;
    }

    public int getCostTime() {
        return CostTime;
    }

    public boolean isActive() {
        return IsActive;
    }
}
