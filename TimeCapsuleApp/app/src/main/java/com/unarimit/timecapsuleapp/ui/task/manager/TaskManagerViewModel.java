package com.unarimit.timecapsuleapp.ui.task.manager;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class TaskManagerViewModel extends ViewModel {
    List<TaskClass> taskClasses;
    public TaskManagerViewModel(){
        taskClasses = DbContext.TaskClasses.GetTaskClassList();
    }
    public List<TaskClass> getTaskClasses() {
        return taskClasses;
    }
}
