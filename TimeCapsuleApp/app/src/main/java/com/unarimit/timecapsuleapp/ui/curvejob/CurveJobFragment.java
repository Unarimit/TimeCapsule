package com.unarimit.timecapsuleapp.ui.curvejob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.ui.curvejob.manager.CurveJobCreateActivity;
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
    Button createBtn;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curvejob, container, false);
        createBtn = view.findViewById(R.id.curvejob_fragment_create_btn);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurveJobCreateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        UpdateList();
        super.onResume();
    }

    private void UpdateList(){
        CurveJobViewModel viewModel = new CurveJobViewModel();
        List<CurveJobBase> list = viewModel.getJobBases();
        if(list == null){
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setAdapter(new CurveJobRecyclerViewAdapter(list));
        }
    }
}