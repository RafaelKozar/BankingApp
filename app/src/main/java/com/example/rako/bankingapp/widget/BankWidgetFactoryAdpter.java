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

class BankWidgetFactoryAdpter implements RemoteViewsService.RemoteViewsFactory{
    private Context context;
    private List<Ingredient> ingredientList;


    BankWidgetFactoryAdpter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        ingredientList = new ArrayList<Ingredient>();
        ingredientList.add(new Ingredient("Tesfdste", "teerste", (float) 0.9));
        ingredientList.add(new Ingredient("Tefdsste", "tfdseste", (float) 0.9));
        ingredientList.add(new Ingredient("Tesewte", "tesfdste", (float) 5.2));

        new Prefs.Builder()
                .setContext(context)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Log.e("tsteanaod", Prefs.getString("testeaqui", "iiiihh"));

        URL url = null;
        try {
            url = new URL("fd");
            JSONArray jsonArray = NetworkConnection.getResponseFromHttpUrl(url);
            List<Recipe> recipes = FeedRecipes.process(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item_ingredient_widget);
        //view.setTextViewText(R.id.item_name_igredient,  list.get(i));
        view.setTextViewText(R.id.item_name_igredient, ingredientList.get(i).getIngredient());
        Log.e("getViewAt - ", ingredientList.get(i).getIngredient());


       view.setTextViewText(R.id.item_measure, ingredientList.get(i).getMeasure());
        Log.e("getViewAt - ", ingredientList.get(i).getMeasure());

        view.setTextViewText(R.id.item_quantity, String.valueOf(ingredientList.get(i).getQuantity()));
        Log.e("getViewAt - ", String.valueOf(ingredientList.get(i).getQuantity()));

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
