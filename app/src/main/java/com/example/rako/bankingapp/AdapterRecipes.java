package com.example.rako.bankingapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterRecipes extends RecyclerView.Adapter<AdapterRecipes.AdapterRecipeViewHolder> {
    private static final String TAG = "AdapterRecipes";
    private List<Recipe> recipes = new ArrayList<Recipe>();
    private boolean isTablet;

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
        holder.title.setText(recipes.get(position).getTituloReceita());
        holder.ingredientes.setText("Teste");
        holder.passos.setText("Teste");
        holder.passoUm.setText("Teste Teste TesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTeste");
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
        TextView ingredientes;
        TextView passos;
        TextView passoUm;

        public AdapterRecipeViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title_name);
            ingredientes = itemView.findViewById(R.id.subtitle_number_ingredients);
            passos = itemView.findViewById(R.id.subtitle_number_stepes);
            passoUm = (TextView) itemView.findViewById(R.id.passo_um);
        }
    }

    public void setTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

}
