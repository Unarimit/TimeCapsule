package com.unarimit.timecapsuleapp.entities;

import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.UUID;

public class User {
    //TODO: final save in database
    String Id = "550e8400-e29b-41d4-a716-446655440000";
    String Username = DbContext.UserInfos.GetValue(UserConfig.USER_NAME);
    int AchievePoint = Integer.parseInt(DbContext.UserInfos.GetValue(UserConfig.ACHIEVE));

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
