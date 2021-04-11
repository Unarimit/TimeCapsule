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
import com.unarimit.timecapsuleapp.ui.login.LoginActivity;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class SettingFragment extends Fragment {

    private String[] data = {"Apple","Banana"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_setting, container, false);
        ListView list =  root.findViewById(R.id.listview);
        TextView achieve_tv = root.findViewById(R.id.setting_achieve);
        View head = root.findViewById(R.id.setting_head);

        achieve_tv.setText((int) DbContext.CurrentUser.getAchievePoint() + "");
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
}