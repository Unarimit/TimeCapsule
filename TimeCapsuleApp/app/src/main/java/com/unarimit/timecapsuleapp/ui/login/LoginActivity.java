package com.unarimit.timecapsuleapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.dto.UserDto;
import com.unarimit.timecapsuleapp.utils.http.requests.AccountRequest;
import com.unarimit.timecapsuleapp.utils.http.requests.EntitySyncRequest;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    TextView username;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        Button btn = findViewById(R.id.login_login);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String un = username.getText().toString();
                        String pd = password.getText().toString();
                        if(un.isEmpty()){
                            showInsideCheckError(R.string.invalid_username);
                        }else if(pd.isEmpty()){
                            showInsideCheckError(R.string.invalid_password);
                        }else{
                            try {
                                showResponse(AccountRequest.login(new UserDto(un, pd)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // HTTP REQUEST
                        try {
                            if(!DbContext.UserInfos.GetValue(UserConfig.SYNC).equals("false")){
                                EntitySyncRequest.SyncStuff();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void showResponse(boolean isSuccess){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isSuccess){
                    DbContext.UserInfos.AddOrUpdateValue(UserConfig.USER_NAME, username.getText().toString());
                    finish();
                }else{
                    Toast toast = Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void showInsideCheckError(int id){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    Toast toast = Toast.makeText(LoginActivity.this, id, Toast.LENGTH_SHORT);
                    toast.show();
            }
        });
    }


}