package com.futuristic.foodistic.rest;

import com.futuristic.foodistic.FoodCartModel.Food;
import com.futuristic.foodistic.FoodCartModel.Food;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("raw/jiEvzswz")
    abstract public Call<Food> getFoods();
}