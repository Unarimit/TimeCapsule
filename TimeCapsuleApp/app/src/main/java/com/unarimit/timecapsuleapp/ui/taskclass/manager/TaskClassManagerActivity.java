package com.unarimit.timecapsuleapp.ui.taskclass.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

public class TaskClassManagerActivity extends AppCompatActivity {
    ImageButton returnButton;
    ImageButton confirmButton;
    ImageButton deleteButton;
    TextView colorButton;
    EditText name;
    private String color;
    TaskClass taskClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_class_manager);
        colorButton = findViewById(R.id.taskclassmanager_color_button);
        returnButton = findViewById(R.id.taskclassmanager_return_button);
        confirmButton = findViewById(R.id.taskclassmanager_confirm_button);
        deleteButton = findViewById(R.id.taskclassmanager_delete_button);
        name = findViewById(R.id.taskclassmanager_name);

        taskClass = (TaskClass) getIntent().getSerializableExtra("taskclass");
        if(taskClass == null){
            deleteButton.setVisibility(View.GONE);
        }else{
            name.setText(taskClass.getName());
            colorButton.setBackgroundColor(Color.parseColor(taskClass.getColor()));
            color = taskClass.getColor();
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskClass == null) {
                    DbContext.TaskClasses.Add(new TaskClass(name.getText().toString(), color));
                }else{
                    taskClass.setColor(color);
                    taskClass.setName(name.getText().toString());
                    DbContext.TaskClasses.Update(taskClass);
                }

                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.TaskClasses.Remove(taskClass);
                finish();
            }
        });
        initColorBoard();
    }

    //region color board config
    private SeekBar seekbar1,seekbar2,seekbar3;
    private EditText edtxt;
    private TextView colorDisplayText;
    private Button colorConfirmButton;
    private void initColorBoard(){
        View view= LayoutInflater.from(TaskClassManagerActivity.this).inflate(R.layout.view_color_board,null);
        seekbar1 = view.findViewById(R.id.colorboard_seekbar1);
        seekbar2 = view.findViewById(R.id.colorboard_seekbar2);
        seekbar3 = view.findViewById(R.id.colorboard_seekbar3);
        edtxt = view.findViewById(R.id.colorboard_edtxt);
        colorDisplayText = view.findViewById(R.id.colorboard_display);
        colorConfirmButton = view.findViewById(R.id.colorboard_confirm_button);

        seekbar1.setOnSeekBarChangeListener(new colorBarListener());
        seekbar2.setOnSeekBarChangeListener(new colorBarListener());
        seekbar3.setOnSeekBarChangeListener(new colorBarListener());
        AlertDialog.Builder builder=new AlertDialog.Builder(TaskClassManagerActivity.this);
        AlertDialog dialog =  builder.setTitle("color board").setView(view).create();
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        if(color != null && color.length() == 7)
            ChangeBarFromColorSyntax(color.substring(1));

        edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ChangeBarFromColorSyntax(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // confirm button, return color to field, display to user
        colorConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "#"+edtxt.getText().toString();
                colorButton.setBackgroundColor(Color.parseColor(color));
                dialog.cancel();
            }
        });
    }
    private void ChangeBarFromColorSyntax(CharSequence s){
        if(s.length() != 6){
            return;
        }
        seekbar1.setProgress(Integer.parseInt(""+s.charAt(0)+s.charAt(1), 16));
        seekbar2.setProgress(Integer.parseInt(""+s.charAt(2)+s.charAt(3), 16));
        seekbar3.setProgress(Integer.parseInt(""+s.charAt(4)+s.charAt(5), 16));
    }
    class colorBarListener implements SeekBar.OnSeekBarChangeListener {
        //进度条改变
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            // TODO Auto-generated method stub
            int progress1=seekbar1.getProgress();
            int progress2=seekbar2.getProgress();
            int progress3=seekbar3.getProgress();
            colorDisplayText.setBackgroundColor(Color.rgb(progress1, progress2, progress3));

            edtxt.setText(myToHexString(progress1)+myToHexString(progress2)+myToHexString(progress3));

        }
        @Override
        //开始拖动
        public void onStartTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub
            int progress1=seekbar1.getProgress();
            int progress2=seekbar2.getProgress();
            int progress3=seekbar3.getProgress();

            colorDisplayText.setBackgroundColor(Color.rgb(progress1, progress2, progress3));

            edtxt.setText(myToHexString(progress1)+myToHexString(progress2)+myToHexString(progress3));
        }

        @Override
        //停止拖动
        public void onStopTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub
            int progress1=seekbar1.getProgress();
            int progress2=seekbar2.getProgress();
            int progress3=seekbar3.getProgress();
            colorDisplayText.setBackgroundColor(Color.rgb(progress1, progress2, progress3));
            edtxt.setText(myToHexString(progress1)+myToHexString(progress2)+myToHexString(progress3));

        }

        private String myToHexString(int i){
            String result = Integer.toHexString(i);
            if(result.length() == 2)
                return result;
            else if(result.length() == 1)
                return "0" + result;
            else
                return "00";
        }
    }
    //endregion


}