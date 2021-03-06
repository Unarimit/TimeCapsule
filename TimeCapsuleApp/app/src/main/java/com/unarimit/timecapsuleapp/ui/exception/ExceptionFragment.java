package com.unarimit.timecapsuleapp.ui.exception;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unarimit.timecapsuleapp.R;

/**
 * A fragment representing a list of Items.
 */
public class ExceptionFragment extends Fragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExceptionFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    ExceptionViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exception, container, false);
        viewModel = new ExceptionViewModel();
        if(viewModel.getExceptionInfos() == null){
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setVisibility(View.GONE);
            return view;
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ExceptionInfoRecyclerViewAdapter(viewModel.getExceptionInfos()));
        }
        return view;
    }
}