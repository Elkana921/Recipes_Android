package com.elkana.food.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elkana.food.models.api.ApiResponseStatus;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.api.RecipesApiManager;

import org.jsoup.Jsoup;

import java.util.List;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<ApiResponseStatus<List<Recipes>>> recipesLiveData = new MutableLiveData<>();

    public DetailsViewModel(){
        RecipesApiManager manager1 = new RecipesApiManager();
        manager1.getRecipes(recipesLiveData);
    }

    public LiveData<ApiResponseStatus<List<Recipes>>> getRecipesLiveData() {return recipesLiveData;}

    //methods:
    public Intent goToWeb(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}