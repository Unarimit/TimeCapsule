package com.unarimit.timecapsuleapp.ui.taskclass;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.ui.taskclass.manager.TaskClassManagerActivity;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TaskClass}.
 * TODO: Replace the implementation with code for your data type.
 */
class TaskClassRecyclerViewAdapter extends RecyclerView.Adapter<TaskClassRecyclerViewAdapter.ViewHolder> {

    private final List<TaskClass> mValues;

    public TaskClassRecyclerViewAdapter(List<TaskClass> items) {
        mValues = items;
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
                Intent intent = new Intent(holder.mView.getContext(), TaskClassManagerActivity.class);
                intent.putExtra("taskclass", mValues.get(position));
                holder.mView.getContext().startActivity(intent);
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