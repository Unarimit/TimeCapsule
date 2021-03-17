package com.unarimit.timecapsuleapp.ui.curvejob.check;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJob;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class CurveJobCheckActivity extends AppCompatActivity {

    TextView taskName; // change by private function for easy copy
    IconTextView taskIcon;
    TextView dependTaskClass;

    RecyclerView recyclerView;

    CurveJobBase curveJobBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curvejob_check);

        taskIcon = findViewById(R.id.curvejobcheck_task_icon);
        taskName = findViewById(R.id.curvejobcheck_task_text);

        dependTaskClass = findViewById(R.id.curvejobcheck_dependtaskclass_tv);
        recyclerView = findViewById(R.id.curvejob_item_list);

        curveJobBase = (CurveJobBase)getIntent().getSerializableExtra("base");
        List<CurveJob> list = DbContext.CurveJobs.GetByBaseId(curveJobBase.getId(),true);
        for (CurveJob job:
             list) {
            job.setCurveJobBase(curveJobBase);
        }
        if(curveJobBase == null){
            finish();
            return;
        }
        Task task = curveJobBase.getTask();

        taskIcon.setText(task.getIcon());
        taskIcon.setTextColor(Color.parseColor(task.getTaskClass().getColor()));
        taskName.setText(task.getName());
        dependTaskClass.setText(task.getTaskClass().getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CurveJobRecyclerViewAdapter(list, TimeHelper.GetCurrentSeconds() / 3600 / 24));

    }
}