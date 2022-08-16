package com.elkana.food.models.results;

import com.google.gson.annotations.SerializedName;

import java.sql.DataTruncation;

public class Results {

    //Properties:
    @SerializedName("id")
    private final int theId;
    private final String title;

    @SerializedName("image")
    private final String urlImage;

    //Picasso API:
    private static final String imageBaseUrl = "https://image.tmdb.org/t/p/w500";
    public String getImageLink(){
        return imageBaseUrl + urlImage;
    }

    //Constructor:
    public Results(int id, String title, String image) {
        this.theId = id;
        this.title = title;
        this.urlImage = image;
    }

    //Getters:
    public int getTheId() {
        return theId;
    }
    public String getTitle() {
        return title;
    }
    public String getUrlImage() {
        return urlImage;
    }

    //toString:
    @Override
    public String toString() {
        return "Food{" +
                "id=" + theId +
                ", title='" + title + '\'' +
                ", image='" + urlImage + '\'' +
                '}';
    }

}


