/*-----------------------------------------------------------------------------
 - Developed by Haerul Muttaqin                                               -
 - Last modified 3/17/19 5:24 AM                                              -
 - Subscribe : https://www.youtube.com/haerulmuttaqin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.futuristic.foodistic.view.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.Utils;
import com.futuristic.foodistic.activity.MainActivity;
import com.futuristic.foodistic.adapter.RecyclerViewHomeAdapter;
import com.futuristic.foodistic.adapter.ViewPagerHeaderAdapter;
import com.futuristic.foodistic.model.Categories;
import com.futuristic.foodistic.model.Meals;
import com.futuristic.foodistic.view.category.CategoryActivity;
import com.futuristic.foodistic.view.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private ImageView cart;
    private ImageView profile;
    private TextView address;
    private DatabaseReference AddressRef,TextAddressRef;
    private FirebaseAuth mauth;
    private String CurrentUserId;
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";

    @BindView(R.id.viewPagerHeader) ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory) RecyclerView recyclerViewCategory;

    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        cart=(ImageView)findViewById(R.id.cart);
        profile=(ImageView)findViewById(R.id.profile);
        address=(TextView) findViewById(R.id.address);
        AddressRef= FirebaseDatabase.getInstance().getReference();
        mauth=FirebaseAuth.getInstance();
        CurrentUserId=mauth.getCurrentUser().getUid();
        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();
        Cart();
        Profile();
        Address();
    }


    private void Profile() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(HomeActivity.this, UserProfile.class);
                startActivity(main);
            }
        });
    }

    private void Cart() {
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }

    private void Address() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialog);
                builder.setTitle("Enter Address");
                final EditText address=new EditText(HomeActivity.this);
                address.setHint("Address");
                builder.setView(address);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Address =address.getText().toString();
                        if(TextUtils.isEmpty(Address)){
                            Toast.makeText(HomeActivity.this,"Please Enter Address",Toast.LENGTH_SHORT).show();
                        }else{
                            AddAddress(Address);
                            address.setText(Address.subSequence(0,13));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

    }

    private void AddAddress(String Address) {
        AddressRef.child("Addresses").child(CurrentUserId).child(Address).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HomeActivity.this,"Address Added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();

        headerAdapter.setOnItemClickListener((v, position) -> {
            Toast.makeText(this, meal.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }

}
