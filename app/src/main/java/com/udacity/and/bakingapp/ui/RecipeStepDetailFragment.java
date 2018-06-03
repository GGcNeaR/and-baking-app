package com.udacity.and.bakingapp.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.data.contracts.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static final String PLAY_WHEN_READY_EXTRA = "PLAY_WHEN_READY_EXTRA";
    private static final String CURRENT_WINDOW_EXTRA = "CURRENT_WINDOW_EXTRA";
    private static final String PLAYBACK_POSITION_EXTRA = "PLAYBACK_POSITION_EXTRA";

    private Step currentStep;

    private TextView recipeStepDescriptionTextView;

    private SimpleExoPlayer exoPlayer;
    private PlayerView exoPlayerView;

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;

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

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(PLAY_WHEN_READY_EXTRA, playWhenReady);

        if (exoPlayer != null) {
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playbackPosition = exoPlayer.getCurrentPosition();
        }

        outState.putInt(CURRENT_WINDOW_EXTRA, currentWindow);
        outState.putLong(PLAYBACK_POSITION_EXTRA, playbackPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_EXTRA, true);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_EXTRA, 0);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_EXTRA, 0);
        }
    }

    public void changeRecipeStep(Step step) {
        if (currentStep != null && currentStep.getId() != step.getId()) {
            currentWindow = 0;
            playbackPosition = 0;
        }

        currentStep = step;

        if (recipeStepDescriptionTextView != null) {
            recipeStepDescriptionTextView.setText(currentStep.getDescription());
        }

        initializePlayer();
        setupMediaSource(step.getMediaURL());
    }


    private void initializePlayer() {
        if (exoPlayer != null) return;

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        exoPlayerView.setPlayer(exoPlayer);

        exoPlayer.setPlayWhenReady(playWhenReady);
    }

    private void setupMediaSource(String url) {
        if (TextUtils.isEmpty(url)) {
            exoPlayer.stop(true);
        } else {
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            exoPlayer.prepare(mediaSource, true, false);
            exoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking-app")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
