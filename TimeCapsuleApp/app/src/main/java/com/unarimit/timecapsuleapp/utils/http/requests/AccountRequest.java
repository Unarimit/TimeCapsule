package com.unarimit.timecapsuleapp.utils.http.requests;

import android.util.Log;

import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.HttpConfig;
import com.unarimit.timecapsuleapp.utils.http.dto.UserDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.RequestBody.Companion.*;

public class AccountRequest {
    public static boolean login(UserDto user) throws IOException {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            json.put("usernameOrEmail", user.getUsername());
            json.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.Companion.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(HttpConfig.URL+"/api/identify/Account/login").post(body).build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200){
            DbContext.UserInfos.AddOrUpdateValue(UserConfig.COOKIE, response.header("set-cookie")); // save cookie
            return true;
        }else{
            Log.d("account_request.java, login not 200", response.message());
            return false;
        }
    }
}
