package com.unarimit.timecapsuleapp.ui.curvejob;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CurveJobFragment extends Fragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CurveJobFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curvejob, container, false);

        Context context = view.getContext();
        CurveJobViewModel viewModel = new CurveJobViewModel();
        List<CurveJobBase> list = viewModel.getJobBases();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        if(list == null){
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new CurveJobRecyclerViewAdapter(list));
        }

        return view;
    }
}