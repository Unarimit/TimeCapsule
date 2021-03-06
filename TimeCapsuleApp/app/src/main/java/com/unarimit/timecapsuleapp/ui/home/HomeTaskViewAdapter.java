package com.unarimit.timecapsuleapp.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;


public class HomeTaskViewAdapter extends RecyclerView.Adapter<HomeTaskViewAdapter.ViewHolder> {
    private final List<Task> mValues;
    private final HomeFragment fragment;
    public HomeTaskViewAdapter(List<Task> items, HomeFragment fragment) {
        mValues = items;
        this.fragment = fragment;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_taskitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.icon.setText(mValues.get(position).getIcon());
        holder.icon.setTextColor(Color.parseColor(mValues.get(position).getTaskClass().getColor()));
        holder.taskName.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.Periods.Add(new Period(mValues.get(position)));
                fragment.BigUpdateUI();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final IconTextView icon;
        public final TextView taskName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            icon = view.findViewById(R.id.home_taskitem_icon);
            taskName = view.findViewById(R.id.home_taskitem_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskName.getText() + "'";
        }
    }

}
