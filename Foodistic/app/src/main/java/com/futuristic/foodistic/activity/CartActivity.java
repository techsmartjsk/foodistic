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

public class CartActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_cart);

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
                CartActivity.super.onBackPressed();
            }
        });

        cartPrice = findViewById(R.id.cart_price);
        cartPrice.setText(Double.toString(grandTotal(cartFoods)));

        recyclerviewCart = findViewById(R.id.cart_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(linearLayoutManager);
        recyclerviewCart.setNestedScrollingEnabled(false);
        recyclerviewCart.setAdapter(new CartAdapter(cartFoods, R.layout.recyclerview_cart, getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.order_button){
            OrderDetails();

        }

        return super.onOptionsItemSelected(item);
    }
    private void OrderDetails() {

        for(int i=0;i<cartFoods.size();i++) {
            String setTitle = cartFoods.get(i).getTitle();
            Double setPrice = cartFoods.get(i).getPrice();
            Double totalPrice = 0.0;
            totalPrice += cartFoods.get(i).getPrice();



            HashMap<String, String> profilemap = new HashMap<String, String>();
            profilemap.put("Title",setTitle);
            profilemap.put("Price", setPrice.toString());
            profilemap.put("Total",totalPrice.toString());
            OrdersRef.child("Orders").child(CurrentUserId).child(currentDate).child(setTitle).setValue(profilemap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this,R.style.AlertDialog);
                                builder.setTitle("Enter Address and Phone Number");
                                final EditText address=new EditText(CartActivity.this);
                                address.setHint("Address");
                                final EditText phone_no=new EditText(CartActivity.this);
                                address.setHint("Phone");
                                LinearLayout lay = new LinearLayout(CartActivity.this);
                                lay.setOrientation(LinearLayout.VERTICAL);
                                lay.addView(address);
                                lay.addView(phone_no);
                                builder.setView(lay);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String Addres =address.getText().toString();
                                        String phone= phone_no.getText().toString();
                                        if(TextUtils.isEmpty(Addres) || TextUtils.isEmpty(phone)){
                                            Toast.makeText(CartActivity.this,"Please Full details",Toast.LENGTH_SHORT).show();
                                        }else{
                                            for(i=0;i<cartFoods.size();i++) {
                                                SmsManager sms = SmsManager.getDefault();
                                                sms.sendTextMessage("+919625645572", null, "Order: " + cartFoods.get(i).getTitle() + " " + cartFoods.get(i).getPrice() + "  at" + " " + phone + " Call at :" + " " + Addres, null, null);
                                                Toast.makeText(CartActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CartActivity.this,TryMe.class);
                                                startActivity(intent);
                                            }
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


                            } else {
                                Toast.makeText(CartActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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
