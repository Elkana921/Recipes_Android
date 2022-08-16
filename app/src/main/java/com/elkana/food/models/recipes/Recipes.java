package com.elkana.food.models.recipes;

import com.google.gson.annotations.SerializedName;

public class Recipes {

    //Properties:
    private final String id;
    private final String title;

    @SerializedName("image")
    private final String urlImage;
    private final int readyInMinutes;

    @SerializedName("spoonacularSourceUrl")
    private final String goToTheWeb;
    private final String summary;

    @SerializedName("aggregateLikes")
    private final long likes;

    //Constructors:
    public Recipes(String id, String title, String urlImage, int readyInMinutes, String goToTheWeb, String summary, int likes) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.readyInMinutes = readyInMinutes;
        this.goToTheWeb = goToTheWeb;
        this.summary = summary;
        this.likes = likes;
    }

    //Getters:
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public int getReadyInMinutes() {
        return readyInMinutes;
    }
    public String getGoToTheWeb() {
        return goToTheWeb;
    }
    public String getSummary() {
        return summary;
    }
    public long getLikes() {
        return likes;
    }

    //toString:
    @Override
    public String toString() {
        return "Recipes{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", goToTheWeb='" + goToTheWeb + '\'' +
                ", summary='" + summary + '\'' +
                ", likes=" + likes +
                '}';
    }
}
