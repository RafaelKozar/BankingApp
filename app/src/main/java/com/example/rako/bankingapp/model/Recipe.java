package com.example.rako.bankingapp.model;

import java.util.List;

/**
 * Created by rako on 27/09/2017.
 */

public class Recipe {
    private String name;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private int numberSteps;
    private int numberIngredients;

    public Recipe(String name, List<Ingredient> ingredients, List<Step> steps) {
        this.name = name;
        this.ingredientList = ingredients;
        this.stepList = steps;
        this.numberSteps = steps.size();
        this.numberIngredients = ingredients.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public int getNumberSteps() {
        return numberSteps;
    }

    public void setNumberSteps(int numberSteps) {
        this.numberSteps = numberSteps;
    }

    public int getNumberIngredients() {
        return numberIngredients;
    }

    public void setNumberIngredients(int numberIngredients) {
        this.numberIngredients = numberIngredients;
    }
}
