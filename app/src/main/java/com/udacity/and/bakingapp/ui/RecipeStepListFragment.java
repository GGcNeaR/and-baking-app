package com.udacity.and.bakingapp.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.adapters.RecipeStepAdapter;
import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.data.contracts.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepListFragment extends Fragment implements RecipeStepAdapter.OnRecipeStepItemClickListener {

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recipeStepListRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation());
        recipeStepListRecyclerView.addItemDecoration(divider);
        recipeStepListRecyclerView.setHasFixedSize(true);

        return view;
    }

    public void setRecipe(Recipe recipe) {
        RecipeStepAdapter adapter = new RecipeStepAdapter(recipe.getSteps());
        adapter.setOnRecipeStepItemClickListener(this);
        recipeStepListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipeStepItemClicked(Step step) {
        Activity activity = getActivity();
        if (activity instanceof OnRecipeStepClickListener) {
            ((OnRecipeStepClickListener) activity).onRecipeStepClicked(step);
        }
    }

    public interface OnRecipeStepClickListener {
        void onRecipeStepClicked(Step step);
    }
}
