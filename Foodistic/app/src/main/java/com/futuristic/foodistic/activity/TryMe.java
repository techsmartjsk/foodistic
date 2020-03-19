package com.futuristic.foodistic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.FoodCartAdapters.OrderAdapter;
import com.futuristic.foodistic.R;
import com.futuristic.foodistic.FoodCartAdapters.CartAdapter;
import com.futuristic.foodistic.model.GeneralFood;
import com.futuristic.foodistic.view.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.futuristic.foodistic.activity.MainActivity.cartFoods;

public class TryMe extends AppCompatActivity {

    RecyclerView recyclerviewCart;
    static TextView cartPrice;
    private DatabaseReference OrdersRef;
    private FirebaseAuth mauth;
    private String CurrentUserId;
    private String currentDate;
    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_me);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Punjabi Virsa");
        mToolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(mToolbar);


        mauth=FirebaseAuth.getInstance();
        CurrentUserId=mauth.getCurrentUser().getUid();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
        currentDate = dateFormat.format(cal.getTime());

        OrdersRef= FirebaseDatabase.getInstance().getReference();


        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TryMe.super.onBackPressed();
            }
        });

        cartPrice = findViewById(R.id.cart_price);
        cartPrice.setText(Double.toString(grandTotal(cartFoods)));

        recyclerviewCart = findViewById(R.id.cart_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(linearLayoutManager);
        recyclerviewCart.setNestedScrollingEnabled(false);
        recyclerviewCart.setAdapter(new OrderAdapter(cartFoods, R.layout.recyclerview_order, getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public static int grandTotal(List< GeneralFood> cartFoods){

        int totalPrice = 0;
        for(int i = 0 ; i < cartFoods.size(); i++) {
            totalPrice += cartFoods.get(i).getPrice();
        }
        return totalPrice;
    }

    public static void priceAdjust(){
        cartPrice.setText(Double.toString(grandTotal(cartFoods)));
    }

}
