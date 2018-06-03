package com.udacity.and.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.data.contracts.Step;

public class RecipeDetailActivity extends AppCompatActivity
                implements RecipeStepListFragment.OnRecipeStepClickListener {

    private Recipe recipe;
    private Step selectedStep;
    private RecipeStepListFragment recipeStepListFragment;
    private RecipeStepDetailFragment recipeStepDetailFragment;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeStepListFragment = ((RecipeStepListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_step_list_fragment));
        recipeStepDetailFragment = ((RecipeStepDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_step_detail_fragment));

        isTablet = getResources().getBoolean(R.bool.isTablet);

        Intent startIntent = getIntent();
        if (startIntent == null || !startIntent.hasExtra(Recipe.RECIPE_EXTRA)) {
            Toast.makeText(this, "Missing Recipe!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recipe = startIntent.getParcelableExtra(Recipe.RECIPE_EXTRA);
        recipeStepListFragment.setRecipe(recipe);

        if (isTablet) {
            if (savedInstanceState != null) {
                selectedStep = savedInstanceState.getParcelable(Step.RECIPE_STEP_EXTRA);
            } else if (recipe.getSteps().size() > 0) {
                selectedStep = recipe.getSteps().get(0);
            }
            selectStep(selectedStep);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (isTablet && selectedStep != null) {
            outState.putParcelable(Step.RECIPE_STEP_EXTRA, selectedStep);
        }
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        if (isTablet) {
            selectedStep = step;
            selectStep(step);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(Step.RECIPE_STEP_EXTRA, step);
            intent.putExtra(Recipe.RECIPE_EXTRA, recipe);
            startActivity(intent);
        }
    }

    private void selectStep(Step step) {
        recipeStepDetailFragment.changeRecipeStep(step);
    }
}
