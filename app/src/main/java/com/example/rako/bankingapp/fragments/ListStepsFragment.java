package com.example.rako.bankingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.adapters.AdapterSteps;
import com.example.rako.bankingapp.model.Step;

import java.util.List;

import static com.example.rako.bankingapp.R.layout.fragment_list_steps;

/**
 * Created by rako on 28/09/2017.
 */

public class ListStepsFragment extends Fragment implements AdapterSteps.ClickStep {


    private RecyclerView recyclerViewSteps;
    private AdapterSteps adapterSteps;
    private List<Step> stepList;
    private interfaceClickStep listennerClickStep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(fragment_list_steps, container, false);

        recyclerViewSteps = rootView.findViewById(R.id.list_steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewSteps.setLayoutManager(layoutManager);

        adapterSteps = new AdapterSteps(this);
        adapterSteps.setStepList(stepList);
        recyclerViewSteps.setAdapter(adapterSteps);

        return rootView;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
        this.stepList.add(new Step());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listennerClickStep = (interfaceClickStep) context;
    }

    @Override
    public void onClickStep(int position) {
        listennerClickStep.onClickedStep(position);
    }

    public interface interfaceClickStep {
        void onClickedStep(int position);
    }
}
