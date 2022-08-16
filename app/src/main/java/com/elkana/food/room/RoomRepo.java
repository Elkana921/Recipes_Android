package com.elkana.food.room;

import android.app.Application;

import java.util.List;

public class RoomRepo {

    private final RoomDao dao;

    public RoomRepo(Application application) {
        dao = AppDatabase.getInstance(application).dao();
    }

    public void getSavedIds(RoomCallback<List<SavedItemId>> callback) {
        new Thread(() -> {
            List<SavedItemId> ids = dao.getAllSavedIds();
            callback.onSavedItemIdsReceived(ids);
        }).start();
    }

    public void saveId(String id,RoomCallback<String> callback) {
        new Thread(() ->  {
            dao.save(new SavedItemId(id));
            callback.onSavedItemIdsReceived("ID " + id + ", Saved successfully to Room Database");
        }).start();
    }

}
