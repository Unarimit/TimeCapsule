package com.unarimit.timecapsuleapp.ui.curvejob;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CurveJobBase}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CurveJobRecyclerViewAdapter extends RecyclerView.Adapter<CurveJobRecyclerViewAdapter.ViewHolder> {

    private final List<CurveJobBase> mValues;

    public CurveJobRecyclerViewAdapter(List<CurveJobBase> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_curvejob_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getTask().getName());
        holder.mContentView.setText(mValues.get(position).getBaseCost());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}