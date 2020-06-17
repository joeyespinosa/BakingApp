package com.axelia.bakingapp;

import android.app.Application;
import android.content.Context;

import androidx.databinding.library.BuildConfig;

import com.axelia.bakingapp.di.DaggerRecipeComponent;
import com.axelia.bakingapp.di.RecipeComponent;
import com.axelia.bakingapp.di.modules.AppExecutorsModule;
import com.axelia.bakingapp.di.modules.RecipeServiceModule;
import com.axelia.bakingapp.di.modules.RoomDatabaseModule;

import timber.log.Timber;

public class BakingApplication extends Application {

    private RecipeComponent component;

    public static RecipeComponent getComponent(Context context) {
        return ((BakingApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerRecipeComponent.builder()
                .appExecutorsModule(new AppExecutorsModule())
                .recipeServiceModule(new RecipeServiceModule())
                .roomDatabaseModule(new RoomDatabaseModule(this))
                .build();
    }
}
