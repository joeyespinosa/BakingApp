package com.axelia.bakingapp.ui.recipedetail.ingredients;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.bakingapp.data.local.model.Ingredient;
import com.axelia.bakingapp.databinding.ItemIngredientBinding;


public class IngredientViewHolder extends RecyclerView.ViewHolder {

    private final ItemIngredientBinding binding;

    public IngredientViewHolder(@NonNull ItemIngredientBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(final Ingredient ingredient) {
        binding.setIngredient(ingredient);
        binding.executePendingBindings();
    }

    public static IngredientViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(layoutInflater, parent, false);
        return new IngredientViewHolder(binding);
    }
}
