package com.example.rako.bankingapp.services;


import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Context;
import android.os.Bundle;


import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.resources.SimpleIdlingResource;
import com.example.rako.bankingapp.connection.NetworkConnection;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.resources.FeedRecipes;
import com.example.rako.bankingapp.widget.BankWidgetFactoryAdpter;
import com.example.rako.bankingapp.widget.BankingWidget;

import org.json.JSONArray;

import java.net.URL;
import java.util.List;

/**
 * Created by rako on 28/09/2017.
 */

public class RecipesService implements LoaderManager.LoaderCallbacks<List<Recipe>> {
    private static final String recipesLink = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int LOADER_MOVIES_RECIPES = 50;
    private LoaderManager loaderManager;
    private AsyncTaskDelegateRecipes delegateRecipes;
    private Context context;

    public RecipesService(Context context, android.support.v4.app.LoaderManager supportLoaderManger,
                          @Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdlState(false);
        }
        this.context = context;
        this.loaderManager = supportLoaderManger;
        this.delegateRecipes = (AsyncTaskDelegateRecipes) context;
        Bundle bundle = new Bundle();
        bundle.putString("url", recipesLink);
        loaderManager.initLoader(LOADER_MOVIES_RECIPES, bundle, this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(context) {
            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Recipe> loadInBackground() {
                try {
                    URL url = new URL(bundle.getString("url"));
                    JSONArray jsonArray = NetworkConnection.getResponseFromHttpUrl(url);
                    List<Recipe> recipes = FeedRecipes.process(jsonArray);
                    BankWidgetFactoryAdpter.recipes = recipes;

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getApplicationContext(), BankingWidget.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_wiget_ingredients);
                    return  recipes;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        delegateRecipes.onProcessFinishRecipes(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    public interface AsyncTaskDelegateRecipes {
        void onProcessFinishRecipes(List<Recipe> recipes);
    }

}
