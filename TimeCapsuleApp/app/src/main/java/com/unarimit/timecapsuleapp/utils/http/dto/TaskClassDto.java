package com.unarimit.timecapsuleapp.utils.http.dto;


public class TaskClassDto {
    public int _id;
    String Id;
    String Name;
    String Color;
    private TaskClassDto(){

    }

    public TaskClassDto(int _id, String id, String name, String color) {
        this._id = _id;
        Id = id;
        Name = name;
        Color = color;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
