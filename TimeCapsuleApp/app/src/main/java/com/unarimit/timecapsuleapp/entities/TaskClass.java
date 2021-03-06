package com.unarimit.timecapsuleapp.entities;

import java.io.Serializable;
import java.util.UUID;

public class TaskClass implements Serializable {
    int Id; // id for sql connection query
    String Guid;
    String Name;
    String Color; // e.g. #FF6666

    private TaskClass(){}

    /**
     * create new taskclass evoke
     * */
    public TaskClass(String name, String color) {
        Name = name;
        Color = color;
        Guid = UUID.randomUUID().toString();
    }

    /**
     * DAO evoke
     * */
    public TaskClass(String guid, String name, String color, int id) {
        Guid = guid;
        Name = name;
        Color = color;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGuid() {
        return Guid;
    }

    public String getName() {
        return Name;
    }

    public String getColor() {
        return Color;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setColor(String color) {
        Color = color;
    }
}
