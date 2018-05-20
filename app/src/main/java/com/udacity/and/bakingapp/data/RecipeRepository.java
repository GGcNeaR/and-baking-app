package com.udacity.and.bakingapp.data;

import android.arch.lifecycle.MutableLiveData;

import com.udacity.and.bakingapp.data.contracts.Recipe;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 5/20/2018.
 */
public class RecipeRepository {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public MutableLiveData<ArrayList<Recipe>> getRecipeList() {
        final MutableLiveData<ArrayList<Recipe>> mutableLiveData = new MutableLiveData<>();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(60, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        RecipeAPIService recipeAPIService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(RecipeAPIService.class);

        Call<ArrayList<Recipe>> recipeCall = recipeAPIService.getRecipeListData();

        recipeCall.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Recipe> recipes = response.body();
                    if (recipes != null) {
                        mutableLiveData.setValue(recipes);
                    } else {
                        mutableLiveData.setValue(new ArrayList<Recipe>());
                    }
                } else {
                    // TODO: handle other cases
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                // TODO: handle errors
            }
        });

        return  mutableLiveData;
    }
}
