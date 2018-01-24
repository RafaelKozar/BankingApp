package com.example.rako.bankingapp.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.activitys.MainActivity;
import com.example.rako.bankingapp.connection.NetworkConnection;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.resources.FeedRecipes;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Kozar on 04/12/2017.
 */

public class BankWidgetFactoryAdpter implements RemoteViewsService.RemoteViewsFactory{
    private Context context;
    private List<Ingredient> ingredientList;// = new ArrayList<Ingredient>();;
    public static List<Recipe> recipes;
    private static final String recipesLink = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    BankWidgetFactoryAdpter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        new Prefs.Builder()
                .setContext(context)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        URL url = null;
        try {
            url = new URL(recipesLink);
            JSONArray jsonArray = NetworkConnection.getResponseFromHttpUrl(url);
            recipes = FeedRecipes.process(jsonArray);
            Log.e("Receita - Preferida", recipes.get((int) Prefs
                    .getLong(context.getString(R.string.key_preference_bank), 0)).getName());

            ingredientList = recipes.get((int) Prefs
                    .getLong(context.getString(R.string.key_preference_bank), 0)).
                    getIngredientList();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataSetChanged() {
        if (recipes != null) {
            ingredientList = recipes.get((int) Prefs
                    .getLong(context.getString(R.string.key_preference_bank), 0)).
                    getIngredientList();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList != null) {
            Log.e("teste", "hhe" + String.valueOf(ingredientList.size()));
            return ingredientList.size();
        } else {
            return 0;
        }

    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item_ingredient_widget);
        //view.setTextViewText(R.id.item_name_igredient,  list.get(i));
        view.setTextViewText(R.id.item_name_igredient, ingredientList.get(i).getIngredient());

        view.setTextViewText(R.id.item_measure, ingredientList.get(i).getMeasure());

        view.setTextViewText(R.id.item_quantity, String.valueOf(ingredientList.get(i).getQuantity()));

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
