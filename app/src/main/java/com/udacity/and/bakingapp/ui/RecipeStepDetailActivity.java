package com.udacity.and.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.data.contracts.Step;

import java.util.List;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private List<Step> steps;
    private Step currentStep;
    private int currentStepIndex;

    private TextView recipeStepDescriptionTextView;
    private Button prevRecipeStepButton;
    private Button nextRecipeStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent startIntent = getIntent();
        if (startIntent == null ||
                !startIntent.hasExtra(Step.RECIPE_STEP_EXTRA) || !startIntent.hasExtra(Recipe.RECIPE_EXTRA)) {
            Toast.makeText(this, "Missing Recipe or Step!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recipeStepDescriptionTextView = findViewById(R.id.recipe_step_description_tv);
        prevRecipeStepButton = findViewById(R.id.prev_recipe_step_btn);
        nextRecipeStepButton = findViewById(R.id.next_recipe_step_btn);

        Recipe recipe = startIntent.getParcelableExtra(Recipe.RECIPE_EXTRA);
        currentStep = startIntent.getParcelableExtra(Step.RECIPE_STEP_EXTRA);

        steps = recipe.getSteps();
        currentStepIndex = recipe.getSteps().indexOf(currentStep);

        prevRecipeStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStepIndex > 0) {
                    currentStepIndex--;
                    currentStep = steps.get(currentStepIndex);

                    refreshUI();
                }
            }
        });

        nextRecipeStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStepIndex < steps.size() - 1) {
                    currentStepIndex++;
                    currentStep = steps.get(currentStepIndex);

                    refreshUI();
                }
            }
        });

        refreshUI();
    }

    private void refreshUI() {
        recipeStepDescriptionTextView.setText(currentStep.getDescription());

        prevRecipeStepButton.setEnabled(currentStepIndex != 0);
        nextRecipeStepButton.setEnabled(currentStepIndex != steps.size() - 1);
    }
}
