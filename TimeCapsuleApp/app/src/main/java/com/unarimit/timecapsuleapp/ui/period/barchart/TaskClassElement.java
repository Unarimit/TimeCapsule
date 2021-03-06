package com.unarimit.timecapsuleapp.ui.period.barchart;

import com.unarimit.timecapsuleapp.ui.period.PeriodFragment;

import java.util.LinkedList;
import java.util.List;

public class TaskClassElement {
    public String name;
    public String color;
    public long totalDuring;
    public List<TaskElement> taskList = new LinkedList<>();
    public TaskClassElement(String name, String color, long totalDuring) {
        this.name = name;
        this.color = color;
        this.totalDuring = totalDuring;
    }
}
