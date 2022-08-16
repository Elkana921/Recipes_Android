package com.elkana.food.room;

public interface RoomCallback<T> {
    void onSavedItemIdsReceived(T data);
}
