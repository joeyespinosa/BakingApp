package com.axelia.bakingapp.ui.recipelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.data.remote.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private LiveData<List<Recipe>> listLiveData;

    public RecipeListViewModel(RecipeRepository repository) {
        listLiveData = repository.loadAllRecipes();
    }

    public LiveData<List<Recipe>> getListLiveData() {
        return listLiveData;
    }
}
