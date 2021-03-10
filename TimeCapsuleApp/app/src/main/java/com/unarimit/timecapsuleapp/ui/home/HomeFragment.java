package com.unarimit.timecapsuleapp.ui.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.MainActivity;
import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;

    LayoutInflater inflater;
    ViewGroup container;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        this.inflater = inflater;
        this.container = container;
        homeViewModel = new HomeViewModel();
        if(homeViewModel.getCurrentPeriod() == null){
            root = inflater.inflate(R.layout.fragment_home, container, false);
            NoCurrentPeriodCreate();
        }else{
            root = inflater.inflate(R.layout.fragment_home_ontask, container, false);
            InCurrentPeriodCreate();
        }
        return root;
    }

    public void BigUpdateUI(){
        MainActivity activity = (MainActivity)getActivity();
        activity.refreshFragment(this);
    }

    RecyclerView oftenTaskRecyclerView;
    RecyclerView unOftenTaskRecyclerView;
    RecyclerView curveTaskRecyclerView;
    TextView noTaskHint;
    private void NoCurrentPeriodCreate(){
        // init task grid
        // create period in recyclerAdapter
        oftenTaskRecyclerView = root.findViewById(R.id.home_task_often_grid);
        unOftenTaskRecyclerView = root.findViewById(R.id.home_task_unoften_grid);
        curveTaskRecyclerView = root.findViewById(R.id.home_task_curve_grid);
        noTaskHint = root.findViewById(R.id.home_task_notask);
        if(homeViewModel.getTasks() == null || homeViewModel.getTasks().isEmpty()){
            oftenTaskRecyclerView.setVisibility(View.GONE);
            unOftenTaskRecyclerView.setVisibility(View.GONE);
            noTaskHint.setVisibility(View.VISIBLE);
        }else{
            noTaskHint.setVisibility(View.GONE);
            List<Task> oftenTask = homeViewModel.getTasks().stream().filter(x -> x.isOften()).collect(Collectors.toList());
            List<Task> unOftenTask = homeViewModel.getTasks().stream().filter(x -> !x.isOften()).collect(Collectors.toList());
            if(oftenTask.isEmpty()){
                root.findViewById(R.id.home_task_often_layout).setVisibility(View.GONE);
            }else{
                root.findViewById(R.id.home_task_often_layout).setVisibility(View.VISIBLE);
                oftenTaskRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
                oftenTaskRecyclerView.setAdapter(new HomeTaskViewAdapter(oftenTask, this));
            }

            if(homeViewModel.getJobBases() == null){
                root.findViewById(R.id.home_task_curve_layout).setVisibility(View.GONE);
            }else{
                root.findViewById(R.id.home_task_curve_layout).setVisibility(View.VISIBLE);
                curveTaskRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
                curveTaskRecyclerView.setAdapter(new HomeCurveTaskViewAdapter(homeViewModel.getJobBases(), this));
            }

            if(unOftenTask.isEmpty()){
                root.findViewById(R.id.home_task_unoften_layout).setVisibility(View.GONE);
            }else{
                root.findViewById(R.id.home_task_unoften_layout).setVisibility(View.VISIBLE);
                unOftenTaskRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
                unOftenTaskRecyclerView.setAdapter(new HomeTaskViewAdapter(unOftenTask, this));
            }

        }
    }

    // following is on task config
    IconTextView icon;
    TextView timeText;
    TextView taskDescText;
    TextView nameText;
    Button taskStopButton;
    RecyclerView jobsRecyclerView;
    TimingServiceConnection connection;
    TimingHandler handler = new TimingHandler();
    Period period;
    public static final int UPDATE_TIMING = 123;
    private void InCurrentPeriodCreate(){
        icon = root.findViewById(R.id.home_ontask_icon);
        timeText = root.findViewById(R.id.home_ontask_time_text);
        taskDescText = root.findViewById(R.id.home_ontask_desc_text);
        taskStopButton = root.findViewById(R.id.home_ontask_stop_button);
        nameText = root.findViewById(R.id.home_ontask_name_text);
        jobsRecyclerView = root.findViewById(R.id.home_ontask_job_list);
        period = homeViewModel.getCurrentPeriod();
        icon.setText(period.getTask().getIcon());
        icon.setTextColor(Color.parseColor(period.getTask().getTaskClass().getColor()));
        timeText.setText("00:00:00");
        taskDescText.setText(period.getTask().getDesc());
        nameText.setText(period.getTask().getName());
        Intent intent = new Intent(getContext(), TimingService.class);
        connection = new TimingServiceConnection();
        getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);

        CurveJobBase thisTask = null;
        // curve task
        for (CurveJobBase base: homeViewModel.getJobBases()
             ) {
            if(base.getTask().getId() == period.getTask().getId()){
                thisTask = base;
                break;
            }
        }

        if(thisTask == null){
            jobsRecyclerView.setVisibility(View.GONE);
        }else{
            jobsRecyclerView.setVisibility(View.VISIBLE);
            jobsRecyclerView.setAdapter(new HomeCurveJobViewAdapter(thisTask.getJobs(), homeViewModel.getCalendar()));
        }


        taskStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                period.Finish();
                DbContext.Periods.UpdatePeriod(period);
                connection.StopTiming();
                getContext().unbindService(connection);
                BigUpdateUI();
            }
        });
    }
    class TimingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == UPDATE_TIMING){
                timeText.setText(msg.getData().getString("show"));
            }
        }
    }


    class TimingServiceConnection implements ServiceConnection {

        TimingService.TimingBinder binder;
        boolean run;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (TimingService.TimingBinder) service;
            binder.StartTiming(period.getTask().getTaskClass().getColor(), period.getTask().getIcon(), period.getBegin(), handler);
            run = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        public void StopTiming(){
            run = false;
            binder.StopTiming();
        }
    }
}