package com.example.rako.bankingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rako.bankingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterSteps extends RecyclerView.Adapter<AdapterSteps.AdapterStepViewHolder> {
    private static final String TAG = "AdapterRecipes";
    private List<Step> stepList = new ArrayList<>();


    private ClickStep listennerClick;

    public interface ClickStep {
        void onClickRecipe(int position);
    }

    public AdapterSteps(ClickStep listennerClick) {
        this.listennerClick = listennerClick;
    }

    @Override
    public AdapterStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        return null;//new AdapterStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterStepViewHolder holder, int position) {
        Log.i(TAG, String.valueOf(position));
      }

    public void setStepList(List<Step> stpes){
        this.stepList = stpes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (stepList != null) {
            return stepList.size();
        } else {
            return 0;
        }
    }

    public class AdapterStepViewHolder extends RecyclerView.ViewHolder {

        public AdapterStepViewHolder(View itemView) {
            super(itemView);
        }
    }

}
