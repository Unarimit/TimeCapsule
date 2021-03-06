package com.unarimit.timecapsuleapp.ui.task.manager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.TaskClass;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TaskClass}.
 * TODO: Replace the implementation with code for your data type.
 */
class TaskClassRecyclerViewAdapter extends RecyclerView.Adapter<TaskClassRecyclerViewAdapter.ViewHolder> {

    private final List<TaskClass> mValues;
    private final TaskManagerActivity mActivity;

    public TaskClassRecyclerViewAdapter(List<TaskClass> items, TaskManagerActivity activity) {
        mValues = items;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_taskclass_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.colorDisplay.setBackgroundColor(Color.parseColor(mValues.get(position).getColor()));
        holder.taskClassName.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setTaskClass(mValues.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView colorDisplay;
        public final TextView taskClassName;
        public TaskClass mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            colorDisplay = (TextView) view.findViewById(R.id.taskclass_item_colordiaplay);
            taskClassName = (TextView) view.findViewById(R.id.taskclass_item_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskClassName.getText() + "'";
        }
    }
}