package com.udacity.and.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.R;

import java.util.List;

/**
 * Created on 5/20/2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private OnRecipeItemClickListener listener;

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void setOnRecipeItemClickListener(OnRecipeItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);

            recipeNameTextView = itemView.findViewById(R.id.recipe_name_tv);
        }

        void bind(final Recipe recipe) {
            recipeNameTextView.setText(recipe.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onRecipeItemClicked(recipe);
                    }
                }
            });
        }
    }

    public interface OnRecipeItemClickListener {
        void onRecipeItemClicked(Recipe recipe);
    }
}
