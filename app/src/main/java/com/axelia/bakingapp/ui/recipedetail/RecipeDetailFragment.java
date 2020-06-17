package com.axelia.bakingapp.ui.recipedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.axelia.bakingapp.BakingApplication;
import com.axelia.bakingapp.R;
import com.axelia.bakingapp.data.local.model.Ingredient;
import com.axelia.bakingapp.data.local.model.Step;
import com.axelia.bakingapp.ui.recipedetail.ingredients.IngredientsAdapter;
import com.axelia.bakingapp.ui.recipedetail.steps.StepsAdapter;
import com.axelia.bakingapp.util.RecipeDetailViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RecipeDetailFragment extends Fragment {

    private RecipeDetailViewModel mViewModel;

    @Inject
    RecipeDetailViewModelFactory factory;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance() {
        return new RecipeDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BakingApplication.getComponent(Objects.requireNonNull(getActivity())).inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), factory).get(RecipeDetailViewModel.class);
        setupIngredientsAdapter();
        setupStepsAdapter();
    }

    private void setupIngredientsAdapter() {
        RecyclerView listIngredients = getActivity().findViewById(R.id.recyclerview_ingredients);
        final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        listIngredients.setAdapter(ingredientsAdapter);
        listIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        ViewCompat.setNestedScrollingEnabled(listIngredients, false);

        mViewModel.getIngredientsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                ingredientsAdapter.submitList(ingredients);
            }
        });
    }

    private void setupStepsAdapter() {
        RecyclerView listSteps = getActivity().findViewById(R.id.recyclerview_steps);
        final StepsAdapter adapter = new StepsAdapter(mViewModel);
        listSteps.setAdapter(adapter);
        listSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        ViewCompat.setNestedScrollingEnabled(listSteps, false);

        mViewModel.getStepsList().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                adapter.submitList(steps);
            }
        });
    }
}
