package com.axelia.bakingapp.ui.recipelist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.axelia.bakingapp.R;
import com.axelia.bakingapp.data.local.model.Recipe;
import com.axelia.bakingapp.databinding.ItemRecipeBinding;
import com.axelia.bakingapp.ui.recipedetail.RecipeDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;


class RecipeViewHolder extends RecyclerView.ViewHolder {

    private final ItemRecipeBinding binding;

    public RecipeViewHolder(@NonNull ItemRecipeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(final Recipe recipe, int position) {
        binding.setRecipe(recipe);

        Picasso.get()
                .load(R.drawable.cake)
                .placeholder(R.color.colorPrimary)
                .into(binding.imageviewRecipe);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_DATA, recipe);
                view.getContext().startActivity(intent);
            }
        });

        binding.executePendingBindings();
    }

    public static RecipeViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeBinding binding = ItemRecipeBinding.inflate(layoutInflater, parent, false);
        return new RecipeViewHolder(binding);
    }
}
