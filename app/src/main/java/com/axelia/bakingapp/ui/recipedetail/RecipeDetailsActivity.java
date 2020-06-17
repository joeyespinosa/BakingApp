package com.axelia.bakingapp.ui.recipedetail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.axelia.bakingapp.BakingApplication;
import com.axelia.bakingapp.R;
import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.data.local.model.Step;
import com.axelia.bakingapp.ui.stepdetail.StepDetailActivity;
import com.axelia.bakingapp.ui.stepdetail.StepDetailFragment;
import com.axelia.bakingapp.util.ActivityUtils;
import com.axelia.bakingapp.util.Constants;
import com.axelia.bakingapp.util.InjectionHandler;
import com.axelia.bakingapp.util.RecipeDetailViewModelFactory;
import com.axelia.bakingapp.util.ViewModelFactory;
import com.axelia.bakingapp.widget.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_DATA = "EXTRA_RECIPE_DATA";
    private Recipe recipe;
    private boolean mTwoPane = false;
    private RecipeDetailViewModel mViewModel;

    @Inject
    RecipeDetailViewModelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        BakingApplication.getComponent(Objects.requireNonNull(this)).inject(this);

        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE_DATA);
        if (recipe == null) {
            closeOnError();
            return;
        }

        if (findViewById(R.id.fragment_step_detail) != null) {
            mTwoPane = true;
        }

        mViewModel = ViewModelProviders.of(this, factory).get(RecipeDetailViewModel.class);
        setupToolbar();
        if (savedInstanceState == null) {
            mViewModel.init(recipe, mTwoPane);
            setupViewFragment();
            saveRecipeDataToSharedPreferences(recipe);
            refreshWidgetIngredientsList(recipe);
        }

        mViewModel.getOpenStepDetailEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                Step step = mViewModel.getCurrentStep().getValue();
                if (mTwoPane) {
                    StepDetailFragment fragment = StepDetailFragment.newInstance(step);
                    ActivityUtils.replaceFragmentInActivity(
                            getSupportFragmentManager(), fragment, R.id.fragment_step_detail);
                } else {
                    ArrayList<Step> steps = new ArrayList<>(recipe.getSteps());
                    Intent intent = new Intent(RecipeDetailsActivity.this, StepDetailActivity.class);
                    intent.putParcelableArrayListExtra(StepDetailActivity.EXTRA_STEP_LIST, steps);
                    intent.putExtra(StepDetailActivity.EXTRA_POSITION, position);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(recipe.getName());
        }
    }

    private void refreshWidgetIngredientsList(Recipe recipe) {
        // Update the Widget display
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int ids[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplication(), RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.listview_widget);


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_recipe_ingredients);
        remoteViews.setTextViewText(R.id.textview_widget_recipe_name, recipe.getName());
        appWidgetManager.partiallyUpdateAppWidget(ids, remoteViews);
    }

    private void saveRecipeDataToSharedPreferences(Recipe recipe) {
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(getString(R.string.recipe_name), recipe.getName());
        editor.putLong(getString(R.string.recipe_id), recipe.getId());
        editor.apply();
    }

    private void setupViewFragment() {
        if (mTwoPane) {
            return;
        }
    }

    public static RecipeDetailViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = InjectionHandler.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(RecipeDetailViewModel.class);
    }

    private void closeOnError() {
        throw new IllegalArgumentException("Access denied.");
    }
}
