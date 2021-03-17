package com.unarimit.timecapsuleapp.entities;

import android.util.Log;

import com.unarimit.timecapsuleapp.ui.common.ConstField;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

public class CurveJob implements Serializable {
    int Id;
    CurveJobBase CurveJobBase;
    String EpochLog;
    String DoWhat;
    int CostTime;
    int Days;
    boolean IsActive;

    /**
     * create call
     * */
    public CurveJob(CurveJobBase curveJobBase, String doWhat, int costTime, int days) {
        CurveJobBase = curveJobBase;
        EpochLog = "";
        DoWhat = doWhat;
        CostTime = costTime;
        IsActive = true;
        Days = days;
    }

    /**
     * DAO call
     * */
    public CurveJob(int id, com.unarimit.timecapsuleapp.entities.CurveJobBase curveJobBase, String epochLog, String doWhat, int costTime, int days, boolean isActive) {
        Id = id;
        CurveJobBase = curveJobBase;
        EpochLog = epochLog;
        DoWhat = doWhat;
        CostTime = costTime;
        IsActive = isActive;
        Days = days;
    }

    public String GetEpochInfo(){
        long during = (CurveJobBase.getLastCheckCalendar() - CurveJobBase.getBeginCalendar()) - Days + 1;
        int loop = 1;
        for(int i = ConstField.EpochCount - 1; i >= 0; i--){
            if(during > ConstField.CurveEpoch[i]){
                loop += (i + 1);
                break;
            }
        }
        return "#" + Days + " loop " + loop;
    }

    public int GetCostTimeForNowLoop(){
        long during = (CurveJobBase.getLastCheckCalendar() - CurveJobBase.getBeginCalendar()) - Id + 2;
        double loop_factor = 1;
        if(during > 4){
            loop_factor += 1;
        }
        else if(during > 1){
            loop_factor += 0.5;
        }
        return (int)Math.floor(CostTime / loop_factor);
    }

    public String GetCostString(){
        int cost = GetCostTimeForNowLoop();
        return cost / 60 + ":" + String.format(Locale.getDefault(), "%02d", cost % 60);
    }


    public com.unarimit.timecapsuleapp.entities.CurveJobBase getCurveJobBase() {
        return CurveJobBase;
    }
    public void Begin(){
        IsActive = true;
    }
    public void Fail(){
        EpochLog += "0";
        IsActive = false;
    }
    public void Finish(){
        EpochLog += "1";
        IsActive = false;
    }

    public void ResetFinish(){
        EpochLog = EpochLog.substring(0,EpochLog.length() - 1);
        IsActive = true;
    }

    /**
     * 根据EpochLog判断此时的任务是否完成
     * */
    public boolean IsFinish(long calendar) throws Exception {
        long during = (calendar - CurveJobBase.getBeginCalendar()) - Days + 1;
        for(int i = 0; i < ConstField.EpochCount; i++){
            if(during == ConstField.CurveEpoch[i]){
                // 若未完成，最新位会out of index
                int result = Integer.parseInt((EpochLog+"0").toCharArray()[i]+""); // for out of index
                if(result == 1){
                    return true;
                }else{
                    return false;
                }
            }
        }
        throw new Exception("error calendar");
    }

    public int getId() {
        return Id;
    }

    public String getEpochLog() {
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

    public void setCurveJobBase(com.unarimit.timecapsuleapp.entities.CurveJobBase curveJobBase) {
        CurveJobBase = curveJobBase;
    }

    public void setDoWhat(String doWhat) {
        DoWhat = doWhat;
    }

    public int getDays() {
        return Days;
    }
}
