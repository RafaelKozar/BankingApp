package com.example.rako.bankingapp.activitys;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.fragments.FragmentRecipeStepDetail;
import com.example.rako.bankingapp.fragments.ListStepsFragment;
import com.example.rako.bankingapp.fragments.FragmentSelectRecipeStepDetail;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Step;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class RecipeDetail extends AppCompatActivity implements ListStepsFragment.interfaceClickStep,
        FragmentRecipeStepDetail.InterfacePhone {
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int position;

    private static final String TAGrecipeDetailFragment = "detailFragment";
    private static final String TAGDetailTabletFragment = "detailTabletFragment";


    public boolean isTablet() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return true;
        }else{
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTablet()) {
            setContentView(R.layout.activity_recipe_detail_tablet);
        } else {
            setContentView(R.layout.activity_recipe_detail);
        }

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(this.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        if (savedInstanceState != null) {
            ingredients = savedInstanceState.getParcelableArrayList("ingredientes");
            steps = savedInstanceState.getParcelableArrayList("steps");
            position = savedInstanceState.getInt("position");
        }else {
            ingredients = getIntent().getParcelableArrayListExtra("ingredients");
            steps = getIntent().getParcelableArrayListExtra("steps");
        }

        int size = steps.size() - 1;
        if (steps.get(size).getDescription() == null) {
            steps.remove(size);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentRecipeStepDetail fragmentDetailPhoneRetain = (FragmentRecipeStepDetail) fragmentManager.findFragmentByTag(
                TAGrecipeDetailFragment
        );

        FragmentSelectRecipeStepDetail fragmentSelectDetail = (FragmentSelectRecipeStepDetail) fragmentManager.findFragmentByTag(
                TAGDetailTabletFragment
        );

        if (fragmentDetailPhoneRetain == null) {
            ListStepsFragment listStepsFragment = new ListStepsFragment();
            listStepsFragment.setStepList(steps);

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_list_steps, listStepsFragment)
                    .commit();

            if (isTablet()  &&  fragmentSelectDetail != null) {
                FragmentSelectRecipeStepDetail fragmentDetailTablet = new FragmentSelectRecipeStepDetail();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_detail_step, fragmentDetailTablet, TAGDetailTabletFragment)
                        .commit();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe_favorito, menu);
        return true;
    }

    @Override
    public void onClickedStep(int position) {
        Toast.makeText(this, "Você clicou no passo " + String.valueOf(position), Toast.LENGTH_SHORT).show();
        this.position = position;
        if (isTablet()) {
            setFragmentTablet();
        } else {
            setFragmentPhone();
        }
    }

    public void setFragmentTablet() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentSelectRecipeStepDetail fragmentDetailTablet = null;
        if (position == 0) {
            fragmentDetailTablet = new FragmentSelectRecipeStepDetail(
                    steps.get(position).getVideoURL(), ingredients, steps.get(position).getThumbnailURL()
            );
        } else {
            fragmentDetailTablet = new FragmentSelectRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL()
            );
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_step, fragmentDetailTablet, TAGDetailTabletFragment)
                .commit();

        fragmentDetailTablet.setDescription(steps.get(position).getDescription());
        fragmentDetailTablet.setTitulo(steps.get(position).getShortDescription());
    }

    public void setFragmentPhone() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentRecipeStepDetail fragmentDetailPhone = null;
        if (position == 0) {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL(), ingredients);
        } else {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL());
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_list_steps, fragmentDetailPhone, TAGrecipeDetailFragment)
                .commit();

        fragmentDetailPhone.setDescription(steps.get(position).getDescription());
        fragmentDetailPhone.setTitulo(steps.get(position).getShortDescription());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ingredientes", ingredients);
        outState.putParcelableArrayList("steps", steps);
        outState.putInt("position", position);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void delegateSwipeRight() {
        if (position < (steps.size() - 1)) {
            this.position++;
            setFragmentPhone();
        } else {
            Toast.makeText(this, getString(R.string.text_ultimo_passo), Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void delegateSwipeLeft() {
        if (position > 0) {
            this.position--;
            setFragmentPhone();
        } else {
            Toast.makeText(this, getString(R.string.text_primeiro_passo), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
