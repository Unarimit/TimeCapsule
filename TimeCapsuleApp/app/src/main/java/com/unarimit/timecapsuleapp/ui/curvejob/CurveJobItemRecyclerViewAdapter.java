package com.unarimit.timecapsuleapp.ui.curvejob;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJob;

import java.util.List;

public class CurveJobItemRecyclerViewAdapter extends RecyclerView.Adapter<CurveJobItemRecyclerViewAdapter.ViewHolder>{
    private final List<CurveJob> mValues;
    private final long mCalendar;
    public CurveJobItemRecyclerViewAdapter(List<CurveJob> items, long calendar) {
        mValues = items;
        mCalendar  = calendar;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_curvejob_item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.doWhat.setText(mValues.get(position).getDoWhat());
        holder.epochLog.setText(mValues.get(position).GetEpochInfo());
        try {
            if(mValues.get(position).IsFinish(mCalendar)){
                holder.cost.setText(R.string.curvejob_finish);
            }else{
                holder.cost.setText(mValues.get(position).GetCostString());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(mValues.get(position).getDoWhat().isEmpty()){
            holder.doWhat.setText(R.string.curvejob_empty_dowhat);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug", "i am clicked");
                }
            });
        }else{
            holder.doWhat.setText(mValues.get(position).getDoWhat());
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final TextView epochLog;
        public final TextView cost;
        public final TextView doWhat;
        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            epochLog = view.findViewById(R.id.curvejob_item_item_epochlog);
            cost = view.findViewById(R.id.curvejob_item_item_cost);
            doWhat = view.findViewById(R.id.curvejob_item_item_dowhat);
        }
    }
}
