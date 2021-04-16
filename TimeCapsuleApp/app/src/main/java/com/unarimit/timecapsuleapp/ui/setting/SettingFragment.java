package com.unarimit.timecapsuleapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.ui.login.LoginActivity;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class SettingFragment extends Fragment {

    private String[] data = {"Apple","Banana"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView achieveTv;
    TextView usernameTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_setting, container, false);
        ListView list =  root.findViewById(R.id.listview);
        achieveTv = root.findViewById(R.id.setting_achieve);
        usernameTv = root.findViewById(R.id.setting_username);
        View head = root.findViewById(R.id.setting_head);


        list.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, data));

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