package com.example.rako.bankingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterRecipes extends RecyclerView.Adapter<AdapterRecipes.AdapterRecipeViewHolder> {
    private static final String TAG = "AdapterRecipes";
    private List<Recipe> recipes = new ArrayList<Recipe>();
    private boolean isTablet;

    private ClickRecipe listennerClick;

    public interface ClickRecipe {
        void onClickRecipe(int position);
    }

    public AdapterRecipes(ClickRecipe listennerClick) {
        this.listennerClick = listennerClick;
    }

    @Override
    public AdapterRecipes.AdapterRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (isTablet) {
            view = inflater.inflate(R.layout.item_view_tablet, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_view, parent, false);
        }

        return new AdapterRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecipes.AdapterRecipeViewHolder holder, int position) {
        Log.i(TAG, String.valueOf(position));
        holder.title.setText(recipes.get(position).getName());
        holder.numberIngredients.setText(String.valueOf(recipes.get(position).getNumberIngredients()));
        holder.numberSteps.setText(String.valueOf(recipes.get(position).getNumberSteps()));

    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        } else {
            return 0;
        }
    }

    public class AdapterRecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView numberIngredients;
        TextView numberSteps;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listennerClick.onClickRecipe(getAdapterPosition());
            }
        };

        public AdapterRecipeViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title_name);
            numberIngredients = itemView.findViewById(R.id.subtitle_number_ingredients);
            numberSteps = itemView.findViewById(R.id.subtitle_number_stepes);
            itemView.setOnClickListener(listener );
        }
    }

    public void setTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

}
