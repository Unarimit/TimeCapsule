package com.unarimit.timecapsuleapp.utils.http.requests;

import android.util.Log;

import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.HttpConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntitySyncRequest {

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
