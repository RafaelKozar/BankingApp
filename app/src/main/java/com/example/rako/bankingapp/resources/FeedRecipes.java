package com.example.rako.bankingapp.resources;

import com.example.rako.bankingapp.model.Ingredient;
import com.example.rako.bankingapp.model.Recipe;
import com.example.rako.bankingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rako on 28/09/2017.
 */

public class FeedRecipes {
    public static List<Recipe> process(JSONArray jsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        if (jsonArray != null) {
            int tamJsonArray = jsonArray.length();
            for (int i = 0; i < tamJsonArray; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                List<Ingredient> ingredients = new ArrayList<>();
                List<Step> steps = new ArrayList<>();

                int tamIngredients = jsonObject.getJSONArray("ingredients").length();
                JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
                for (int j = 0; tamIngredients < j; j++) {
                    JSONObject jsonObjectIngredient = jsonArrayIngredients.getJSONObject(j);
                    ingredients.add(
                            new Ingredient(jsonObjectIngredient.getString("ingredient"),
                                    jsonObjectIngredient.getString("measure"),
                                    Integer.parseInt(jsonObjectIngredient.getString("quantity"))
                            ));
                }

                int tamSteps = jsonObject.getJSONArray("steps").length();
                JSONArray jsonArraySteps = jsonObject.getJSONArray("steps");
                for (int j = 0; tamSteps < j; j++) {
                    JSONObject jsonObjectSteps = jsonArraySteps.getJSONObject(j);
                    steps.add(
                            new Step(jsonObjectSteps.getString("shortDescription"),
                                    jsonObjectSteps.getString("description"),
                                    jsonObjectSteps.getString("videoURL"),
                                    jsonObjectSteps.getString("thumbnailURL")
                            ));
                }

                recipes.add(
                        new Recipe(jsonObject.getString("name"),
                                ingredients,
                                steps
                        ));
            }
            return recipes;
        } else {
            return null;
        }

    }
}
