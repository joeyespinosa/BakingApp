package com.axelia.bakingapp.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.axelia.bakingapp.data.local.dao.IngredientsDao;
import com.axelia.bakingapp.data.local.dao.RecipesDao;
import com.axelia.bakingapp.data.local.model.Ingredient;
import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.data.local.model.Step;


@Database(
        entities = {Recipe.class, Step.class, Ingredient.class},
        version = 1,
        exportSchema = false)
public abstract class RecipesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "recipes.db";

    private static RecipesDatabase INSTANCE;

    private static final Object sLock = new Object();

    public abstract RecipesDao recipesDao();

    public abstract IngredientsDao ingredientsDao();

    public static RecipesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static RecipesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                RecipesDatabase.class,
                DATABASE_NAME).build();
    }
}
