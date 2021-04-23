package com.unarimit.timecapsuleapp.utils.http.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.HttpConfig;
import com.unarimit.timecapsuleapp.utils.http.dto.PeriodDto;
import com.unarimit.timecapsuleapp.utils.http.dto.TaskClassDto;
import com.unarimit.timecapsuleapp.utils.http.dto.TaskDto;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntitySyncRequest {

    private static class RequestModel{
        public List<TaskClassDto> aTimeTaskClasses;
        public List<TaskDto> aTimeTasks;
    }

    public static boolean SyncStuff() throws IOException {
        if(DbContext.UserInfos.GetValue(UserConfig.SYNC).equals("false"))
           return false;
        OkHttpClient client = new OkHttpClient();
        String cookie = DbContext.UserInfos.GetValue(UserConfig.COOKIE);
        if(cookie == null){
            Log.d("entity_sync_request.java", "empty cookie");
            return false;
        }
        RequestModel requestModel = new RequestModel();
        requestModel.aTimeTaskClasses = DbContext.TaskClasses.GetNotSyncAll();
        requestModel.aTimeTasks = DbContext.Tasks.GetNotSyncAll();
        if(requestModel.aTimeTaskClasses == null && requestModel.aTimeTasks ==null){
            return true;
        }else{
            if(requestModel.aTimeTaskClasses == null)
                requestModel.aTimeTaskClasses = new LinkedList<>();
            if(requestModel.aTimeTasks == null)
                requestModel.aTimeTasks = new LinkedList<>();
        }

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

    public enum SyncStatus{
        NOT_SYNC,
        FAIL,
        NO_CHANGE,
        CHANGED
    }

    public static SyncStatus SyncPeriods() throws IOException {
        if(DbContext.UserInfos.GetValue(UserConfig.SYNC).equals("false"))
            return SyncStatus.NOT_SYNC;
        Log.d("entity_sync_request.java", "I am sync");
        // pull stage
        SyncStatus status = SyncPullPeriods();
        if(status!=SyncStatus.FAIL){
            // push stage
            if(SyncPushPeriods()){
                return status;
            }else{
                return SyncStatus.FAIL;
            }
        }else{
            return SyncStatus.FAIL;
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
    public static class SyncPeriodsPullModel{
        public long lastSync;
    }
    public static class SyncPeriodsPullResult{
        public List<PeriodDto> aTimePeriods;
        public boolean isFinish;
    }
    private static SyncStatus SyncPullPeriods() throws IOException
    {
        SyncPeriodsPullModel pullModel = new SyncPeriodsPullModel();
        pullModel.lastSync = Long.parseLong(DbContext.UserInfos.GetValue(UserConfig.LAST_SYNC));

        OkHttpClient client = new OkHttpClient();
        String cookie = DbContext.UserInfos.GetValue(UserConfig.COOKIE);
        if(cookie == null){
            Log.d("entity_sync_request.java", "empty cookie");
            return SyncStatus.FAIL;
        }
        Gson gson = new Gson();
        RequestBody body = RequestBody.Companion.create(gson.toJson(pullModel), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(HttpConfig.URL+"/api/user/Sync/getPeriods")
                .post(body)
                .addHeader("cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        long last_sync = pullModel.lastSync;
        if(response.code() == 200){
            String temp = response.body().string();
            Log.d("entity_sync_request.java", temp);
            SyncPeriodsPullResult result = gson.fromJson(temp, SyncPeriodsPullResult.class);
            List<Task> taskList = DbContext.Tasks.GetTaskList(true);
            if(result.aTimePeriods != null)
                for (PeriodDto period_dto: result.aTimePeriods
                     ) {
                    Optional<Task> first = taskList.stream().filter(x -> x.getGuid().equals(period_dto.getTaskId())).findFirst();
                    Task task = null;
                    if(first.isPresent()){
                        task = first.get();
                    }else{
                        Log.d("entity_sync_request.java", period_dto.getTaskId() + " is not present");
                        continue;
                    }
                    // TODO: task taskClass的双向同步
                    DbContext.Periods.UpdateOrAddPeriod(new Period(
                            period_dto.getId(),
                            0,
                            task,
                            period_dto.getBegin(),
                            period_dto.getEnd(),
                            period_dto.getBegin() / 3600 / 24,
                            period_dto.getEnd() / 3600 / 24,
                            period_dto.getLastModified()));
                    if(last_sync < period_dto.getLastModified()){
                        last_sync = period_dto.getLastModified();
                    }
                }

            if(result.isFinish){
                if(last_sync != pullModel.lastSync)
                    DbContext.UserInfos.AddOrUpdateValue(UserConfig.LAST_SYNC, last_sync+"");
                if(result.aTimePeriods.size() == 0){
                    return SyncStatus.NO_CHANGE;
                }else{
                    return SyncStatus.CHANGED;
                }

            }else{
                return SyncPullPeriods();
            }
        }else{
            Log.d("entity_sync_request.java, response not 200", response.message());
            return SyncStatus.FAIL;
        }

    }

    public static class SyncPeriodsPushModel{
        public List<PeriodDto> aTimePeriods;
    }
    private static boolean SyncPushPeriods() throws IOException{
        SyncPeriodsPushModel pushModel = new SyncPeriodsPushModel();
        pushModel.aTimePeriods = DbContext.Periods.GetNotSyncPeriodAll();
        if(pushModel.aTimePeriods == null){
            return true;
        }
        OkHttpClient client = new OkHttpClient();
        String cookie = DbContext.UserInfos.GetValue(UserConfig.COOKIE);
        if(cookie == null){
            Log.d("entity_sync_request.java", "empty cookie");
            return false;
        }
        Gson gson = new Gson();
        RequestBody body = RequestBody.Companion.create(gson.toJson(pushModel), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(HttpConfig.URL+"/api/user/Sync/periods")
                .post(body)
                .addHeader("cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200){
            long sync = Long.parseLong(DbContext.UserInfos.GetValue(UserConfig.LAST_SYNC));
            for (PeriodDto p: pushModel.aTimePeriods
            ) {
                DbContext.Periods.Sync(p._id);
                if(sync < p.getLastModified()){
                    sync = p.getLastModified();
                }
            }
            DbContext.UserInfos.AddOrUpdateValue(UserConfig.LAST_SYNC, sync+"");
            return true;
        }else{
            Log.d("entity_sync_request.java, response not 200", response.message());
            return false;
        }
    }
}
