package com.udacity.and.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent startIntent = getIntent();
        if (startIntent == null || !startIntent.hasExtra(RECIPE_EXTRA)) {
            Toast.makeText(this, "Missing Recipe!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Recipe recipe = startIntent.getParcelableExtra(RECIPE_EXTRA);

        RecipeStepListFragment recipeStepListFragment =
                ((RecipeStepListFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_list_fragment));

        recipeStepListFragment.setRecipe(recipe);
    }
}
