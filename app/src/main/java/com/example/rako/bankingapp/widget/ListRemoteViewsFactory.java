package com.example.rako.bankingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.connection.NetworkConnection;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.model.Step;
import com.example.rako.bankingapp.resources.FeedRecipes;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Kozar on 19/11/2017.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String recipesLink = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    Context context;
    List<Step> steps = new ArrayList<Step>();

    public ListRemoteViewsFactory(Context ctx)
    {
        //getSteps();
        teste();
    }

    @Override
    public void onCreate() {
        //getSteps();
        teste();
    }

    public void teste() {

        steps.add(new Step("Teste 1", "dfs ", "fdsfsd", "fdsf"));
        steps.add(new Step("Teste 2", "", "", ""));
        steps.add(new Step("Teste 3", "", "", ""));
        steps.add(new Step("Teste 4", "", "", ""));
        steps.add(new Step("Teste 5", "", "", ""));

    }

    private void getSteps() {
        URL url = null;
        try {
            url = new URL(recipesLink);
            JSONArray jsonArray = NetworkConnection.getResponseFromHttpUrl(url);
            List<Recipe> recipes = FeedRecipes.process(jsonArray);
            steps = recipes.get(0).getStepList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {
       // getSteps();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(steps == null) return 0;
        return steps.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_step);
        views.setTextViewText(R.id.text_item_step, steps.get(i).getShortDescription());

        Intent filIntent = new Intent();
        views.setOnClickFillInIntent(R.id.text_item_step, filIntent);
        return views;
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
