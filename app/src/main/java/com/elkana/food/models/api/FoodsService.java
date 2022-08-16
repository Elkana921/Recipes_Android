package com.elkana.food.models.api;

import com.elkana.food.models.results.ResultsResponse;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.recipes.RecipesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface FoodsService {

    //---Random---
    //EndPoint/Path:
    // recipes/random

    //Query:
    // number=int -> 1 =< 100
    @GET("recipes/random")
    Call<RecipesResponse> randomRecipes(@Query("apiKey") String apiKey,
                                        @Query("number") int number);

    //---Search---
    //EndPoint/Path:
    // recipes/complexSearch

    //Query:
    // query=String -> "pasta", "toast", ...
    // number=int -> 1 =< 100
    @GET("recipes/complexSearch")
    Call<ResultsResponse> searchFoods(@Query("apiKey") String apiKey,
                                      @Query("query") String food,
                                      @Query("number") int number);

    //---Information---
    //EndPoint/Path:
    // recipes/{id}/information

    //Query:
    // includeNutrition=boolean -> false
    @GET("recipes/{id}/information")
    Call<Recipes> getInformation(@Path("id") String id, @Query("apiKey") String apiKey,
                                 @Query("includeNutrition") boolean includeNutrition);


}
