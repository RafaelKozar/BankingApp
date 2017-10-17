package com.example.rako.bankingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.AdapterIngredientViewHolder> {
    private static final String TAG = "AdapterIngredient";
    private List<Ingredient> ingredientList = new ArrayList<>();


    @Override
    public AdapterIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new AdapterIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterIngredientViewHolder holder, int position) {
        holder.ingrediente.setText(ingredientList.get(position).getIngredient());
        holder.medida.setText(ingredientList.get(position).getMeasure());
        holder.quantidade.setText(String.valueOf(ingredientList.get(position).getQuantity()));
      }

    public void setIngredientList(List<Ingredient> ingredients){
        this.ingredientList = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (ingredientList != null) {
            return ingredientList.size();
        } else {
            return 0;
        }
    }

    public class AdapterIngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingrediente;
        TextView medida;
        TextView quantidade;

        public AdapterIngredientViewHolder(View itemView){
            super(itemView);
            ingrediente = itemView.findViewById(R.id.item_name_igredient);
            medida = itemView.findViewById(R.id.item_measure);
            quantidade = itemView.findViewById(R.id.item_quantity);
        }
    }

}
