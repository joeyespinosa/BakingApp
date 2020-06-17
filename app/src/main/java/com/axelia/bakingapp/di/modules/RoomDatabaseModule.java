package com.axelia.bakingapp.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.axelia.bakingapp.data.local.dao.IngredientsDao;
import com.axelia.bakingapp.data.local.dao.RecipesDao;
import com.axelia.bakingapp.data.local.database.RecipesDatabase;
import com.axelia.bakingapp.data.local.model.Ingredient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomDatabaseModule {

    private RecipesDatabase database;

    public RoomDatabaseModule(Application application) {
        database = Room.databaseBuilder(application, RecipesDatabase.class, RecipesDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    RecipesDatabase provideDatabase() {
        return database;
    }

    @Singleton
    @Provides
    RecipesDao provideRecipesDao() {
        return database.recipesDao();
    }

    @Singleton
    @Provides
    IngredientsDao provideIngredientsDao() {
        return database.ingredientsDao();
    }
}

