package com.example.rako.bankingapp.activitys;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.fragments.FragmentRecipeStepDetail;
import com.example.rako.bankingapp.fragments.ListStepsFragment;
import com.example.rako.bankingapp.fragments.FragmentSelectRecipeStepDetail;
import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Step;

import java.util.ArrayList;

public class RecipeDetail extends AppCompatActivity implements ListStepsFragment.interfaceClickStep{
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ingredients = getIntent().getParcelableArrayListExtra("ingredients");
        steps = getIntent().getParcelableArrayListExtra("steps");

        FragmentManager fragmentManager = getSupportFragmentManager();
        ListStepsFragment listStepsFragment = new ListStepsFragment();
        listStepsFragment.setStepList(steps);

        fragmentManager.beginTransaction()
                .add(R.id.fragment_list_steps, listStepsFragment)
                .commit();

        if(MainActivity.isTablet(this)){
            FragmentSelectRecipeStepDetail fragmentDetailTablet = new FragmentSelectRecipeStepDetail();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_detail_step, fragmentDetailTablet)
                    .commit();
        }
    }

    @Override
    public void onClickedStep(int position) {
        Toast.makeText(this, "Você clicou no passo "+ String.valueOf(position), Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (MainActivity.isTablet(this)) {
            FragmentSelectRecipeStepDetail fragmentDetailTablet = new FragmentSelectRecipeStepDetail();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_detail_step, fragmentDetailTablet)
                    .commit();
        } else {
            FragmentRecipeStepDetail fragmentDetailPhone = new FragmentRecipeStepDetail();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_list_steps, fragmentDetailPhone)
                    .commit();
        }
    }
}
