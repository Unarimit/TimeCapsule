package com.unarimit.timecapsuleapp.ui.period.barchart;

public class TaskElement {
    public String name;
    public String icon;
    public long time;
    public TaskClassElement taskClassElement;

    public TaskElement(TaskClassElement taskClassElement, String name, String icon, long time) {
        this.taskClassElement = taskClassElement;
        this.name = name;
        this.icon = icon;
        this.time = time;
    }

    public float GetHours(){
        return (float)time / 3600;
    }

    public long getTime() {
        return time;
    }
}
