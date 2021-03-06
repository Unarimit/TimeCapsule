package com.unarimit.timecapsuleapp.ui.taskclass;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class TaskClassViewModel extends ViewModel {


    List<TaskClass> taskClasses;
    public TaskClassViewModel(){
        taskClasses = DbContext.TaskClasses.GetTaskClassList();
    }

    public List<TaskClass> getTaskClasses() {
        return taskClasses;
    }

}
