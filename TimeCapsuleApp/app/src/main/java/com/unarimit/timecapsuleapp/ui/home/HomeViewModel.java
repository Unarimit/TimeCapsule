package com.unarimit.timecapsuleapp.ui.home;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final List<Task> tasks;
    public HomeViewModel() {
        tasks = DbContext.Tasks.GetTaskList(false);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Period getCurrentPeriod() {
        return DbContext.Periods.GetCurrentPeriod();
    }
}