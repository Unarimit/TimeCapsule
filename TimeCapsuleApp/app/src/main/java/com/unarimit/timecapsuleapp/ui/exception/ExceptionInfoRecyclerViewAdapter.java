package com.unarimit.timecapsuleapp.ui.exception;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.ExceptionInfo;
import com.unarimit.timecapsuleapp.utils.TimeHelper;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ExceptionInfo}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExceptionInfoRecyclerViewAdapter extends RecyclerView.Adapter<ExceptionInfoRecyclerViewAdapter.ViewHolder> {

    private final List<ExceptionInfo> mValues;

    public ExceptionInfoRecyclerViewAdapter(List<ExceptionInfo> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_exception_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(TimeHelper.DateLongToString(mValues.get(position).getDate()));
        holder.mContentView.setText(mValues.get(position).getInfo());
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