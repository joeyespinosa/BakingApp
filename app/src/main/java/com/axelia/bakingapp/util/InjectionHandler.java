package com.axelia.bakingapp.util;

import android.content.Context;

import com.axelia.bakingapp.data.local.database.RecipesDatabase;
import com.axelia.bakingapp.data.remote.RecipeRepository;
import com.axelia.bakingapp.data.remote.api.ApiClient;
import com.axelia.bakingapp.data.remote.api.RecipeService;


public class InjectionHandler {
    public static ViewModelFactory provideViewModelFactory(Context context) {
        RecipeRepository repository = provideRecipeRepository(context);
        return ViewModelFactory.getInstance(repository);
    }

    public static RecipeRepository provideRecipeRepository(Context context) {
        RecipeService apiService = ApiClient.getInstance();
        AppExecutors executors = AppExecutors.getInstance();
        RecipesDatabase database = RecipesDatabase.getInstance(context.getApplicationContext());
        return RecipeRepository.getInstance(
                executors,
                apiService,
                database);
    }
}
