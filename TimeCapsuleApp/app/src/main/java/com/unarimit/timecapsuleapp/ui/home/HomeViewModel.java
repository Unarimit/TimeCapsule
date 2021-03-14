package com.unarimit.timecapsuleapp.ui.home;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.CurveJob;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final List<Task> tasks;
    private final List<CurveJobBase> jobBases;
    private final long calendar = TimeHelper.GetCurrentSeconds()/3600/24;
    public HomeViewModel() {
        tasks = DbContext.Tasks.GetTaskList(false);

        jobBases = DbContext.CurveJobBases.GetList();
        if(jobBases == null)
            return;
        for (CurveJobBase jobBase: jobBases
             ) {

            if(calendar > jobBase.getLastCheckCalendar()){
                jobBase.AuditActiveJob();
                jobBase.Create(calendar);
                DbContext.CurveJobBases.Update(jobBase);
            }
            // get active job
            // TODO: change ways
            jobBase.setJobs(DbContext.CurveJobs.GetDateList(jobBase, calendar));
            if(jobBase.getJobs() == null){
                return;
            }
            for (CurveJob job:  jobBase.getJobs()
                 ) {
                job.setCurveJobBase(jobBase);
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Period getCurrentPeriod() {
        return DbContext.Periods.GetCurrentPeriod();
    }

    public List<CurveJobBase> getJobBases() {
        return jobBases;
    }

    public long getCalendar() {
        return calendar;
    }
}