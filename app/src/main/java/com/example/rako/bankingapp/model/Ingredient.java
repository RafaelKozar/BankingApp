package com.example.rako.bankingapp.model;

/**
 * Created by rako on 28/09/2017.
 */

public class Ingredient {
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(String ingredient, String measure, int quantity) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
