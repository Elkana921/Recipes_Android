package com.elkana.food.models.recipes;

import java.util.ArrayList;

public class RecipesResponse {

    private ArrayList<Recipes> recipes;

    public RecipesResponse(){};

    public ArrayList<Recipes> getRecipes() {
        return recipes;
    }
    public void setRecipes(ArrayList<Recipes> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "RecipesResponse{" +
                "recipes=" + recipes +
                '}';
    }
}
