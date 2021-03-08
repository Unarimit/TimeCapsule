package com.unarimit.timecapsuleapp.ui.curvejob.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class CurveJobCreateActivity extends AppCompatActivity {

    Button returnBtn;
    Button confirmBtn;

    TextView taskName;
    IconTextView taskIcon;
    View taskLayout;

    TextView baseTimeTv;

    CurveJobCreateViewModel viewModel;
    Task task;
    int baseCost = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curvejob_create);
        returnBtn = findViewById(R.id.curvejobcreator_return_button);
        confirmBtn = findViewById(R.id.curvejobcreator_confirm_button);

        taskLayout = findViewById(R.id.curvejobcreator_task_layout);
        taskIcon = findViewById(R.id.curvejobcreator_task_icon);
        taskName = findViewById(R.id.curvejobcreator_task_text);
        baseTimeTv = findViewById(R.id.curvejobcreator_begin_time);

        viewModel = new CurveJobCreateViewModel();

        baseTimeTv.setText(baseCost / 60 + ":" + baseCost % 60);
        task = viewModel.getTasks().get(0);
        InitTaskSelector();
        ChangeTaskDisplay(task);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.CurveJobBases.Add(new CurveJobBase(task, TimeHelper.GetCurrentSeconds()/3600/24, baseCost));
            }
        });

        baseTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(CurveJobCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        baseTimeTv.setText(hourOfDay + ":" + minute);
                        baseCost = hourOfDay * 60 + minute;
                    }
                }, baseCost / 60, baseCost % 60, true);
                dialog.show();
            }
        });
    }

    Dialog taskSelectorDialog;
    private void InitTaskSelector(){
        View view= LayoutInflater.from(this).inflate(R.layout.view_task_list,null);
        RecyclerView recyclerView = view.findViewById(R.id.periodmanager_task_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,6));
        recyclerView.setAdapter(new TaskRecyclerViewAdapter(this, viewModel.getTasks()));

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        taskSelectorDialog =  builder.setTitle("choose task").setView(view).create();
        taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskSelectorDialog.show();
            }
        });
    }
    public void ChangeTaskForDialog(Task task){
        ChangeTaskDisplay(task);
        taskSelectorDialog.cancel();
    }
    private void ChangeTaskDisplay(Task task){
        this.task = task;
        taskIcon.setText(task.getIcon());
        taskIcon.setTextColor(Color.parseColor(task.getTaskClass().getColor()));
        taskName.setText(task.getName());
    }
}