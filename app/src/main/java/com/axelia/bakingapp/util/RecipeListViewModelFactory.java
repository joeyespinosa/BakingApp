package com.axelia.bakingapp.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axelia.bakingapp.data.remote.RecipeRepository;
import com.axelia.bakingapp.ui.recipedetail.RecipeDetailViewModel;
import com.axelia.bakingapp.ui.recipelist.RecipeListViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeListViewModelFactory implements ViewModelProvider.Factory {

    private final RecipeRepository repository;

    public static RecipeListViewModelFactory getInstance(RecipeRepository repository) {
        return new RecipeListViewModelFactory(repository);
    }

    @Inject
    public RecipeListViewModelFactory(RecipeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeListViewModel(repository);
    }
}
