package com.example.rako.bankingapp.adapters;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.widget.BankingWidget;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class AdapterRecipes extends RecyclerView.Adapter<AdapterRecipes.AdapterRecipeViewHolder> {
    private static final String TAG = "AdapterRecipes";
    private List<Recipe> recipes = new ArrayList<Recipe>();
    private boolean isTablet;
    private Context ctx;

    private ClickRecipe listennerClick;


    public interface ClickRecipe {
        void onClickRecipe(int position);
    }


    public AdapterRecipes(ClickRecipe listennerClick, Context context) {
        this.listennerClick = listennerClick;
        this.ctx = context;
        new Prefs.Builder()
                .setContext(context)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
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
        holder.title.setText(recipes.get(position).getName());
        holder.numberIngredients.setText(holder.context.getString(R.string.texto_quantidade_ingredientes, String.valueOf(recipes.get(position).getNumberIngredients())));
        holder.numberSteps.setText(holder.context.getString(R.string.texto_quantidade_pasos, String.valueOf(recipes.get(position).getNumberSteps())));

        if (Prefs.getLong(ctx.getString(R.string.key_preference_bank), 0) == position) {
            holder.btnFavoritar.setColorFilter(Color.parseColor(ctx.getString(R.color.colorPrimary)));
        }else{
            holder.btnFavoritar.setColorFilter(Color.parseColor(ctx.getString(R.color.colorAccent)));
        }


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
        Context context;
        ImageView btnFavoritar;
        LinearLayout btnFavoritarLayout;
        LinearLayout item;


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listennerClick.onClickRecipe(getAdapterPosition());

            }
        };

        View.OnClickListener listenerFavoritar = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if (Prefs.getLong(ctx.getString(R.string.key_preference_bank), 0)
                        != getAdapterPosition()){
                    int fvAntigo = (int) Prefs.getLong(ctx.getString(R.string.key_preference_bank), 0);
                    notifyItemChanged(fvAntigo);
                    Prefs.putLong(ctx.getString(R.string.key_preference_bank), getAdapterPosition());
                    btnFavoritar.setColorFilter(Color.parseColor(ctx.getString(R.color.colorPrimary)));


                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx.getApplicationContext());
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ctx.getApplicationContext(), BankingWidget.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_wiget_ingredients);
                }
            }
        };

        public AdapterRecipeViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title_name);

            numberIngredients = itemView.findViewById(R.id.subtitle_number_ingredients);
            numberSteps = itemView.findViewById(R.id.subtitle_number_stepes);
            btnFavoritarLayout = itemView.findViewById(R.id.btn_favoritar);
            btnFavoritar = itemView.findViewById(R.id.btn_favoritar_img);
            item = itemView.findViewById(R.id.item_card);

            item.setOnClickListener(listener);
            btnFavoritarLayout.setOnClickListener(listenerFavoritar);

            context = itemView.getContext();
        }
    }

    public void setTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

}
