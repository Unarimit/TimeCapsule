package com.unarimit.timecapsuleapp.entities;

import com.unarimit.timecapsuleapp.utils.TimeHelper;

public class ExceptionInfo {
    long Date;
    String Info;

    public ExceptionInfo(String info){
        Date = TimeHelper.GetCurrentSeconds();
        Info = info;
    }

    /**
     * call by DAO
     * */
    public ExceptionInfo(long date, String info) {
        Date = date;
        Info = info;
    }

    public long getDate() {
        return Date;
    }

    public String getInfo() {
        return Info;
    }
}
