package com.unarimit.timecapsuleapp.ui.period;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.Comparator;
import java.util.List;

public class PeriodViewModel extends ViewModel {
    List<Period> periods;
    private long calendar;
    public PeriodViewModel(){
        calendar = TimeHelper.GetCurrentSeconds() / 3600 / 24;
        QueryPeriods();
    }

    public void Refresh(){
        QueryPeriods();
    }

    public void NextDay(){
        calendar += 1;
        QueryPeriods();
    }

    public void LastDay(){
        calendar -= 1;
        QueryPeriods();
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public long getCalendar() {
        return calendar;
    }
    private void QueryPeriods(){
        periods = DbContext.Periods.GetPeriodList(calendar);
        if(periods == null)
            return;
        periods.sort(Comparator.comparing(Period::getBegin).reversed());
    }
}
