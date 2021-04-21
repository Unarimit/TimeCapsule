package com.unarimit.timecapsuleapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.ui.login.LoginActivity;
import com.unarimit.timecapsuleapp.utils.database.DbContext;
import com.unarimit.timecapsuleapp.utils.http.requests.EntitySyncRequest;

import java.io.IOException;

public class SettingFragment extends Fragment {

    private String[] data = {"sync", "Apple", "Banana"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView achieveTv;
    TextView usernameTv;
    boolean sync = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_setting, container, false);

        // init sync menu
        if(DbContext.UserInfos.GetValue(UserConfig.SYNC).equals("false")){
            data[0] = getContext().getString(R.string.setting_sync_off);
            sync = false;
        }else{
            data[0] = getContext().getString(R.string.setting_sync_on);
            sync = true;
        }
        // init login tip
        if(DbContext.UserInfos.GetValue(UserConfig.COOKIE) != null){
            root.findViewById(R.id.setting_unlogin_tip).setVisibility(View.GONE);
        }else{
            root.findViewById(R.id.setting_unlogin_tip).setVisibility(View.VISIBLE);
        }

        ListView list =  root.findViewById(R.id.listview);
        achieveTv = root.findViewById(R.id.setting_achieve);
        usernameTv = root.findViewById(R.id.setting_username);
        View head = root.findViewById(R.id.setting_head);


        list.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, data));

        // menu list on click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Log.d("settingFragment.java", position + "");
                if(position == 0){
                    if(sync){
                        sync = false;
                        data[0] = getContext().getString(R.string.setting_sync_off);
                        DbContext.UserInfos.AddOrUpdateValue(UserConfig.SYNC, "false");
                    }else{
                        sync = true;
                        data[0] = getContext().getString(R.string.setting_sync_on);
                        DbContext.UserInfos.AddOrUpdateValue(UserConfig.SYNC, "true");
                        // when sync switch to open, sync task and taskClass now
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EntitySyncRequest.SyncStuff();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    list.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, data));
                }
            }
        });

        // head layout on clock --> go to login menu
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        usernameTv.setText(DbContext.UserInfos.GetValue(UserConfig.USER_NAME));
        achieveTv.setText((int) DbContext.CurrentUser.getAchievePoint() + "");
    }
}