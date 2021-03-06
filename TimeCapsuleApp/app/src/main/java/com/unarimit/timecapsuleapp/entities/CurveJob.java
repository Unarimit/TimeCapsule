package com.unarimit.timecapsuleapp.entities;

import java.util.UUID;

public class CurveJob {
    String Guid;
    CurveJobBase CurveJobBase;
    int Epoch;
    String DoWhat;
    int CostTime;
    boolean IsFinish;

    public CurveJob(CurveJobBase curveJobBase, int epoch, String doWhat, int costTime) {
        CurveJobBase = curveJobBase;
        Epoch = epoch;
        DoWhat = doWhat;
        CostTime = costTime;
        IsFinish = false;
        Guid = UUID.randomUUID().toString();
    }

    public void Finish(){
        IsFinish = true;
    }

    public String getGuid() {
        return Guid;
    }

    public com.unarimit.timecapsuleapp.entities.CurveJobBase getCurveJobBase() {
        return CurveJobBase;
    }

    public int getEpoch() {
        return Epoch;
    }

    public String getDoWhat() {
        return DoWhat;
    }

    public int getCostTime() {
        return CostTime;
    }

    public boolean isFinish() {
        return IsFinish;
    }
}
