package com.unarimit.timecapsuleapp.ui.task;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class TaskViewModel extends ViewModel {
    List<Task> Tasks;

    public TaskViewModel(){
        Tasks = DbContext.Tasks.GetTaskList(true);
    }

    public List<Task> getTasks() {
        return Tasks;
    }
}
