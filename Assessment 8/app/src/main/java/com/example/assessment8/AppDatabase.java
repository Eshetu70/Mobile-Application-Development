package com.example.assessment8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assessment8.db.Drink;

@Database(entities = {Drink.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract DrinkDao drinkDao();
}
