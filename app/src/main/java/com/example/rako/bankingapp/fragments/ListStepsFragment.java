package com.example.rako.bankingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rako.bankingapp.R;

import static com.example.rako.bankingapp.R.layout.fragment_list_steps;

/**
 * Created by rako on 28/09/2017.
 */

public class ListStepsFragment extends Fragment{

    private RecyclerView recyclerViewSteps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(fragment_list_steps, container, false);
        recyclerViewSteps = rootView.findViewById(R.id.list_steps);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewSteps.setLayoutManager(layoutManager);

        return rootView;
    }
}
