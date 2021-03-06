package com.unarimit.timecapsuleapp.ui.period.barchart;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.ui.period.PeriodRecyclerViewAdapter;
import com.unarimit.timecapsuleapp.ui.period.manager.PeriodManagerActivity;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;
import java.util.Locale;

public class BarChartRecyclerViewAdapter extends RecyclerView.Adapter<BarChartRecyclerViewAdapter.ViewHolder> {
    private final List<TaskElement> mValues;
    private final long maxTime;
    public BarChartRecyclerViewAdapter(List<TaskElement> items, long maxTime) {
        mValues = items;
        this.maxTime = maxTime;
    }

    @Override
    public BarChartRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_period_statistic_view_bar_item, parent, false);
        return new BarChartRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BarChartRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.icon.setText(mValues.get(position).icon);
        holder.icon.setTextColor(Color.parseColor(mValues.get(position).taskClassElement.color));
        holder.taskName.setText(mValues.get(position).name);
        holder.totalTime.setText(TimeHelper.DateLongToTimeString(mValues.get(position).time));
        holder.bar.setBackgroundColor(Color.parseColor(mValues.get(position).taskClassElement.color));
        holder.bar.getLayoutParams().width = (int)( (double)DbContext.WindowsWidth * (double)mValues.get(position).time / (double)maxTime / 1.2);
        Log.d("debug", "" + holder.bar.getLayoutParams().width);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        IconTextView icon;
        TextView taskName;
        TextView totalTime;
        View bar;
        // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            icon = view.findViewById(R.id.statistic_bar_item_icon);
            taskName = view.findViewById(R.id.statistic_bar_item_name);
            totalTime = view.findViewById(R.id.statistic_bar_item_time);
            bar = view.findViewById(R.id.statistic_bar_item_bar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskName.getText() + "'";
        }
    }
}

