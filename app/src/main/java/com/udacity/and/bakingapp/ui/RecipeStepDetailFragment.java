package com.udacity.and.bakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    private Step currentStep;

    private TextView recipeStepDescriptionTextView;

    private SimpleExoPlayer exoPlayer;
    private PlayerView exoPlayerView;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);


        exoPlayerView = view.findViewById(R.id.playerView);
        recipeStepDescriptionTextView = view.findViewById(R.id.recipe_step_description_tv);

        return view;
    }

    public void changeRecipeStep(Step step) {
        currentStep = step;

        recipeStepDescriptionTextView.setText(currentStep.getDescription());

        // TODO: change video
    }

}
