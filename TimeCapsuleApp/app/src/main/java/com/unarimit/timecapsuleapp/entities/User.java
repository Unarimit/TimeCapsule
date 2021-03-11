package com.unarimit.timecapsuleapp.entities;

import java.util.UUID;

public class User {
    //TODO: final save in database
    String Id = "550e8400-e29b-41d4-a716-446655440000";
    String Username = "test";
    int AchievePoint = 100;

    public void GetAchieve(int point){
        AchievePoint += point;
    }

    public String getId() {
        return Id;
    }

    public String getUsername() {
        return Username;
    }

    public int getAchievePoint() {
        return AchievePoint;
    }
}
