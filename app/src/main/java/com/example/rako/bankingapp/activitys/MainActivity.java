package com.example.rako.bankingapp.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;


import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.connection.NetworkConnection;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.adapters.AdapterRecipes;
import com.example.rako.bankingapp.services.RecipesService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterRecipes.ClickRecipe,
    RecipesService.AsyncTaskDelegateRecipes{

    private static final String TAG = "MainActivity";
    private AdapterRecipes adapterRecipes;
    private ProgressBar bar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_titles);
        bar = (ProgressBar) findViewById(R.id.loading_receitas);
        recyclerView.setVisibility(View.INVISIBLE);
        bar.setVisibility(View.VISIBLE);
        createActivity();
    }

    public void createActivity(){
        if (NetworkConnection.isNetworkConnected(getApplicationContext())) {
            if (isTablet(this)) {
                GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
            } else {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
            }

            recyclerView.setHasFixedSize(true);
            adapterRecipes = new AdapterRecipes((AdapterRecipes.ClickRecipe) this);
            adapterRecipes.setTablet(isTablet(this));
            recyclerView.setAdapter(adapterRecipes);
            new RecipesService(this, getSupportLoaderManager());

        }else{
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
            Snackbar snackbar = Snackbar.make(coordinatorLayout,
                    getResources().getString(R.string.mensagem_erro_conexao),Snackbar.LENGTH_LONG).
                    setAction(getResources().getString(R.string.texto_repetir),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createActivity();
                        }
                    }
            );
            bar.setVisibility(View.INVISIBLE);
            snackbar.show();
        }
    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onClickRecipe(int position) {
        Intent it = new Intent(this, RecipeDetail.class);
        startActivity(it);
    }

    @Override
    public void onProcessFinishRecipes(List<Recipe> recipes) {
        adapterRecipes.setRecipes(recipes);
        bar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}