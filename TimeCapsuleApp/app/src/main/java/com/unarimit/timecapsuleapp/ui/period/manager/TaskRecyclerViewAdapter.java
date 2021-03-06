package com.unarimit.timecapsuleapp.ui.period.manager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final PeriodManagerActivity mActivity;

    public TaskRecyclerViewAdapter(PeriodManagerActivity activity, List<Task> mValues) {
        this.mValues = mValues;
        mActivity = activity;
    }

    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_task_item, parent, false);
        return new TaskRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.iconDisplay.setText(mValues.get(position).getIcon());
        holder.iconDisplay.setTextColor(Color.parseColor(mValues.get(position).getTaskClass().getColor()));
        holder.taskName.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.ChangeTaskForDialog(mValues.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final IconTextView iconDisplay;
        public final TextView taskName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iconDisplay = view.findViewById(R.id.task_icon);
            taskName = view.findViewById(R.id.task_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskName.getText().toString() + "'";
        }
    }
}
