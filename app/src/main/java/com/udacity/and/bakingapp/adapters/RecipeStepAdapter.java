package com.udacity.and.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Step;

import java.util.List;

/**
 * Created on 5/23/2018.
 */
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private List<Step> recipeSteps;

    public RecipeStepAdapter(List<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_step_list_item, parent, false);

        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        Step step = recipeSteps.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder {

        TextView recipeStepShortDescriptionTextView;

        RecipeStepViewHolder(View itemView) {
            super(itemView);

            recipeStepShortDescriptionTextView = itemView.findViewById(R.id.recipe_short_description_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        void bind(Step step) {
            recipeStepShortDescriptionTextView.setText(step.getShortDescription());
        }
    }
}
