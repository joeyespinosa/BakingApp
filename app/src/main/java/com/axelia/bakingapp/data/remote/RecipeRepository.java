package com.axelia.bakingapp.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.axelia.bakingapp.data.local.database.RecipesDatabase;
import com.axelia.bakingapp.data.local.model.Ingredient;
import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.data.remote.api.RecipeService;
import com.axelia.bakingapp.util.AppExecutors;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


@Singleton
public class RecipeRepository {

    private static volatile RecipeRepository sInstance;
    private final RecipeService mRecipeService;
    private final RecipesDatabase mRecipesDatabase;
    private final AppExecutors mExecutors;

    @Inject
    public RecipeRepository(AppExecutors executors,
                             RecipeService recipeService,
                             RecipesDatabase database) {
        mExecutors = executors;
        mRecipeService = recipeService;
        mRecipesDatabase = database;
    }

    public static RecipeRepository getInstance(AppExecutors executors,
                                               RecipeService recipeService,
                                               RecipesDatabase database) {
        if (sInstance == null) {
            synchronized (RecipeRepository.class) {
                if (sInstance == null) {
                    sInstance = new RecipeRepository(executors, recipeService, database);
                }
            }
        }
        return sInstance;
    }

    public List<Ingredient> getAllIngredients() {
        return mRecipesDatabase.ingredientsDao().getAllIngredients();
    }

    public void saveRecipe(final Recipe recipe) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipesDatabase.recipesDao().insertRecipe(recipe);
                mRecipesDatabase.ingredientsDao().deleteIngredient();
                insertIngredients(recipe.getIngredients());
            }
        });
    }

    private void insertIngredients(List<Ingredient> ingredients) {
        mRecipesDatabase.ingredientsDao().insertAllIngredients(ingredients);
        Timber.d("%s ingredients inserted into database.", ingredients.size());
    }

    public LiveData<List<Recipe>> loadAllRecipes() {
        final MutableLiveData<List<Recipe>> recipeListLiveData = new MutableLiveData<>();
        mRecipeService.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> data = response.body();
                    List<Recipe> recipes = data != null ? data : Collections.<Recipe>emptyList();
                    Timber.d("Parsing finished. number of recipes: %s", recipes.size());
                    recipeListLiveData.postValue(recipes);
                } else {
                    Timber.d("error code: %s", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.d("unknown error: %s", t.getMessage());
            }
        });
        return recipeListLiveData;
    }
}
