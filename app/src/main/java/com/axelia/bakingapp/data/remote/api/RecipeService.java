package com.axelia.bakingapp.data.remote.api;

import com.axelia.bakingapp.data.local.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
