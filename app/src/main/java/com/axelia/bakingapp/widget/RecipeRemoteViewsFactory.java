package com.axelia.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.axelia.bakingapp.R;
import com.axelia.bakingapp.data.local.model.Ingredient;
import com.axelia.bakingapp.data.remote.RecipeRepository;
import com.axelia.bakingapp.util.InjectionHandler;

import java.util.ArrayList;
import java.util.List;

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> ingredientList = new ArrayList<>();
    private Context mContext;

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientList.clear();
        RecipeRepository repository = InjectionHandler.provideRecipeRepository(mContext);

        List<Ingredient> ingredients = repository.getAllIngredients();
        ingredientList.addAll(ingredients);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_row);
        Ingredient ingredient = ingredientList.get(position);
        remoteView.setTextViewText(R.id.textview_widget_ingredient_name, ingredient.getIngredient());
        remoteView.setTextViewText(R.id.textview_widget_ingredient_quantity,
                ingredient.getMeasure() + " " + ingredient.getQuantity());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
