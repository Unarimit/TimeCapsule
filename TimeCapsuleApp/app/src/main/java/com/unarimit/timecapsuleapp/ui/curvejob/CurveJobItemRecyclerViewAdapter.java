package com.unarimit.timecapsuleapp.ui.curvejob;

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

    public CurveJobItemRecyclerViewAdapter(List<CurveJob> items) {
        mValues = items;
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
        holder.cost.setText(mValues.get(position).GetCostString());
    }

    @Override
    public int getItemCount() {
        return 0;
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
