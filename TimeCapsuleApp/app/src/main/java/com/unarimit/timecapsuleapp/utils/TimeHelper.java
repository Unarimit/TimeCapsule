package com.unarimit.timecapsuleapp.utils;

import java.util.Locale;
import java.util.TimeZone;

public class TimeHelper {
    public static long GetCurrentSeconds(){
        return (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()) / 1000;
    }

    /**
     * long date are seconds from 1970/1/1,
     * @return hh:mm
     * */
    public static String DateLongToTimeString(long date){
        return  String.format(Locale.getDefault(), "%02d",date / 3600 % 24)
                + ":" + String.format(Locale.getDefault(), "%02d",date / 60 % 60);
    }



    /**
     * long date are seconds from 1970/1/1, use for **debug** log
     * @return YYYY/MM/DDThh:mm:ss
     */
    public static String DateLongToString(long date){
        CustomDate customDate = new CustomDate(date / 24 / 3600);
        return customDate.year + "/" + customDate.month + "/" + customDate.day + "T"
                + String.format(Locale.getDefault(), "%02d",date / 3600 % 24)
                + ":" + String.format(Locale.getDefault(), "%02d",date / 60 % 60)
                + ":" + String.format(Locale.getDefault(), "%02d",date % 60);
    }

}
