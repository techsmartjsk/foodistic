/*-----------------------------------------------------------------------------
 - Developed by Haerul Muttaqin                                               -
 - Last modified 3/24/19 5:13 AM                                              -
 - Subscribe : https://www.youtube.com/haerulmuttaqin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.futuristic.foodistic.view.category;

import com.futuristic.foodistic.Utils;
import com.futuristic.foodistic.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {
    private CategoryView view;

    public CategoryPresenter(CategoryView view) {
        this.view = view;
    }
    
    void getMealByCategory(String category) {

        view.showLoading();
        Call<Meals> mealsCall = Utils.getApi().getMealByCategory(category);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(final Call<Meals> call, final Response<Meals> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setMeals(response.body().getMeals());
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(final Call<Meals> call, final Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());

            }
        });
        

    }
}
