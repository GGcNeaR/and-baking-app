package com.udacity.and.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Recipe;

import static com.udacity.and.bakingapp.ui.RecipeDetailActivity.RECIPE_EXTRA;

public class RecipeActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        startActivity(intent);
    }
}
