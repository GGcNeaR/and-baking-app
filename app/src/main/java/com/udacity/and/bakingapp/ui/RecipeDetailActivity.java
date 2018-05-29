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
    private RecipeStepListFragment recipeStepListFragment;
    private RecipeStepDetailFragment recipeStepDetailFragment;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent startIntent = getIntent();
        if (startIntent == null || !startIntent.hasExtra(Recipe.RECIPE_EXTRA)) {
            Toast.makeText(this, "Missing Recipe!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        isTablet = getResources().getBoolean(R.bool.isTablet);

        recipe = startIntent.getParcelableExtra(Recipe.RECIPE_EXTRA);

        recipeStepListFragment = ((RecipeStepListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_step_list_fragment));

        recipeStepListFragment.setRecipe(recipe);

        recipeStepDetailFragment = ((RecipeStepDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_step_detail_fragment));

        if (isTablet && recipe.getSteps().size() > 0) {
            selectStep(recipe.getSteps().get(0));
        }
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        if (isTablet) {
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
        // TODO highlight item ?
    }
}
