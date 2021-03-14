package com.unarimit.timecapsuleapp.ui.period.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.ui.task.manager.IconRecyclerViewAdapter;
import com.unarimit.timecapsuleapp.ui.task.manager.TaskManagerActivity;
import com.unarimit.timecapsuleapp.utils.CustomDate;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;
import java.util.Locale;

public class PeriodManagerActivity extends AppCompatActivity {

    TextView taskName; // change by private function for easy copy
    IconTextView taskIcon;
    View taskLayout;

    TextView dependTaskClass;
    TextView beginDate;
    TextView beginTime;
    TextView endDate;
    TextView endTime;
    ImageButton returnBtn;
    ImageButton confirmBtn;
    ImageButton deleteBtn;

    CustomDate date_begin;
    CustomDate date_end;
    String[] begin_time_array;
    String[] end_time_array;

    PeriodManagerViewModel viewModel;
    Period period;
    Task task;

    Dialog taskSelectorDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_manager);
        returnBtn = findViewById(R.id.periodmanager_return_button);
        confirmBtn = findViewById(R.id.periodmanager_confirm_button);
        deleteBtn = findViewById(R.id.periodmanager_delete_button);
        taskLayout = findViewById(R.id.periodmanager_task_layout);
        taskIcon = findViewById(R.id.periodmanager_task_icon);
        taskName = findViewById(R.id.periodmanager_task_text);
        dependTaskClass = findViewById(R.id.periodmanager_dependtaskclass_tv);
        beginDate = findViewById(R.id.periodmanager_begin_date_tv);
        beginTime = findViewById(R.id.periodmanager_begin_time_tv);
        endDate = findViewById(R.id.periodmanager_end_date_tv);
        endTime = findViewById(R.id.periodmanager_end_time_tv);
        period = (Period)getIntent().getSerializableExtra("period");
        viewModel = new PeriodManagerViewModel();
        List<Task> taskList = viewModel.getTasks();

        if(period != null){
            ChangeTaskDisplay(period.getTask());
            date_begin = new CustomDate(period.getBeginCalendar());
            date_end = new CustomDate(period.getEndCalendar());
            beginDate.setText(date_begin.GetDateStringWithYear());
            endDate.setText(date_end.GetDateStringWithYear());
            beginTime.setText(TimeHelper.DateLongToTimeString(period.getBegin()));
            endTime.setText(TimeHelper.DateLongToTimeString(period.getEnd()));
            // time_array =
        }else{
            // set date
            CustomDate date = new CustomDate(viewModel.getTodayCalendar());
            beginDate.setText(date.GetDateStringWithYear());
            endDate.setText(date.GetDateStringWithYear());
            date_begin = new CustomDate(viewModel.getTodayCalendar());
            date_end = new CustomDate(viewModel.getTodayCalendar());
            // set time
            String lastPeriodTime = TimeHelper.DateLongToTimeString(viewModel.GetLastPeriodTime());
            beginTime.setText(lastPeriodTime);
            endTime.setText(TimeHelper.DateLongToTimeString(TimeHelper.GetCurrentSeconds()));
            // if have no task
            if(taskList == null){
                taskName.setText("请先创建任务");
                return;
            }else{
                Task first = taskList.get(0);
                ChangeTaskDisplay(first);
            }
        }
        begin_time_array = beginTime.getText().toString().split(":");
        end_time_array = endTime.getText().toString().split(":");

        beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(PeriodManagerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginTime.setText(String.format(Locale.getDefault(), "%02d",hourOfDay)
                                + ":" + String.format(Locale.getDefault(), "%02d", minute));
                        begin_time_array[0] = hourOfDay + "";
                        begin_time_array[1] = minute + "";
                    }
                }, Integer.parseInt(begin_time_array[0]), Integer.parseInt(begin_time_array[1]), true);
                dialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(PeriodManagerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(String.format(Locale.getDefault(), "%02d",hourOfDay)
                                + ":" + String.format(Locale.getDefault(), "%02d", minute));
                        end_time_array[0] = hourOfDay + "";
                        end_time_array[1] = minute + "";
                    }
                }, Integer.parseInt(end_time_array[0]), Integer.parseInt(end_time_array[1]), true);
                dialog.show();
            }
        });

        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(PeriodManagerActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_begin.OnPicker(year, month, dayOfMonth);
                        beginDate.setText(date_begin.GetDateStringWithYear());
                    }
                },date_begin.getYear(), date_begin.getMonth()-1, date_begin.getDay());
                dialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(PeriodManagerActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_end.OnPicker(year, month, dayOfMonth);
                        endDate.setText(date_end.GetDateStringWithYear());
                    }
                }, date_end.getYear(), date_end.getMonth()-1, date_end.getDay());
                dialog.show();
            }
        });

        InitTaskSelector();
        // return button
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // confirm button period setting aggregate here
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long bc = date_begin.GetCalendar();
                long ec = date_end.GetCalendar();
                long bt = bc * 3600 * 24 + Integer.parseInt(begin_time_array[0]) * 3600 + Integer.parseInt(begin_time_array[1]) * 60;
                long et = ec * 3600 * 24 + Integer.parseInt(end_time_array[0]) * 3600 + Integer.parseInt(end_time_array[1]) * 60;
                if(bt > et){
                    Snackbar.make(v, R.string.period_create_begin_larger_than_end_hint, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
                if(period != null){
                    period.setTask(task);
                    period.setBeginCalendar(bc);
                    period.setEndCalendar(ec);
                    period.setBegin(bt);
                    period.setEnd(et);
                    DbContext.Periods.UpdatePeriod(period);
                }else{
                    period = new Period(task);
                    period.setBeginCalendar(bc);
                    period.setEndCalendar(ec);
                    period.setBegin(bt);
                    period.setEnd(et);
                    DbContext.Periods.Add(period);
                }
                finish();
            }
        });
        // delete button
        if(period != null){
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbContext.Periods.DeletePeriod(period);
                }
            });
        }else{
            deleteBtn.setVisibility(View.GONE);
        }

    }

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
        dependTaskClass.setText(task.getTaskClass().getName());
    }
}