package com.elkana.food.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elkana.food.models.results.Results;
import com.elkana.food.models.api.RecipesApiManager;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Results>> foodLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> excLiveData = new MutableLiveData<>();

    private final RecipesApiManager manager;
    public SearchViewModel(){
         manager = new RecipesApiManager();
    }

    public void getFoods(String query) {
        manager.getResults(query, foodLiveData, excLiveData);
    }

    public void getInfo(String id, RecipesApiManager.RecipeInfoCallback callback) {
        manager.getSingleRecipeInfo(id, callback);
    }

    public LiveData<List<Results>> getFoodsLiveData(){
        return foodLiveData;
    }
    public LiveData<Throwable> getFoodsExcData(){
        return excLiveData;
    }

}