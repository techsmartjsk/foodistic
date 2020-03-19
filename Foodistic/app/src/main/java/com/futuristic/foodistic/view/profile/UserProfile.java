
package com.futuristic.foodistic.view.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.futuristic.foodistic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {
    private EditText name, phone_no, address;
    private Button save;
    private DatabaseReference userRef;
    private FirebaseAuth mauth;
    private String CurrentUserId;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (EditText) findViewById(R.id.UserName);
        phone_no = (EditText) findViewById(R.id.Phone_Number);
        address = (EditText) findViewById(R.id.Address);
        save = (Button) findViewById(R.id.save);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();
        CurrentUserId = mauth.getCurrentUser().getUid();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSettings();
            }
        });
        RetriveUserinfo();
}

    private void RetriveUserinfo() {
userRef.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Address")) && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("phone_no"))){
            String userAddress = dataSnapshot.child("Address").getValue().toString();
            String userName=dataSnapshot.child("name").getValue().toString();
            String userPhone=dataSnapshot.child("phone_no").getValue().toString();
            address.setText(userAddress);
            phone_no.setText(userPhone);
            name.setText(userName);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }


    private void UpdateSettings() {
        String setUsername = name.getText().toString();
        String setUserphone = phone_no.getText().toString();
        String setUserAddress=address.getText().toString();
        if(TextUtils.isEmpty(setUsername)){
            Toast.makeText(UserProfile.this,"Enter username",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setUserphone)){
            Toast.makeText(UserProfile.this,"Enter Status",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setUserAddress)){
            Toast.makeText(UserProfile.this,"Enter Status",Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String,String> profilemap=new HashMap<>();
            profilemap.put("uid",CurrentUserId);
            profilemap.put("name",setUsername);
            profilemap.put("phone_no",setUserphone);
            profilemap.put("Address",setUserAddress);
            userRef.child("Users").child(CurrentUserId).setValue(profilemap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserProfile.this,"Successful",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(UserProfile.this,"UnSuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
