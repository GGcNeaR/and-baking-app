package com.udacity.and.bakingapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.adapters.RecipeStepAdapter;
import com.udacity.and.bakingapp.data.contracts.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepListFragment extends Fragment {

    private RecyclerView recipeStepListRecyclerView;

    public RecipeStepListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);

        recipeStepListRecyclerView = view.findViewById(R.id.recipe_step_rv);
        recipeStepListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeStepListRecyclerView.setHasFixedSize(true);

        return view;
    }

    public void setRecipe(Recipe recipe) {
        recipeStepListRecyclerView.setAdapter(new RecipeStepAdapter(recipe.getSteps()));
    }
}
