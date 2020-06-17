package com.axelia.bakingapp.ui.recipedetail.steps;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.bakingapp.data.local.model.Step;
import com.axelia.bakingapp.databinding.ItemStepBinding;

public class StepViewHolder extends RecyclerView.ViewHolder {

    private final ItemStepBinding binding;

    public StepViewHolder(@NonNull ItemStepBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(final Step step, final int position) {
        binding.textviewOrder.setText(String.valueOf(position));
        binding.textviewTitle.setText(step.getShortDescription());
        binding.executePendingBindings();
    }

    public static StepViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = ItemStepBinding.inflate(layoutInflater, parent, false);
        return new StepViewHolder(binding);
    }
}
