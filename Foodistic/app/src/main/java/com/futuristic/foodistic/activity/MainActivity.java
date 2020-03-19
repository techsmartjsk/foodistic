package com.futuristic.foodistic.activity;

import android.content.Intent;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.FoodCartModel.SliderItem;
import com.futuristic.foodistic.R;
import com.futuristic.foodistic.FoodCartAdapters.HorizontalAdapter;
import com.futuristic.foodistic.FoodCartAdapters.VerticalAdapter;
import com.futuristic.foodistic.FoodCartModel.Food;
import com.futuristic.foodistic.model.GeneralFood;
import com.futuristic.foodistic.rest.RetrofitClient;
import com.futuristic.foodistic.rest.RetrofitInterface;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    private SliderAdapterExample adapter;
    RecyclerView recyclerViewHorizontal;
    RecyclerView recyclerViewVertical;
    public static TextView tv;
    public static List<GeneralFood> cartFoods = new ArrayList<>();
    public Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Slider View...
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);


//Toolbar..


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Punjabi Virsa");
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        cartUpdate();
        renewItems();


        recyclerViewHorizontal = findViewById(R.id.horizontal_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(linearLayoutManager);

        recyclerViewVertical = findViewById(R.id.vertical_recyclerview);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVertical.setLayoutManager(linearLayoutManager2);

        RetrofitInterface retrofitService = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<Food> call = retrofitService.getFoods();
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                List<GeneralFood> popularFoods = response.body().getPopularFood();
                recyclerViewHorizontal.setAdapter(new HorizontalAdapter(popularFoods, R.layout.recyclerview_horizontal, MainActivity.this));

                List<GeneralFood> regularFoods = response.body().getRegularFood();
                recyclerViewVertical.setNestedScrollingEnabled(false);
                recyclerViewVertical.setAdapter(new VerticalAdapter(regularFoods, R.layout.recyclerview_vertical, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {

            }
        });
    }

    public void renewItems() {
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(1);
        sliderView.setAutoCycle(false);
        sliderView.setIndicatorVisibility(true);
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://imgur.com/64KBZZJ" );
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        cartUpdate();
        MenuItem item = menu.findItem(R.id.action_addcart);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        tv = notifCount.findViewById(R.id.hotlist_hot);
        View view = notifCount.findViewById(R.id.hotlist_bell);

        cartUpdate();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(myIntent);
        }});

        return true;
    }

    public static void cartUpdate() {
        if (tv != null && cartFoods != null)
            tv.setText(Integer.toString(cartFoods.size()));

    }


    private void msg() {
        Toast msg = Toast.makeText(MainActivity.this,"Item Added To cart",Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

