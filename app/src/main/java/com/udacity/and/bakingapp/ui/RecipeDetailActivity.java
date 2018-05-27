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

        recipe = startIntent.getParcelableExtra(Recipe.RECIPE_EXTRA);

        RecipeStepListFragment recipeStepListFragment =
                ((RecipeStepListFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_list_fragment));

        recipeStepListFragment.setRecipe(recipe);
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        Intent intent = new Intent(this, RecipeStepDetailActivity.class);
        intent.putExtra(Step.RECIPE_STEP_EXTRA, step);
        intent.putExtra(Recipe.RECIPE_EXTRA, recipe);
        startActivity(intent);
    }
}
