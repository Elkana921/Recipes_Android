package com.elkana.food.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_ids")
public class SavedItemId {

    @PrimaryKey(autoGenerate = true) private long id;

    private String savedId;

    public SavedItemId(String savedId) {
        this.savedId = savedId;
    }

    public String getSavedId() {
        return savedId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSavedId(String savedId) {
        this.savedId = savedId;
    }
}
