package com.elkana.food.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * from saved_ids")
    List<SavedItemId> getAllSavedIds();

    @Insert
    void save(SavedItemId id);

}
