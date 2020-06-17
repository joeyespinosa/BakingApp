package com.axelia.bakingapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.axelia.bakingapp.data.local.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllIngredients(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredients();

    @Query("DELETE FROM ingredient")
    void deleteIngredient();

}
