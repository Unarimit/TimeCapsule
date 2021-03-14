package com.unarimit.timecapsuleapp.ui.curvejob.check;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJob;
import com.unarimit.timecapsuleapp.utils.BitmapHelper;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

class CurveJobRecyclerViewAdapter extends RecyclerView.Adapter<CurveJobRecyclerViewAdapter.ViewHolder>{
    private final List<CurveJob> mValues;
    private final long mCalendar;
    public CurveJobRecyclerViewAdapter(List<CurveJob> items, long calendar) {
        mValues = items;
        mCalendar  = calendar;
    }
    @NonNull
    @Override
    public CurveJobRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_curvejob_check_item, parent, false);
        View dialogView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_curvejob_dowhat, parent, false);
        return new CurveJobRecyclerViewAdapter.ViewHolder(view, dialogView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurveJobRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.doWhat.setText(mValues.get(position).getDoWhat());
        holder.epoch.setText("#" + mValues.get(position).getId());
        holder.epochBar.setImageBitmap(BitmapHelper.DrawEpochLogBar(DbContext.WindowsWidth-100, mValues.get(position).getEpochLog()));

        if(mValues.get(position).getDoWhat().isEmpty()){
            holder.doWhat.setText(R.string.curvejob_empty_dowhat);

        }else{
            holder.doWhat.setText(mValues.get(position).getDoWhat());
        }

        // config change dowhat view
        AlertDialog.Builder builder=new AlertDialog.Builder(holder.mView.getContext());
        AlertDialog dialog = builder.setTitle(mValues.get(position).GetEpochInfo()).setView(holder.doWhatView).create();
        holder.doWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mValues.get(position).getDoWhat().isEmpty()){
                    holder.doWhatEt.setText(mValues.get(position).getDoWhat());
                }
                holder.doWhatConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CurveJob job =  mValues.get(position);
                        job.setDoWhat(holder.doWhatEt.getText().toString());
                        holder.doWhat.setText(holder.doWhatEt.getText().toString());
                        DbContext.CurveJobs.Update(job);
                        dialog.dismiss();
                    }
                });
                holder.doWhatReturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final TextView epoch;
        public final TextView doWhat;
        public final ImageView epochBar;

        public final View doWhatView;
        public final EditText doWhatEt;
        public final Button doWhatConfirm;
        public final Button doWhatReturn;
        public ViewHolder(@NonNull View view, @NonNull View dialogView) {
            super(view);
            mView = view;
            epoch = view.findViewById(R.id.curvejob_epochlog);
            doWhat = view.findViewById(R.id.curvejob_dowhat);
            epochBar = view.findViewById(R.id.curvejob_epochlog_bar);

            doWhatView = dialogView;
            doWhatEt = dialogView.findViewById(R.id.view_curvejob_et);
            doWhatConfirm = dialogView.findViewById(R.id.view_curvejob_confrim);
            doWhatReturn = dialogView.findViewById(R.id.view_curvejob_cancel);
        }
    }
}
