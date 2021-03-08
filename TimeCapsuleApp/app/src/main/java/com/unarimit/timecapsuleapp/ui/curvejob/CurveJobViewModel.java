package com.unarimit.timecapsuleapp.ui.curvejob;

import com.unarimit.timecapsuleapp.entities.CurveJob;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class CurveJobViewModel {

    private final List<CurveJobBase> jobBases;
    public CurveJobViewModel(){
        jobBases = DbContext.CurveJobBases.GetList();
        for (CurveJobBase jobBase: jobBases
        ) {
            long calendar = TimeHelper.GetCurrentSeconds()/3600/24;
            if(calendar > jobBase.getLastCheckCalendar()){
                jobBase.AuditActiveJob();
                jobBase.Create(calendar);
                DbContext.CurveJobBases.Update(jobBase);
            }
            jobBase.setJobs(DbContext.CurveJobs.GetByBaseId(jobBase.getId(), false));
            for (CurveJob job:  jobBase.getJobs()
            ) {
                job.setCurveJobBase(jobBase);
            }
        }
    }

    public List<CurveJobBase> getJobBases() {
        return jobBases;
    }
}
