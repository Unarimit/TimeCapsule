package com.unarimit.timecapsuleapp.ui.period.manager;

import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class PeriodManagerViewModel {
    List<Task> Tasks;
    long todayCalendar;

    public PeriodManagerViewModel(){
        todayCalendar = TimeHelper.GetCurrentSeconds() / 3600 / 24;
        Tasks = DbContext.Tasks.GetTaskList(false);
    }

    public long GetLastPeriodTime(){
        List<Period> periods = DbContext.Periods.GetPeriodList(todayCalendar);
        long time = todayCalendar * 3600 * 24;
        if(periods == null)
            return time;
        else{
            for (Period period: periods) {
                if(period.getEnd() > time){
                    time = period.getEnd();
                }
            }
        }
        return time;
    }

    public long getTodayCalendar() {
        return todayCalendar;
    }

    public List<Task> getTasks() {
        return Tasks;
    }

}
