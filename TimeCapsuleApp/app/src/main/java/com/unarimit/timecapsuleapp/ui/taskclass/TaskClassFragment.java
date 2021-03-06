package com.unarimit.timecapsuleapp.ui.taskclass;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.ui.taskclass.manager.TaskClassManagerActivity;

/**
 * A fragment representing a list of Items.
 */
public class TaskClassFragment extends Fragment {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskClassFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TaskClassViewModel viewModel;
    Button createTaskClassButton;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taskclass, container, false);

        // set the button
        createTaskClassButton = view.findViewById(R.id.taskclass_fragment_new_button);
        createTaskClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskClassManagerActivity.class);
                startActivity(intent);
            }
        });
        // list config
        recyclerView = view.findViewById(R.id.taskclass_fragment_list);
        updateList();
        return view;
    }

    @Override
    public void onResume() {
        updateList();
        super.onResume();
    }

    private void updateList(){
        viewModel = new TaskClassViewModel();
        if(viewModel.getTaskClasses() == null || viewModel.getTaskClasses().isEmpty()){
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new TaskClassRecyclerViewAdapter(viewModel.getTaskClasses()));
        }
    }
}