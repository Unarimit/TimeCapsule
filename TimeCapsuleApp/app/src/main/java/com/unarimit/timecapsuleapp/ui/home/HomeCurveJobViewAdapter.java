package com.unarimit.timecapsuleapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJob;
import com.unarimit.timecapsuleapp.ui.curvejob.CurveJobItemRecyclerViewAdapter;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

public class HomeCurveJobViewAdapter extends RecyclerView.Adapter<HomeCurveJobViewAdapter.ViewHolder>{
    private final List<CurveJob> mValues;
    private final long mCalendar;

    public HomeCurveJobViewAdapter(List<CurveJob> values, long calendar){
        mValues = values;
        mCalendar  = calendar;
    }

    @NonNull
    @Override
    public HomeCurveJobViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_ontask_curvejobitem, parent, false);
        return new HomeCurveJobViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCurveJobViewAdapter.ViewHolder holder, int position)
    {
        holder.doWhat.setText(mValues.get(position).getDoWhat());
        holder.epochLog.setText(mValues.get(position).GetEpochInfo());
        try {
            if(mValues.get(position).IsFinish(mCalendar)){
                holder.finish.setChecked(true);
            }else{
                holder.finish.setChecked(false);
            }
            holder.cost.setText(mValues.get(position).GetCostString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(mValues.get(position).getDoWhat().isEmpty()){
            holder.doWhat.setText(R.string.home_curvejob_empty_dowhat);

        }else{
            holder.doWhat.setText(mValues.get(position).getDoWhat());
        }

        holder.finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CurveJob job = mValues.get(position);
                if(isChecked){
                    job.Finish();
                }else{
                    job.ResetFinish();
                }
                DbContext.CurveJobs.Update(job);
            }
        });
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
        public final CheckBox finish;
        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            epochLog = view.findViewById(R.id.curvejob_epochlog);
            cost = view.findViewById(R.id.curvejob_cost);
            doWhat = view.findViewById(R.id.curvejob_dowhat);
            finish = view.findViewById(R.id.curvejob_finish_cb);
        }
    }
}
