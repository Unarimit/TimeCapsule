package com.unarimit.timecapsuleapp.ui.task;

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
import com.unarimit.timecapsuleapp.ui.task.manager.TaskManagerActivity;
import com.unarimit.timecapsuleapp.ui.taskclass.manager.TaskClassManagerActivity;

/**
 * A fragment representing a list of Items.
 */
public class TaskFragment extends Fragment {

    TaskViewModel viewModel;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Button createButton;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        // list config
        recyclerView = view.findViewById(R.id.task_fragment_list);
        updateList();


        // create new button config
        createButton = view.findViewById(R.id.task_fragment_new_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskManagerActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        updateList();
        super.onResume();
    }

    private void updateList(){
        viewModel = new TaskViewModel();
        if(viewModel.getTasks() == null || viewModel.getTasks().isEmpty()){
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new TaskRecyclerViewAdapter(viewModel.getTasks()));
        }
    }
}