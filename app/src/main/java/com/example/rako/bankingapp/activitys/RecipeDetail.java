package com.example.rako.bankingapp.activitys;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import java.util.jar.Manifest;

public class RecipeDetail extends AppCompatActivity implements ListStepsFragment.interfaceClickStep,
        FragmentRecipeStepDetail.InterfacePhone {
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int position;
    //int permissionCheck = ContextCompat.checkSelfPermission(this,
      //      android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


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
        if (MainActivity.isTablet(this)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentSelectRecipeStepDetail fragmentDetailTablet = new FragmentSelectRecipeStepDetail();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_step, fragmentDetailTablet)
                    .commit();
        } else {
            this.position = position;
            setFragmentPhone();
        }
    }

    public void setFragmentPhone(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentRecipeStepDetail fragmentDetailPhone = null;
        if (position == 0) {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL(), ingredients);
        }else {
            fragmentDetailPhone = new FragmentRecipeStepDetail(
                    steps.get(position).getVideoURL());
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_list_steps, fragmentDetailPhone)
                .commit();

        fragmentDetailPhone.setDescription(steps.get(position).getDescription());
        fragmentDetailPhone.setTitulo(steps.get(position).getShortDescription());

    }


    @Override
    public void delegateSwipeRight() {
        if (position <= steps.size()) {
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
