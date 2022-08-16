package com.elkana.food.models.api;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.elkana.food.Singleton;
import com.elkana.food.home.HomeFragment;
import com.elkana.food.models.results.ResultsResponse;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.recipes.RecipesResponse;
import com.elkana.food.models.results.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesApiManager {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private FoodsService service = retrofit.create(FoodsService.class);
    private static final String API_KEY = "62d3a23d1b2744528835df44855c2e4b";

    // ---- Random: ----
    public void getRecipes(MutableLiveData<ApiResponseStatus<List<Recipes>>> recipesLiveData) {

        Call<RecipesResponse> recipesHTTPRequest = service.randomRecipes(API_KEY, 30);

        //Random List Request:
        recipesHTTPRequest.enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {

                if (response.code() == 402){
                    recipesLiveData.postValue(new ApiResponseStatus<>(response.message(),response.code(),null));
                    return;
                }


                RecipesResponse recipesResponse = response.body();
                if (recipesResponse != null) {
                    ArrayList<Recipes> recipes = recipesResponse.getRecipes();
                    recipesLiveData.postValue(new ApiResponseStatus<>(response.message(),response.code(),recipes));
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                recipesLiveData.postValue(new ApiResponseStatus<>(t.getMessage(),500,null));
            }
        });

    }

    // ---- Search: ----
    public void getResults(String food, MutableLiveData<List<Results>> foodsLiveData, MutableLiveData<Throwable> excLiveData) {

        Call<ResultsResponse> foodsHTTPRequest = service.searchFoods(API_KEY, food, 30);

        foodsHTTPRequest.enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {

                ResultsResponse foodsResponse = response.body();
                if (foodsResponse != null && food != "") {
                    ArrayList<Results> results = foodsResponse.getResults();
                    foodsLiveData.postValue(results);
                }
            }

            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {
                excLiveData.postValue(t);
            }
        });

    }

    // ---- Information: ----
    public void getSingleRecipeInfo(String id, RecipeInfoCallback recipesCallback) {

        Call<Recipes> infoHTTPRequest = service.getInformation(id, API_KEY, true);

        infoHTTPRequest.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {

                Recipes recipesResponse = response.body();
                if (recipesResponse != null) {
                    recipesCallback.onSuccess(recipesResponse);
                } else {
                    recipesCallback.onFailure(new Exception("Recipe is null"));
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                recipesCallback.onFailure(new Exception(t.getMessage()));
            }
        });
    }

    public interface RecipeInfoCallback {
        void onSuccess(Recipes recipe);
        void onFailure(Exception e);
    }

}
