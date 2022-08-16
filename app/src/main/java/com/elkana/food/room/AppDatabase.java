package com.elkana.food.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {SavedItemId.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

   public abstract RoomDao dao();
   private static AppDatabase instance;

   public static synchronized AppDatabase getInstance(Application context) {
       if(instance==null)
            instance = Room.databaseBuilder(context,AppDatabase.class,"savedIdDB")
                    .fallbackToDestructiveMigration()
                    .build();
       return instance;
   }

}
