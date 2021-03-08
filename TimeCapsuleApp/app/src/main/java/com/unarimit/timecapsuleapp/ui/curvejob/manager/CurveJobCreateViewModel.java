package com.unarimit.timecapsuleapp.ui.curvejob.manager;

import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class CurveJobCreateViewModel {
    List<Task> tasks;

    public CurveJobCreateViewModel() {
        tasks = DbContext.Tasks.GetTaskList(false);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
