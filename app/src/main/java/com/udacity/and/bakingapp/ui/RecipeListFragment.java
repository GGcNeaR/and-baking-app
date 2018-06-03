package com.udacity.and.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.and.bakingapp.R;
import com.udacity.and.bakingapp.adapters.RecipeAdapter;
import com.udacity.and.bakingapp.data.contracts.Recipe;
import com.udacity.and.bakingapp.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment implements RecipeAdapter.OnRecipeItemClickListener {

    private RecipeViewModel recipeViewModel;
    private RecyclerView recipeRecyclerView;
    private ProgressBar progressBar;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private OnRecipeClickListener onRecipeItemClickListener;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        recipeRecyclerView = view.findViewById(R.id.recipe_rv);
        recipeRecyclerView.setHasFixedSize(true);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int numberOfColumnsIfTablet = 3;
        recipeRecyclerView.setLayoutManager(
                isTablet ?
                new GridLayoutManager(getActivity(), numberOfColumnsIfTablet) :
                new LinearLayoutManager(getActivity()));

        recipeAdapter = new RecipeAdapter();
        recipeAdapter.setOnRecipeItemClickListener(this);
        recipeList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        recipeViewModel.getRecipeList().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                progressBar.setVisibility(View.GONE);
                recipeList.clear();
                if (recipes != null) {
                    recipeList.addAll(recipes);
                } else {
                    Toast.makeText(getActivity(), R.string.error_loading_recipe_list, Toast.LENGTH_SHORT).show();
                }
                recipeAdapter.notifyDataSetChanged();
            }
        });

        recipeAdapter.setRecipeList(recipeList);

        recipeRecyclerView.setAdapter(recipeAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof OnRecipeClickListener) {
            onRecipeItemClickListener = ((OnRecipeClickListener) getActivity());
        }
    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        if (onRecipeItemClickListener != null) {
            onRecipeItemClickListener.onRecipeClicked(recipe);
        }
    }

    interface OnRecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }
}
