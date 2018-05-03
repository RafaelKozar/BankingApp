package com.example.rako.bankingapp.activitys;

import android.content.ContextWrapper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;


import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.fragments.FragmentRecipeStepDetail;
import com.example.rako.bankingapp.fragments.ListStepsFragment;
import com.example.rako.bankingapp.fragments.FragmentSelectRecipeStepDetail;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Step;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import static com.example.rako.bankingapp.fragments.FragmentRecipeStepDetail.PLAY_PAUSE;
import static com.example.rako.bankingapp.fragments.FragmentRecipeStepDetail.POSITION;

public class RecipeDetail extends AppCompatActivity implements ListStepsFragment.interfaceClickStep,
        FragmentRecipeStepDetail.InterfacePhone {
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int position = -1;

    private static final String TAGrecipeDetailFragment = "detailFragment";
    private static final String TAGDetailTabletFragment = "detailTabletFragment";
    private static final String GETBUNDLE = "getBundle";

    private long pTime;
    private boolean ready;

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
        Log.e("RecipeDetail", "onCreate");
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

            Bundle bundle = savedInstanceState.getBundle(GETBUNDLE);
            if (bundle != null) {
                pTime = bundle.getLong(POSITION, 0);
                ready = bundle.getBoolean(PLAY_PAUSE, false);
            }else{ setBund(); }

        }else {
            ingredients = getIntent().getParcelableArrayListExtra("ingredients");
            steps = getIntent().getParcelableArrayListExtra("steps");
            setBund();
        }

        setFragment();
    }

    public void setBund() {
        pTime = 0;
        ready = false;
    }

    public void setFragment() {
        if (position == -1 || isTablet()) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            ListStepsFragment listStepsFragment = new ListStepsFragment();
            listStepsFragment.setStepList(steps);

            fragmentManager.beginTransaction().replace(
                    R.id.fragment_list_steps,
                    listStepsFragment
            ).commit();

            if (isTablet()) {
                onClickedStep(position);
            }
        }else onClickedStep(position);
    }

    @Override
    public void onClickedStep(int position) {
        this.position = position;
        if (isTablet()) {
            setFragmentTablet();
        } else {
            setFragmentPhone();
        }
    }

    public void setFragmentTablet() {
        Log.e("RecipeDetail", "setFragmentTablet");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentSelectRecipeStepDetail fragmentDetailTablet = null;
        if(position == -1)position = 0;
        if (position == 0) {
            fragmentDetailTablet = new FragmentSelectRecipeStepDetail(
                    steps.get(position).getVideoURL(), ingredients,
                    steps.get(position).getThumbnailURL(), pTime, ready
            );
        } else {
            fragmentDetailTablet = new FragmentSelectRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL(),
                    pTime, ready
            );
        }

                fragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_step, fragmentDetailTablet, TAGDetailTabletFragment)
                .commit();

        fragmentDetailTablet.setDescription(steps.get(position).getDescription());
        fragmentDetailTablet.setTitulo(steps.get(position).getShortDescription());
    }

    public void setFragmentPhone() {
        Log.e("RecipeDetail", "setFragmentPhone");
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment previousFragment = fragmentManager.findFragmentById(R.id.fragment_list_steps);

        FragmentRecipeStepDetail fragmentDetailPhone = null;
        if (position == 0) {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL(),
                    ingredients, pTime, ready);
        } else {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL(),
                    pTime, ready);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 1. Exit for Previous Fragment
        Fade exitFade = new Fade();
        int duration = 50;
        exitFade.setDuration(duration);
        previousFragment.setExitTransition(exitFade);

        // 2. Shared Elements Transition
        TransitionSet enterTransitionSet = new TransitionSet();
        enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));

        enterTransitionSet.setDuration(duration);
        enterTransitionSet.setStartDelay(duration);
            fragmentDetailPhone.setSharedElementEnterTransition(enterTransitionSet);

        // 3. Enter Transition for New Fragment
        Fade enterFade = new Fade();
        enterFade.setStartDelay(duration + duration);
        enterFade.setDuration(duration);
        fragmentDetailPhone.setEnterTransition(enterFade);


        fragmentTransaction.replace(R.id.fragment_list_steps, fragmentDetailPhone);
        fragmentTransaction.commit();


        fragmentDetailPhone.setDescription(steps.get(position).getDescription());
        fragmentDetailPhone.setTitulo(steps.get(position).getShortDescription());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("RecipeDetail", "OnSaveInstanceState");
        outState.putParcelableArrayList("ingredientes", ingredients);
        outState.putParcelableArrayList("steps", steps);
        outState.putInt("position", position);
        if (isTablet()) {
            outState.putBundle(GETBUNDLE, FragmentSelectRecipeStepDetail.bundle);
        }else{
            outState.putBundle(GETBUNDLE, FragmentRecipeStepDetail.bundle);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void delegateSwipeRight() {
        if (position < (steps.size() - 1)) {
            this.position++;
            setFragmentPhone();
        } /*else {
            Toast.makeText(this, getString(R.string.text_ultimo_passo), Toast.LENGTH_SHORT)
                    .show();
        }*/

    }

    @Override
    public void delegateSwipeLeft() {
        if (position > 0) {
            this.position--;
            setFragmentPhone();
        } /*else {
            Toast.makeText(this, getString(R.string.text_primeiro_passo), Toast.LENGTH_SHORT)
                    .show();
        }*/
    }
}
