package com.unarimit.timecapsuleapp.utils.http.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.HttpConfig;
import com.unarimit.timecapsuleapp.utils.http.dto.TaskClassDto;
import com.unarimit.timecapsuleapp.utils.http.dto.TaskDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntitySyncRequest {
    private static final RequestModel requestModel = new RequestModel();

    private static class RequestModel{
        public List<TaskClassDto> aTimeTaskClasses;
        public List<TaskDto> aTimeTasks;
    }

    public static boolean SyncStuff() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String cookie = DbContext.UserInfos.GetValue(UserConfig.COOKIE);
        if(cookie == null){
            Log.d("entity_sync_request.java", "empty cookie");
            return false;
        }
        requestModel.aTimeTaskClasses = DbContext.TaskClasses.GetNotSyncAll();
        requestModel.aTimeTasks = DbContext.Tasks.GetNotSyncAll();
        Gson gson = new Gson();
        RequestBody body = RequestBody.Companion.create(gson.toJson(requestModel), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(HttpConfig.URL+"/api/user/Sync/stuff")
                .post(body)
                .addHeader("cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200){
            for (TaskDto task: requestModel.aTimeTasks
                 ) {
                DbContext.Tasks.Sync(task._id);
            }
            for (TaskClassDto taskClass: requestModel.aTimeTaskClasses
            ) {
                DbContext.TaskClasses.Sync(taskClass._id);
            }
            return true;
        }else{
            Log.d("entity_sync_request.java, response not 200", response.message());
            return false;
        }
    }

    public static boolean Test() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String cookie = DbContext.UserInfos.GetValue(UserConfig.COOKIE);
        if(cookie == null){
            Log.d("entity_sync_request.java", "empty cookie");
            return false;
        }

        Request request = new Request.Builder()
                .url(HttpConfig.URL+"/api/admin/ManagerRegisters")
                .get()
                .addHeader("cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200){
            //TODO: save cookie
            Log.d("entity_sync_request.java", response.body().string());
            return true;
        }else{
            Log.d("entity_sync_request.java, status not 200", response.message());
            return false;
        }
    }
}
