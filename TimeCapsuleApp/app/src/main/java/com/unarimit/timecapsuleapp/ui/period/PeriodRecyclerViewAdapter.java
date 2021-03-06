package com.unarimit.timecapsuleapp.ui.period;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.ui.period.manager.PeriodManagerActivity;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Period}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PeriodRecyclerViewAdapter extends RecyclerView.Adapter<PeriodRecyclerViewAdapter.ViewHolder> {

    private final List<Period> mValues;

    public PeriodRecyclerViewAdapter(List<Period> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_period_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.icon.setText(mValues.get(position).getTask().getIcon());
        holder.icon.setTextColor(Color.parseColor(mValues.get(position).getTask().getTaskClass().getColor()));
        holder.nameTv.setText(mValues.get(position).getTask().getName());
        if(mValues.get(position).getEnd() == -1){
            String during_text = mValues.get(position).getBegin() % (24 * 3600) / 3600
                    + ":" + String.format(Locale.getDefault(), "%02d",mValues.get(position).getBegin() % 3600 / 60)
                    + " - ";
            holder.duringTv.setText(during_text);
            holder.totalTimeTv.setText("--");
            holder.achieveTv.setText("--");
        }else{
            long total = (mValues.get(position).getEnd() - mValues.get(position).getBegin()) / 60; // to min
            String total_text = total / 60 + ":" + String.format(Locale.getDefault(), "%02d",total % 60);
            String during_text = mValues.get(position).getBegin() % (24 * 3600) / 3600
                    + ":" + String.format(Locale.getDefault(), "%02d",mValues.get(position).getBegin() % 3600 / 60)
                    + " - " + mValues.get(position).getEnd() % (24 * 3600) / 3600
                    + ":" + String.format(Locale.getDefault(), "%02d",mValues.get(position).getEnd() % 3600 / 60);

            holder.duringTv.setText(during_text);
            holder.totalTimeTv.setText(total_text);
            // TODO: create achieve calculate function
            double achieve = (double)total / 60 * mValues.get(position).getTask().getAchievePerHour();
            String achieve_text = "";
            if(achieve == 0){
                holder.achieveTv.setTextColor(holder.mView.getResources().getColor(R.color.gray, holder.mView.getContext().getTheme()));
            }
            else if(achieve >= 0){
                achieve_text += "+";
                holder.achieveTv.setTextColor(holder.mView.getResources().getColor(R.color.bright_blue, holder.mView.getContext().getTheme()));
            }
            else{
                achieve_text += "-";
                holder.achieveTv.setTextColor(holder.mView.getResources().getColor(R.color.red, holder.mView.getContext().getTheme()));
            }
            achieve_text += String.format(Locale.getDefault(), "%.2f", achieve);
            holder.achieveTv.setText(achieve_text);
        }

        // set click direct to manager
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), PeriodManagerActivity.class);
                intent.putExtra("period", mValues.get(position));
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
        public final TextView icon;
        public final TextView duringTv;
        public final TextView nameTv;
        public final TextView totalTimeTv;
        public final TextView achieveTv;
        // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            icon = view.findViewById(R.id.period_item_icon);
            duringTv = view.findViewById(R.id.period_item_during_tv);
            nameTv = view.findViewById(R.id.period_item_name_tv);
            totalTimeTv = view.findViewById(R.id.period_item_totaltime_tv);
            achieveTv = view.findViewById(R.id.period_item_achieve_tv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + duringTv.getText() + "'";
        }
    }
}