package com.axelia.bakingapp.di;

import com.axelia.bakingapp.di.modules.AppExecutorsModule;
import com.axelia.bakingapp.di.modules.RecipeServiceModule;
import com.axelia.bakingapp.di.modules.RoomDatabaseModule;
import com.axelia.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.axelia.bakingapp.ui.recipedetail.RecipeDetailsActivity;
import com.axelia.bakingapp.ui.recipelist.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppExecutorsModule.class,
        RecipeServiceModule.class,
        RoomDatabaseModule.class,
})
public interface RecipeComponent {

    void inject(RecipeDetailFragment recipeDetailFragment);
    void inject(MainActivity mainActivity);
    void inject(RecipeDetailsActivity recipeDetailsActivity);
}
