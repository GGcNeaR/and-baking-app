package com.udacity.and.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.and.bakingapp.data.RecipeRepository;
import com.udacity.and.bakingapp.data.contracts.Recipe;

import java.util.ArrayList;

/**
 * Created on 5/20/2018.
 */
public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        recipeRepository = new RecipeRepository();
    }

    public LiveData<ArrayList<Recipe>> getRecipeList() {
        return recipeRepository.getRecipeList();
    }
}
