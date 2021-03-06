package com.unarimit.timecapsuleapp.ui.task.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class TaskManagerActivity extends AppCompatActivity {

    private View taskClassLayout;
    private TextView taskClassName;
    private TextView taskClassColorDisplay;
    private AlertDialog taskClassDialog;

    private EditText nameEt;
    private EditText descEt;
    private SeekBar achieveSb;
    private TaskClass taskClass;
    private TaskManagerViewModel viewModel;

    private IconTextView iconButton;
    private AlertDialog iconDialog;

    private Button returnButton;
    private Button confirmButton;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        taskClassLayout = findViewById(R.id.taskmanager_taskclass_layout);
        taskClassName = findViewById(R.id.taskmanager_taskclass_name);
        taskClassColorDisplay = findViewById(R.id.taskmanager_taskclass_colordisplay);

        nameEt = findViewById(R.id.taskmanager_name_et);
        descEt = findViewById(R.id.taskmanager_desc_et);
        achieveSb = findViewById(R.id.taskmanager_achieve_sb);
        iconButton = findViewById(R.id.taskmanager_icon_button);
        viewModel = new TaskManagerViewModel();

        returnButton = findViewById(R.id.taskmanager_return_button);
        confirmButton = findViewById(R.id.taskmanager_confirm_button);
        deleteButton = findViewById(R.id.taskmanager_delete_button);

        Task task = (Task)getIntent().getSerializableExtra("task");
        // if click list into this page
        if(task != null){
            nameEt.setText(task.getName());
            descEt.setText(task.getDesc());
            iconButton.setText(task.getIcon());
            iconButton.setTextColor(Color.parseColor(task.getTaskClass().getColor()));
            achieveSb.setProgress((int)((task.getAchievePerHour() + 1) * 10));
            taskClass = task.getTaskClass();
            taskClassName.setText(taskClass.getName());
            taskClassColorDisplay.setBackgroundColor(Color.parseColor(taskClass.getColor()));
        }else{
            deleteButton.setVisibility(View.GONE);
        }

        // return button for test
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // confirm button for test
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if click list into this page
                if(task != null){
                    DbContext.Tasks.Update(new Task(task.getId(),
                            task.getGuid(),
                            nameEt.getText().toString(),
                            descEt.getText().toString(),
                            task.getTaskClass(),
                            (double)achieveSb.getProgress() / 10 - 1,
                            task.isFinished(),
                            iconButton.getText().toString()));
                } else { // else click add new button into this page
                    DbContext.Tasks.Add(new Task(nameEt.getText().toString(),
                            descEt.getText().toString(),
                            taskClass,
                            (double)achieveSb.getProgress() / 10 - 1,
                            iconButton.getText().toString()));
                }
                finish();
            }
        });

        // delete button for test
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.Tasks.Remove(task); // if task is null, the button will GONE
                finish();
            }
        });

        initTaskClassList();
        initIconList();
    }
    /**
     * init taskclass choose list
     * */
    private void initTaskClassList(){
        // taskclass layout, click to choose taskclass
        View view= LayoutInflater.from(TaskManagerActivity.this).inflate(R.layout.view_taskclass_list,null);
        RecyclerView recyclerView = view.findViewById(R.id.taskmanager_classview_list);
        AlertDialog.Builder builder=new AlertDialog.Builder(TaskManagerActivity.this);
        taskClassDialog =  builder.setTitle("choose taskclass").setView(view).create();
        if(viewModel.getTaskClasses() == null || viewModel.getTaskClasses().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.taskmanager_classview_tip).setVisibility(View.VISIBLE);
        }else{
            recyclerView.setAdapter(new TaskClassRecyclerViewAdapter(viewModel.getTaskClasses(), this));
        }
        taskClassLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClassDialog.show();
            }
        });
    }

    /**
     * init icon choose list
     * */
    private void initIconList(){
        View view= LayoutInflater.from(TaskManagerActivity.this).inflate(R.layout.view_icon_list,null);
        RecyclerView recyclerView = view.findViewById(R.id.taskmanager_icon_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,6));
        recyclerView.setAdapter(new IconRecyclerViewAdapter(this));

        AlertDialog.Builder builder=new AlertDialog.Builder(TaskManagerActivity.this);
        iconDialog =  builder.setTitle("choose icon").setView(view).create();
        iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconDialog.show();
            }
        });
    }

    /**
     * set taskclass display color and name, icon color together, call by view adapter
     * */
    public void setTaskClass(TaskClass taskClass) {
        this.taskClass = taskClass;
        taskClassName.setText(taskClass.getName());
        taskClassColorDisplay.setBackgroundColor(Color.parseColor(taskClass.getColor()));
        iconButton.setTextColor(Color.parseColor(taskClass.getColor()));
        taskClassDialog.cancel();
    }

    /**
     * call by view adapter
     * */
    public void setIcon(String icon_text){
        iconButton.setText(icon_text);
        iconDialog.cancel();
    }
}