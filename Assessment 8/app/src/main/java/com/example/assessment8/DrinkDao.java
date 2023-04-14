package com.example.assessment8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assessment8.db.Drink;

import java.util.List;

@Dao
public interface DrinkDao {

    @Query("SELECT * FROM drink")
    List<Drink> getAll();
    @Insert
    void insertAll(Drink... drinks);

    @Update
    void update(Drink drink);

    @Delete
    void delete(Drink drink);

    @Query("DELETE from drink")

    void deleteAll();

    @Query("SELECT *FROM drink where did=:did limit 1")
    Drink findById(int did);
}
