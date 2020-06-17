package com.axelia.bakingapp.ui.recipedetail.ingredients;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.bakingapp.data.local.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> mIngredientList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IngredientViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);
        ((IngredientViewHolder) holder).bindTo(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredientList != null ? mIngredientList.size() : 0;
    }

    public void submitList(List<Ingredient> ingredients) {
        mIngredientList = ingredients;
        notifyDataSetChanged();
    }
}
