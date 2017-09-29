package com.example.rako.bankingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterSteps extends RecyclerView.Adapter<AdapterSteps.AdapterStepViewHolder> {
    private static final String TAG = "AdapterRecipes";
    private List<Step> stepList = new ArrayList<>();


    private ClickStep listennerClickStep;

    public interface ClickStep {
        void onClickStep(int position);
    }

    public AdapterSteps(ClickStep listennerClickStep) {
        this.listennerClickStep = listennerClickStep;
    }

    @Override
    public AdapterStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_step, parent, false);
        return new AdapterStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterStepViewHolder holder, int position) {
        Log.i(TAG, String.valueOf(position));
        holder.title.setText(stepList.get(position).getShortDescription());
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
        TextView title;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listennerClickStep.onClickStep(getAdapterPosition());
            }
        };

        public AdapterStepViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_step);
            itemView.setOnClickListener(listener);
        }
    }

}
