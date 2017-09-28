package com.example.rako.bankingapp.services;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.example.rako.bankingapp.model.Recipe;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by rako on 28/09/2017.
 */

public class RecipesService implements LoaderManager.LoaderCallbacks<List<Recipe>> {
    private static final String recipesLink = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int LOADER_MOVIES_RECIPES = 50;
    private Context context;


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
                    //SONArray jsonArray = NetworkCon

                    return  null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }
}
