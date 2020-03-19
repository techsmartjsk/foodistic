package com.futuristic.foodistic.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.futuristic.foodistic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.futuristic.foodistic.activity.MainActivity.cartFoods;


public class OrderActivity extends AppCompatActivity {

    TextView mOrderTotal;
    Button mSMS,mEmail, mPhone;
    Drawable back;

    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Punjabi Virsa");
        mToolbar.setTitleTextColor(getColor(R.color.colorPrimary));

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.super.onBackPressed();
            }
        });

        mOrderTotal = findViewById(R.id.order_price_total);
        mOrderTotal.setText(Double.toString(CartActivity.grandTotal(cartFoods)));



        mSMS =findViewById(R.id.sms_button);
        mEmail = findViewById(R.id.email_button);
        mPhone = findViewById(R.id.phone_button);

        mSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });

        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
