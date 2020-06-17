package com.axelia.bakingapp.ui.recipelist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;


import com.axelia.bakingapp.BakingApplication;
import com.axelia.bakingapp.R;
import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.util.EspressoIdlingResource;
import com.axelia.bakingapp.util.RecipeListViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private RecipeListViewModel viewModel;

    @Inject
    RecipeListViewModelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BakingApplication.getComponent(Objects.requireNonNull(this)).inject(this);

        viewModel = ViewModelProviders.of(this, factory).get(RecipeListViewModel.class);
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_recipe_list);
        final RecipesAdapter adapter = new RecipesAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.grid_spans));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        EspressoIdlingResource.increment();

        viewModel.getListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    EspressoIdlingResource.decrement();
                    adapter.submitList(recipes);
                }
            }
        });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
