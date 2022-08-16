package com.elkana.food.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elkana.food.models.api.ApiResponseStatus;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.api.RecipesApiManager;
import com.elkana.food.room.RoomCallback;
import com.elkana.food.room.RoomRepo;
import com.elkana.food.room.SavedItemId;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<ApiResponseStatus<List<Recipes>>> recipesLiveData = new MutableLiveData<>();
    public final RecipesApiManager manager;
    private RoomRepo roomRepo;

    public HomeViewModel(Application application){
        super(application);

        roomRepo = new RoomRepo(application);

        manager = new RecipesApiManager();
        manager.getRecipes(recipesLiveData);
    }


    public void getSavedIds(RoomCallback<List<SavedItemId>> callback) {
        roomRepo.getSavedIds(callback);
    }

    public void saveId(String id,RoomCallback<String> callback) {
        roomRepo.saveId(id,callback);
    }

    public void refreshRecipes() {
        manager.getRecipes(recipesLiveData);
    }

    public LiveData<ApiResponseStatus<List<Recipes>>> getRecipesLiveData() {return recipesLiveData;}


}