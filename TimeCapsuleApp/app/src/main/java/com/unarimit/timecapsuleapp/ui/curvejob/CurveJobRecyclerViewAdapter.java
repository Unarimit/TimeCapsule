package com.unarimit.timecapsuleapp.ui.curvejob;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.utils.TimeHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CurveJobBase}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CurveJobRecyclerViewAdapter extends RecyclerView.Adapter<CurveJobRecyclerViewAdapter.ViewHolder> {

    private final List<CurveJobBase> mValues;
    private final long mCalendar;
    private boolean mSwitch;
    public CurveJobRecyclerViewAdapter(List<CurveJobBase> items) {
        mValues = items;
        mCalendar = TimeHelper.GetCurrentSeconds()/3600/24;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_curvejob_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.icon.setText(mValues.get(position).getTask().getIcon());
        holder.icon.setTextColor(Color.parseColor(mValues.get(position).getTask().getTaskClass().getColor()));
        holder.task_name.setText(mValues.get(position).getTask().getName());
        float sync = 1f - (float)mValues.get(position).getFails() / (float)mValues.get(position).GetTotalJobs(mCalendar);
        holder.sync_value.setText(String.format(Locale.getDefault(), "%.1f",sync * 100));
        holder.sync_bar.getLayoutParams().width = (int)(DbContext.WindowsWidth *  sync);
        holder.sync_bar.setBackgroundColor(Color.parseColor(GetColorString(sync)));
        holder.cost.setText(mValues.get(position).GetListCostString());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.mView.getContext()));
        holder.recyclerView.setAdapter(new CurveJobItemRecyclerViewAdapter(mValues.get(position).getJobs(), mCalendar));
        mSwitch = true;
        holder.expandTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSwitch){
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.expandTv.setText(R.string.curvejob_close);
                    mSwitch = false;
                }else{
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.expandTv.setText(R.string.curvejob_open);
                    mSwitch = true;
                }
            }
        });
    }
    private String GetColorString(float sync){

        String r = "FF";
        String g = "FF";
        if(sync > 0.5){
            r = Integer.toHexString((int)(255 * (1 - sync) * 2));
        }
        if(sync < 0.5){
            g = Integer.toHexString((int)(255 *  sync * 2));
        }
        String b = "00";
        if(r.length() == 1)
            r = '0' + r;
        if(g.length() == 1)
            g = '0' + r;

        return "#" + r + g + b;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final IconTextView icon;
        public final TextView task_name;
        public final TextView sync_value;
        public final View sync_bar;
        public final TextView cost;
        public final RecyclerView recyclerView;
        public final TextView expandTv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            recyclerView = view.findViewById(R.id.curvejob_item_list);
            icon = view.findViewById(R.id.curvejob_item_icon);
            task_name = view.findViewById(R.id.curvejob_item_name);
            sync_value = view.findViewById(R.id.curvejob_item_sync_value);
            sync_bar = view.findViewById(R.id.curvejob_item_sync_bar);
            cost = view.findViewById(R.id.curvejob_item_cost);
            expandTv = view.findViewById(R.id.curvejob_item_expand);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + cost.getText() + "'";
        }
    }
}