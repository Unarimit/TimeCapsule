package com.unarimit.timecapsuleapp.ui.task.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.common.IconStringList;

import java.util.Arrays;
import java.util.List;

public class IconRecyclerViewAdapter extends RecyclerView.Adapter<IconRecyclerViewAdapter.ViewHolder>{

    private final List<String> mValues;
    private final TaskManagerActivity mActivity;

    public IconRecyclerViewAdapter(TaskManagerActivity activity) {
        mValues = Arrays.asList(IconStringList.List);
        System.out.println(mValues.isEmpty());
        mActivity = activity;
    }

    @Override
    public IconRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_icon_item, parent, false);
        return new IconRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IconRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.iconText = mValues.get(position);
        holder.iconDisplay.setText(mValues.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setIcon(mValues.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView iconDisplay;
        public String iconText;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iconDisplay = view.findViewById(R.id.icon_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + iconText + "'";
        }
    }
}
