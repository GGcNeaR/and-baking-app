package com.udacity.and.bakingapp.data;

import com.udacity.and.bakingapp.data.contracts.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created on 5/20/2018.
 */
//https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
public interface RecipeAPIService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipeListData();
}
