package com.example.rako.bankingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AdapterRecipes adapterRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isTablet(this)) {
            createViewTablet();
        } else {
            createViewPhone();
        }

    }

    public void mock(){
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("amendoin"));
        recipes.add(new Recipe("morango"));
        recipes.add(new Recipe("cebola"));
        recipes.add(new Recipe("amendoin"));
        recipes.add(new Recipe("canela"));
        recipes.add(new Recipe("laranja"));
        adapterRecipes.setRecipes(recipes);
    }

    public void createViewTablet() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_titles);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        adapterRecipes = new AdapterRecipes();
        mock();
        adapterRecipes.setTablet(true);
        recyclerView.setAdapter(adapterRecipes);

    }

    public void createViewPhone(){

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
