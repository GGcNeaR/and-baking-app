package com.udacity.and.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.data.contracts.Step;

import java.util.List;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private List<Step> steps;
    private Step currentStep;
    private int currentStepIndex;

    private Button prevRecipeStepButton;
    private Button nextRecipeStepButton;

    private RecipeStepDetailFragment recipeStepDetailFragment;

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

        recipeStepDetailFragment = ((RecipeStepDetailFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.recipe_step_detail_fragment));

        refreshUI();

        // TODO: move to RecipeStepDetailFragment ?
        //initPlayer();
    }

    private void refreshUI() {
        recipeStepDetailFragment.changeRecipeStep(currentStep);

        prevRecipeStepButton.setEnabled(currentStepIndex != 0);
        nextRecipeStepButton.setEnabled(currentStepIndex != steps.size() - 1);
    }

    // TODO: move to RecipeStepDetailFragment ?
//    private void initPlayer() {
//        if (exoPlayer == null) {
//            TrackSelector trackSelector = new DefaultTrackSelector();
//            LoadControl loadControl = new DefaultLoadControl();
//
//            @DefaultRenderersFactory.ExtensionRendererMode
//            int extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
//            DefaultRenderersFactory renderersFactory =
//                    new DefaultRenderersFactory(this, extensionRendererMode);
//
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
//            exoPlayerView.setPlayer(exoPlayer);
//
//            String userAgent = Util.getUserAgent(this, "BakingApp");
//
//            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent))
//                    .createMediaSource(Uri.parse(currentStep.getMediaURL()));
//
//            exoPlayer.prepare(mediaSource);
//            exoPlayer.setPlayWhenReady(true);
//        }
//    }
}
