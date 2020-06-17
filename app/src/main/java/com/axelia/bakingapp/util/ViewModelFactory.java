package com.axelia.bakingapp.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axelia.bakingapp.data.remote.RecipeRepository;
import com.axelia.bakingapp.ui.recipedetail.RecipeDetailViewModel;
import com.axelia.bakingapp.ui.recipelist.RecipeListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RecipeRepository repository;

    public static ViewModelFactory getInstance(RecipeRepository repository) {
        return new ViewModelFactory(repository);
    }

    private ViewModelFactory(RecipeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeListViewModel.class)) {
            //noinspection unchecked
            return (T) new RecipeListViewModel(repository);
        } else if (modelClass.isAssignableFrom(RecipeDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new RecipeDetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
