package com.unarimit.timecapsuleapp.utils;

import android.content.Intent;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class CustomDate {
    int year;
    int month;
    int day;
    Calendar c;
    /**
     * calendar is days from 1970/1/1
     * */
    public CustomDate(long calendar){
        Date date = new Date(calendar * 24 * 3600 * 1000);
        c = Calendar.getInstance();
        c.setTime(date);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
    }
    public String GetDateString(){
        return month + "月" + day + "日";
    }

    public String GetDateStringWithYear(){
        return month + "月" + day + "日" + ", " + year;
    }

    public String GetLastDateString(){
        Calendar c2 = (Calendar) c.clone();
        c2.add(Calendar.DAY_OF_MONTH, -1);
        return c2.get(Calendar.MONTH) + 1 + "月" + c2.get(Calendar.DAY_OF_MONTH) + "日";
    }

    public String GetNextDateString(){
        Calendar c2 = (Calendar) c.clone();
        c2.add(Calendar.DAY_OF_MONTH, 1);
        return c2.get(Calendar.MONTH) + 1 + "月" + c2.get(Calendar.DAY_OF_MONTH) + "日";
    }

    public void OnPicker(int year, int month, int day){
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        this.year = year;
        this.month = month +1;
        this.day = day;
    }

    public long GetCalendar(){
        return c.getTimeInMillis() / 1000 / 3600 / 24;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
